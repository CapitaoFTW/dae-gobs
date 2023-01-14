<template>
	<b-container>
		<h1 class="text-center mb-2">Ocorrência</h1>
		<b-overlay :show="ocorrenciaLoading" spinner-variant="primary">
			<template #overlay>
				<div class="text-center text-primary my-2">
					<b-spinner class="align-middle"></b-spinner>
					<strong>Carregando...</strong>
				</div>
			</template>
			<b-col class="text-center">
				<!--suppress JSUnresolvedVariable -->
				<b-row>
					Estado: {{ formatEstado(ocorrencia.estado) }}
				</b-row>
				<b-row>
					Criado: {{ formatDate(ocorrencia.criado) }}
				</b-row>
				<b-row>
					Assunto: {{ ocorrencia.assunto }}
				</b-row>
				<div v-if="Object.keys(this.ficheiros).length > 0">
					<hr/>
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
				</div>
				<br/>
				<b-row class="border border-primary p-2 rounded mb-3 ">
					<b-col>
						Mensagens:
						<b-row v-for="item in mensagens" v-bind:key="item.id">
							{{ `${item.sender}: ${item.mensagem}` }}
						</b-row>
					</b-col>
				</b-row>
				<b-row>
					<!--suppress JSUnresolvedVariable -->
					<b-form v-if="ocorrencia.estado < 9" ref="msgForm" :validated="isFormValid" class="w-100"
							@submit.prevent="formSend">
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
						<b-overlay :show="estadoLoading" spinner-variant="primary">
							<b-form-group
								:invalid-feedback="invalidEstadoFeedback"
								:state="isEstadoValid"
								label="Alterar estado:"
								label-for="input-estado">
								<b-form-select
									id="input-estado"
									v-model="estadoAtual"
									:options="estados"
									:state="isEstadoValid"
									required
									text-field="nome"
									value-field="id"/>
							</b-form-group>
							<template #overlay>
								<div class="text-center text-primary my-2">
									<b-spinner class="align-middle"></b-spinner>
									<strong>Carregando...</strong>
								</div>
							</template>
						</b-overlay>
					</b-form>
				</b-row>
			</b-col>
		</b-overlay>
		<div>
			<b-button @click="$router.push('/ocorrencias')">Voltar</b-button>
			<b-button v-if="!ocorrenciaFechada" type="reset" variant="danger" @click="resetForms">Limpar</b-button>
			<b-button v-if="!ocorrenciaFechada" :disabled="!isFormValid || sending" type="submit" variant="success" @click.prevent="formSend">
				{{ invalidMensagemFeedback || isMensagemValid ? "Enviar Mensagem" : "Guardar estado" }}
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
		ocorrenciaFechada() {
			return this.ocorrencia.estado === 8 || this.ocorrencia.estado === 9
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
		invalidEstadoFeedback() {
			const estadoId = this.estadoAtual
			// noinspection JSUnresolvedVariable
			if (estadoId === this.ocorrencia.estado)
				return null

			if (!this.estados.some(estado => !estado.disabled && estado.id === estadoId)) {
				return 'Estado não exitente ou inválido.'
			}

			return ''
		},
		isEstadoValid() {
			if (this.invalidEstadoFeedback == null) {
				return null
			}

			return this.invalidEstadoFeedback === ''
		},
		isFormValid() {
			if ((this.isMensagemValid === null || this.isMensagemValid) && this.isEstadoValid)
				return true

			return this.isMensagemValid && (this.isEstadoValid === null || this.isEstadoValid);//todo change
		}
	},
	data() {
		return {
			estados: [],
			estadoAtual: null,
			estadoLoading: true,
			ficheiros: {},
			ficheirosLoading: true,
			mensagens: {},
			novosFicheiros: [],
			novaMensagem: null,
			ocorrencia: {},
			ocorrenciaLoading: true,
			sending: false,
			senders: {},
			uploadProgress: 0
		}
	},
	async fetch() {

		await this.$axios.$get(`/api/ocorrencias/${this.id}`)
			.then(async data => {
				this.ocorrencia = data
				// noinspection JSUnresolvedVariable
				this.estadoAtual = this.ocorrencia.estado

				this.$axios.$get('/api/ocorrencias/estados')
					.then(data => {
						for (const id in data) {
							const estadoId = Number(id)

							let disabled
							if (estadoId === this.estadoAtual) {
								disabled = false
							} else if (estadoId !== 0) {
								if (this.isCliente) {
									switch (estadoId) {
										case 0:
										case 1:
										case 3:
										case 9:
											disabled = true
											break
									}
								} else {
									switch (estadoId) {
										case 0:
										case 2:
										case 4:
										case 5:
										case 6:
											disabled = true
											break
									}
								}
							} else {
								disabled = true
							}

							this.estados.push({
								id: estadoId,
								disabled: disabled,
								nome: this.formatEstadoForm(estadoId)
							})
						}

						this.estadoLoading = false
					})
					.catch(e => {
						console.error(`Erro ao obter estados: ${e}`)
						this.$root.$bvToast.toast('Erro ao obter estados.', {
							solid: true,
							title: 'Erro ao obter dados',
							toaster: 'b-toaster-top-center',
							variant: 'danger'
						});
						this.$router.push('/ocorrencias')
					});

				const files = []
				await this.processMensagens(this.ocorrencia.mensagens, files)
				this.loadFiles(files)
				this.ocorrenciaLoading = false
			})
			.catch(e => {
				console.error(`Erro ao obter ocorrência: ${e}`)
				this.$root.$bvToast.toast('Erro ao obter ocorrência.', {
					solid: true,
					title: 'Erro ao obter dados',
					toaster: 'b-toaster-top-center',
					variant: 'danger'
				});
				this.$router.push('/ocorrencias')
			});
	},
	fetchOnServer: false,
	methods: {
		formSend() {
			if (this.isMensagemValid) {
				this.sendMsg()
			}
			else {
				this.sendEstado()
			}
		},
		sendMsg() {
			this.sending = true

			const formData = new FormData()
			formData.append('descricao', this.novaMensagem);

			const estado = this.estadoAtual
			// noinspection JSUnresolvedVariable
			if (estado !== this.ocorrencia.estado)
				formData.append('estado', estado)

			if (this.isCliente)
				this.novosFicheiros.forEach(file => formData.append('files', file));

			this.$axios.$post(`/api/ocorrencias/${this.id}/message`, formData, {
				headers: {
					'Content-Type': 'multipart/form-data'
				},
				onUploadProgress: (event) => this.uploadProgress = Math.round(100 * event.loaded / event.total)
			})
				.then(data => this.mensagemEnviada(data))
				.catch(error => {
					this.uploadProgress = 0
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
		sendEstado() {
			this.$axios.$patch(`/api/ocorrencias/${this.id}/estado`, {estado: this.estadoAtual})
				.then(data => this.mensagemEnviada(data))
				.catch(error => {
					let msg
					if (error.response && error.response.data)
						msg = error.response.data
					else
						msg = error.message

					console.error(`Erro ao atualizar estado: ${msg}`)
					this.$bvToast.toast(msg, {
						solid: true,
						title: `Erro ao atualizar estado`,
						toaster: 'b-toaster-top-center',
						variant: 'danger'
					})
				})
		},
		async mensagemEnviada(data) {
			this.sending = false
			this.uploadProgress = 0
			this.resetForms()

			this.ocorrencia = data
			// noinspection JSUnresolvedVariable
			this.estadoAtual = this.ocorrencia.estado

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
		formatEstado(value) {
			switch (value) {
				case 0:
				case 2:
				case 5:
				case 6:
					return 'Em processo'
				case 1:
				case 3:
				case 4:
					return 'Aguardando utilizador'
				case 7:
					return 'Pagamento'
				case 8:
					return 'Concluída'
				case 9:
					return 'Inválida'
				default:
					return 'Algo correu mal! Contacte-nos'
			}
		},
		formatEstadoForm(value) {
			switch (value) {
				case 0:
					return 'Criada';
				case 1:
					return 'Dados em falta';
				case 2:
					return 'Para análise';
				case 3:
					return 'Para reparação';
				case 4:
					return 'Em reparação';
				case 5:
					return 'Impossivel reparar';
				case 6:
					return 'Reparado';
				case 7:
					return 'Em pagammento';
				case 8:
					return 'Concluida';
				case 9:
					return 'Invalida';
				default:
					return `Unknown (${value})`;
			}
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
				} else {
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
