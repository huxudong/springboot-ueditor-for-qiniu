<template>
  <div id="app">
         <UE :defaultMsg=defaultMsg :config=config :id=ue ref="ue"></UE>
  </div>
</template>

<script>
import UE from './components/ue/ueditor.vue'

export default {
  name: 'app',
  data () {
   return {
        defaultMsg: '',
        config: {
          initialFrameWidth: null,
          initialFrameHeight: 350
        },
        ue:'ue'
    }
  },
  components: {
    UE
  }
}
</script>

