<template>
  <v-app>
    <v-navigation-drawer permanent class="elevation-0" app light v-model="drawer">
      <sidebar/>
    </v-navigation-drawer>
    <v-toolbar dense class="elevation-0" color="blue-grey" dark app>
      <navSessionList/>
      <v-btn icon @click="logout">
        <v-icon>exit_to_app</v-icon>
      </v-btn>
    </v-toolbar>
    <v-content>
      <v-container fluid fill-height>
        <v-layout
          v-if="sessions.length==0 && !loadingSessions"
          class="mb-5"
          align-center
          justify-center
        >
          <span>There are no open sessions.</span>
        </v-layout>
        <v-layout
          v-if="sessions.length>0"
          d-flex
          :class="{'showTerminal':showTerminal}"
          id="terminalContainer"
        ></v-layout>
      </v-container>
    </v-content>
    <session/>
    <socket/>
    <terminal/>
  </v-app>
</template>

<script>
import Sidebar from "./Sidebar";
import Session from "./Session";
import Socket from "./Socket";
import NavSessionList from "./NavSessionList";
import Terminal from "./Terminal";
export default {
  components: {
    Sidebar,
    Session,
    Socket,
    NavSessionList,
    Terminal
  },
  data() {
    return {
      loadingSessions: true,
      showTerminal: false,
      drawer: false,
      sessions: [],
      connections: []
    };
  },
  methods: {
    loadConnections() {
      this.$http.get("/api/connection/get").then(e => {
        this.connections = e.body;
      });
    },
    logout() {
      bus.$emit("logout");
    }
  },
  mounted() {
    this.loadConnections();
    bus.$off("loadConnections");
    bus.$on("loadConnections", this.loadConnections);
  }
};
</script>
<style lang=scss scoped>
#terminalContainer {
  opacity: 0;
  &.showTerminal {
    opacity: 1;
    transition: opacity 0.2s;
  }
}
</style>