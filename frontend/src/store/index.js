import Vue from 'vue'
import Vuex from 'vuex'
import constants from '../lib/constants'


Vue.use(Vuex)

export default new Vuex.Store({
  state: {
      kakaoId:'dldldldldl',
      baseUrl:'http://localhost:8080',
  },
  mutations: {
    kakaoIdUpdate(state,payload){
        state.kakaoId = payload
    }
  },
  actions: {
  },
  getters:{
      getKakaoId(state){
        return state.kakaoId;
      },
      getBaseUrl(state){
        return state.baseUrl;
    },
  },
  modules: {
  }
})