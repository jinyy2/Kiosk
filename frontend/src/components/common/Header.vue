<template>
  <div v-if="isHeader">
    <nav class="navbar navbar-expand  py-3 shadow-sm">
      <div id="navbarContent" class="collapse navbar-collapse">
        <ul class="navbar-nav ml-auto">
          <li class="nav-item">
            <router-link class="nav-link" to="/">
              <div class="nav-link font-weight-bold text-uppercase">Home</div>
            </router-link>
          </li>
          <li class="nav-item">
            <router-link class="ml-5 nav-link" to="/list">
              <div class="nav-link font-weight-bold text-uppercase">KIOSK</div>
            </router-link>
          </li>
          <li class="nav-item" v-if="this.$cookies.isKey('Auth-Token')">
            <router-link class="ml-5 nav-link" to="/user/mypage">
              <div class="nav-link font-weight-bold text-uppercase">
                Account
              </div>
            </router-link>
          </li>
          <li class="nav-item" v-if="this.$cookies.isKey('Auth-Token')">
            <router-link class="ml-5 nav-link" to="/">
              <div
                class="nav-link font-weight-bold text-uppercase"
                @click="logout"
              >
                Logout
              </div>
            </router-link>
          </li>
          <li class="nav-item" v-if="!this.$cookies.isKey('Auth-Token')">
            <router-link class="ml-5 nav-link" to="/">
              <div
                class="nav-link font-weight-bold text-uppercase"
                @click="kakaojoin"
              >
                Login
              </div>
            </router-link>
          </li>
        </ul>
      </div>
    </nav>
  </div>
</template>

<script>
import axios from "axios";
import constants from "../../lib/constants";
import KakaoVue from "../../page/user/Kakao.vue";

const baseURL = constants.baseUrl;
export default {
  name: "Kakao",
  name: "Header",
  components: {
    KakaoVue,
  },
  props: ["isHeader"],
  computed: {},
  watch: {},
  created() {},
  methods: {
    kakaoInfoUpdate: function(id) {
      this.$store.commit("kakaoIdUpdate", id);
    },
    kakaojoin() {
      let x = this;
      // var kakaotempToken = "";
      var a = 0;
      var kakaoToken = "";
      Kakao.Auth.login({
        success: function(authObj) {
          Kakao.API.request({
            url: "/v2/user/me",

            success: function(res) {
              x.kakao.uid = res.id;
              x.kakao.name = res.properties.nickname;

              axios
                .post(`${baseURL}/account/kakaologin`, x.kakao)
                .then((response) => {
                  console.log(response.data);
                  console.log("logger - check line");
                  if (response.data != "") {
                    kakaoToken = response.data;
                    x.$cookies.set("Auth-Token", kakaoToken);
                    console.log("logger - test111");
                    x.$router.go("/");
                    // x.$router.push({name: "main"}).catch((err) => {
                    //   console.log(err);
                    // });
                    console.log("logger - test222");
                  } else {
                    axios
                      .get(`${baseURL}/account/takepic`)
                      .then((response) => {
                        console.log(response.data)
                        x.kakaoInfoUpdate(res.id);
                        console.log(res.id);
                        x.$router.push({ name: "join" });
                      })
                      .catch((err) => console.log(err.response));
                      console.log("12");
                  }
                })
                .catch((err) => console.log(err.response));
              console.log("hh");
            },
          });
        },

        fail: function(error) {
          alert(JSON.stringify(error));
        },
      });
      setTimeout(() => {
        console.log(a);
      }, 5);
    },

    logout: function() {
      this.$cookies.remove("Auth-Token");

      setTimeout(() => {
        this.$router.push("/").catch((err) => {
          console.log(err);
        });
        this.$router.go();
      }, 1000);
    },
  },

  data: function() {
    return {
      kakao: {
        uid: "",
        name: "",
      },
      constants,
      keyword: "",
    };
  },
};
</script>
<style scoped>
@import "https://fonts.googleapis.com/css?family=Montserrat|Open+Sans";
* {
  background-color: #003300;
  color: #ffffff;
}

.navbar {
  font-family: "Open Sans", sans-serif;
  height: 100px;
  font-size: 20px;
}

.nav-link:hover {
  color: #ffcc00;
}
</style>
