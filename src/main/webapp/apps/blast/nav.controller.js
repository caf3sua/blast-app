(function() {
    'use strict';

    angular
        .module('app')
        .controller('NavController', NavController);

    NavController.$inject = ['$state', 'Principal'];

    function NavController($state, Principal) {

    		var vm = this;
  		
  		vm.account = null;
        
        // Init        
		(function initController() {
			getAccount();
		})();
		
		// Document ready
		angular.element(document).ready(function() {
		});
		
		function getAccount() {
            Principal.identity().then(function(account) {
                vm.account = account;
                vm.isAuthenticated = Principal.isAuthenticated;
                console.log(vm.account);
            });
        }
    }
})();
