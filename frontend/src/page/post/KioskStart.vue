<template>
  <div class="cover-container d-flex w-100 h-100 p-3 mx-auto flex-column">
    <div class="bcenter text-center">
      <h1 style="font-weight:bold; color:white;">키오스크 메인 화면입니다.</h1><br/>
      <h2 style="color:white;"> 사용을 위해 클릭해주세요.</h2>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import constants from "../../lib/constants";

const baseURL = constants.baseUrl;
export default {
  name: "KioskStart",
  created() {
    this.checkvisitor();
  },
  methods: {
    checkvisitor() {
      tmp()
      function tmp () {
        setTimeout(function() {
          axios
          .get(baseURL + "/visitor")
          .then((res) => {
            if (res.data.object.length != 0) {
              var visitorID = res.data.object[0].uid
              axios.get(baseURL + "/tracking/start", { params: { tid: visitorID } })
              .then(res => {console.log(res.data.data)})
              .catch((err) => {
                console.log(err.response);
              }); 
            } else {
              tmp()
            }
          })
          .catch((Error) => {
            console.log(Error);
          });  
        }, 1000);
        }
    },
  }
};
</script>

<style scoped>
.cover-container {
  margin: 300px auto;
  background-color: rgba(0, 0, 0, .7);
  border-right:solid 3px black;
  border-bottom:solid 3px black;
}
span {
  margin: 0 auto;

}

.bcenter{
  width:50%;
  margin: 0 auto;
}
h1{
}
</style>
