(function() {
    'use strict';

    angular
        .module('blastApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('keyword', {
            parent: 'entity',
            url: '/keyword?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'blastApp.keyword.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/keyword/keywords.html',
                    controller: 'KeywordController',
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
                    $translatePartialLoader.addPart('keyword');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('keyword-detail', {
            parent: 'keyword',
            url: '/keyword/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'blastApp.keyword.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/keyword/keyword-detail.html',
                    controller: 'KeywordDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('keyword');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Keyword', function($stateParams, Keyword) {
                    return Keyword.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'keyword',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('keyword-detail.edit', {
            parent: 'keyword-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/keyword/keyword-dialog.html',
                    controller: 'KeywordDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Keyword', function(Keyword) {
                            return Keyword.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('keyword.new', {
            parent: 'keyword',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/keyword/keyword-dialog.html',
                    controller: 'KeywordDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                feedId: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('keyword', null, { reload: 'keyword' });
                }, function() {
                    $state.go('keyword');
                });
            }]
        })
        .state('keyword.edit', {
            parent: 'keyword',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/keyword/keyword-dialog.html',
                    controller: 'KeywordDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Keyword', function(Keyword) {
                            return Keyword.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('keyword', null, { reload: 'keyword' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('keyword.delete', {
            parent: 'keyword',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/keyword/keyword-delete-dialog.html',
                    controller: 'KeywordDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Keyword', function(Keyword) {
                            return Keyword.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('keyword', null, { reload: 'keyword' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
