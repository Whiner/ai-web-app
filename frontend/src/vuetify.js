import Vue from 'vue';
import Vuetify from 'vuetify/lib';
import 'vuetify/src/stylus/app.styl';
import ru from 'vuetify';

Vue.use(Vuetify, {
    customProperties: true,
    theme: {
        primary: '#b1b1b1', //#424242',
        secondary: '#424242', //#424242',
    },
    options: {
        customProperties: true,
    },
    lang: {
        locales: { ru },
        current: 'ru',
    },
});
