import Vue from 'vue'
import App from './App'
import Router from './Router'
import VueResource from 'vue-resource'
Vue.use(VueResource)

import "./assets/index.scss"

new Vue({
  el: '#app',
  router: Router,
  components: {
    App
  },
  template: '<App/>'
})
