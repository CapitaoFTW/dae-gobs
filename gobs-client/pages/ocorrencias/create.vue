<template>
	<b-container class="d-flex align-items-center flex-column h-75 mt-3">
		<h2>Registar uma nova ocorrência</h2>
		<b-form :validated="isFormValid" class="mt-5" @submit.prevent="create">
			<!--suppress JSUnresolvedVariable -->
			<b-overlay :show="$fetchState.pending" spinner-variant="primary">
				<b-form-group
					:invalid-feedback="invalidApoliceFeedback"
					:state="isApoliceValid"
					label="Apólice:"
					label-for="input-apolice">
					<b-form-select
						id="input-apolice"
						v-model="apoliceId"
						:options="apolices"
						:state="isApoliceValid"
						required
						text-field="seguradoraBem"
						value-field="id">
						<template #first>
							<b-form-select-option :value="null" disabled>-- Selecione a apólice --
							</b-form-select-option>
						</template>
					</b-form-select>
				</b-form-group>
				<template #overlay>
					<div class="text-center text-primary my-2">
						<b-spinner class="align-middle"></b-spinner>
						<strong>Carregando...</strong>
					</div>
				</template>
			</b-overlay>
			<b-form-group
				:invalid-feedback="invalidAssuntoFeedback"
				:state="isAssuntoValid"
				label="Introduzir o assunto:"
				label-for="input-assunto">
				<b-form-input
					id="input-assunto"
					v-model.trim="assunto"
					:state="isAssuntoValid"
					placeholder="Assunto"
					required
					type="text"/>
			</b-form-group>
			<b-form-group
				:invalid-feedback="invalidDescricaoFeedback"
				:state="isDescricaoValid"
				label="Descricao:"
				label-for="input-descricao">
				<b-form-textarea
					id="input-descricao"
					v-model="descricao"
					:state="isDescricaoValid"
					required
					rows="5"/>
			</b-form-group>
			<b-form-group
				label="Ficheiros (opcional):"
				label-for="input-files">
				<b-form-file
					id="input-files"
					v-model="ficheiros"
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
			<b-button @click=$router.back()>Voltar</b-button>
			<b-button type="reset" variant="danger">Limpar</b-button>
			<b-button :disabled="!isFormValid || sending" type="submit" variant="success" @click.prevent="create">
				Registar Ocorrência
			</b-button>
		</b-form>
	</b-container>
</template>
<!--suppress JSCheckFunctionSignatures -->
<script>
export default {
	computed: {
		invalidAssuntoFeedback() {
			const assunto = this.assunto;
			if (!assunto) {
				return null;
			}

			const assuntoLen = assunto.length;
			if (assuntoLen < 3) {
				return 'O assunto tem de ter pelo menos 3 caracteres.'
			}

			return '';
		},
		isAssuntoValid() {
			if (this.invalidAssuntoFeedback == null) {
				return null;
			}

			return this.invalidAssuntoFeedback === '';
		},
		invalidApoliceFeedback() {
			const apoliceId = this.apoliceId
			if (!apoliceId) {
				return null
			}

			if (!this.apolices.some(apolice => apolice.id === apoliceId)) {
				return 'Apólice não exitente.'
			}

			return ''
		},
		isApoliceValid() {
			if (this.invalidApoliceFeedback == null) {
				return null
			}

			return this.invalidApoliceFeedback === ''
		},
		invalidDescricaoFeedback() {
			const descricao = this.descricao
			if (!descricao) {
				return null
			}

			let descricaoLen = descricao.length
			if (descricaoLen < 15) {
				return 'A descrição tem de ter pelo menos 15 caracteres.'
			}

			return ''
		},
		isDescricaoValid() {
			if (this.invalidDescricaoFeedback === null) {
				return null
			}

			return this.invalidDescricaoFeedback === ''
		},
		isFormValid() {
			return this.isAssuntoValid && this.isApoliceValid && this.isDescricaoValid;
		}
	},
	data() {
		return {
			apolices: [],
			apoliceId: null,
			assunto: null,
			descricao: null,
			ficheiros: [],
			sending: false,
			uploadProgress: 0
		}
	},
	async fetch() {
		await this.$axios.$get('/api/apolices')
			.then(async data => {
				const apolices = data
				const seguradoras = {}

				for (const apolice of apolices) {
					// noinspection JSUnresolvedVariable
					const seguradoraId = apolice.seguradoraId
					const seguradora = seguradoras[seguradoraId]
					if (seguradora) {
						apolice.seguradoraBem = `Seguradora: ${seguradora} | Bem: ${apolice.bem}`
						continue
					}

					await this.$axios.$get(`/api/seguradoras/${seguradoraId}`)
						.then(data => {
							// noinspection JSUnresolvedVariable
							const seguradora = data.nome
							seguradoras[seguradoraId] = seguradora
							apolice.seguradoraBem = `Seguradora: ${seguradora} | Bem: ${apolice.bem}`
						})
				}

				this.apolices = apolices
			})
			.catch(e => {
				console.error(`Erro ao obter apolices: ${e}`)
				this.$root.$bvToast.toast('Erro ao obter apolices.', {
					solid: true,
					title: 'Erro ao obter dados',
					toaster: 'b-toaster-top-center',
					variant: 'danger'
				});
				//todo reload
			});
	},
	fetchOnServer: false,
	methods: {
		create() {
			this.sending = true

			const formData = new FormData()
			formData.append("apoliceId", this.apoliceId);
			formData.append("assunto", this.assunto);
			formData.append("descricao", this.descricao);
			this.ficheiros.forEach(file => formData.append("files", file));

			this.$axios.$post('/api/ocorrencias', formData, {
				headers: {
					"Content-Type": "multipart/form-data"
				},
				onUploadProgress: (event) => this.uploadProgress = Math.round(100 * event.loaded / event.total)
			})
				.then(data => this.ocorrenciaCriada(data))
				.catch(error => {
					this.sending = false
					let msg
					if (error.response && error.response.data)
						msg = error.response.data
					else
						msg = error.message

					console.error(`Erro ao criar ocorrência: ${msg}`)
					this.$bvToast.toast(msg, {
						solid: true,
						title: `Erro ao criar ocorrência`,
						toaster: 'b-toaster-top-center',
						variant: 'danger'
					})
				})
		},
		ocorrenciaCriada(data) {
			this.sending = false
			console.info(`Nova ocorrência criada. | Result: '${JSON.stringify(data)}'`)
			this.$root.$bvToast.toast('Nova ocorrência criada com sucesso.', {
				solid: true,
				title: `Ocorrência criada`,
				variant: 'success'
			})
			this.$router.push('/ocorrencias')//todo change
		}
	}
}
</script>

