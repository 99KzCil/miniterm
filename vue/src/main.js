import Vue from 'vue'
import App from './App'
import Router from './Router'
import VueResource from 'vue-resource'
Vue.use(VueResource);

import "./assets/index.scss"


import "xterm/dist/xterm.css"
import { Terminal } from 'xterm';
import * as fit from 'xterm/lib/addons/fit/fit';
import * as attach from 'xterm/lib/addons/attach/attach';
Terminal.applyAddon(fit);
Terminal.applyAddon(attach);
window.Terminal = Terminal;


new Vue({
  el: '#app',
  router: Router,
  components: {
    App
  },
  template: '<App/>'
})
