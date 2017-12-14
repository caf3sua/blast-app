(function() {
    'use strict';

    angular
        .module('app')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider', '$urlRouterProvider', 'MODULE_CONFIG'];

    function stateConfig($stateProvider, $urlRouterProvider,   MODULE_CONFIG) {
        $stateProvider.state('app.trend-love', {
            parent: 'app',
            url: '/trend-love',
            templateUrl: 'apps/blast/trend/trend-love.html',
            data : { title: 'TrendLove' },
            controller: "TrendLoveController",
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
	            			, 'apps/blast/trend/trend-new-dialog.controller.js'
	            			, 'apps/blast/trend/trend-love.controller.js']);
		        }
            }
        });
    }
})();
