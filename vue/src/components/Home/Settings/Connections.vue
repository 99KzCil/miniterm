<template>
  <div id="settingsConnections" class="flex flex1 column pad">
    <div class="flex valign noselect">
      <i @click="$parent.page='settings'" class="material-icons pointer">arrow_back_ios</i>
      <span class="flex1 talign">connections</span>
    </div>
    <div v-if="page=='connections'" class="content flex flex1 column marginTop2 ">
      <div @click="newConnection" class="flex valign noselect settingsButton pointer">
        <i class="material-icons">add</i>
        <span class="flex1 talign">new connection</span>
      </div>
      <div class="flex flex1 column pad marginTop">
        <div v-for="connection in connections" :key="connection.id" class="flex valign spaces">
          <span>{{connection.name}}</span>
          <span>
            <i @click="edit(connection)" class="material-icons pointer">edit</i>
            <i @click="remove(connection)" class="material-icons pointer">delete</i>
          </span>
        </div>
      </div>
    </div>

    <addEditConnection v-if="page=='addEditConnection'" />
  </div>
</template>

<script>
import AddEditConnection from "./AddEditConnection";
export default {
  components: {
    AddEditConnection
  },
  data() {
    return {
      connections: [],
      connection: {},
      page: "connections"
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
    newConnection() {
      this.connection = {};
      this.page = "addEditConnection";
    },
    edit(connection) {
      this.connection = connection;
      this.page = "addEditConnection";
    },
    remove(connection) {
      if (confirm("delete " + connection.name + " ?")) {
        this.$http.post("/api/connection/remove", connection).then(result);
        function result(e) {
          this.load();
        }
      }
    }
  }
};
</script>

<style scoped lang=scss>
#settingsConnections {
  .settingsButton {
    padding: 0.5rem;
    transition: background 0.25s;
    &:hover {
      background: lightgray;
    }
  }
  .content {
    overflow-y: auto;
  }
}
</style>