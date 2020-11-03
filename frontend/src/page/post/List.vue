<template>
  <div>
    <div>
      <b-sidebar id="sidebar-right" backdrop title="장바구니" right shadow>
        <div class="px-2 py-2">
          <div class="d-flex mb-4" v-for="(menu, k) in basket" :key="k">
            <th>
              <b-img
                :src="menu.image"
                class="rounded-circle image"
                width="140%"
              ></b-img>
            </th>
            <th class="px-3 py-3">
              <tr style="font-size: 20px">
                {{
                  menu.name
                }}
              </tr>
              <tr style="font-size: 20px">
                {{
                  menu.price
                }}원
              </tr>
            </th>
            <br /><br />
          </div>
        </div>
        <div class="px-2">
          <p style="font-size: 20px">총 금액 : {{ basketPrice }}원</p>
          <div>
            <b-button v-b-modal.modal-1>결제하기</b-button>
            <b-button v-b-toggle.sidebar-right @click="GetMenuListRecent()"
              >최근 목록</b-button
            >

            <b-modal id="modal-1" title="결제 하기" @ok="GetMenuList()">
              <p class="my-4">Hello from modal!</p>
            </b-modal>
          </div>
        </div>
      </b-sidebar>
    </div>

    <div class="container mt-1 p-0">
      <div id="body">
        <div id="content">
          <b-card no-body>
            <b-tabs
              align="center"
              active-nav-item-class="font-weight-bold text-uppercase"
              active-tab-class="font-weight-bold text-success"
              style="font-size: 40px"
            >
              <b-tab title="음료">
                <div>
                  <b-tabs content-class="mt-5" pills style="font-size: 20px">
                    <b-tab title="콜드 브루" @click="y = 1"></b-tab>
                    <b-tab title="리저브" @click="y = 2"></b-tab>
                    <b-tab title="에스프레소" @click="y = 3"></b-tab>
                    <b-tab title="블론드" @click="y = 5"></b-tab>
                    <b-tab title="프라푸치노" @click="y = 6"></b-tab>
                    <b-tab title="블렌디드" @click="y = 7"></b-tab>
                    <b-tab title="피지오" @click="y = 8"></b-tab>
                    <b-tab title="티바나" @click="y = 9"></b-tab>
                    <b-tab title="브루드 커피" @click="y = 10"></b-tab>
                    <b-tab title="기타" @click="y = 11"></b-tab>
                    <b-tab title="병음료" @click="y = 12"></b-tab>

                    <br /><br />

                    <div>
                      <div
                        v-for="(menu, i) in menusCate1"
                        :key="i"
                        style="width: 32%; float: left"
                      >
                        <div v-if="menu.category2 == y">
                          <div
                            class="m-3 hover"
                            @click="GetMenuId(menu)"
                            v-b-toggle.sidebar-right
                          >
                            <div>
                              <img
                                style="width: 100%"
                                :src="menu.image"
                                class="rounded-circle image"
                              />
                            </div>
                            <div style="text-align: center; font-size: 20px">
                              {{ menu.name }}
                            </div>
                            <div style="text-align: center; font-size: 20px">
                              {{ menu.price }}원
                            </div>
                          </div>
                        </div>
                      </div>
                    </div>
                  </b-tabs>
                </div>
              </b-tab>
              <b-tab title="푸드">
                <div>
                  <b-tabs content-class="mt-3" pills style="font-size: 20px">
                    <b-tab title="브레드" @click="y = 1"></b-tab>
                    <b-tab title="케이크" @click="y = 2"></b-tab>
                    <b-tab title="샌드위치&샐러드" @click="y = 3"></b-tab>
                    <b-tab title="따뜻한 푸드" @click="y = 4"></b-tab>
                    <b-tab title="과일&요거트" @click="y = 5"></b-tab>
                    <b-tab title="스낵&미니디저트" @click="y = 6"></b-tab>
                    <b-tab title="아이스크림" @click="y = 7"></b-tab>

                    <br /><br />
                    <div>
                      <div
                        v-for="(menu, i) in menusCate2"
                        :key="i"
                        style="width: 32%; float: left"
                      >
                        <div v-if="menu.category2 == y">
                          <div
                            class="m-3 hover"
                            @click="GetMenuId(menu)"
                            v-b-toggle.sidebar-right
                          >
                            <div>
                              <img
                                style="width: 100%"
                                :src="menu.image"
                                class="rounded-circle image"
                              />
                            </div>
                            <div style="text-align: center; font-size: 20px">
                              {{ menu.name }}
                            </div>
                            <div style="text-align: center; font-size: 20px">
                              {{ menu.price }}원
                            </div>
                          </div>
                        </div>
                      </div>
                    </div>
                  </b-tabs>
                </div>
              </b-tab>
            </b-tabs>
            <b-button v-b-toggle.sidebar-right>결제</b-button>
          </b-card>
        </div>
      </div>
    </div>
  </div>
</template>
 
<script>
import axios from "axios";
import constants from "../../lib/constants";

const baseURL = constants.baseUrl;

export default {
  name: "List",
  components: {},
  watch: {},
  computed: {
    basketCheck() {
      if (this.$route.params.basket == "") {
        return false;
      } else {
        return true;
      }
    },
  },
  data() {
    return {
      test: true,
      menusCate1: {},
      menusCate2: {},
      menuAll: {},
      y: 1,
      basket: [],
      basketRecent: [],
      modalShow: false,
      basketPrice: 0,
    };
  },
  created() {
    this.GetMenuInfo();
  },
  methods: {
    GetMenuInfo() {
      axios
        .get(baseURL + "/branch/menu", { params: { sid: 1 } })
        .then((res) => {
          const menu = res.data.object;
          this.menuAll = res.data.object;
          this.menusCate1 = menu.filter((cate) => cate.category1 == 1);
          this.menusCate2 = menu.filter((cate) => cate.category1 == 2);
          console.log(this.menusCate1);
        })
        .catch((err) => console.log(err.response));
    },
    GetMenuId(res) {
      this.basketPrice += res.price;
      this.basket.push(res);
      // console.log(this.basket);
    },
    GetMenuList() {
      axios
        .post(baseURL + "/create/order", this.basket)
        .then(() => {
          console.log(this.basket);
        })
        .catch((err) => console.log(err.response));
    },
    GetMenuListRecent() {
      axios
        .get(baseURL + "/get/orderlist/recent", { params: { sid: 1, uid: 11 } })
        .then((res) => {
          this.basketRecent = res.data.object;
          console.log(this.basketRecent);
        })
        .catch((err) => console.log(err.response));
    },
  },
};
</script>

<style scoped>
@import url("https://fonts.googleapis.com/css2?family=Jua&display=swap");
* {
  font-family: "Jua", sans-serif;
  font-size: 13px;
}
.display {
  font-family: "Jua", sans-serif;
  font-size: 25px;
}
.hover:hover {
  background-color: #eee;
  cursor: pointer;
}


</style>