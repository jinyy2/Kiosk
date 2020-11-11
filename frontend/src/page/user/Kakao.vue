<template>
  <div>
    <div v-if="isToken">
      <div class="btn" @click="logout">Logout</div>
    </div>
    <div v-if="!isToken">
      <div class="btn" @click="kakaojoin">Login</div>
    </div>
    <div v-if="isToken">
       <router-link to="/user/userinfo">
      <div class="btn">Account</div>
      </router-link>
    </div>
    
  </div>
</template>

<script>
import axios from "axios";
import constants from "../../lib/constants";

const baseURL = constants.baseUrl;
export default {
  name: "Kakao",
  data() {
    return {
      isToken: false,
    };
  },
  computed: {},
  watch: {},
  created() {
    this.islogin();
  },
  methods: {
    islogin() {
      this.isToken = this.$cookies.isKey("Auth-Token");
    },
    kakaoInfoUpdate: function (id) {
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
                   
                  } else {
                    alert("얼굴 촬영을 시작하도록 하겠습니다 잠시만 기다려주시길 바랍니다")
                     console.log("logger - test222");
                    axios
                      .get(`${baseURL}/account/takepic`)
                      .then((response) => {
                        console.log(response)
                        console.log('logger - baseURL/accout/takepic axios result')
                        console.log(response.data);

                        x.kakaoInfoUpdate(res.id);
                        console.log(res.id);
                        x.$router.push({ name: "join" });
                      })
                      .catch((err) => console.log(err.response));
                   
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

    logout: function () {
      this.$cookies.remove("Auth-Token");
      setTimeout(() => {
        this.$router.push("/").catch((err) => {
          console.log(err);
        });
        this.$router.go();
      }, 1000);
    },
  },

  data: function () {
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
.btn {
  font-size: 30px;
  color: white;
}
</style>
