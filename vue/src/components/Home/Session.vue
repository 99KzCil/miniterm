<template>

</template>

<script>
export default {
  mounted() {
    bus.$off("socketConnected", socketConnected);
    bus.$on("socketConnected", socketConnected);
    var that = this;
    function socketConnected() {
      that.loadSessions();
    }
  },
  methods: {
    loadSessions() {
      this.$http.get("/api/session/getSessions").then(result);
      function result(e) {
        this.sessions = [];
        for (var key in e.body)
          if (e.body.hasOwnProperty(key)) this.sessions.push(e.body[key]);
        if (this.sessions.length > 0) this.activate(this.sessions[0]);
      }
    }
  }
};
</script>