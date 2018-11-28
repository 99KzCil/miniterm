import Vue from 'vue'
import Store from "./Store"
import Router from 'vue-router'
import Login from '@/components/Login'
import Home from '@/components/Home/Home'

Vue.use(Router)
console.log("router, " + window.subdir);

var router = new Router({
  mode: 'history',
  base: window.subdir,
  routes: [{
    path: '/',
    component: Login
  }, {
    path: '/home',
    component: Home
  }]
})


router.beforeEach(function (to, from, next) {
  if (to.path != "/" && Store.user == null) {
    next({
      path: '/'
    });
  } else {
    next();
  }
});

export default router;