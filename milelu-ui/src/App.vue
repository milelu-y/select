<template>
  <div id="app">
    <router-view />
    <drag-ball v-if="this.$store.getters.token"></drag-ball>
  </div>
</template>

<script>
import DragBall from '@/components/DragBall/DragBall'

export default  {
  name:  'App',
  components: {DragBall}
}
</script>
