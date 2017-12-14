(function() {
    'use strict';

    angular
        .module('app')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider', '$urlRouterProvider', 'MODULE_CONFIG'];

    function stateConfig($stateProvider, $urlRouterProvider,   MODULE_CONFIG) {
        $stateProvider
        .state('app.trend-new', {
            parent: 'app',
            url: '/trend-new',
            data: {
                authorities: [],
                title: 'TrendDetail' 
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'apps/blast/trend/trend-new-dialog.html',
                    controller: 'TrendNewDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
	                    	loadPlugin: function ($ocLazyLoad) {
		    	            		return $ocLazyLoad.load(['apps/blast/trend/trend-new-dialog.controller.js']);
		    		        }
//	                    	entity: ['Keyword', function(Keyword) {
//	                            return Keyword.getByName({name : $stateParams.keywordName}).$promise;
//	                        }]
//	                		, feed: ['FeedItem', function(FeedItem) {
//	                            return FeedItem.get({id : $stateParams.feedId}).$promise;
//	                        }]
                    }
                }).result.then(function() {
                    //$state.go('^', null, { reload: 'trend' });
                		$state.go('app.trend-love', null, { reload: 'app.trend-love' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
    }
})();
