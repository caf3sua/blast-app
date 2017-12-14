(function() {
    'use strict';

    angular
        .module('blastApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('social-follow', {
            parent: 'entity',
            url: '/social-follow?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'blastApp.socialFollow.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/social-follow/social-follows.html',
                    controller: 'SocialFollowController',
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
                    $translatePartialLoader.addPart('socialFollow');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('social-follow-detail', {
            parent: 'social-follow',
            url: '/social-follow/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'blastApp.socialFollow.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/social-follow/social-follow-detail.html',
                    controller: 'SocialFollowDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('socialFollow');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'SocialFollow', function($stateParams, SocialFollow) {
                    return SocialFollow.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'social-follow',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('social-follow-detail.edit', {
            parent: 'social-follow-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/social-follow/social-follow-dialog.html',
                    controller: 'SocialFollowDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['SocialFollow', function(SocialFollow) {
                            return SocialFollow.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('social-follow.new', {
            parent: 'social-follow',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/social-follow/social-follow-dialog.html',
                    controller: 'SocialFollowDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                providerId: null,
                                providerUserId: null,
                                followUserId: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('social-follow', null, { reload: 'social-follow' });
                }, function() {
                    $state.go('social-follow');
                });
            }]
        })
        .state('social-follow.edit', {
            parent: 'social-follow',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/social-follow/social-follow-dialog.html',
                    controller: 'SocialFollowDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['SocialFollow', function(SocialFollow) {
                            return SocialFollow.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('social-follow', null, { reload: 'social-follow' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('social-follow.delete', {
            parent: 'social-follow',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/social-follow/social-follow-delete-dialog.html',
                    controller: 'SocialFollowDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['SocialFollow', function(SocialFollow) {
                            return SocialFollow.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('social-follow', null, { reload: 'social-follow' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
