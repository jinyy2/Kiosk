<template>
  <div class="container">
    <div class="mt-1 p-0" style="margin-left: 450px;">
      <router-link class="ml-5 nav-link" to="/">
       HOME
      </router-link>
    </div>

    <div class="mr-10" style="font-size: 20px; margin-left: 750px; margin-top: 30px;">
      {{ form.name }} 님 구매 내역
    </div>

    <div class="mt-10 mr-5" style="float:center;">
    
      <div
        class="mb-5 mt-5"  
        style="margin-left: 200px; margin-right: 200px;"
        v-for="(menu, i) in Mymenu"
        :key="i"
        data-holder-rendered="true"
      >
        <button class="btn btn-success" style="font-size:15px;">
          {{ i.substring(5, 7) }}월 {{ i.substring(8, 11) }}일
          {{ i.substring(11, 13) }}시 {{ i.substring(14, 16) }}분
        </button>
        <div
          v-for="(menuDetail, j) in Mymenu[i]"
          :key="j"
          id="collapse-1"
          class="mt-2"
          
        >
     
            <b-card class="row">
              <!-- {{ j + 1 }} -->
              <div class="col-3">
                <b-img class="" :src="menuDetail[3]" height="100px"></b-img>
              </div>
              <div class="col-5">{{ menuDetail[2] }}</div>
              <div class="col-2" style="color:red">{{ z(menuDetail[4]) }}원</div>
            </b-card>
        
        </div>
      </div>
    </div>

    <!-- <div class="col-2" style="float:right;">
      많이 구매한 상점 내역<br />

      총액 :
    </div> -->
  </div>
</template>

<script>
import axios from "axios";
import constants from "../../lib/constants";

const baseURL = constants.baseUrl;

export default {
  name: "OrderList",
  data() {
    return {
      form: {
        uid: "",
        email: "",
        name: "",
        gender: "",
        age: "",
        tel: "",
        learningfile: "",
      },
      Mymenu: [],
      MymenuDate: [],
    };
  },
  created() {
    this.getUserInfo();
  },
  methods: {
    getUserInfo() {
      // console.log("method - getUserInfo");

      const axiosConfig = {
        headers: {
          jwtToken: `${this.$cookies.get("Auth-Token")}`,
        },
      };
      console.log(axiosConfig);
      axios
        .post(`${constants.baseUrl}/authuser`, "", axiosConfig)
        .then((res) => {
          // console.log(res.data.uid);
          this.form = res.data;
          this.getUserOrderList();
        })
        .catch((err) => console.log(err));
    },
    getUserOrderList() {
      axios
        .get(baseURL + "/mypage/searchorder", {
          params: { sid: 1, uid: this.form.uid },
        })
        .then((res) => {
          this.Mymenu = res.data.object;

          for (var i = 0; i < this.Mymenu.length; i++) {
            const day = this.Mymenu[i][0];
            this.Mymenu[i][0] = day;
            // for (let i = 0; i < this.Mymenu.length; i++) {
            //   // var a = this.Mymenu.length-1-i;
            //   const day = this.MymenuDate[i].orderdate;
            //   this.Mymenu[i].orderdate = day;
          }
          var tmp = {};
          for (let j = 0; j < this.Mymenu.length; j++) {
            const aa = this.Mymenu[j][0];
            if (aa in tmp) {
              tmp[aa].push(this.Mymenu[j]);
            } else {
              tmp[aa] = [];
              tmp[aa].push(this.Mymenu[j]);
            }
          }
          this.Mymenu = tmp;
          console.log(this.Mymenu);
        })
        .catch((err) => console.log(err.response));
    },

    z(x) {
      return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
    },
    // getUserOrderList2() {
    //   axios
    //     .get(baseURL + "/get/orderlist2", {
    //       params: { sid: 1, uid: this.form.uid },
    //     })
    //     .then((res) => {
    //       this.MymenuDate = res.data.object;
    //       this.getUserOrderList();
    //     })
    //     .catch((err) => console.log(err.response));
    // },
  },
};
</script>

<style scoped>
.row > div {
  display: flex;
  align-items: center;
  justify-content: center;
}
.row {
  font-size: 20px;
}
</style>


//Orderlist