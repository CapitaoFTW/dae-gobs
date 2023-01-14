<template>
	<b-container>
		<h1 class="text-center mb-5">Apólice</h1>
		<!--suppress JSUnresolvedVariable -->
		<b-overlay :show="$fetchState.pending" spinner-variant="primary">
			<p>Seguradora: {{ apolice.seguradora }}</p>
			<p>Bem: {{ apolice.bem }}</p>
			<p>Premio: {{ apolice.premio }}€</p>
			<!--suppress JSUnresolvedVariable -->
			<p>Expira: {{ formatDate(apolice.prazo) }}</p>
			<!--suppress JSUnresolvedVariable -->
			<p>Criado: {{ formatDate(apolice.criado) }}</p>
			<template #overlay>
				<div class="text-center text-primary my-2">
					<b-spinner class="align-middle"></b-spinner>
					<strong>Carregando...</strong>
				</div>
			</template>
		</b-overlay>
		<b-button @click="$router.push('/apolices')">Voltar</b-button>
	</b-container>
</template>

<script>
export default {
	computed: {
		id() {
			return this.$route.params.id
		}
	},
	data() {
		return {
			apolice: {}
		}
	},
	async fetch() {
		await this.$axios.$get(`/api/apolices/${this.id}`)
			.then(async data => {
				this.apolice = data

				// noinspection JSUnresolvedVariable
				const seguradoraId = this.apolice.seguradoraId
				// noinspection JSUnresolvedVariable
				await this.$axios.$get(`/api/seguradoras/${seguradoraId}`)
					.then(data => this.apolice.seguradora = data.nome)
			})
			.catch(e => {
				console.error(`Erro ao obter apólice: ${e}`)
				this.$root.$bvToast.toast('Erro ao obter apólice.', {
					solid: true,
					title: 'Erro ao obter dados',
					toaster: 'b-toaster-top-center',
					variant: 'danger'
				});
				this.$router.push('/apolices')
			});
	},
	fetchOnServer: false,
	methods: {
		formatDate(value) {
			if (!value)
				return value

			return new Date(value.replace('[UTC]', '')).toLocaleDateString();
		}
	}
}
</script>
