(function() {
    'use strict';

    angular
        .module('blastApp')
        .controller('StatusItemDeleteController',StatusItemDeleteController);

    StatusItemDeleteController.$inject = ['$uibModalInstance', 'entity', 'StatusItem'];

    function StatusItemDeleteController($uibModalInstance, entity, StatusItem) {
        var vm = this;

        vm.statusItem = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            StatusItem.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
