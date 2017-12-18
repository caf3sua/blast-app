// code style: https://github.com/johnpapa/angular-styleguide 
(function() {
    'use strict';

    angular
        .module('app')
        .controller('SigninController', SigninController);


    SigninController.$inject = ['$rootScope', '$state', '$timeout', 'Auth', 'Principal', '$window', '$http', '$facebook'];

    function SigninController ($rootScope, $state, $timeout, Auth, Principal, $window, $http, $facebook) {
    		var vm = this;

        vm.isOpen = false;
        vm.socialLogin = socialLogin;
        vm.welcomeMsg;
        
        vm.data = {
          	  accessToken: "sdfghjk",
          	  displayName: "Nam Nguyen Hoai",
          	  email: "hoainamtin2@gmail.com",
          	  imageUrl: "https://graph.facebook.com/v2.5/1793854933961610/picture?width=150&height=150",
          	  profileUrl: "https://www.facebook.com/app_scoped_user_id/1793854933961610/",
          	  providerId: "facebook",
          	  providerUserId: "1793854933961610"
          	};
        
        // Init        
 		(function initController() {
 		})();
 		
 		function getMoreInformationFb() {
 		    $facebook.api("/me", {fields: 'email,name,last_name,picture'}).then( 
 		      function(response) {
	 		    	  	debugger
	 		        vm.isLoggedIn = true;
	 		        vm.data.displayName = response.name;
	 		        vm.data.email = response.email;
	 		        vm.data.imageUrl = "https://graph.facebook.com/v2.5/" + vm.data.providerUserId + "/picture?width=150&height=150";
	 		        vm.data.profileUrl = "https://www.facebook.com/app_scoped_user_id/" + vm.data.providerUserId;
	 		        vm.data.providerId = 'facebook';
	 		        console.log(response);
	 		        loginWithBackend();
 		      },
 		      function(err) {
 		    	  		consonle.log('errr:' + err);
 		      });
 		}
 		
 		// Document ready
 		angular.element(document).ready(function() {
 		});
 		
        function socialLogin(providerId) {
        		// Facebook
        		if (providerId == 'facebook') {
        			// Check getLoginStatus
     			$facebook.getLoginStatus().then(
    				function(response){
    					console.log(response);
    					if (response.status === 'connected') {
    					    vm.data.accessToken = response.authResponse.accessToken;
    					    vm.data.providerUserId = response.authResponse.userID;
    					    getMoreInformationFb();
    					  } else if (response.status === 'not_authorized') {
    						  	// the user is logged in to Facebook, 
    						  	// but has not authenticated your app
    				 			$facebook.login().then(function() {
    				 				getMoreInformationFb();
    				 			});
    					  } else {
    						  	// the user isn't logged in to Facebook.
    				 			$facebook.login().then(function() {
    				 				getMoreInformationFb();
    				 			});
    					  }
    				}, function(err){
     				
    				});
        		}
        }
        
        function loginWithBackend() {
              return $http.post('api/social-auth', vm.data).success(authenticateSuccess);

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
