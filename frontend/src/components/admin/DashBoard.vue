<template>
  <div class="container">
    <div class="row">
      <div class="col-sm">
        <div class="card text-center" style="width: 18rem">
          <div class="card-body">
            <h5 class="card-title">오늘의 방문자 수</h5>
            <p class="card-text" style="font-size:3em">
              {{todayCount}}
            </p>
            <b-btn href="#" class="card-link" router-link :to="{name: 'visithisotry'}">방문기록 확인</b-btn>
            <!-- <b-btn href="#" class="card-link">방문기록 확인</b-btn> -->
          </div>
        </div>
      </div>
      <div class="col-sm">
        <div class="card" style="width: 18rem">
          <div class="card-body">
            <h5 class="card-title">Card title</h5>
            <h6 class="card-subtitle mb-2 text-muted">Card subtitle</h6>
            <p class="card-text">
              Some quick example text to build on the card title and make up the
              bulk of the card's content.
            </p>
            <a href="#" class="card-link">Card link</a>
            <a href="#" class="card-link">Another link</a>
          </div>
        </div>
      </div>
      <div class="col-sm">
        <div class="card" style="width: 18rem">
          <div class="card-body">
            <h5 class="card-title">Card title</h5>
            <h6 class="card-subtitle mb-2 text-muted">Card subtitle</h6>
            <p class="card-text">
              Some quick example text to build on the card title and make up the
              bulk of the card's content.
            </p>
            <a href="#" class="card-link">Card link</a>
            <a href="#" class="card-link">Another link</a>
          </div>
        </div>
      </div>
    </div>
    <div class="row">
      <div class="col-sm">
        <div class="card" style="width: 100%">
          <div class="card-body">
            <h5 class="card-title">Card title</h5>
            <h6 class="card-subtitle mb-2 text-muted">Card subtitle</h6>
            <p class="card-text">
              Some quick example text to build on the card title and make up the
              bulk of the card's content.
            </p>
            <a href="#" class="card-link">Card link</a>
            <a href="#" class="card-link">Another link</a>
          </div>
        </div>
      </div>
    </div>

  </div>
</template>
<script>
import axios from 'axios'
import constants from '@/lib/constants'

const baseURL = constants.baseUrl;

export default {
  name: "DashBoard",
  data(){
    return{
      todayCount:0,
    }
  },
  created(){
    this.getTodayVisitor()
  },
  methods:{
    getTodayVisitor(){
      axios
      .get(`${baseURL}/admin/getvisitorcount`)
      .then((res)=>{
          this.todayCount=res.data
        // console.log(res)
        }
      
      )
      .catch((err)=>{
        console.log(err)
      })
    },
  },

};
</script>
<style scoped>
.container {
  background-color: white;
  width: 1000;
  margin: 60px auto;
  padding: 50px;
}
.row{
  margin-bottom: 50px;
}
</style>