<template>
  <div id="app">
    <router-view/>
  </div>
</template>

<script src="https://use.fontawesome.com/releases/v5.2.0/js/all.js"></script>  
<script> 
import constants from './lib/constants'

export default {
  name: 'App',
  components: { 
    // Header,
    // SignupForm,

  },
  created() {

  },
  watch: {
  },
  methods : {
  },
  data: function () {
        return {
            isHeader: true,
            constants
        } 
    }
}
</script>

<style>
</style>
