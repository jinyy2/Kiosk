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
