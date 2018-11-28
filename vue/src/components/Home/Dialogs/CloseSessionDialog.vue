<template>
  <v-dialog v-model="dialog" width="400" @keydown.esc="dialog = false" @keydown.enter="closeSession" @keydown.space="closeSession">
    <v-card>
      <v-card-title class="headline primary white--text" dark>
        <v-icon left dark>desktop_windows</v-icon>
        <span class="ml-4">close session</span>
        <v-spacer></v-spacer>
        <v-icon dark @click="dialog=false">cancel</v-icon>
      </v-card-title>
      <v-card-text>running session will be closed, are you sure?</v-card-text>
      <v-card-actions class="pa-3">
        <v-btn color="red" dark class="text-lowercase" @click="dialog=false">no</v-btn>
        <v-spacer></v-spacer>
        <v-btn color="secondary" class="text-lowercase" dark @click="closeSession">yes</v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script>
export default {
  data() {
    return {
      dialog: false,
      session: {}
    };
  },
  mounted() {
    bus.$off("showCloseSessionDialog");
    bus.$on("showCloseSessionDialog", session => {
      this.session = session;
      this.dialog = true;
    });
  },
  methods: {
    closeSession() {
      this.dialog = false;
      bus.$emit("removeSession", this.session);
    }
  }
};
</script>