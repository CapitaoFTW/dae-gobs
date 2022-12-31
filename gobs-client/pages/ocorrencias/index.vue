<template>
	<b-container>
		<h4>Ocorrencias</h4>
		<b-table
			:busy="$fetchState.pending"
			:current-page="currentPage"
			empty-text="Não existem ocorrências registadas."
			:items="ocorrencias"
			:per-page="perPage"
			bordered
			hover
			head-variant="dark"
			responsive
			show-empty
			striped>
			<template #table-busy>
				<div class="text-center text-primary my-2">
					<b-spinner class="align-middle"></b-spinner>
					<strong>Loading...</strong>
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
			const u = this.$auth.user;
			// noinspection JSUnresolvedVariable
			const nif = u.nif;
			if (nif)
				return nif;
			else// noinspection JSUnresolvedVariable
				return u.username;
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
				this.$root.$bvToast.toast("Erro ao obter ocorrencias.", {
					solid: true,
					title: 'Erro ao obter dados',
					toaster: 'b-toaster-top-center',
					variant: 'danger'
				});
				this.$router.push('/')
			});
	},
	fetchOnServer: false,
}
</script>
