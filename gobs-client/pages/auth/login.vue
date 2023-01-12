<template>
	<b-container class="align-items-center d-flex justify-content-center h-75 mt-5 pt-5">
		<b-tabs v-model="loginIndex" align="center" lazy pills @activate-tab="reset">
			<br>
			<b-form :validated="isFormValid" class="border border-primary p-4 rounded"
					@submit.prevent="login">
				<b-tab active title="Cliente">
					<b-form-group
						:invalid-feedback="invalidIdFeedback"
						:state="isIdValid"
						label="Introduzir NIF/NIPC:"
						label-for="input-nif">
						<b-form-input
							id="input-nif"
							v-model.trim="nif"
							:no-wheel=true
							:state="isIdValid"
							placeholder="NIF/NIPC"
							required
							type="number"/>
					</b-form-group>
					<b-form-group
						:invalid-feedback="invalidPasswordFeedback"
						:state="isPasswordValid"
						label="Introduzir password:"
						label-for="input-password">
						<b-form-input
							id="input-password"
							v-model="password"
							:state="isPasswordValid"
							placeholder="Password"
							required
							type="password"/>
					</b-form-group>
				</b-tab>
				<b-tab title="Funcionário">
					<b-form-group
						:invalid-feedback="invalidIdFeedback"
						:state="isIdValid"
						label="Introduzir nome de utilizador:"
						label-for="input-username">
						<b-form-input
							id="input-username"
							v-model.trim="username"
							:state="isIdValid"
							placeholder="Nome de utilizador"
							required
							type="text"/>
					</b-form-group>
					<b-form-group
						:invalid-feedback="invalidPasswordFeedback"
						:state="isPasswordValid"
						label="Introduzir password:"
						label-for="input-password">
						<b-form-input
							id="input-password"
							v-model="password"
							:state="isPasswordValid"
							placeholder="Password"
							required
							type="password"/>
					</b-form-group>
				</b-tab>
				<b-button type="reset" variant="danger">Reset</b-button>
				<b-button :disabled="!isFormValid" type="submit" variant="success" @click.prevent="login">Submit
				</b-button>
			</b-form>
		</b-tabs>
	</b-container>
</template>

<!--suppress JSUnusedGlobalSymbols -->
<script>
export default {
	auth: false,
	computed: {
		invalidIdFeedback() {
			switch (this.loginIndex) {
				case 0:
					const nif = this.nif;
					if (!nif) {
						return null;
					}

					const nifLen = nif.length;
					if (nifLen !== 9) {
						return 'NIF/NIPC inválido.';
					}

					return '';

				case 1:
					const username = this.username;
					if (!username) {
						return null;
					}

					const usernameLen = username.length;
					if (usernameLen < 3 || usernameLen > 50) {
						return 'O nome de usuário deve ter entre [3, 50] caracteres.';
					}

					return '';

				default:
					return null;
			}
		},
		isIdValid() {
			if (this.invalidIdFeedback == null) {
				return null;
			}

			return this.invalidIdFeedback === '';
		},
		invalidPasswordFeedback() {
			const password = this.password;
			if (!password) {
				return null;
			}

			const passwordLen = password.length;
			if (passwordLen < 3 || passwordLen > 255) {
				return 'A senha deve ter entre [3, 255] caracteres.';
			}

			return '';
		},
		isPasswordValid() {
			if (this.invalidPasswordFeedback == null) {
				return null;
			}

			return this.invalidPasswordFeedback === '';
		},
		isFormValid() {
			return this.isIdValid && this.isPasswordValid;
		},
		userId() {
			switch (this.loginIndex) {
				case 0:
					return this.nif;

				case 1:
					return this.username;
			}
		}
	},
	data() {
		return {
			nif: null,
			username: null,
			password: null,
			loginIndex: 0
		}
	},
	methods: {
		login() {
			switch (this.loginIndex) {
				case 0:
					this.$auth.loginWith('client', {
						data: {
							nif: this.nif,
							password: this.password
						}
					}).then(this.afterLogin).catch(this.catchLogin);
					return;

				case 1:
					this.$auth.loginWith('employee', {
						data: {
							username: this.username,
							password: this.password
						}
					}).then(this.afterLogin).catch(this.catchLogin);
					return;

				default:
					this.$root.$bvToast.toast('Erro interno detetado, recarregando...', {
						solid: true,
						title: 'Erro de pagina',
						toaster: 'b-toaster-top-center',
						variant: 'danger'
					});
					this.loginIndex = 0;
					this.reset();
					return;
			}
		},
		catchLogin(error) {
			let msg
			if (error.response && error.response.data)
				msg = error.response.data
			else
				msg = error.message

			console.error(`Erro ao tentar fazer login com ${this.userId}: ${error.message}`);
			this.$bvToast.toast(msg, {
				solid: true,
				title: `Erro ao tentar fazer login com ${this.userId}`,
				toaster: 'b-toaster-top-center',
				variant: 'danger'
			});
		},
		afterLogin() {
			console.info(`Logged in into ${JSON.stringify(this.$auth.user)}`);
			this.$root.$bvToast.toast('You are logged in!', {
				solid: true,
				title: 'Logged in!',
				toaster: 'b-toaster-top-left',
				variant: 'success'
			});
			this.$router.push('/');
		},
		reset() {
			this.nif = null;
			this.username = null;
			this.password = null;
		}
	}
}
</script>
