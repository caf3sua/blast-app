(function() {
    'use strict';

    angular
        .module('blastApp')
        .controller('SocialFollowDeleteController',SocialFollowDeleteController);

    SocialFollowDeleteController.$inject = ['$uibModalInstance', 'entity', 'SocialFollow'];

    function SocialFollowDeleteController($uibModalInstance, entity, SocialFollow) {
        var vm = this;

        vm.socialFollow = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            SocialFollow.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
