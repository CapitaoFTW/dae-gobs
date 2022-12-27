<template>
	<div id="app" class="vh-100">
		<router-view/>
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
