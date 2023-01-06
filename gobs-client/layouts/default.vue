<template>
	<div id="app" class="vh-100">
		<b-navbar toggleable="lg">
			<b-navbar-toggle target="nav-collapse"></b-navbar-toggle>
			<b-collapse id="nav-collapse" is-nav>
				<!-- Right aligned nav items -->
				<b-navbar-nav class="ml-auto">
					<b-nav-item-dropdown v-if="$auth.loggedIn" right>
						<!-- Using 'button-content' slot -->
						<template #button-content>
							{{ $auth.user.nome }}
						</template>
						<b-dropdown-item @click.prevent="logout">
							Logout
						</b-dropdown-item>
					</b-nav-item-dropdown>
					<li class="nav-item" v-else>
						<nuxt-link class="nav-link" to="/auth/login">
							Login
						</nuxt-link>
					</li>
				</b-navbar-nav>
			</b-collapse>
		</b-navbar>
		<main>
			<router-view/>
		</main>
	</div>
</template>

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
