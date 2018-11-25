<template>
  <div id="sidebar" class="">
    <div class="title flex valign noselect">
      <i @click="iconClick" class="material-icons marginLeft pointer ">{{icon}}</i>
      <h1 class="flex1 talign">{{title}}</h1>
    </div>
    <div v-if="page=='list'" class="content flex flex1 column">
      <div @click="$parent.newConnection(connection)" class="connection flex valign pad pointer" v-for="connection in connections" :key="connection.id">
        {{connection.name}}
      </div>
    </div>
    <settings v-if="page=='settings'" />

  </div>
</template>

<script>
import Settings from "./Settings/Settings";
export default {
  components: {
    Settings
  },
  watch: {
    page(val) {
      if (val == "list") this.load();
    }
  },
  data() {
    return {
      icon: "settings",
      page: "list",
      title: "miniterm",
      connections: []
    };
  },
  mounted() {
    this.load();
  },
  methods: {
    load() {
      this.$http.get("/api/connection/get").then(result);
      function result(e) {
        this.connections = e.body;
      }
    },
    iconClick() {
      if (this.page == "settings") {
        this.page = "list";
        this.icon = "settings";
        this.title = "miniterm";
      } else if (this.page == "list") {
        this.page = "settings";
        this.icon = "arrow_back_ios";
        this.title = "settings";
      }
    }
  }
};
</script>


<style scoped lang=scss>
#sidebar {
  float: left;
  height: 100%;
  min-width: 300px;
  max-width: 300px;
  border-right: 1px solid rgba(0, 0, 0, 0.1);
  .title {
    height: 71px;
    justify-content: space-between;
    font-size: 20px;
    border-bottom: 1px solid rgba(0, 0, 0, 0.1);
  }
  .content {
    .connection {
      transition: background 0.25s;
      &:hover {
        background: lightgray;
      }
    }
  }
}
</style>
