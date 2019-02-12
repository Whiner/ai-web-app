import Vue from 'vue';
import { library } from '@fortawesome/fontawesome-svg-core';
import { faAmbulance, faCat, faChartArea } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
import router from './router/index';
import Main from './views/Main.vue';

library.add(faCat);
library.add(faAmbulance);
library.add(faChartArea);

Vue.component('font-awesome-icon', FontAwesomeIcon);

Vue.config.productionTip = false;

new Vue({
    router,
    render: h => h(Main),
}).$mount('#app');
