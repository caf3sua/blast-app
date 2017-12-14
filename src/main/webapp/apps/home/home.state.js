(function() {
    'use strict';

    angular
        .module('app')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider', '$urlRouterProvider', 'MODULE_CONFIG'];

    function stateConfig($stateProvider, $urlRouterProvider,   MODULE_CONFIG) {
        $stateProvider.state('app.home', {
            parent: 'app',
            url: '/home',
            templateUrl: 'apps/home/home.html',
            data : { title: 'Home' },
            controller: "HomeController",
            controllerAs: 'vm',
            resolve: {
            		translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate,$translatePartialLoader) {
        				$translatePartialLoader.addPart('dashboard');
        				return $translate.refresh();
            		}],
            		loadPlugin: function ($ocLazyLoad) {
	            		return $ocLazyLoad.load(['apps/home/home.service.js', 'apps/home/home.controller.js']);
		        }
            }
        });
    }
})();
