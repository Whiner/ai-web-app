import Vue from 'vue';
import { library } from '@fortawesome/fontawesome-svg-core';
import {
  faAmbulance,
  faCat,
  faChartArea,
  faCommentMedical,
  faEdit,
  faStethoscope,
  faTrash,
} from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
import router from './router/index';
import App from './App.vue';
import './vuetify';
import 'vuetify/dist/vuetify.min.css';

library.add(faCat);
library.add(faAmbulance);
library.add(faChartArea);
library.add(faStethoscope);
library.add(faCommentMedical);
library.add(faTrash);
library.add(faEdit);

Vue.component('font-awesome-icon', FontAwesomeIcon);

Vue.config.productionTip = false;

new Vue({
    router,
    render: h => h(App),
}).$mount('#app');
