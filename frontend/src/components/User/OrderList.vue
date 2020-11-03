<template>
  <div class="container">
    <div>
      <!-- <b-button @click="getUserOrderList()"> 내가 주문한거</b-button> -->
      <div>
        <table class="table table-hover">
          <thead class="thead-dark">
            <tr>
              <th scope="col">#</th>
              <th scope="col" class="mx-auto">상품</th>
              <th scope="col">이름</th>
              <th scope="col">가격</th>
              <th scope="col">주문 내역</th>
            </tr>
          </thead>
          <tbody>
              <tr v-for="(menu, i) in Mymenu" :key="i">
                <th scope="row" style="padding-top:50px;">{{ i +1}}</th>
                <td><b-img
                  :src="menu.image"
                  width="100%"
                ></b-img></td>
                <td style="padding-top:50px;">{{menu.name}}</td>
                <td style="padding-top:50px;">{{menu.price}}원</td>
                <td style="padding-top:50px;">{{menu.orderdate}}</td>
              </tr>
          </tbody>
        </table>
      </div>
    </div>
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
      MymenuDate:[],
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
          this.getUserOrderList2();
        })
        .catch((err) => console.log(err));
    },
    getUserOrderList() {
      axios
        .get(baseURL + "/get/orderlist", {
          params: { sid: 1, uid: this.form.uid },
        })
        .then((res) => {
          this.Mymenu = res.data.object;

          for (let i = 0; i < this.Mymenu.length; i++) {
            const day = this.MymenuDate[i].orderdate;
            this.Mymenu[i].orderdate = day;
          }
          console.log(this.Mymenu);
        })
        .catch((err) => console.log(err.response));
    },
    getUserOrderList2() {
      axios
        .get(baseURL + "/get/orderlist2", {
          params: { sid: 1, uid: this.form.uid },
        })
        .then((res) => {
          this.MymenuDate = res.data.object;
          this.getUserOrderList();
          
        })
        .catch((err) => console.log(err.response));
    },
  },
};
</script>
<style scoped>
td,th{
  text-align: center;
}
</style>