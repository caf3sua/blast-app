(function () {
    'use strict';

    angular
        .module('blastApp')
        .controller('SocialAuthController', SocialAuthController);

    SocialAuthController.$inject = ['$state', '$cookies', 'Auth'];

    function SocialAuthController($state, $cookies, Auth) {
        var token = $cookies.get('social-authentication');
        console.log('SocialAuthController token:' + token);

        Auth.loginWithToken(token, false).then(function () {
            $cookies.remove('social-authentication');
            Auth.authorize(true);
        }, function () {
            $state.go('social-register', {'success': 'false'});
        	//$state.go('home');
        });
    }
})();
