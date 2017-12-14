(function() {
    'use strict';

    angular
        .module('blastApp')
        .controller('StatusItemDialogController', StatusItemDialogController);

    StatusItemDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'StatusItem'];

    function StatusItemDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, StatusItem) {
        var vm = this;

        vm.statusItem = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.statusItem.id !== null) {
                StatusItem.update(vm.statusItem, onSaveSuccess, onSaveError);
            } else {
                StatusItem.save(vm.statusItem, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('blastApp:statusItemUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.createDate = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
