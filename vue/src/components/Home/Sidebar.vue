<template>
  <v-card fill-height height="100%">
    <v-list subheader>
      <v-subheader class="headline primary white--text" dark>
        <v-img max-height="32" max-width="32" style="margin:0 4px" :src="'static/icon.png'"/>
        <span class="ml-3">miniterm</span>
        <v-spacer></v-spacer>
        <v-btn
          flat
          icon
          small
          class="elevation-0 ma-0"
          @click="$parent.$parent.$parent.hideDrawer=true"
        >
          <v-icon>arrow_left</v-icon>
        </v-btn>
      </v-subheader>
      <v-list-tile avatar @click="addConnection">
        <v-list-tile-avatar>
          <v-icon left>desktop_windows</v-icon>
        </v-list-tile-avatar>
        <v-list-tile-content>
          <v-list-tile-title>add connection</v-list-tile-title>
        </v-list-tile-content>
      </v-list-tile>

      <v-divider color="white"></v-divider>

      <v-list-tile
        v-for="connection in parent.connections"
        :key="connection.id"
        avatar
        @click="newSession(connection)"
      >
        <v-list-tile-content>
          <v-list-tile-title>
            <v-layout>
              {{connection.name}}
              <v-spacer></v-spacer>
              <v-icon small @click.stop="editConnection(connection)">edit</v-icon>
            </v-layout>
          </v-list-tile-title>
          <v-list-tile-sub-title>
            <v-layout>
              {{connection.host}}
              <v-spacer></v-spacer>
              <v-icon color="red" small @click.stop="deleteConnection(connection)">delete</v-icon>
            </v-layout>
          </v-list-tile-sub-title>
        </v-list-tile-content>
      </v-list-tile>

      <v-list-tile v-if="parent.connections.length==0" avatar>
        <v-list-tile-content>
          <v-list-tile-title>No connections added yet.</v-list-tile-title>
        </v-list-tile-content>
      </v-list-tile>
    </v-list>
    <connectionDialog/>
    <connectionDeleteDialog/>
  </v-card>
</template>

<script>
import ConnectionDialog from "./Dialogs/ConnectionDialog";
import ConnectionDeleteDialog from "./Dialogs/ConnectionDeleteDialog";
export default {
  components: {
    ConnectionDialog,
    ConnectionDeleteDialog
  },
  computed: {
    href() {
      return window.href;
    },
    parent() {
      return this.$parent.$parent.$parent;
    }
  },
  methods: {
    addConnection() {
      bus.$emit("showConnectionDialog", {});
    },
    editConnection(connection) {
      bus.$emit("showConnectionDialog", connection);
    },
    deleteConnection(connection) {
      bus.$emit("showDeleteDialog", connection);
    },
    newSession(connection) {
      bus.$emit("newSession", connection);
    }
  },
  mounted() {}
};
</script>

<style lang=scss>
html {
  .v-navigation-drawer {
    .theme--light.v-card {
      background: transparent;
    }
  }
}
</style>
