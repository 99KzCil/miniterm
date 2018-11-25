<template>
  <div id="addEditConnection" class="flex flex1 column marginTop2">
    <div class="title talign flex valign halign">
      {{title}}
    </div>
    <div class="content marginTop2">
      <div class="flex valign inputContainer">
        <span>name</span>
        <input class="nameInput" type="text" v-model="$parent.connection.name">
      </div>
      <div class="flex valign inputContainer marginTop">
        <span>host</span>
        <input type="text" v-model="$parent.connection.host">
      </div>
      <div class="flex valign inputContainer marginTop">
        <span>port</span>
        <input type="text" v-model="$parent.connection.port">
      </div>
      <div class="flex valign inputContainer marginTop">
        <span>username</span>
        <input type="text" v-model="$parent.connection.username">
      </div>
      <div class="flex valign inputContainer marginTop">
        <span>password</span>
        <input type="password" v-model="$parent.connection.password">
      </div>
      <div class="flex valign inputContainer marginTop">
        <span>private key</span>
        <input :disabled="true" type="password" v-model="$parent.connection.privateKey">
      </div>
       <div class="flex valign inputContainer marginTop">
        <span>pk password</span>
        <input :disabled="true" type="password" v-model="$parent.connection.privateKeyPassword">
      </div>
    </div>
    <div class="footer flex marginTop2 column">
      <div class="flex buttonContainer">
        <button @click="save">save</button>
        <button @click="$parent.page='connections'">cancel</button>
      </div>
      <pre class="flex valign error marginTop">{{error}}</pre>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      title: "",
      error: ""
    };
  },
  mounted() {
    this.title = this.$parent.connection.id ? "edit connection" : "add connection";
    document.querySelector("#addEditConnection .nameInput").focus();
  },
  methods: {
    save() {
      this.check();
      if (this.error != "") return;
      this.$http
        .post("/api/connection/save", this.$parent.connection)
        .then(result, result);
      var that = this;
      function result(e) {
        if (e.status == 200) {
          that.$parent.page = "connections";
          that.$parent.load();
        } else {
          that.error = e.bodyText;
        }
      }
    },
    check() {
      var conn = this.$parent.connection;
      if (!conn.name) return (this.error = "enter name");
      if (!conn.host) return (this.error = "enter host");
      if (!isNumeric(conn.port)) return (this.error = "enter numeric port");
      if (!conn.username) return (this.error = "enter username");
      if (!conn.password && !conn.publickey)
        return (this.error = "enter password or public key");

      return (this.error = "");
    }
  }
};
function isNumeric(n) {
  return !isNaN(parseFloat(n)) && isFinite(n);
}
</script>

<style scoped lang=scss>
#addEditConnection {
  .title {
    padding: 0.5rem;
    height: 39px;
  }
  .content {
    .inputContainer {
      justify-content: space-between;
      input {
        width: 150px;
        padding: 0.25rem;
      }
    }
  }
  .footer {
    .buttonContainer {
      justify-content: space-between;
      button {
        transition: background 0.25s;
        background: #fff;
        border: 1px solid rgba(0, 0, 0, 0.1);
        width: 75px;
        height: 30px;
        cursor: pointer;
        &:hover {
          background: lightgray;
        }
      }
    }
    .error {
      color: red;
      white-space: pre-wrap;
      word-break: break-all;
    }
  }
}
</style>
