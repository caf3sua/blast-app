// code style: https://github.com/johnpapa/angular-styleguide 
(function() {
    'use strict';

    angular
        .module('app')
        .controller('SigninController', SigninController);


    SigninController.$inject = ['$rootScope', '$state', '$timeout', 'Auth', 'Principal', '$window', '$http'];

    function SigninController ($rootScope, $state, $timeout, Auth, Principal, $window, $http) {
    		var vm = this;

        vm.isOpen = false;
        vm.socialLogin = socialLogin;
        
        function socialLogin() {
            var data = {
            	  accessToken: "sdfghjk",
            	  displayName: "Nam Nguyen Hoai",
            	  email: "hoainamtin2@gmail.com",
            	  imageUrl: "https://graph.facebook.com/v2.5/1793854933961610/picture",
            	  profileUrl: "https://www.facebook.com/app_scoped_user_id/1793854933961610/",
            	  providerId: "facebook",
            	  providerUserId: "1793854933961610"
            	}
            
            return $http.post('api/social-auth', data).success(authenticateSuccess);

            function authenticateSuccess (data, status, headers) {
                var bearerToken = headers('Authorization');
                if (angular.isDefined(bearerToken) && bearerToken.slice(0, 7) === 'Bearer ') {
                    var jwt = bearerToken.slice(7, bearerToken.length);
                    Auth.loginWithToken(jwt, true).then(function () {
                        Auth.authorize(true);
                    }, function () {
                        $state.go('app.home');
                    });
                }
            }
        }
    }
})();
