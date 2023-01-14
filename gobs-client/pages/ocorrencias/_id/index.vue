<template>
	<b-container>
		<h1 class="text-center mb-5">Ocorrência</h1>
		<!--suppress JSUnresolvedVariable -->
		<b-overlay :show="$fetchState.pending" spinner-variant="primary">
			<p>Seguradora: {{ ocorrencia.seguradora }}</p>
			<p>Bem: {{ ocorrencia.bem }}</p>
			<p>Premio: {{ ocorrencia.premio }}€</p>
			<!--suppress JSUnresolvedVariable -->
			<p>Expira: {{ formatDate(ocorrencia.prazo) }}</p>
			<!--suppress JSUnresolvedVariable -->
			<p>Criado: {{ formatDate(ocorrencia.criado) }}</p>
			<template #overlay>
				<div class="text-center text-primary my-2">
					<b-spinner class="align-middle"></b-spinner>
					<strong>Carregando...</strong>
				</div>
			</template>
			<b-col>
				<b-row>
					<object v-for="item in attachments"/>
					Imagens
				</b-row>
				<b-row>
					Msg
				</b-row>
			</b-col>
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
			attachments: [],
			messages: [],
			ocorrencia: {},
		}
	},
	async fetch() {
		await this.$axios.$get(`/api/ocorrencias/${this.id}`)
			.then(data => {
				this.ocorrencia = data
				console.log(data)
			})
			.catch(e => {
				console.error(`Erro ao obter ocorrência: ${e}`)
				this.$root.$bvToast.toast('Erro ao obter ocorrência.', {
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
