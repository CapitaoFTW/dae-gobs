<template>
	<b-container class="d-flex align-items-center flex-column h-75 mt-3">
		<h2>Registar uma nova ocorrência</h2>
		<b-form :validated="isFormValid" class="mt-5" @submit.prevent="create">
			<b-overlay :show="$fetchState.pending" spinner-variant="primary">
				<b-form-group
					:invalid-feedback="invalidApoliceFeedback"
					:state="isApoliceValid"
					label="Apolice:"
					label-for="input-apolice">
					<b-form-select
						id="input-apolice"
						v-model="apoliceId"
						:options="apolices"
						:state="isApoliceValid"
						required
						text-field="nome"
						value-field="id">
						<template #first>
							<b-form-select-option :value="null" disabled>-- Selecione a apólice --</b-form-select-option>
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
			<b-button :disabled="!isFormValid || creating" type="submit" variant="success" @click.prevent="create">
				Registar Ocorrência
			</b-button>
		</b-form>
	</b-container>
</template>
<!--suppress JSCheckFunctionSignatures -->
<script>
export default {
	computed: {
		invalidApoliceFeedback() {
			const apoliceId = this.apoliceId
			if (!apoliceId) {
				return null
			}

			if (!this.apolices.some(apolice => apolice.id === apoliceId)) {
				return 'The course does not exists.'
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
			return this.isApoliceValid && this.isDescricaoValid;
		}
	},
	data() {
		return {
			apolices: [],
			apoliceId: null,
			creating: false,
			descricao: null,
			ficheiros: [],
			uploadProgress: 0
		}
	},
	async fetch() {
		await this.$axios.$get('/api/apolices')
			.then(data => {
				if (!data)
					return

				data.forEach(i => {
					// noinspection JSUnresolvedVariable
					if (!i.seguradora)
						return i

					// noinspection JSUnresolvedVariable
					i.nome = `Seguradora: ${i.seguradora.nome} | Bem: ${i.bem}`
					return i
				})
				this.apolices = data
			})
			.catch(e => {
				console.error(`Erro ao obter apolices: ${e}`)
				this.$root.$bvToast.toast('Erro ao obter apolices.', {
					solid: true,
					title: 'Erro ao obter dados',
					toaster: 'b-toaster-top-center',
					variant: 'danger'
				});
				this.$router.push('/')
			});
	},
	fetchOnServer: false,
	methods: {
		create() {
			this.creating = true

			const formData = new FormData()
			formData.append("apoliceId", this.apoliceId);
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
					this.creating = false
					let msg
					if (error.response && error.response.data)
						msg = error.response.data
					else
						msg = error.message

					console.error(`Erro ao criar apólice: ${msg}`)
					this.$bvToast.toast(msg, {
						solid: true,
						title: `Erro ao criar apólice`,
						toaster: 'b-toaster-top-center',
						variant: 'danger'
					})
				})
		},
		ocorrenciaCriada(data) {
			this.creating = false
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

