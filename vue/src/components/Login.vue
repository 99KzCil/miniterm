<template>
  <v-app v-if="loaded">
    <v-container fluid fill-height>
      <v-layout align-center justify-center>
        <v-flex md6 xl3>
          <v-card>
            <v-toolbar flat color="primary" dark>
              <v-toolbar-title>miniterm login</v-toolbar-title>
            </v-toolbar>
            <v-card-title primary-title>
              <v-flex>
                <v-form @submit.stop.prevent="login">
                  <v-text-field label="hidden" style="display:none"></v-text-field>
                  <v-text-field v-model="user.username" label="username" required></v-text-field>
                  <v-text-field v-model="user.password" type="password" label="password" required></v-text-field>
                  <v-btn type="submit" :loading="working" @click.stop.prevent="login" class="ma-0 text-lowercase" dark color="secondary">Login</v-btn>
                </v-form>
              </v-flex>
            </v-card-title>
          </v-card>
        </v-flex>
      </v-layout>
    </v-container>
  </v-app>
</template>

<script>
import Store from "@/Store";
import Router from "@/Router";
var component;
export default {
  data() {
    return {
      user: {},
      valid: false,
      working: false,
      loaded: false
    };
  },
  mounted() {
    component = this;
    bus.$off("logout");
    bus.$on("logout", () => {
      this.$http.post("/api/login/logout").then(() => {
        Router.replace("/");
      });
    });
    this.$http.post("/api/login/check").then(result, result);
  },
  methods: {
    login() {
      this.working = true;
      this.$http.post("/api/login", this.user).then(result, result);
    }
  }
};

function result(e) {
  if (e.status == 200) {
    Store.user = e.data;
    Router.replace("/home/");
    component.working = false;
    component.loaded = true;
  } else {
    Store.user = null;
    component.working = false;
    component.loaded = true;
  }
}
</script>

<style lang=scss>
.icon {
  border-radius: 50%;
}
</style>
