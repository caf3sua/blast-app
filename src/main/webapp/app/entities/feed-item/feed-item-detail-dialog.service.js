(function() {
    'use strict';

    angular
        .module('blastApp')
        .factory('FeedItemDetailDialogService', FeedItemDetailDialogService);

    FeedItemDetailDialogService.$inject = ['$uibModal', 'FeedItem'];

    function FeedItemDetailDialogService ($uibModal, FeedItem) {
        var service = {
            open: open
        };

        var modalInstance = null;
        var resetModal = function () {
            modalInstance = null;
        };

        return service;

        function open (feedId) {
            if (modalInstance !== null) return;
            modalInstance = $uibModal.open({
                animation: true,
                templateUrl: 'app/entities/feed-item/feed-item-detail-dialog.html',
                controller: 'FeedItemDialogController',
                controllerAs: 'vm',
                resolve: {
                	entity: ['FeedItem', function(FeedItem) {
                        return FeedItem.info({id : feedId}).$promise;
                    }]
                    , translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('login');
                        return $translate.refresh();
                    }]
                }
            });
            modalInstance.result.then(
                resetModal,
                resetModal
            );
        }
    }
})();
