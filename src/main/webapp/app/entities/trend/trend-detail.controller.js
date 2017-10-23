(function() {
    'use strict';

    angular
        .module('blastApp')
        .controller('TrendDetailController', TrendDetailController);

    TrendDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Trend'];

    function TrendDetailController($scope, $rootScope, $stateParams, previousState, entity, Trend) {
        var vm = this;

        vm.trend = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('blastApp:trendUpdate', function(event, result) {
            vm.trend = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
