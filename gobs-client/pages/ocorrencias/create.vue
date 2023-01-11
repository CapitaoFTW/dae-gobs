<template>
	<b-container class="w-50 py-3">
		<b-row class="justify-content-center">
			<h2>Registar Nova Ocorrência</h2>
		</b-row>
		<form :disabled="!isFormValid" @submit.prevent="create">
			<b-form-group id="id" label="Id" label-for="id"
						  :invalid-feedback="invalidIdFeedback"
						  :state="isIdValid">
				<b-input v-model.trim="id" :state="isIdValid" trim/>
			</b-form-group>
			<b-form-group id="estado" label="Estado" label-for="estado"
						  :invalid-feedback="invalidEstadoFeedback"
						  :state="isEstadoValid">
				<b-input v-model.trim="estado" :state="isEstadoValid"/>
			</b-form-group>
			<b-form-group id="descricao" label="Descricao" label-for="descricao"
						  :invalid-feedback="invalidDescricaoFeedback"
						  :state="isDescricaoValid">
				<b-textarea v-model.trim="descricao" :state="isDescricaoValid"/>
			</b-form-group>
			<b-form-group id="atualizado" label="Atualizado" label-for="atualizado"
						  :invalid-feedback="invalidAtualizadoFeedback"
						  :state="isAtualizadoValid">
				<b-input v-model.trim="atualizado" :state="isAtualizadoValid"/>
			</b-form-group>
			<b-form-group id="criado" label="Criado" label-for="criado"
						  :invalid-feedback="invalidCriadoFeedback"
						  :state="isCriadoValid">
				<b-input v-model.trim="criado" type="radio" :state="isCriadoValid"/>
			</b-form-group>
			<b-form-group label="Apolice" label-for="apoliceId"
						  :state="isApoliceValid">
				<b-select
					v-model="apoliceId"
					:options="apolices"
					:state="isApoliceValid"
					required
					value-field="id"
					text-field="name"
				>
					<template v-slot:first>
						<option :value="null" disabled>-- Seleciona a Apólice --</option>
					</template>
				</b-select>
			</b-form-group>
			<p v-show="errorMsg" class="text-danger">
				{{ errorMsg }}
			</p>
			<div class="d-flex justify-content-between">
				<b-button to="/">Return</b-button>
				<button class="btn btn-success" @click.prevent="create">Registar Ocorrência</button>
				<button class="btn btn-danger" type="reset" @click=reset>Limpar</button>
			</div>
		</form>
	</b-container>
</template>
<script>
export default {
	data() {
		return {
			id: null,
			estado: null,
			descricao: null,
			atualizado: null,
			criado: null,
			apoliceId: null,
			apolices: [],
			errorMsg: false
		}
	},

	computed: {
		invalidIdFeedback() {
			if (!this.id) {
				return null
			}

			return ''
		},

		isIdValid() {
			if (this.invalidIdFeedback === null) {
				return null
			}
			return this.invalidIdFeedback === ''
		},

		invalidEstadoFeedback() {
			if (!this.estado) {
				return null
			}
			let estadoLen = this.estado.length
			if (estadoLen < 10) {
				return 'O estado tem de ter mais de 10 caracteres.'
			}
			return ''
		},

		isEstadoValid() {
			if (this.invalidEstadoFeedback === null) {
				return null
			}
			return this.invalidEstadoFeedback === ''
		},

		invalidDescricaoFeedback() {
			if (!this.descricao) {
				return null
			}
			let descricaoLen = this.descricao.length
			if (descricaoLen < 10) {
				return 'A descrição tem de ter mais de 10 caracteres.'
			}
			return ''
		},

		isDescricaoValid() {
			if (this.invalidDescricaoFeedback === null) {
				return null
			}
			return this.invalidDescricaoFeedback === ''
		},

		invalidAtualizadoFeedback() {
			if (!this.atualizado) {
				return null
			}
			return ''
		},

		isAtualizadoValid() {
			if (this.invalidAtualizadoFeedback === null) {
				return null
			}
			return this.invalidAtualizadoFeedback === ''
		},

		invalidCriadoFeedback() {
			if (!this.criado) {
				return null
			}
			return ''
		},

		isCriadoValid() {
			if (this.invalidCriadoFeedback === null) {
				return null
			}
			return this.invalidCriadoFeedback === ''
		},

		isApoliceValid() {
			if (!this.apoliceId) {
				return null;
			}
			return this.apolices.some((apolice) => this.apoliceId === apolice.id);
		},

		isFormValid() {
			if (!this.isIdValid) {
				return false
			}
			if (!this.isEstadoValid) {
				return false
			}
			if (!this.isDescricaoValid) {
				return false
			}
			if (!this.isAtualizadoValid) {
				return false
			}
			if (!this.isCriadoValid) {
				return false
			}
			if (!this.isApoliceValid) {
				return false;
			}
			return true
		}
	},

	methods: {
		reset() {
			this.errorMsg = false
		},

		create() {
			this.$axios.$post('/api/ocorrencias', {
				id: this.id,
				estado: this.estado,
				descricao: this.descricao,
				atualizado: this.atualizado,
				criado: this.criado,
				apoliceId: this.apoliceId,
			})
				.then(() => {
					this.$router.push('/ocorrencias')
				})

				.catch(error => {
					this.errorMsg = error.response.data
				})
		}
	}
}
</script>

