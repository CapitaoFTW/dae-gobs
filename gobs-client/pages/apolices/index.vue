<template>
	<b-container>
		<h4 class="text-center mb-5">Apolices</h4>
		<b-table
			:busy="$fetchState.pending"
			:current-page="currentPage"
			:fields="apolicesFields"
			:items="apolices"
			:per-page="perPage"
			bordered
			empty-text="Não existem apolices registadas."
			head-variant="dark"
			hover
			responsive
			show-empty
			striped
			@row-clicked="clickApolice">
			<template #table-busy>
				<div class="text-center text-primary my-2">
					<b-spinner class="align-middle"></b-spinner>
					<strong>Carregando...</strong>
				</div>
			</template>
		</b-table>
		<b-row>
			<b-col>
				<b-button @click="$router.push('/')">Voltar</b-button>
			</b-col>
			<b-col class="flex-grow-0">
				<b-pagination
					v-model="currentPage"
					:per-page="perPage"
					:total-rows="apolices.length"/>
			</b-col>
		</b-row>
	</b-container>
</template>

<script>
export default {
	data() {
		return {
			apolices: [],
			apolicesFields: [
				{
					key: 'seguradora',
					label: 'Seguradora'
				},
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
				},
				{
					key: 'criado',
					formatter: 'formatDate'
				}
			],
			currentPage: 1,
			perPage: 10
		}
	},
	async fetch() {
		await this.$axios.$get('/api/apolices')
			.then(async data => {
				this.apolices = data
				const seguradoras = {}

				for (const apolice of this.apolices) {
					// noinspection JSUnresolvedVariable
					const seguradoraId = apolice.seguradoraId
					const seguradora = seguradoras[seguradoraId]
					if (seguradora) {
						apolice.seguradora = seguradora
						continue
					}

					await this.$axios.$get(`/api/seguradoras/${seguradoraId}`)
						.then(data => {
							// noinspection JSUnresolvedVariable
							const seguradora = data.nome
							seguradoras[seguradoraId] = seguradora
							apolice.seguradora = seguradora
						})
				}
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
		formatDate(value) {
			return new Date(value.replace('[UTC]', '')).toLocaleString();
		},
		formatMoney(value) {
			return `${value}€`
		},
		clickApolice(item) {
			this.$router.push(`/apolices/${item.id}`)
		}
	}
}
</script>
