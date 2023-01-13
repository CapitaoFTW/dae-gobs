<template>
	<b-container>
		<h4 class="text-center mb-5">Ocorrências</h4>
		<b-table
			:busy="$fetchState.pending"
			:current-page="currentPage"
			:fields="ocorrenciasFields"
			:items="ocorrencias"
			:per-page="perPage"
			bordered
			empty-text="Não existem ocorrências registadas."
			head-variant="dark"
			hover
			responsive
			show-empty
			striped
			@row-clicked="clickOcorrencia">
			<template #table-busy>
				<div class="text-center text-primary my-2">
					<b-spinner class="align-middle"></b-spinner>
					<strong>Carregando...</strong>
				</div>
			</template>
		</b-table>
		<b-row>
			<b-col>
				<b-button @click=$router.back()>Voltar</b-button>
				<b-button v-if="isCliente" to="/ocorrencias/create" variant="success">Registar nova ocorrência
				</b-button>
			</b-col>
			<b-col class="flex-grow-0">
				<b-pagination
					v-model="currentPage"
					:per-page="perPage"
					:total-rows="ocorrencias.length"/>
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
			currentPage: 1,
			perPage: 10,
			ocorrencias: [],
			ocorrenciasFields: [
				{
					key: 'id'
				},
				{
					key: 'estado',
					formatter: 'formatEstado'
				},
				{
					key: 'descricao'
				},
				{
					key: 'atualizado',
					formatter: 'formatDate'
				},
				{
					key: 'criado',
					formatter: 'formatDate'
				},
			],
		}
	},
	async fetch() {
		await this.$axios.$get('/api/ocorrencias')
			.then(data => this.ocorrencias = data)
			.catch(e => {
				console.error(`Erro ao obter ocorrencias: ${e}`)
				this.$root.$bvToast.toast('Erro ao obter ocorrencias.', {
					solid: true,
					title: 'Erro ao obter dados',
					toaster: 'b-toaster-top-center',
					variant: 'danger'
				});
				this.$router.back()
			});
		/*if (this.isCliente)
			await this.getClienteData();

		if (this.isFuncionario)
			await this.getFuncionarioData();*/
	},
	fetchOnServer: false,
	methods: {
		/*async getClienteData() {
			await this.$axios.$get('/api/ocorrencias')
				.then(data => this.ocorrencias = data)
				.catch(e => {
					console.error(`Erro ao obter ocorrencias: ${e}`)
					this.$root.$bvToast.toast('Erro ao obter ocorrencias.', {
						solid: true,
						title: 'Erro ao obter dados',
						toaster: 'b-toaster-top-center',
						variant: 'danger'
					});
					this.$router.push('/')
				});
		},
		async getFuncionarioData() {
			await this.$axios.$get('/api/ocorrencias')
				.then(data => this.ocorrencias = data)
				.catch(e => {
					console.error(`Erro ao obter ocorrencias: ${e}`)
					this.$root.$bvToast.toast('Erro ao obter ocorrencias.', {
						solid: true,
						title: 'Erro ao obter dados',
						toaster: 'b-toaster-top-center',
						variant: 'danger'
					});
					this.$router.push('/')
				});
		},*/
		formatDate(value) {
			if (!value)
				return '-'

			return new Date(value.replace('[UTC]', '')).toLocaleString()
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
					return 'Concluída'
				case 7:
					return 'Pedido inválido'
				default:
					return 'Algo correu mal! Contacte-nos'
			}
		},
		clickOcorrencia(item) {
			this.$router.push(`/ocorrencias/${item.id}`)
		}
	}
}
</script>
