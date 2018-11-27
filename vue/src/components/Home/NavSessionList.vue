<template>
  <v-layout fill-height class="layout ml-3">
    <!-- <v-divider vertical class="grey lighten-2"></v-divider> -->
    <v-btn
      @click="activate(session)"
      :color="getColor(session)"
      v-for="session in sessions"
      :key="session.id"
    >{{session.connection.name}}</v-btn>
  </v-layout>
</template>

<script>
export default {
  data() {
    return {
      sessions: []
    };
  },
  mounted() {
    this.sessions = this.$parent.$parent.$parent.sessions;
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
.layout {
  overflow-x: auto;
  -webkit-overflow-scrolling: touch;
}

::-webkit-scrollbar {
  display: none;
}

::-webkit-scrollbar-track {
  background: rgba(0, 0, 0, 0.1);
}

::-webkit-scrollbar-thumb {
  background: #b0bec5 !important;
}
</style>
