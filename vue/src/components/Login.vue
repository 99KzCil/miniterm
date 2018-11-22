<template>
  <div id=login v-if="loaded" class="flex flex1 halign valign column">
    <div class="title column valign">
      <h1>miniTerm</h1>
    </div>
    <form @submit.prevent="login" class="flex column">
      <input type="text" placeholder="username" v-model="user.username">
      <input type="password" placeholder="password" v-model="user.password">
      <input type="submit" :disabled="working" value="Login">
    </form>
  </div>
</template>

<script>
import Store from "@/Store";
import Router from "@/Router";
var component;
export default {
  data() {
    return {
      user: {},
      working: false,
      loaded: false
    };
  },
  mounted() {
    component = this;
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

<style scoped lang=scss>
#login {
  .title {
    margin-bottom: 1rem;
    width: 222px;
    h1 {
      text-align: center;
      font-size: 38.8px;
      color: #2d6bbf;
    }
    img {
      width: 222px;
      height: 46.17px;
    }
  }
  input {
    margin-bottom: 1rem;
    padding: 0.5rem;
    border: 1px solid rgba(0, 0, 0, 0.1);
  }
}
</style>
