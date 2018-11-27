<template></template>

<script>
import SockJS from "sockjs-client";
var socket;
export default {
  mounted() {
    socket = new SockJS("/api/ssh");
    socket.onopen = this.socketOpened;
    socket.onmessage = this.socketMessageReceived;

    bus.$off("socketSend");
    bus.$on("socketSend", this.socketSend);
  },
  beforeDestroy() {
    socket.close();
  },
  methods: {
    socketOpened() {
      bus.$emit("socketConnected");
    },
    socketSend(data) {
      socket.send(JSON.stringify(data));
    },
    socketMessageReceived(e) {
      var data = JSON.parse(e.data);
      switch (data.command) {
        case "terminal":
          bus.$emit("terminalData", data);
          break;
        case "setState":
          bus.$emit("stateData", data);
          break;
        default:
          break;
      }
    }
  }
};
</script>
