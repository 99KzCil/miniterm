<template>
  <v-dialog v-model="dialog" width="400">
    <v-card>
      <v-card-title class="headline blue-grey white--text" dark>
        <v-icon left dark>desktop_windows</v-icon>
        <span class="ml-4">delete connection</span>
        <v-spacer></v-spacer>
        <v-icon dark @click="dialog=false">cancel</v-icon>
      </v-card-title>
      <v-card-text>{{connection.name}} will be deleted, are you sure?</v-card-text>
      <v-card-actions class="pa-3">
        <v-btn color="red" dark class="text-lowercase" @click="dialog=false">no</v-btn>
        <v-spacer></v-spacer>
        <v-btn
          :loading="working"
          color="blue-grey"
          class="text-lowercase"
          dark
          @click="deleteConnection"
        >yes</v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script>
export default {
  data() {
    return {
      dialog: false,
      working: false,
      connection: {}
    };
  },
  mounted() {
    bus.$off("showDeleteDialog");
    bus.$on("showDeleteDialog", connection => {
      this.connection = connection;
      this.dialog = true;
    });
  },
  methods: {
    deleteConnection() {
      this.working = true;
      this.$http.post("/api/connection/remove", this.connection).then(() => {
        this.working = false;
        this.dialog = false;
        bus.$emit("loadConnections");
      });
    }
  }
};
</script>