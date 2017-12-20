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
        				$translatePartialLoader.addPart('blast');
        				return $translate.refresh();
            		}],
            		loadPlugin: function ($ocLazyLoad) {
	            		return $ocLazyLoad.load(['apps/home/home.service.js'
	            			, 'apps/blast/feed-item.service.js'
	            			, 'apps/blast/keyword.service.js'
	            			, 'apps/blast/social-follow.service.js'
	            			, 'apps/home/home.controller.js']);
		        }
            }
        });
    }
})();
