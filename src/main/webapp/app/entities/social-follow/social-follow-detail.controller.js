(function() {
    'use strict';

    angular
        .module('blastApp')
        .controller('SocialFollowDetailController', SocialFollowDetailController);

    SocialFollowDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'SocialFollow'];

    function SocialFollowDetailController($scope, $rootScope, $stateParams, previousState, entity, SocialFollow) {
        var vm = this;

        vm.socialFollow = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('blastApp:socialFollowUpdate', function(event, result) {
            vm.socialFollow = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
