<template>
  <v-layout fill-height class="layout" align-center>
    <v-btn
      small
      @click="activate(session)"
      @click.middle.stop="close(session)"
      :color="getColor(session)"
      v-for="session in orderedSessions"
      :key="session.id"
    >{{session.connection.name}}</v-btn>
    <close-session-dialog/>
  </v-layout>
</template>

<script>
import CloseSessionDialog from "./Dialogs/CloseSessionDialog";
export default {
  components: {
    CloseSessionDialog
  },
  computed: {
    orderedSessions() {
      return this.sessions.sort(function(a, b) {
        return parseInt(a.order) - parseInt(b.order);
      });
    },
    sessions() {
      return this.$parent.$parent.$parent.sessions;
    }
  },
  methods: {
    getColor(session) {
      var color = "";
      color = session.active ? "" : "lighten-3 ";
      if (session.state == "stopped") color += "red";
      if (session.state == "closed") color += "red";
      if (session.state == "started") color += "green";
      return color;
    },
    activate(session) {
      bus.$emit("activateSession", session);
    },
    close(session) {
      if (session.state == "started") {
        bus.$emit("showCloseSessionDialog", session);
      } else {
        bus.$emit("removeSession", session);
      }
    }
  }
};
</script>

<style scoped lang="scss">
@media only screen and (max-width: 959px) {
  .hidden-sm-and-down {
    display: none !important;
  }
}
.v-btn:first-child {
  margin-left: 0;
}
.layout {
  overflow-x: auto;
  -webkit-overflow-scrolling: touch;

  &::-webkit-scrollbar {
    width: 0px;
    height: 5px;
  }

  &::-webkit-scrollbar-track {
    background: rgba(0, 0, 0, 0.1);
  }

  &::-webkit-scrollbar-thumb {
    background: #b0bec5 !important;
  }
}
</style>
