(function() {
    'use strict';

    angular
        .module('blastApp')
        .controller('KeywordUserDetailController', KeywordUserDetailController);

    KeywordUserDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'KeywordUser'];

    function KeywordUserDetailController($scope, $rootScope, $stateParams, previousState, entity, KeywordUser) {
        var vm = this;

        vm.keywordUser = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('blastApp:keywordUserUpdate', function(event, result) {
            vm.keywordUser = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
