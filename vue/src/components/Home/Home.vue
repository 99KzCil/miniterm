<template>
  <div id="home" class="flex flex1">
    <sidebar />
    <div id="content" class="flex flex1 column">
      <div class="header flex">
        <div @click="activate(connection)" class="conenctionName flex halign valign" :class="{'active':connection.active}" v-for="connection in connections" :key="connection.id">
          {{connection.name}}
        </div>
      </div>
      <div id="middle" class="flex1 flex pad">

      </div>
    </div>
  </div>
</template>

<script>
import Vue from "vue";
import Store from "@/Store";
import Sidebar from "./Sidebar";

export default {
  components: {
    Sidebar
  },
  data() {
    return {
      connections: []
    };
  },
  methods: {
    newConnection(connection) {
      this.$http
        .post("/api/connection/newConnection", connection.id)
        .then(result, result);
      function result(e) {

      }

      this.connections.push(connection);
      this.deactivate();
      connection.active = true;

      // var term = new Terminal();
      // term.open(document.getElementById("middle"));
      // term.write("Hello from \x1B[1;3;31mxterm.js\x1B[0m $ ");
      // var socket = new WebSocket(
      //   "ws://" +
      //     window.location.hostname +
      //     ":8090/api/ssh?" +
      //     Store.user.username +
      //     "," +
      //     connection.id
      // );
    },
    activate(connection) {
      this.deactivate();
      connection.active = true;
    },
    deactivate() {
      this.connections.forEach(connection => {
        connection.active = false;
      });
      this.$forceUpdate();
    }
  }
};
</script>

<style scoped lang=scss>
#content {
  .header {
    min-height: 40px;
    border-bottom: 1px solid rgba(0, 0, 0, 0.1);
    flex-wrap: wrap;
    .conenctionName {
      padding: 0.5rem;
      &.active {
        background: green;
        color: #fff;
      }
    }
  }
  #middle {
    background: #000;
  }
}
</style>
