<template>
  <div class="container mt-5">
    <b-card no-body>
      <b-tabs
        pills card vertical
        active-nav-item-class="font-weight-bold text-uppercase"
        active-tab-class="font-weight-bold text-success"
        content-class="mt-3"
      >
        <b-tab title="First" active><p>I'm the first tab</p></b-tab>
        <b-tab title="Second"><p>I'm the second tab</p></b-tab>
      </b-tabs>
    </b-card>
    <button class="btn" @click="test">히히힣</button>
  </div>
</template>
 
<script>
import axios from "axios";
import { mapGetters } from "vuex";

export default {
  name: "Post",
  components: {},
  watch: {},
  created() {},
  methods: {
    test() {
      axios
        .get("http://127.0.0.1:8080/test")
        .then((res) => console.log(res))
        .catch((err) => console.log(err.response));
    },
  },
  data: () => {
    return {};
  },
};
</script>

<style scoped>

</style>