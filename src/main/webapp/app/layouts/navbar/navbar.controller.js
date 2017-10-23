(function() {
    'use strict';

    angular
        .module('blastApp')
        .controller('NavbarController', NavbarController);

    NavbarController.$inject = ['$rootScope', '$state', 'Auth', 'Principal', 'ProfileService', 'LoginService'];

    function NavbarController ($rootScope, $state, Auth, Principal, ProfileService, LoginService) {
        var vm = this;

        vm.isNavbarCollapsed = true;
        vm.isAuthenticated = Principal.isAuthenticated;

        ProfileService.getProfileInfo().then(function(response) {
            vm.inProduction = response.inProduction;
            vm.swaggerEnabled = response.swaggerEnabled;
        });

        vm.login = login;
        vm.logout = logout;
        vm.toggleNavbar = toggleNavbar;
        vm.collapseNavbar = collapseNavbar;
        vm.$state = $state;
        
        function login() {
            collapseNavbar();
            //LoginService.open();
            $state.go('signin');
        }

        function logout() {
            collapseNavbar();
            Auth.logout();
            vm.isAuthenticated = null;
            $rootScope.$broadcast('logoutSuccess');
            $state.go('signin');
        }

        function toggleNavbar() {
            vm.isNavbarCollapsed = !vm.isNavbarCollapsed;
        }

        function collapseNavbar() {
            vm.isNavbarCollapsed = true;
        }
    }
})();
