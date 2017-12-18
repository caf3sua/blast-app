/**
 * @ngdoc overview
 * @name app
 * @description
 * # app
 *
 * Main module of the application.
 */
(function() {
    'use strict';
    angular
      .module('app', [
          // Core module
          'tmh.dynamicLocale',
          'pascalprecht.translate',
          'ngCookies',
          'ngAria',
          'ngCacheBuster',
//          'ui.bootstrap',
//          'ui.bootstrap.datetimepicker',
          'infinite-scroll',
          'angular-loading-bar',
          // Theme
		 'ngAnimate',
		 'ngResource',
		 'ngSanitize',
		 'ngTouch',
		 'ngStorage',
		 'ngStore',
		 'ui.router',
		 'ui.utils',
		 'ui.load',
		 'ui.jp',
		 'oc.lazyLoad',
		 'highcharts-ng',
		 // Social
		 'ngFacebook'
      ])
      .run(run)
      .config(configFacebook)
      .run( function( $rootScope ) {
		  // Load the facebook SDK asynchronously
		  (function(){
		     // If we've already installed the SDK, we're done
		     if (document.getElementById('facebook-jssdk')) {return;}
		
		     // Get the first script element, which we'll use to find the parent node
		     var firstScriptElement = document.getElementsByTagName('script')[0];
		
		     // Create a new script element and set its id
		     var facebookJS = document.createElement('script'); 
		     facebookJS.id = 'facebook-jssdk';
		
		     // Set the new script's source to the source of the Facebook JS SDK
		     facebookJS.src = '//connect.facebook.net/en_US/all.js';
		
		     // Insert the Facebook JS SDK into the DOM
		     firstScriptElement.parentNode.insertBefore(facebookJS, firstScriptElement);
		   }());
		});
    
    function configFacebook( $facebookProvider) {
    		$facebookProvider.setAppId('120769275152754');
    }
    
    run.$inject = ['stateHandler', 'translationHandler'];

    function run(stateHandler, translationHandler) {
        stateHandler.initialize();
        translationHandler.initialize();
    }
    
//    run.$inject = ['translationHandler'];
//
//    function run(translationHandler) {
//        translationHandler.initialize();
//    }
})();
