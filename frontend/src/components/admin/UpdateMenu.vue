<template>
  <div class="overflow-auto">
    <p class="mt-3">Current Page: {{ currentPage }}</p>
    <b-table
      id="my-table"
      :items="items"
      :per-page="perPage"
      :current-page="currentPage"
      small
      hover
    >
      <template #cell(name)="data"> {{ data.item.name }} +III </template>
      <!-- <template #cell(name)="data">
        <b class="text-info">{{ data.sid }}</b>, <b>{{ data.value.name }}</b>
      </template> -->
      <template #cell(image)="data">
        <!-- <v-img :src="data.item.image" alt="" contain height="100px" width="150px">
        </v-img> -->
        <img :src="data.item.image" />
        <!-- {{ data.item.image }} +III -->
      </template>
    </b-table>
    <div class="mt-3">
      <b-pagination
        v-model="currentPage"
        :total-rows="rows"
        align="center"
      ></b-pagination>
    </div>
  </div>
</template>
<script>
import axios from "axios";
import constants from "../../lib/constants";

const baseURL = constants.baseUrl;

export default {
  name: "UpdateMenu",
  data() {
    return {
      perPage: 10,
      currentPage: 1,
      menuAll: {},
      items: [
      ],
    };
  },
  created() {
    console.log("logger - Create DashBoard");
    this.GetMenuInfo();
  },
  computed: {
    rows() {
      return this.items.length;
    },
  },
  methods: {
    GetMenuInfo() {
      axios
        .get(`${baseURL}/branch/menu`, { params: { sid: 1 } })
        .then((res) => {
          console.log(res);
          this.items = res.data.object;
          //   const menu = res.data.object;
          //   this.menuAll = res.data.object;
          //   this.menusCate1 = menu.filter((cate) => cate.category1 == 1);
          //   this.menusCate2 = menu.filter((cate) => cate.category1 == 2);
          //   console.log(this.menusCate1);
        })
        .catch((err) => console.log(err.response));
    },
  },
};
</script>
<style scoped>
</style>