<template>
	<b-container>
		<h1 class="text-center mb-2">Ocorrência</h1>
		<!--suppress JSUnresolvedVariable -->
		<b-overlay :show="$fetchState.pending" spinner-variant="primary">
			<template #overlay>
				<div class="text-center text-primary my-2">
					<b-spinner class="align-middle"></b-spinner>
					<strong>Carregando...</strong>
				</div>
			</template>
			<b-col class="text-center">
				<b-row>Assunto: {{ ocorrencia.assunto }}</b-row>
				<b-row><!--suppress JSUnresolvedVariable -->Criado: {{ formatDate(ocorrencia.criado) }}</b-row>
				<hr/>
				<div v-if="Object.keys(this.ficheiros).length > 0">
					<b-row>Ficheiros:</b-row>
					<b-row>
						<div v-for="item in ficheiros" @click="openFile(item.url)">
							<b-button v-if="item.mimeType === 'application/pdf'"
									  class="mx-1"
									  variant="outline-primary">{{ item.nome }}
							</b-button>
							<object v-else
									:data="item.url"
									:type="item.mimeType">
							</object>
						</div>
					</b-row>
					<br/>
				</div>
				<b-row class="border border-primary p-2 rounded">
					<b-col>
						Mensagens:
						<b-row v-for="item in mensagens">
							{{ `${item.sender}: ${item.mensagem}` }}
						</b-row>
					</b-col>
				</b-row>
				<b-row>
				<!--suppress JSUnresolvedVariable -->
				<b-form ref="msgForm" :validated="isFormValid" class="mt-3 w-100" @submit.prevent="sendMsg" v-if="ocorrencia.estado < 7">
					<!--suppress JSUnresolvedVariable -->
					<b-form-group
						:invalid-feedback="invalidMensagemFeedback"
						:state="isMensagemValid"
						label="Enviar mensagem:"
						label-for="input-mensagem">
						<b-form-textarea
							id="input-mensagem"
							v-model="novaMensagem"
							:state="isMensagemValid"
							required
							rows="5"/>
					</b-form-group>
					<b-form-group v-if="isCliente"
						label="Ficheiros (opcional):"
						label-for="input-files">
						<b-form-file
							id="input-files"
							v-model="novosFicheiros"
							drop-placeholder="Largar os ficheiros aqui."
							multiple
							no-traverse
							placeholder="Nenhum ficheiro"/>
						<b-progress
							v-if="uploadProgress > 0"
							:value="uploadProgress"
							animated
							class="mt-3"
							show-progress
							striped></b-progress>
					</b-form-group>
				</b-form>
				</b-row>
			</b-col>
		</b-overlay>
		<div>
			<b-button @click=$router.back()>Voltar</b-button>
			<b-button @click="resetForms" type="reset" variant="danger">Limpar</b-button>
			<b-button :disabled="!isFormValid || sending" type="submit" variant="success" @click.prevent="sendMsg">
				Enviar mensagem
			</b-button>
		</div>
	</b-container>
</template>

<script>

export default {
	computed: {
		isCliente() {
			// noinspection JSUnresolvedVariable
			return this.$auth.user.roles.includes('Cliente');
		},
		isFuncionario() {
			// noinspection JSUnresolvedVariable
			return this.$auth.user.roles.includes('Funcionario');
		},
		id() {
			return this.$route.params.id
		},
		invalidMensagemFeedback() {
			const mensagem = this.novaMensagem
			if (!mensagem) {
				return null
			}

			let mensagemLen = mensagem.length
			if (mensagemLen < 15) {
				return 'A mensagem tem de ter pelo menos 15 caracteres.'
			}

			return ''
		},
		isMensagemValid() {
			if (this.invalidMensagemFeedback === null) {
				return null
			}

			return this.invalidMensagemFeedback === ''
		},
		isFormValid() {
			//&& true to remove warning
			return this.isMensagemValid && true;
		}
	},
	data() {
		return {
			ficheiros: {},
			ficheirosLoading: true,
			mensagens: {},
			novosFicheiros: [],
			novaMensagem: null,
			ocorrencia: {},
			sending: false,
			senders: {},
			uploadProgress: 0
		}
	},
	async fetch() {
		const files = []
		await this.$axios.$get(`/api/ocorrencias/${this.id}`)
			.then(async data => {
				this.ocorrencia = data
				// noinspection JSUnresolvedVariable
				await this.processMensagens(this.ocorrencia.mensagens, files)
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

		this.loadFiles(files)
	},
	fetchOnServer: false,
	methods: {
		sendMsg() {
			this.sending = true

			const formData = new FormData()
			formData.append("descricao", this.novaMensagem);

			if (this.isCliente)
				this.novosFicheiros.forEach(file => formData.append("files", file));

			this.$axios.$put(`/api/ocorrencias/message/${this.id}`, formData, {
				headers: {
					"Content-Type": "multipart/form-data"
				},
				onUploadProgress: (event) => this.uploadProgress = Math.round(100 * event.loaded / event.total)
			})
				.then(data => this.mensagemEnviada(data))
				.catch(error => {
					this.sending = false
					let msg
					if (error.response && error.response.data)
						msg = error.response.data
					else
						msg = error.message

					console.error(`Erro ao enviar mensagem: ${msg}`)
					this.$bvToast.toast(msg, {
						solid: true,
						title: `Erro ao enviar mensagem`,
						toaster: 'b-toaster-top-center',
						variant: 'danger'
					})
				})
		},
		async mensagemEnviada(data) {
			this.sending = false
			this.uploadProgress = 0
			this.resetForms()

			const files = []
			await this.processMensagens(data.mensagens, files)
			this.loadFiles(files)
		},
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
			open(fileUrl)
		},
		resetForms() {
			this.$refs.msgForm.reset()
			this.novosFicheiros = []//to reset validation
			this.novaMensagem = null//to reset validation
		},
		async processMensagens(mensagens, files) {
			const sortMsgs = (a, b) => {
				return this.toDate(a.criado) - this.toDate(b.criado)
			}

			// noinspection JSUnresolvedVariable
			for (const mensagem of mensagens.sort(sortMsgs)) {
				if (mensagem.id in this.mensagens) {
					continue
				}

				const msg = {
					criado: mensagem.criado,
					id: mensagem.id,
					mensagem: mensagem.mensagem
				}

				const senderId = mensagem.sender
				if (senderId === 0) {
					msg.sender = this.isCliente ? 'Eu' : 'Cliente'
				}
				else {
					msg.sender = this.isCliente ? 'Gobs' : 'Eu'
				}

				for (const ficheiro of mensagem.ficheiros) {
					const file = {
						id: ficheiro.id,
						nome: ficheiro.filename,
						mimeType: ficheiro.mimeType
					}
					files.push(file)
				}

				this.mensagens[mensagem.id] = msg
			}
		},
		loadFiles(files) {
			if (files < 1) {
				this.ficheirosLoading = false
				return
			}

			for (const file of files) {
				if (file.id in this.ficheiros) {
					continue
				}

				this.$axios.$get(`/api/ficheiros/${file.id}`, {
					responseType: 'blob'
				})
					.then(data => {
						const blob = new Blob([data], {type: file.mimeType})
						file.url = URL.createObjectURL(blob)
						this.ficheiros[file.id] = file

						if (Object.keys(this.ficheiros).length === files.length)
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
		}
	},
	beforeDestroy() {
		for (const [, ficheiro] of Object.entries(this.ficheiros)) {
			URL.revokeObjectURL(ficheiro.url)
		}
	}
}
</script>
