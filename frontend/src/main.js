import Vue from 'vue';
import { library } from '@fortawesome/fontawesome-svg-core';
import { faAmbulance, faCat, faChartArea } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
import Vuetify from 'vuetify';
import router from './router/index';
import App from './App.vue';

library.add(faCat);
library.add(faAmbulance);
library.add(faChartArea);

Vue.component('font-awesome-icon', FontAwesomeIcon);

Vue.config.productionTip = false;
Vue.use(Vuetify);

new Vue({
    router,
    render: h => h(App),
}).$mount('#app');
