// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue';
import Vuetify from 'vuetify';
import router from './router';
import Home from './views/Home';

Vue.config.productionTip = false;

/* eslint-disable no-new */
Vue.use(Vuetify);

new Vue({
  el: '#app',
  router,
  components: { Home },
  template: '<Home/>',
});
