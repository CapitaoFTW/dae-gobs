<template>
	<b-container>
		<h1 class="text-center mb-5">Dashboard</h1>
		<b-button>Reload</b-button><!-- todo change location adding reload function use variable to show-->
		<b-row :class="{ 'row-cols-2': isCliente }" class="flex-fill text-center">
			<b-col v-if="isCliente" class="h-100">
				<b-row class="border border-primary mx-1">
					<h4 class="my-2 w-100">Apólices</h4>
					<b-table
						:busy="apolicesLoading"
						:fields="apolicesFields"
						:items="apolices"
						bordered
						class="m-0"
						empty-text="Não existem apolices registadas."
						hover
						show-empty
						thead-class="d-none"
						@row-clicked="clickApolice">
						<template v-slot:cell(dataRange)="row">
							{{ `${formatDate(row.item.criado)} - ${formatDate(row.item.prazo)}` }}
						</template>
						<template #table-busy>
							<div class="text-center text-primary my-2">
								<b-spinner class="align-middle"></b-spinner>
								<strong>Carregando...</strong>
							</div>
						</template>
					</b-table>
				</b-row>
				<b-button class="mt-2" to="/apolices" variant="primary">Ver apólices</b-button>
			</b-col>
			<b-col class="h-100">
				<b-row class="border border-primary mx-1">
					<h4 class="my-2 w-100">Ocorrências</h4>
					<b-table
						:busy="ocorrenciasLoading"
						:fields="ocorrenciasFields"
						:items="ocorrencias"
						bordered
						class="m-0"
						empty-text="Não existem ocorrências registadas."
						hover
						show-empty
						thead-class="d-none"
						@row-clicked="clickOcorrencia">
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
					<b-button v-if="isCliente" class="mt-2" to="/ocorrencias/create" variant="success">Registar nova
						ocorrência
					</b-button>
				</b-row>
			</b-col>
		</b-row>
	</b-container>
</template>

<script>
export default {
	computed: {
		isCliente() {
			return this.$auth.user.roles.includes('Cliente');
		},
		isFuncionario() {
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
					key: 'dataRange'
				}
			],
			apolicesLoading: true,
			needReload: false,
			ocorrencias: [],
			ocorrenciasFieldsClientes: [
				{
					key: 'seguradora.nome'
				},
				{
					key: 'apolice.bem'
				},
				{
					key: 'estado',
					formatter: 'formatEstado'
				},
			],
			ocorrenciasFieldsFuncionario: [
				{
					key: 'estado',
					formatter: 'formatEstado'
				},
				{
					key: 'atualizado',
					formatter: 'formatDateTime'
				}
			],
			ocorrenciasLoading: true
		}
	},
	async fetch() {
		if (this.isCliente)
			await this.getClienteData();

		if (this.isFuncionario)
			await this.getFuncionarioData();
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
					this.$root.$bvToast.toast('Erro ao obter apólices.', {
						solid: true,
						title: 'Erro ao obter dados',
						toaster: 'b-toaster-top-center',
						variant: 'danger'
					});
					this.needReload = true
				});
			const requestOcorrencias = this.$axios.$get('/api/ocorrencias/recent?limit=5')
				.then(data => {
					this.ocorrencias = data
					console.log(data)
					this.ocorrenciasLoading = false;
				})
				.catch(e => {
					console.error(`Erro ao obter ocorrencias: ${e}`)
					this.$root.$bvToast.toast('Erro ao obter ocorrências.', {
						solid: true,
						title: 'Erro ao obter dados',
						toaster: 'b-toaster-top-center',
						variant: 'danger'
					});
					this.needReload = true
				});

			await Promise.all([requestApolices, requestOcorrencias]);
		},
		async getFuncionarioData() {
			await this.$axios.$get('/api/ocorrencias/recent?limit=5')
				.then(data => {
					this.ocorrencias = data
					this.ocorrenciasLoading = false;
				})
				.catch(e => {
					console.error(`Erro ao obter ocorrencias: ${e}`)
					this.$root.$bvToast.toast('Erro ao obter ocorrências.', {
						solid: true,
						title: 'Erro ao obter dados',
						toaster: 'b-toaster-top-center',
						variant: 'danger'
					});
					this.needReload = true
				});
		},
		formatDate(value) {
			if (!value)
				return value

			return new Date(value.replace('[UTC]', '')).toLocaleDateString();
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
		},
		clickApolice(item) {
			this.$router.push(`/apolices/${item.id}`)
		},
		clickOcorrencia(item) {
			this.$router.push(`/ocorrencias/${item.id}`)
		}
	}
}
</script>
