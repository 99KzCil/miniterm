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

    bus.$off("removeTerminal");
    bus.$on("removeTerminal", this.removeTerminal);

    bus.$off("terminalData");
    bus.$on("terminalData", this.terminalData);

    bus.$off("terminalFocus");
    bus.$on("terminalFocus", this.terminalFocus);

    window.removeEventListener("resize", this.windowResized);
    window.addEventListener("resize", this.windowResized);
  },
  beforeDestroy() {
    if (terminal) {
      terminal.destroy();
      terminal = null;
    }
  },
  methods: {
    activateTerminal(session) {
      if (currentSession == session) return;
      Vue.nextTick(() => {
        component.$parent.$parent.showTerminal = false;

        localStorage.currentSessionId = session.id;
        component.initTerminal();
        terminal.fit();
        currentSession = session;
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
        setTimeout(() => {
          component.$parent.$parent.showTerminal = true;
        }, 150);
      });
    },
    initTerminal() {
      if (terminal == null) {
        terminal = new Terminal({
          theme: {
            foreground: "#000",
            background: "#fff",
            cursor: "#fafafa",
            selection: "rgba(0, 0, 0, 0.8)",
            // black: "#000000",
            // red: "#e06c75",
            // brightRed: "#e06c75",
            // green: "#80bd7e",
            // brightGreen: "#62ff5d",
            // brightYellow: "#EDDC96",
            // yellow: "#EDDC96",
            // magenta: "#e39ef7",
            // brightMagenta: "#e39ef7",
            // cyan: "#5fcbd8",
            // brightBlue: "#5fcbd8",
            // brightCyan: "#5fcbd8",
            // blue: "#5fcbd8",
            // white: "#d0d0d0",
            // brightBlack: "#808080",
            // brightWhite: "#ffffff"
          },
          allowTransparency: true,
          fontFamily: "Lucida Console,monospace",
          fontSize: 16,
          cursorStyle: "block",
          cursorBlink: true
        });
        terminal.open(document.getElementById("terminalContainer"));
      }
      terminal.off("data", this.terminalDataEntered);
      terminal.on("data", this.terminalDataEntered);

      terminal.off("key", this.terminalKeyPressed);
      terminal.on("key", this.terminalKeyPressed);

      terminal.focus();
      terminal.reset();
      setTimeout(() => {
        terminal.scrollToBottom();
      }, 150);
    },
    removeTerminal() {
      if (terminal) {
        terminal.destroy();
        terminal = null;
      }
    },
    terminalDataEntered(e) {
      var data = {
        command: "keySSH",
        sessionId: currentSession.id,
        key: e
      };
      bus.$emit("socketSend", data);
    },
    terminalKeyPressed(e) {
      if (currentSession.state == "closed") {
        if (e.charCodeAt(0) == 13) {
          this.activateTerminal(currentSession);
        }
        if (e.charCodeAt(0) == 27) {
          bus.$emit("removeSession", currentSession);
        }
      }
    },
    terminalData(data) {
      if (terminal) {
        terminal.write(data.text);
      }
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
          terminal.focus();
        }, 100);
      }
    },
    terminalFocus() {
      if (terminal) {
        terminal.focus();
      }
    }
  }
};
</script>
<style>
html .xterm .xterm-viewport {
  /* background-color: transparent !important;
  green: #80bd7e;
  brightgreen: #62ff5d;
  brightyellow: #eddc96;
  yellow: #eddc96; */
}
</style>
