(function() {
    'use strict';

    angular
        .module('blastApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('trend', {
            parent: 'entity',
            url: '/trend?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'blastApp.trend.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/trend/trends.html',
                    controller: 'TrendController',
                    controllerAs: 'vm'
                }
            },
            params: {
                page: {
                    value: '1',
                    squash: true
                },
                sort: {
                    value: 'id,asc',
                    squash: true
                },
                search: null
            },
            resolve: {
                pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                    return {
                        page: PaginationUtil.parsePage($stateParams.page),
                        sort: $stateParams.sort,
                        predicate: PaginationUtil.parsePredicate($stateParams.sort),
                        ascending: PaginationUtil.parseAscending($stateParams.sort),
                        search: $stateParams.search
                    };
                }],
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('trend');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('trend-detail', {
            parent: 'trend',
            url: '/trend/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'blastApp.trend.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/trend/trend-detail.html',
                    controller: 'TrendDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('trend');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Trend', function($stateParams, Trend) {
                    return Trend.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'trend',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('trend-detail.edit', {
            parent: 'trend-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/trend/trend-dialog.html',
                    controller: 'TrendDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Trend', function(Trend) {
                            return Trend.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('trend.new', {
            parent: 'trend',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/trend/trend-dialog.html',
                    controller: 'TrendDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                description: null,
                                interval: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('trend', null, { reload: 'trend' });
                }, function() {
                    $state.go('trend');
                });
            }]
        })
        .state('trend.detail', {
            parent: 'trend',
            url: '/detail/{feedId}/{keywordName}',
            data: {
                authorities: ['ROLE_USER']
            },
            params: {
                page: {
                    value: '1',
                    squash: true
                },
                sort: {
                    value: 'id,asc',
                    squash: true
                },
                search: null
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/trend/trend-detail-keyword.html',
                    controller: 'TrendDetailKeywordDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                    	entity: ['Keyword', function(Keyword) {
                            return Keyword.getByName({name : $stateParams.keywordName}).$promise;
                        }]
                		, feed: ['FeedItem', function(FeedItem) {
                            return FeedItem.get({id : $stateParams.feedId}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('trend', null, { reload: 'trend' });
                }, function() {
                    $state.go('trend');
                });
            }]
        })
        .state('trend.edit', {
            parent: 'trend',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/trend/trend-dialog.html',
                    controller: 'TrendDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Trend', function(Trend) {
                            return Trend.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('trend', null, { reload: 'trend' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('trend.delete', {
            parent: 'trend',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/trend/trend-delete-dialog.html',
                    controller: 'TrendDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Trend', function(Trend) {
                            return Trend.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('trend', null, { reload: 'trend' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
