(function() {
    'use strict';

    angular
        .module('blastApp')
        .controller('StatusItemDetailController', StatusItemDetailController);

    StatusItemDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'StatusItem'];

    function StatusItemDetailController($scope, $rootScope, $stateParams, previousState, entity, StatusItem) {
        var vm = this;

        vm.statusItem = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('blastApp:statusItemUpdate', function(event, result) {
            vm.statusItem = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
