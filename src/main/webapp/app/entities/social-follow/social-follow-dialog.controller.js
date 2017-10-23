(function() {
    'use strict';

    angular
        .module('blastApp')
        .controller('SocialFollowDialogController', SocialFollowDialogController);

    SocialFollowDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'SocialFollow'];

    function SocialFollowDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, SocialFollow) {
        var vm = this;

        vm.socialFollow = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.socialFollow.id !== null) {
                SocialFollow.update(vm.socialFollow, onSaveSuccess, onSaveError);
            } else {
                SocialFollow.save(vm.socialFollow, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('blastApp:socialFollowUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
