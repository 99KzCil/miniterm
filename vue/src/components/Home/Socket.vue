<template>

</template>

<script>
import SockJS from "sockjs-client";
var socket;
export default {
  mounted() {
    socket = new SockJS("/api/ssh");
    socket.onopen = function() {
      bus.$emit("socketConnected");
    };
  },
  beforeDestroy() {
    socket.close();
  }
};
</script>
