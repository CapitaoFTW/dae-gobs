<template>
	<div id="app" class="vh-100">
		<b-navbar toggleable="lg">
			<b-navbar-brand href="/">
				Gestão de ocorrências em bens segurados (Gobs)
			</b-navbar-brand>
			<b-navbar-toggle target="nav-menu"/>
			<b-collapse id="nav-menu" is-nav>
				<b-navbar-nav class="ml-auto">
					<b-nav-item-dropdown v-if="$auth.loggedIn" right>
						<b-dropdown-item to="/">
							{{ $auth.user.nome }}
						</b-dropdown-item>
						<b-dropdown-item @click.prevent="logout">
							Logout
						</b-dropdown-item>
					</b-nav-item-dropdown>
					<b-nav-item v-else href="/auth/login">
						Login
					</b-nav-item>
				</b-navbar-nav>
			</b-collapse>
		</b-navbar>
		<router-view/>
	</div>
</template>

<!--suppress JSUnusedGlobalSymbols -->
<script>
export default {
	beforeCreate() {
		if (this.$auth.loggedIn)
			this.$router.push('/#')
	},
	methods: {
		logout() {
			this.$auth.logout()
			this.$router.push('/#')
		}
	}
}
</script>
