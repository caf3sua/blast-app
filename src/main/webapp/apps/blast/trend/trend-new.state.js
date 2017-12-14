(function() {
    'use strict';

    angular
        .module('app')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider', '$urlRouterProvider', 'MODULE_CONFIG'];

    function stateConfig($stateProvider, $urlRouterProvider,   MODULE_CONFIG) {
        $stateProvider.state('app.trend-new', {
            parent: 'app',
            url: '/trend-new',
            templateUrl: 'apps/blast/trend/trend-new.html',
            data : { title: 'TrendNew' },
            controller: "TrendNewController",
            controllerAs: 'vm',
            resolve: {
            		translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate,$translatePartialLoader) {
        				$translatePartialLoader.addPart('blast');
        				return $translate.refresh();
            		}],
            		loadPlugin: function ($ocLazyLoad) {
	            		return $ocLazyLoad.load(['ng-file-model', 'angularFileUpload'
	            			, 'apps/blast/feed-item.service.js'
	            			, 'apps/blast/trend/trend-new.controller.js']);
		        }
            }
        });
    }
})();
