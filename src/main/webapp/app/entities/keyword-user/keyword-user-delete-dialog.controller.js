(function() {
    'use strict';

    angular
        .module('blastApp')
        .controller('KeywordUserDeleteController',KeywordUserDeleteController);

    KeywordUserDeleteController.$inject = ['$uibModalInstance', 'entity', 'KeywordUser'];

    function KeywordUserDeleteController($uibModalInstance, entity, KeywordUser) {
        var vm = this;

        vm.keywordUser = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            KeywordUser.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
