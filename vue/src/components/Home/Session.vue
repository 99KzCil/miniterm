<template>

</template>

<script>
export default {
  mounted() {
    bus.$off("socketConnected");
    bus.$on("socketConnected", this.loadSessions);

    bus.$off("newSession");
    bus.$on("newSession", this.newSession);

    bus.$off("activateSession");
    bus.$on("activateSession", this.activateSession);
    this.sessions = this.$parent.$parent.sessions;
  },
  methods: {
    loadSessions() {
      this.$http.get("/api/session/getSessions").then(e => {
        this.sessions.length = 0;
        for (var key in e.body)
          if (e.body.hasOwnProperty(key)) this.sessions.push(e.body[key]);
        if (this.sessions.length > 0) this.activateSession(this.sessions[0]);
      });
    },
    newSession(connection) {
      console.log(connection);
      this.$http.post("/api/session/newSession", connection.id).then(e => {
        this.sessions.push(e.body);
        this.activateSession(e.body);
      });
    },
    activateSession(session) {
      this.deactivateAll();
      session.active = true;
    },
    deactivateAll() {
      this.sessions.forEach(session => {
        session.active = false;
      });
      this.$forceUpdate();
    }
  }
};
</script>