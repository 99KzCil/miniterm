<template></template>

<script>
import Vue from "vue";
var terminal = null;
var component = null;
var currentSession = null;
var resizeTimeout = {};
export default {
  mounted() {
    component = this;
    bus.$off("activateTerminal");
    bus.$on("activateTerminal", this.activateTerminal);

    bus.$off("terminalData");
    bus.$on("terminalData", this.terminalData);

    window.removeEventListener("resize", this.windowResized);
    window.addEventListener("resize", this.windowResized);
  },
  beforeDestroy() {
    terminal.destroy();
    terminal = null;
  },
  methods: {
    activateTerminal(session) {
      currentSession = session;
      Vue.nextTick(() => {
        component.initTerminal();
        terminal.fit();
        if (session.state != "closed") {
          var data = {
            command: "activateSSH",
            sessionId: session.id,
            rows: terminal.rows,
            cols: terminal.cols
          };
          bus.$emit("socketSend", data);
        } else {
          var data = {
            command: "sendCacheSSH",
            sessionId: session.id,
            rows: terminal.rows,
            cols: terminal.cols
          };
          bus.$emit("socketSend", data);
        }
      });
    },
    initTerminal() {
      if (terminal == null) {
        terminal = new Terminal({
          theme: {
            foreground: "#000",
            background: "#f0f0f0",
            cursor: "#000",
            selection: "rgba(0, 0, 0, 0.3)"
          },
          fontFamily: "Lucida Console,monospace",
          fontSize: 16
        });
        terminal.open(document.getElementById("terminalContainer"));
      }
      terminal.off("key", this.terminalKeyPressed);
      terminal.on("key", this.terminalKeyPressed);
      terminal.focus();
      terminal.reset();
    },
    terminalKeyPressed(e) {
      var data = {
        command: "keySSH",
        sessionId: currentSession.id,
        key: e
      };
      bus.$emit("socketSend", data);
    },
    terminalData(data) {
      terminal.write(data.text);
    },
    windowResized() {
      if (terminal) {
        clearTimeout(resizeTimeout);
        resizeTimeout = setTimeout(() => {
          terminal.fit();
          var data = {
            command: "setPtySizeSSH",
            sessionId: currentSession.id,
            rows: terminal.rows,
            cols: terminal.cols
          };
          bus.$emit("socketSend", data);
        }, 100);
      }
    }
  }
};
</script>