(function() {
    'use strict';

    angular
        .module('blastApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('status-item', {
            parent: 'entity',
            url: '/status-item?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'blastApp.statusItem.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/status-item/status-items.html',
                    controller: 'StatusItemController',
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
                    $translatePartialLoader.addPart('statusItem');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('status-item-detail', {
            parent: 'status-item',
            url: '/status-item/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'blastApp.statusItem.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/status-item/status-item-detail.html',
                    controller: 'StatusItemDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('statusItem');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'StatusItem', function($stateParams, StatusItem) {
                    return StatusItem.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'status-item',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('status-item-detail.edit', {
            parent: 'status-item-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/status-item/status-item-dialog.html',
                    controller: 'StatusItemDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['StatusItem', function(StatusItem) {
                            return StatusItem.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('status-item.new', {
            parent: 'status-item',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/status-item/status-item-dialog.html',
                    controller: 'StatusItemDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                itemId: null,
                                userId: null,
                                status: null,
                                description: null,
                                createDate: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('status-item', null, { reload: 'status-item' });
                }, function() {
                    $state.go('status-item');
                });
            }]
        })
        .state('status-item.edit', {
            parent: 'status-item',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/status-item/status-item-dialog.html',
                    controller: 'StatusItemDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['StatusItem', function(StatusItem) {
                            return StatusItem.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('status-item', null, { reload: 'status-item' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('status-item.delete', {
            parent: 'status-item',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/status-item/status-item-delete-dialog.html',
                    controller: 'StatusItemDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['StatusItem', function(StatusItem) {
                            return StatusItem.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('status-item', null, { reload: 'status-item' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
