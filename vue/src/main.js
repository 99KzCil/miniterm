window.subdir = location.href.indexOf("miniterm") == -1 ? "" : "/miniterm";


import Vue from 'vue'
import Vuetify from 'vuetify'
import VueResource from 'vue-resource'
import Router from './Router'
import App from './App'

Vue.use(VueResource);
Vue.use(Vuetify, {
  theme: {
    primary: "#616161", // #E53935
  }
})

import 'vuetify/dist/vuetify.min.css'
import "./assets/index.scss"

import "xterm/dist/xterm.css"
import {
  Terminal
} from 'xterm';
import * as fit from 'xterm/lib/addons/fit/fit';
import * as attach from 'xterm/lib/addons/attach/attach';
Terminal.applyAddon(fit);
Terminal.applyAddon(attach);
window.Terminal = Terminal;

window.bus = new Vue;

new Vue({
  el: '#app',
  router: Router,
  components: {
    App
  },
  template: '<v-app><App/></v-app>'
})
