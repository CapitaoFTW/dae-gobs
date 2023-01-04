<template>
	<b-container>
		<h1 class="text-center">Dashboard</h1>
		<b-row class="row-cols-2 flex-fill">
			<b-col class="h-100">
				<div class="border border-primary">
					<b-table
						:busy="apolicesLoading"
						:fields="apolicesFields"
						:items="apolices"
						bordered
						class="m-0"
						empty-text="Não existem apolices registadas."
						hover
						show-empty
						thead-class="d-none">
						<template #table-busy>
							<div class="text-center text-primary my-2">
								<b-spinner class="align-middle"></b-spinner>
								<strong>Carregando...</strong>
							</div>
						</template>
					</b-table>
				</div>
				<b-button to="/apolices" variant="primary">Ver detalhes</b-button>
			</b-col>
			<b-col class="h-100">
				<div class="border border-primary">
					<b-table
						:busy="ocorrenciasLoading"
						:items="ocorrencias"
						bordered
						class="m-0"
						empty-text="Não existem ocorrencias registadas."
						hover
						show-empty
						thead-class="d-none">
						<template #table-busy>
							<div class="text-center text-primary my-2">
								<b-spinner class="align-middle"></b-spinner>
								<strong>Carregando...</strong>
							</div>
						</template>
					</b-table>
				</div>
				<b-button to="/ocorrencias" variant="primary">Mais ocorrencias</b-button>
			</b-col>
		</b-row>
	</b-container>
</template>

<script>
export default {
	computed: {
		id() {
			return this.$auth.user.id;
		}
	},
	data() {
		return {
			apolices: [],
			apolicesFields: [
				{
					key: 'bem'
				},
				{
					key: 'premio'
				},
				{
					key: 'prazo',
					formatter: "formatDate"
				}
			],
			apolicesLoading: true,
			ocorrencias: [],
			ocorrenciasLoading: true
		}
	},
	async fetch() {
		const requestApolices = this.$axios.$get('/api/apolices')
			.then(data => {
				this.apolices = data
				this.apolicesLoading = false;
			})
			.catch(e => {
				console.error(`Erro ao obter apolices: ${e}`)
				this.$root.$bvToast.toast("Erro ao obter ocorrencias.", {
					solid: true,
					title: 'Erro ao obter dados',
					toaster: 'b-toaster-top-center',
					variant: 'danger'
				});
				this.$router.push('/')
			});
		const requestOcorrencias = this.$axios.$get(`/api/ocorrencias/cliente/${this.id}/waiting`)
			.then(data => {
				this.ocorrencias = data
				this.ocorrenciasLoading = false;
			})
			.catch(e => {
				console.error(`Erro ao obter ocorrencias: ${e}`)
				this.$root.$bvToast.toast("Erro ao obter ocorrencias.", {
					solid: true,
					title: 'Erro ao obter dados',
					toaster: 'b-toaster-top-center',
					variant: 'danger'
				});
				this.$router.push('/')
			});

		await Promise.all([requestApolices, requestOcorrencias])
	},
	fetchOnServer: false,
	methods: {
		formatDate(value) {
			return new Date(Date(value)).toLocaleString();
		}
	}
}
</script>
