<template>
	<b-container v-if="isCliente">
		<h1 class="text-center mb-5">Dashboard</h1>
		<b-row class="row-cols-2 flex-fill text-center">
			<b-col class="h-100">
				<div class="border border-primary">
					<h4 class="my-2">Apólices</h4>
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
				<b-button class="mt-2" to="/apolices" variant="primary">Ver apólices</b-button>
			</b-col>
			<b-col class="h-100">
				<b-row class="border border-primary">
					<h4 class="my-2">Ocorrências</h4>
					<b-table
						:busy="ocorrenciasLoading"
						:fields="ocorrenciasFields"
						:items="ocorrencias"
						bordered
						class="m-0"
						empty-text="Não existem ocorrências registadas."
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
				</b-row>
				<b-row class="d-flex justify-content-around">
					<b-button class="mt-2" to="/ocorrencias" variant="primary">Ver ocorrências</b-button>
					<b-button class="mt-2" to="/ocorrencias/create" variant="success">Registar nova ocorrência
					</b-button>
				</b-row>
			</b-col>
		</b-row>
	</b-container>
	<b-container v-else-if="isFuncinario">
		<h1 class="text-center mb-5">Dashboard</h1>
		<h2 class="text-center mb-5">This is the funcionario dashboard</h2>
	</b-container>
	<b-container v-else>
		<h1 class="text-center mb-5">Algo correu mal, usário inválido.</h1>
		<h2 class="text-center mb-5">Por favor contacte-nos.</h2>
	</b-container>
</template>

<script>
export default {
	computed: {
		isCliente() {
			return this.$auth.user.roles.includes('Cliente');
		},
		isFuncinario() {
			return this.$auth.user.roles.includes('Funcionario');
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
					key: 'premio',
					formatter: 'formatMoney'
				},
				{
					key: 'prazo',
					formatter: 'formatDate'
				}
			],
			apolicesLoading: true,
			ocorrencias: [],
			ocorrenciasFields: [
				{
					key: 'estado',
					formatter: 'formatEstado'
				},
				{
					key: 'atualizado',
					formatter: 'formatDate'
				}
			],
			ocorrenciasLoading: true
		}
	},
	async fetch() {
		if (this.isCliente)
			await this.getClienteData();
	},
	fetchOnServer: false,
	methods: {
		async getClienteData() {
			const requestApolices = this.$axios.$get('/api/apolices/recent?limit=5')
				.then(data => {
					this.apolices = data
					this.apolicesLoading = false;
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
			const requestOcorrencias = this.$axios.$get('/api/ocorrencias/recent?limit=5')
				.then(data => {
					this.ocorrencias = data
					this.ocorrenciasLoading = false;
				})
				.catch(e => {
					console.error(`Erro ao obter ocorrencias: ${e}`)
					this.$root.$bvToast.toast('Erro ao obter ocorrencias.', {
						solid: true,
						title: 'Erro ao obter dados',
						toaster: 'b-toaster-top-center',
						variant: 'danger'
					});
					//todo reload
				});

			await Promise.all([requestApolices, requestOcorrencias]);
		},
		formatDate(value) {
			return new Date(value.replace('[UTC]', '')).toLocaleString();
		},
		formatMoney(value) {
			return `${value}€`
		},
		formatEstado(value) {
			switch (value) {
				case 1:
				case 2:
				case 4:
					return 'Em processo'
				case 3:
				case 5:
					return 'Aguardando utilizador'
				case 6:
					return 'Concluida'
				case 7:
					return 'Pedido inválido'
				default:
					return 'Algo correu mal! Contacte-nos'
			}
		}
	}
}
</script>
