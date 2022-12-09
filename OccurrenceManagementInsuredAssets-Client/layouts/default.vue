<template>
  <div id="app">
    <main>
      <Nuxt/>
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
