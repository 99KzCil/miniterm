<template></template>
<script>
var currentSession;
export default {
  mounted() {
    bus.$off("socketConnected");
    bus.$on("socketConnected", this.loadSessions);

    bus.$off("newSession");
    bus.$on("newSession", this.newSession);

    bus.$off("activateSession");
    bus.$on("activateSession", this.activateSession);

    bus.$off("removeSession");
    bus.$on("removeSession", this.removeSession);

    bus.$off("stateData");
    bus.$on("stateData", this.stateData);

    // bind sessions array with home's
    this.sessions = this.$parent.$parent.sessions;

    // to inform home component that loading is finished
    this.loadingSessions = this.$parent.$parent.loadingSessions;
  },
  methods: {
    loadSessions() {
      this.$http.get(href + "api/session/getSessions").then(e => {
        this.sessions.length = 0;
        for (var key in e.body)
          if (e.body.hasOwnProperty(key)) this.sessions.push(e.body[key]);
        if (this.sessions.length > 0) {
          if (localStorage.currentSessionId) {
            var remembered = false;
            this.sessions.forEach(session => {
              if (session.id == localStorage.currentSessionId) {
                this.activateSession(session);
                remembered = true;
                return;
              }
            });
            if (!remembered) this.activateSession(this.sessions[0]);
          } else {
            this.activateSession(this.sessions[0]);
          }
        }
        this.$parent.$parent.loadingSessions = false;
      });
    },
    newSession(connection) {
      this.$http.post(href + "api/session/newSession", connection.id).then(e => {
        this.sessions.push(e.body);
        this.activateSession(e.body);
      });
    },
    activateSession(session) {
      if (currentSession == session) {
        bus.$emit("terminalFocus");
        return;
      }
      this.deactivateAll();
      session.active = true;
      currentSession = session;
      bus.$emit("activateTerminal", session);
    },
    removeSession(session) {
      this.$http.post(href + "api/session/remove", session.id).then(() => {
        this.$parent.$parent.sessions = this.$parent.$parent.sessions.filter(
          e => {
            return e.id != session.id;
          }
        );
        this.sessions = this.$parent.$parent.sessions;
        if (currentSession == session && this.sessions.length > 0)
          this.activateSession(this.sessions[0]);
        if (this.sessions.length == 0) bus.$emit("removeTerminal");
      });
    },
    deactivateAll() {
      this.sessions.forEach(session => {
        session.active = false;
      });
      // this is necessary
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