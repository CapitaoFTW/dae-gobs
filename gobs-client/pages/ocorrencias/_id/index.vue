<template>
	<b-container>
		<h1 class="text-center mb-5">Ocorrência</h1>
		<!--suppress JSUnresolvedVariable -->
		<b-overlay :show="$fetchState.pending" spinner-variant="primary">
			<template #overlay>
				<div class="text-center text-primary my-2">
					<b-spinner class="align-middle"></b-spinner>
					<strong>Carregando...</strong>
				</div>
			</template>
			<b-col class="text-center">
				<b-row>
					<p>Assunto: {{ ocorrencia.assunto }}</p>
				</b-row>
				<b-row>
					<!--suppress JSUnresolvedVariable -->
					<p>Criado: {{ formatDate(ocorrencia.criado) }}</p>
				</b-row>
				<hr/>
				<b-row>
					<p>Mensagens:</p>
				</b-row>
				<b-row>
					<div v-for="item in mensages">
						<p> {{ `Eu: ${item.mensagem}` }}</p>
					</div>
				</b-row>
				<div v-if="ficheiros.length > 0">
					<hr/>
					<b-row>
						<p>Ficheiros:</p>
					</b-row>
					<b-row>
						<div v-for="item in ficheiros" @click="openFile(item.blob)">
							<b-button v-if="item.mimeType === 'application/pdf'"
									  class="mx-1"
									  variant="outline-primary">{{ item.nome }}
							</b-button>
							<object v-else
									:data="item.blob"
									:type="item.mimeType">
							</object>
						</div>
					</b-row>
				</div>
			</b-col>
		</b-overlay>
		<b-button class="mt-3" @click=$router.back()>Voltar</b-button>
	</b-container>
</template>

<script>

export default {
	computed: {
		id() {
			return this.$route.params.id
		}
	},
	data() {
		return {
			ficheiros: [],
			ficheirosLoading: true,
			mensages: [],
			ocorrencia: {},
		}
	},
	async fetch() {
		const files = []
		await this.$axios.$get(`/api/ocorrencias/${this.id}`)
			.then(async data => {
				this.ocorrencia = data
				const senders = {}

				const sortMsgs = (a, b) => {
					return this.toDate(a.criado).compare(this.toDate(b.criado))
				}

				// noinspection JSUnresolvedVariable
				for (const mensagem of this.ocorrencia.mensagens.sort(sortMsgs)) {
					const msg = {}
					msg.criado = mensagem.criado
					msg.id = mensagem.id
					msg.mensagem = mensagem.mensagem

					const senderId = mensagem.sender
					const sender = senders[senderId]
					if (!sender) {
						// noinspection JSUnresolvedVariable
						const url = senderId === 0 ? `/api/clientes/${this.ocorrencia.clienteId}` : `/api/funcionarios/${senderId}`
						await this.$axios.$get(url)
							.then(data => {
								// noinspection JSUnresolvedVariable
								const sender = data.nome
								senders[senderId] = sender
								msg.sender = sender
							})
					} else {
						msg.sender = sender
					}

					for (const ficheiro of mensagem.ficheiros) {
						const file = {}
						file.id = ficheiro.id
						file.nome = ficheiro.filename
						file.mimeType = ficheiro.mimeType

						files.push(file)
					}

					this.mensages.push(msg)
				}
			})
			.catch(e => {
				console.error(`Erro ao obter ocorrência: ${e}`)
				this.$root.$bvToast.toast('Erro ao obter ocorrência.', {
					solid: true,
					title: 'Erro ao obter dados',
					toaster: 'b-toaster-top-center',
					variant: 'danger'
				});
				this.$router.back()
			});

		if (files < 1) {
			this.ficheirosLoading = false
			return
		}

		for (const ficheiro of files) {
			this.$axios.$get(`/api/ficheiros/${ficheiro.id}`, {
				responseType: 'blob'
			})
				.then(data => {
					ficheiro.blob = URL.createObjectURL(new Blob([data], {type: ficheiro.mimeType}))
					this.ficheiros.push(ficheiro)
					if (this.ficheiros.length === files.length)
						this.ficheirosLoading = false
				})
				.catch(e => {
					console.error(`Erro ao obter ficheiro: ${e}`)
					this.$root.$bvToast.toast('Erro ao obter ficheiro.', {
						solid: true,
						title: 'Erro ao obter dados',
						toaster: 'b-toaster-top-center',
						variant: 'danger'
					});
				});
		}
	},
	fetchOnServer: false,
	methods: {
		toDate(value) {
			if (!value)
				return value

			return value.replace('[UTC]', '')
		},
		formatDate(value) {
			value = this.toDate(value)
			if (!value)
				return value

			return new Date(value).toLocaleDateString();
		},
		openFile(fileUrl) {
			console.log(fileUrl)
			open(fileUrl)
		}
	},
	unmounted() {
		for (const ficheiro of this.ficheiros) {
			console.log(ficheiro.blob)
			URL.revokeObjectURL(ficheiro.blob)
		}
	}
}
</script>
