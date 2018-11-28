<template>
  <v-dialog v-model="dialog" width="400" @keydown.esc="dialog = false" @keydown.enter="saveConnection" @keydown.space="saveConnection">
    <v-card id="connectionDialog">
      <v-card-title class="headline primary white--text" dark>
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
            :error-messages="passwordRules()"
            v-model="connection.password"
            label="password"
            type="password"
          ></v-text-field>

          <div
            @click="selectFile"
            @drop.prevent.stop="drop"
            @dragover.prevent="dragging=true"
            @dragleave.prevent="dragging=false"
            class="dropzone pointer mt-3"
            :class="{dragging:dragging}"
          >
            <div class="pointer">
              <span class="pointer">{{fileMessage}}</span>
              <input @change="fileSelected" type="file">
            </div>
          </div>

          <v-text-field
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
          color="616161"
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
      dragging: false,
      valid: false,
      connection: {},
      fileMessage: "Drag private key file, or click to select one.",
      passwordError: "",
      rules: [v => !!v || "Required"]
    };
  },
  mounted() {
    bus.$off("showConnectionDialog");
    bus.$on("showConnectionDialog", connection => {
      this.fileMessage = "Drag private key file, or click to select one.";
      this.dragging = false;
      this.dialog = true;
      this.connection = connection;
      this.title = connection.id ? "edit connection" : "add connection";
    });
  },
  methods: {
    passwordRules() {
      if (!this.connection.password && !this.connection.privateKey) {
        return ["Password or private key must be set"];
      } else {
        return [];
      }
    },
    saveConnection() {
      this.$refs.form.validate();
      if (!this.valid) return;
      if (!this.connection.password && !this.connection.privateKey) return;
      this.$http.post(subdir + "/api/connection/save", this.connection).then(() => {
        this.dialog = false;
        bus.$emit("loadConnections");
      });
    },
    selectFile() {
      document.querySelector("#connectionDialog .dropzone input").click();
    },
    drop(e) {
      this.handleFile(e.dataTransfer.files[0]);
    },
    fileSelected(e) {
      this.handleFile(e.target.files[0]);
    },
    handleFile(e) {
      var that = this;
      if (e.size > 5000) {
        that.fileMessage = "File cant be larget than 5KB";
        that.dragging = false;
        return;
      }
      var reader = new FileReader();
      reader.onload = ready;
      function ready(a) {
        that.connection.privateKey = a.target.result;
        that.fileMessage = "OK!";
      }
      reader.readAsText(e);
    }
  }
};
</script>
<style scoped lang=scss>
.dropzone {
  border: 1px solid rgba(0, 0, 0, 0.1);
  padding: 2rem;
  display: flex;
  align-content: center;
  justify-content: center;
  transition: background 0.25s;
  * {
    pointer-events: none;
  }
  input {
    display: none;
  }
  &.dragging {
    background: tomato;
    color: #fff;
  }
}
</style>