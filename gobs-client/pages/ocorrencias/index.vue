<template>
	<b-container>
		<h4 class="text-center">Ocorrencias</h4>
		<b-table
			:busy="$fetchState.pending"
			:current-page="currentPage"
			:items="ocorrencias"
			:per-page="perPage"
			bordered
			empty-text="Não existem ocorrências registadas."
			head-variant="dark"
			hover
			responsive
			show-empty
			striped>
			<template #table-busy>
				<div class="text-center text-primary my-2">
					<b-spinner class="align-middle"></b-spinner>
					<strong>Carregando...</strong>
				</div>
			</template>
		</b-table>
		<b-row>
			<b-col>
				<b-button to="/">Return</b-button>
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
		id() {
			return this.$auth.user.id;
		}
	},
	data() {
		return {
			currentPage: 1,
			perPage: 10,
			ocorrencias: []
		}
	},
	async fetch() {
		await this.$axios.$get(`/api/ocorrencias/cliente/${this.id}`)
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
	fetchOnServer: false,
	methods: {
		formatDate(value) {
			return new Date(value.replace('[UTC]', '')).toLocaleString();
		}
	}
}
</script>
