<template>
  <v-app id="vapp" :class="{hideDrawer:hideDrawer}">
    <v-navigation-drawer permanent class="elevation-0" app light>
      <sidebar/>
    </v-navigation-drawer>
    <v-toolbar dense class="elevation-0" color="primary" dark app>
      <v-btn v-if="hideDrawer" flat icon small class="elevation-0 mr-3" @click="hideDrawer=false">
        <v-icon>arrow_right</v-icon>
      </v-btn>
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
  watch: {
    hideDrawer() {
      localStorage.hideDrawer = "" + this.hideDrawer;
    }
  },
  data() {
    return {
      loadingSessions: true,
      showTerminal: false,
      hideDrawer: false,
      sessions: [],
      connections: []
    };
  },
  methods: {
    loadConnections() {
      this.$http.get(href + "api/connection/get").then(e => {
        this.connections = e.body;
      });
    },
    logout() {
      bus.$emit("logout");
    }
  },
  mounted() {
    if (localStorage.hideDrawer) this.hideDrawer = localStorage.hideDrawer == "true";
    this.loadConnections();
    bus.$off("loadConnections");
    bus.$on("loadConnections", this.loadConnections);
  }
};
</script>
<style lang=scss scoped>
#vapp {
  &.hideDrawer {
    .v-navigation-drawer {
      transform: translateX(-300px) !important;
    }
    .v-content {
      padding-left: 0 !important;
    }
    .v-toolbar {
      padding-left: 0 !important;
    }
  }
  #terminalContainer {
    opacity: 0;
    &.showTerminal {
      opacity: 1;
      transition: opacity 0.2s;
    }
  }
}
</style>