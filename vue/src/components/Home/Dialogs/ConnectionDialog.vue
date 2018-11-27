<template>
  <v-dialog v-model="dialog" width="400">
    <v-card>
      <v-card-title class="headline blue-grey white--text" dark>
        <v-icon left dark>desktop_windows</v-icon>
        <span class="ml-4">{{title}}</span>
        <v-spacer></v-spacer>
        <v-icon dark @click="dialog=false">cancel</v-icon>
      </v-card-title>

      <v-card-text class="pa-4">
        <v-form ref="form" v-model="valid">
          <v-text-field label="hidden" style="display:none"></v-text-field>
          <v-text-field :rules="rules" v-model="connection.name" label="name" required></v-text-field>
          <v-text-field :rules="rules" v-model="connection.host" label="host" required></v-text-field>
          <v-text-field
            :rules="rules"
            v-model="connection.port"
            label="port"
            required
            type="number"
          ></v-text-field>
          <v-text-field :rules="rules" v-model="connection.username" label="username" required></v-text-field>
          <v-text-field
            :rules="rules"
            v-model="connection.password"
            label="password"
            required
            type="password"
          ></v-text-field>
          <v-text-field
            :disabled="true"
            type="password"
            v-model="connection.privateKey"
            label="privateKey"
          ></v-text-field>
          <v-text-field
            :disabled="true"
            type="password"
            v-model="connection.privateKeyPassword"
            label="privateKeyPassword"
          ></v-text-field>
        </v-form>
      </v-card-text>

      <v-divider></v-divider>

      <v-card-actions class="pa-3">
        <v-btn color="red" dark class="text-lowercase" @click="dialog=false">cancel</v-btn>
        <v-spacer></v-spacer>
        <v-btn
          :disabled="!valid"
          color="blue-grey"
          :dark="valid"
          class="text-lowercase"
          @click="saveConnection"
        >add</v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script>
export default {
  data() {
    return {
      title: "",
      dialog: false,
      valid: false,
      connection: {},
      rules: [v => !!v || "Required"]
    };
  },
  mounted() {
    bus.$off("showConnectionDialog");
    bus.$on("showConnectionDialog", connection => {
      this.dialog = true;
      this.connection = connection;
      this.title = connection.id ? "edit connection" : "add connection";
    });
  },
  methods: {
    saveConnection() {
      this.$refs.form.validate();
      if (!this.valid) return;
      this.$http.post("/api/connection/save", this.connection).then(() => {
        this.dialog = false;
        bus.$emit("loadConnections");
      });
    }
  }
};
</script>