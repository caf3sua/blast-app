(function() {
    'use strict';

    angular
        .module('app')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider', '$urlRouterProvider', 'MODULE_CONFIG'];

    function stateConfig($stateProvider, $urlRouterProvider,   MODULE_CONFIG) {
        $stateProvider.state('app.trend-hate', {
            parent: 'app',
            url: '/trend-hate',
            templateUrl: 'apps/blast/trend/trend-hate.html',
            data : { title: 'TrendHate' },
            controller: "TrendHateController",
            controllerAs: 'vm',
            resolve: {
            		translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate,$translatePartialLoader) {
        				$translatePartialLoader.addPart('blast');
        				return $translate.refresh();
            		}],
            		loadPlugin: function ($ocLazyLoad) {
	            		return $ocLazyLoad.load(['ui.bootstrap'
	            			, 'apps/blast/feed-item.service.js'
	            			, 'apps/blast/keyword.service.js'
	            			, 'apps/blast/trend/trend-hate.controller.js']);
		        }
            }
        });
    }
})();
