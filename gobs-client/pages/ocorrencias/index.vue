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
		},
		ocorrenciasFields() {
			return this.isFuncionario ? this.ocorrenciasFieldsFuncionario : this.ocorrenciasFieldsClientes
		}
	},
	data() {
		return {
			currentPage: 1,
			perPage: 10,
			ocorrencias: [],
			ocorrenciasFieldsFuncionario: [
				{
					key: 'cliente'
				},
				{
					key: 'bem',
					label: 'Bem'
				},
				{
					key: 'premio',
					label: 'Premio',
					formatter: 'formatMoney'
				},
				{
					key: 'assunto'
				},
				{
					key: 'estado',
					formatter: 'formatEstado'
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
			ocorrenciasFieldsClientes: [
				{
					key: 'bem',
					label: 'Bem'
				},
				{
					key: 'premio',
					label: 'Premio',
					formatter: 'formatMoney'
				},
				{
					key: 'assunto'
				},
				{
					key: 'estado',
					formatter: 'formatEstado'
				},
				{
					key: 'atualizado',
					formatter: 'formatDate'
				},
				{
					key: 'criado',
					formatter: 'formatDate'
				},
				/*
								{
									key: 'actions'
								}
							]
				*/
			]
		}
	},
	async fetch() {
		await this.$axios.$get('/api/ocorrencias')
			.then(async data => {
				this.ocorrencias = data
				const apolices = {}
				const clientes = {}

				for (const ocorrencia of this.ocorrencias) {
					// noinspection JSUnresolvedVariable
					const apoliceId = ocorrencia.apoliceId
					if (!(apoliceId in apolices)) {
						await this.$axios.$get(`/api/apolices/${apoliceId}`)
							.then(data => {
								const bem = data.bem
								const premio = data.premio

								apolices[apoliceId] = [bem, premio]
								ocorrencia.bem = bem
								ocorrencia.premio = premio
							})
					} else {
						const [bem, premio] = apolices[apoliceId]
						ocorrencia.bem = bem
						ocorrencia.premio = premio
					}

					if (this.isCliente)
						continue

					const clienteId = ocorrencia.clienteId
					const cliente = clientes[clienteId]
					if (!cliente) {
						await this.$axios.$get(`/api/clientes/${clienteId}`)
							.then(data => {
								const msg = `${data.nome}(${data.nif})`
								clientes[clienteId] = msg
								ocorrencia.cliente = msg
							})
					} else {
						ocorrencia.cliente = cliente
					}
				}
			})
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
	},
	fetchOnServer: false,
	methods: {
		formatDate(value) {
			if (!value)
				return '-'

			return new Date(value.replace('[UTC]', '')).toLocaleString()
		},
		formatMoney(value) {
			return `${value}€`
		},
		formatEstado(value) {
			switch (value) {
				case 1:
				case 2:
				case 4:
				case 5:
				case 7:
					return 'Em processo'
				case 3:
				case 8:
					return 'Aguardando utilizador'
				case 9:
					return 'Concluída'
				case 10:
					return 'Inválida'
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
