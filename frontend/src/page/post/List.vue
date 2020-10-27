<template>
  <div class="container mt-5 p-0">
    <b-card no-body>
      <b-tabs
        
        align="center"
        active-nav-item-class="font-weight-bold text-uppercase"
        active-tab-class="font-weight-bold text-success"
      >
        <b-tab title="First" active>
          <div>
            <b-tabs content-class="mt-3" pills>
              <b-tab title="First" active><p>I'm the first tab</p></b-tab>
              <b-tab title="Second"><p>I'm the second tab</p></b-tab>
            </b-tabs>
          </div>
        </b-tab>
        <b-tab title="Second">
          <div>
            <b-tabs content-class="mt-3" pills>
              <b-tab title="First" active><p>I'm the first tab</p></b-tab>
              <b-tab title="Second">
                <button class="btn" @click="test2">히히힣</button>
              </b-tab>
            </b-tabs>
          </div>
        </b-tab>
      </b-tabs>
    </b-card>
  </div>
</template>
 
<script>
import axios from "axios";
import constants from '../../lib/constants';


const baseURL = constants.baseUrl;

export default {
  name: "List",
  components: {},
  watch: {},
  created() {},
  methods: {
    test() {
      axios
        .get(baseURL + "/test")
        .then((res) => console.log(res))
        .catch((err) => console.log(err.response));
    },
    test2() {
      axios
        .get(baseURL + "/branch/menu", { params: { sid: 1 }})
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