(function() {
    'use strict';

    angular
        .module('app')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig ($stateProvider) {
        $stateProvider.state('app.docs', {
            parent: 'app',
            url: '/docs',
            data: {
                authorities: ['ROLE_ADMIN', 'ROLE_USER'],
                pageTitle: 'global.menu.admin.apidocs'
            },
            templateUrl: 'apps/admin/docs/docs.html',
            resolve: {
                translatePartialLoader: ['$translate', function ($translate) {
                    return $translate.refresh();
                }]
            }
        });
    }
})();
