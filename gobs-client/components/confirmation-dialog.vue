<template>
  <b-modal
    id="confirmModalId"
    :cancel-title=cancelBtn
    :cancel-variant="cancelVariant"
    :ok-title=confirmBtn
    :ok-variant="confirmVariant"
    :title="title"
    hide-header-close
    @ok="confirm">{{ msg }}
  </b-modal>
</template>

<script>
// noinspection JSUnusedGlobalSymbols
export default {
  emits: [
    'confirmed'
  ],
  methods: {
    confirm() {
      this.$emit('confirmed')
    }
  },
  props: {
    cancelBtn: {
      type: String,
      default: 'Cancel'
    },
    cancelVariant: {
      type: String,
      default: 'secondary'
    },
    confirmBtn: {
      type: String,
      default: 'Confirm'
    },
    confirmVariant: {
      type: String,
      default: 'primary'
    },
    msg: {
      type: String,
      default: ''
    },
    title: {
      type: String,
      default: 'Confirmation'
    },
  }
}
</script>
