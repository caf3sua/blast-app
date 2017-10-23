(function() {
    'use strict';

    angular
        .module('blastApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('keyword-user', {
            parent: 'entity',
            url: '/keyword-user?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'blastApp.keywordUser.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/keyword-user/keyword-users.html',
                    controller: 'KeywordUserController',
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
                    $translatePartialLoader.addPart('keywordUser');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('keyword-user-detail', {
            parent: 'keyword-user',
            url: '/keyword-user/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'blastApp.keywordUser.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/keyword-user/keyword-user-detail.html',
                    controller: 'KeywordUserDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('keywordUser');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'KeywordUser', function($stateParams, KeywordUser) {
                    return KeywordUser.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'keyword-user',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('keyword-user-detail.edit', {
            parent: 'keyword-user-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/keyword-user/keyword-user-dialog.html',
                    controller: 'KeywordUserDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['KeywordUser', function(KeywordUser) {
                            return KeywordUser.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('keyword-user.new', {
            parent: 'keyword-user',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/keyword-user/keyword-user-dialog.html',
                    controller: 'KeywordUserDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                keywordId: null,
                                userId: null,
                                updatedDate: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('keyword-user', null, { reload: 'keyword-user' });
                }, function() {
                    $state.go('keyword-user');
                });
            }]
        })
        .state('keyword-user.edit', {
            parent: 'keyword-user',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/keyword-user/keyword-user-dialog.html',
                    controller: 'KeywordUserDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['KeywordUser', function(KeywordUser) {
                            return KeywordUser.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('keyword-user', null, { reload: 'keyword-user' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('keyword-user.delete', {
            parent: 'keyword-user',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/keyword-user/keyword-user-delete-dialog.html',
                    controller: 'KeywordUserDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['KeywordUser', function(KeywordUser) {
                            return KeywordUser.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('keyword-user', null, { reload: 'keyword-user' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
