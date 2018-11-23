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
import Sidebar from "./Sidebar";

import { Terminal } from "xterm";
import * as fit from "xterm/lib/addons/fit/fit";
var term = new Terminal();

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
      this.connections.push(connection);
      this.deactivate();
      connection.active = true;
      this.$http.post("/api/ssh/open", connection).then(result, result);
      function result(e) {
        console.log(e);
      }
      term.destroy();
      term = new Terminal();
      term.open(document.getElementById("middle"));
      term.write("Hello from \x1B[1;3;31mxterm.js\x1B[0m $ ");
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
