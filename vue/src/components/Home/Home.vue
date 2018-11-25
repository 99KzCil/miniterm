<template>
  <div id="home" class="">
    <sidebar />
    <div class="content flex flex1 column">
      <div class="header flex">
        <span @click="activate(session)" @click.middle="confirmRemove(session)" class="conenctionName noselect pointer" :class="[session.active?'active':'',session.state]" v-for="session in sessions" :key="session.id">
          {{session.connection.name}}
        </span>
      </div>
      <div id="middle" class="flex1 flex">

      </div>
    </div>
  </div>
</template>

<script>
import Vue from "vue";
import Store from "@/Store";
import SockJS from "sockjs-client";
import Sidebar from "./Sidebar";
var socket;
var term;
var currentSession;
var fitTimeout = {};
export default {
  components: {
    Sidebar
  },
  data() {
    return {
      sessions: []
    };
  },
  mounted() {
    socket = new SockJS("/api/ssh");
    var that = this;
    socket.onopen = function() {
      that.$http.get("/api/session/getSessions").then(result);
      function result(e) {
        that.sessions = [];
        for (var key in e.body) {
          if (e.body.hasOwnProperty(key)) {
            that.sessions.push(e.body[key]);
          }
        }
        if (that.sessions.length > 0) {
          that.activate(that.sessions[0]);
        }
      }
    };
    socket.onmessage = function(e) {
      if (term) {
        if (e.data.indexOf("///term///") == 0) {
          term.write(e.data.split("///term///")[1]);
        } else if (e.data.indexOf("///setStarted///") == 0) {
          currentSession.state = "started";
        } else if (e.data.indexOf("///setClosed///") == 0) {
          currentSession.state = "closed";
        } else {
          console.log(e.data);
        }
      }
    };
    window.removeEventListener("resize", that.handleResize);
    window.addEventListener("resize", that.handleResize);
  },
  methods: {
    handleResize() {
      clearTimeout(fitTimeout);
      fitTimeout = setTimeout(() => {
        term.fit();
        socket.send(
          "setPtySizeSSH|||" +
            currentSession.id +
            "|||" +
            term.rows +
            "|||" +
            term.cols
        );
      }, 100);
    },
    newConnection(connection) {
      this.$http
        .post("/api/session/newSession", connection.id)
        .then(result, result);
      function result(e) {
        var session = e.body;
        this.sessions.push(session);
        this.activate(session);
        1;
      }
    },
    activate(session) {
      if (session === currentSession) return;
      currentSession = session;
      this.deactivate();
      session.active = true;
      if (term == null) {
        term = new Terminal({
          theme: theme,
          fontFamily: "Lucida Console,monospace",
          fontSize: 16
        });
        term.open(document.getElementById("middle"));
      }
      Vue.nextTick(() => {
        term.fit();
        if (session.state != "closed") {
          socket.send(
            "activateSSH|||" +
              session.id +
              "|||" +
              term.rows +
              "|||" +
              term.cols
          );
        } else {
          socket.send(
            "sendCacheSSH|||" +
              session.id +
              "|||" +
              term.rows +
              "|||" +
              term.cols
          );
        }
      });
      window.term = term;

      term.off("key", this.termKeyPress);
      term.on("key", this.termKeyPress);
      term.focus();
      term.reset();
    },
    deactivate() {
      this.sessions.forEach(session => {
        session.active = false;
      });
      this.$forceUpdate();
    },
    termKeyPress(key) {
      if (key.charCodeAt(0) == 27 && currentSession.state == "closed") {
        this.removeSession(currentSession);
      } else {
        socket.send("keySSH|||" + currentSession.id + "|||" + key);
      }
    },
    confirmRemove(session) {
      console.log(session.state);
      if (session.state == "started") {
        if (confirm("close active session?")) {
          this.removeSession(session);
        }
      } else {
        this.removeSession(session);
      }
    },
    removeSession(session) {
      this.$http.post("/api/session/remove", session.id).then(result);
      function result() {
        var activateAnother = currentSession == session;
        this.sessions = this.sessions.filter(s => s.id != session.id);
        if (activateAnother) {
          if (this.sessions.length > 0) {
            this.activate(this.sessions[0]);
          } else {
            term.destroy();
            term = null;
          }
        }
      }
    }
  }
};
function termResized() {
  socket.send(
    "setPtySizeSSH|||" +
      currentSession.id +
      "|||" +
      term.rows +
      "|||" +
      term.cols
  );
}
var theme = {
  foreground: "#000",
  background: "#f0f0f0",
  cursor: "#000",
  selection: "rgba(0, 0, 0, 0.3)"
  // black: "#000000",
  // red: "#ff0000",
  // brightRed: "#e06c75",
  // green: "#00ff00",
  // brightGreen: "#A4fFA1",
  // brightYellow: "#EDff96",
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
};
</script>

<style scoped lang=scss>
#home {
  max-width: 100%;
  min-width: 100%;
  .content {
    margin-left: 300px;
    height: 100%;
    .header {
      min-height: 71px;
      border-bottom: 1px solid rgba(0, 0, 0, 0.1);
      flex-wrap: wrap;
      padding-left: 1rem;
      padding-top: 1rem;
      .conenctionName {
        border-radius: 15px;
        margin-right: 1rem;
        margin-bottom: 1rem;
        width: 150px;
        white-space: nowrap;
        overflow: hidden !important;
        text-overflow: ellipsis;
        padding: 0 0.5rem;
        line-height: 40px;
        height: 40px;
        transition: background 0.25s;
        &.stopped {
          background: rgba(255, 0, 0, 0.1);
        }
        &.closed {
          background: rgba(255, 0, 0, 0.1);
        }
        &.started {
          background: rgba(0, 255, 0, 0.1);
        }
        &.active {
          &.stopped {
            background: red;
            color: #fff;
          }
          &.started {
            background: green;
            color: #fff;
          }
          &.closed {
            background: red;
            color: #fff;
          }
        }
      }
    }
    #middle {
      min-width: 0;
    }
  }
}
</style>
