<template>
  <!-- <div class="container"> -->
  <!-- <table class="table" id="my-table">
      <thead class="thead-dark">
        <tr>
          <th scope="col">#</th>
          <th scope="col">이름</th>
          <th scope="col">번호</th>
          <th scope="col">방문시각</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="(visitor, idx) in visitors" v-bind:key="idx">
          <th scope="row">{{ visitor.vid }}</th>
          <td>{{ visitor.uid }}</td>
          <td>{{ visitor.tel }}</td>
          <td>{{ visitor.currenttime }}</td>
        </tr>
      </tbody>
    </table> -->
  <!-- pagination -->
  <div class="container">
    <b-table
      id="my-table"
      :items="items"
      :per-page="perPage"
      :current-page="currentPage"
      :fields="fields"
      head-variant=dark
      hover
      small
    ></b-table>
    <b-pagination
      v-model="currentPage"
      :total-rows="rows"
      :per-page="perPage"
      aria-controls="my-table"
      align="center"
      pills 
      size="lg"
    ></b-pagination>

    <!-- <p class="mt-3">Current Page: {{ currentPage }}</p> -->
  </div>
  <!-- </div> -->
</template>
<script>
import axios from "axios";
import constants from "@/lib/constants";
const baseURL = constants.baseUrl;

export default {
  name: "VisitHistory",
  data() {
    return {
      fields:[
        {
          key:'vid',
          label: '방문 번호'
        },
        {
          key:'uid',
          label: '회원 번호'
        },
        {
          key:'tel',
          label: '전화번호'
        },
        {
          key:'currenttime',
          label: '방문 시각'
        },
      ],
      perPage: 3,
      currentPage: 1,
      items: [],
    };
  },
  created() {
    this.getVisitors();
  },
  methods: {
    getVisitors() {
      console.log("logger - getVisitors");
      axios
        .get(`${baseURL}/admin/getvisitors`)
        .then((res) => {
          console.log(res);
          this.items = res.data;
        })
        .catch((err) => {
          console.log(err);
        });
    },
  },
  computed: {
    rows() {
      return this.items.length;
    },
  },
};
</script>
<style scoped>
.container {
  width: 1000px;
  margin: 100px auto;
}
</style>