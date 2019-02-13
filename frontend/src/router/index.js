import Vue from 'vue';
import Router from 'vue-router';
import Diagnosis from '../views/Diagnosis.vue';
import Main from '../views/Main.vue';
import App from '../App.vue';
import Settings from '../views/Settings.vue';

Vue.use(Router);

export default new Router({ // тут вопрос
    mode: 'history',
    base: process.env.BASE_URL,
    app: App,
    routes: [
        {
            path: '/',
            component: Main,
            children: [{
                path: '',
                name: 'home',
                component: Diagnosis,
            },
            {
                path: '/animals',
                name: 'animals',
                component: Diagnosis,
            },
            {
                path: '/extremum',
                name: 'extremum',
                component: Diagnosis,
            },
            {
                path: '/settings',
                name: 'settings',
                component: Settings,
            }],
        },

    ],
});
