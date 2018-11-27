<template></template>

<script>
export default {
  mounted() {
    bus.$off("socketConnected");
    bus.$on("socketConnected", this.loadSessions);

    bus.$off("newSession");
    bus.$on("newSession", this.newSession);

    bus.$off("activateSession");
    bus.$on("activateSession", this.activateSession);

    bus.$off("stateData");
    bus.$on("stateData", this.stateData);

    // bind sessions array with home's
    this.sessions = this.$parent.$parent.sessions;

    // to inform home component that loading is finished
    this.loadingSessions = this.$parent.$parent.sessions;
  },
  methods: {
    loadSessions() {
      this.$http.get("/api/session/getSessions").then(e => {
        this.sessions.length = 0;
        for (var key in e.body)
          if (e.body.hasOwnProperty(key)) this.sessions.push(e.body[key]);
        if (this.sessions.length > 0) this.activateSession(this.sessions[0]);
        this.loadingSessions = false;
      });
    },
    newSession(connection) {
      this.$http.post("/api/session/newSession", connection.id).then(e => {
        this.sessions.push(e.body);
        this.activateSession(e.body);
      });
    },
    activateSession(session) {
      this.deactivateAll();
      session.active = true;
      bus.$emit("activateTerminal", session);
    },
    deactivateAll() {
      this.sessions.forEach(session => {
        session.active = false;
      });
      this.$forceUpdate();
    },
    stateData(data) {
      this.sessions.forEach(session => {
        if (session.id == data.sessionId) {
          session.state = data.state;
          return;
        }
      });
    }
  }
};
</script>