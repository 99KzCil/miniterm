<template>
  <v-app>
    <v-navigation-drawer stateless class="elevation-0" app light v-model="drawer">
      <sidebar />
    </v-navigation-drawer>
    <v-toolbar dense class="elevation-0" color="blue-grey" dark app>
      <v-toolbar-side-icon @click.stop="drawer = !drawer"></v-toolbar-side-icon>
      <v-toolbar-title class="ml-0" style="flex-shrink:0;">miniterm</v-toolbar-title>
      <navSessionList />
      <v-btn icon @click="logout">
        <v-icon>exit_to_app</v-icon>
      </v-btn>
    </v-toolbar>
    <v-content>
      <v-container fluid fill-height>
        <v-layout v-if="sessions.length==0" class="mb-5" align-center justify-center>
          <span>There are no open sessions.</span>
        </v-layout>
      </v-container>
    </v-content>
    <session />
    <socket />
  </v-app>
</template>

<script>
import Sidebar from "./Sidebar";
import Session from "./Session";
import Socket from "./Socket";
import NavSessionList from "./NavSessionList";
export default {
  components: {
    Sidebar,
    Session,
    Socket,
    NavSessionList
  },
  data() {
    return {
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

<style scoped>
</style>
