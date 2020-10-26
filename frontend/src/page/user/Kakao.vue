<template>
  <div id="kakao-login-btn" @click="kakaojoin">
    <div>{{ this.$store.state.kakaoId }}</div>
    <div @click="kakaoInfoUpdate">jenkins 되냐 안되냐 돼라 제발 ㅇㅇㅇㅇㅇ</div>
    <img
      src="//k.kakaocdn.net/14/dn/btqCn0WEmI3/nijroPfbpCa4at5EIsjyf0/o.jpg"
      width="222"
    />
  </div>
</template>

<script>
import axios from "axios";
import constants from '../../lib/constants';


const baseURL = constants.baseUrl;

export default {
  name: "Kakao",
  components: {},
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
                  if (response.data != "") {
                    kakaoToken = response.data;
                    x.$cookies.set("Auth-Token", kakaoToken);
                    x.$router.push("/");
                  } else {
              
                    x.kakaoInfoUpdate(res.id);
                    
                    x.$router.push({ name: "join" });
                  }
                }).catch(err => console.log(err.response))
                

              //    alert(kakaotempToken)

              // })
              // alert(JSON.stringify(res)); //<---- kakao.api.request 에서 불러온 결과값 json형태로 출력

              // alert(JSON.stringify(authObj)); //<----Kakao.Auth.createLoginButton에서 불러온 결과값 json형태로 출력

              // console.log(res.id);//<---- 콘솔 로그에 id 정보 출력(id는 res안에 있기 때문에  res.id 로 불러온다)

              // console.log(res.account_email);//<---- 콘솔 로그에 email 정보 출력

              // console.log(res.properties['nickname']);//<---- 콘솔 로그에 닉네임 출력(properties에 있는 nickname 접근

              //res.properties.nickname으로도 접근 가능 )

              // this.kakaoInfoUpdate();

              console.log("hh");
              // console.log(authObj.access_token);//<---- 콘솔 로그에 토큰값 출력
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
  },
  data: () => {
    return {
      kakao: {
        uid: "",
        name: "",
      },
    };
  },
};
</script>
