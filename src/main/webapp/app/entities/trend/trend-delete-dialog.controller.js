(function() {
    'use strict';

    angular
        .module('blastApp')
        .controller('TrendDeleteController',TrendDeleteController);

    TrendDeleteController.$inject = ['$uibModalInstance', 'entity', 'Trend'];

    function TrendDeleteController($uibModalInstance, entity, Trend) {
        var vm = this;

        vm.trend = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Trend.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
