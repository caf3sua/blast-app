(function() {
    'use strict';

    angular
        .module('blastApp')
        .controller('SocialRegisterController', SocialRegisterController);

    SocialRegisterController.$inject = ['$state', '$filter', '$stateParams'];

    function SocialRegisterController ($state, $filter, $stateParams) {
        var vm = this;

        vm.success = $stateParams.success;
        vm.error = !vm.success;
        vm.provider = $stateParams.provider;
        vm.providerLabel = $filter('capitalize')(vm.provider);
        vm.success = $stateParams.success;
        
        // Init controller
		(function initController() {
			//jh-btn-social
	    })();
        
    }
})();
