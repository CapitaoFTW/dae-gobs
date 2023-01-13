<template>
	<b-container>
		<h1 class="text-center mb-5">Apolice</h1>
		<b-overlay :show="$fetchState.pending" spinner-variant="primary">
			<p>Seguradora: {{ apolice.seguradora.nome }}</p>
			<p>Bem: {{ apolice.bem }}</p>
			<p>Premio: {{ apolice.premio }}€</p>
			<p>Expira: {{ formatDate(apolice.prazo) }}</p>
			<p>Criado: {{ formatDate(apolice.criado) }}</p>
			<p>todo add cliente if funcionario</p>
			<template #overlay>
				<div class="text-center text-primary my-2">
					<b-spinner class="align-middle"></b-spinner>
					<strong>Carregando...</strong>
				</div>
			</template>
		</b-overlay>
		<b-button @click=$router.back()>Voltar</b-button>
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
			.then(data => this.apolice = data)
			.catch(e => {
				console.error(`Erro ao obter apolice: ${e}`)
				this.$root.$bvToast.toast('Erro ao obter apolice.', {
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
				return value

			return new Date(value.replace('[UTC]', '')).toLocaleDateString();
		}
	}
}
</script>
