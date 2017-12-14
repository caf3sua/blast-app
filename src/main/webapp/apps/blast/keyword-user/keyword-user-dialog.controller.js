(function() {
    'use strict';

    angular
        .module('blastApp')
        .controller('KeywordUserDialogController', KeywordUserDialogController);

    KeywordUserDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'KeywordUser'];

    function KeywordUserDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, KeywordUser) {
        var vm = this;

        vm.keywordUser = entity;
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
            if (vm.keywordUser.id !== null) {
                KeywordUser.update(vm.keywordUser, onSaveSuccess, onSaveError);
            } else {
                KeywordUser.save(vm.keywordUser, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('blastApp:keywordUserUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.updatedDate = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
