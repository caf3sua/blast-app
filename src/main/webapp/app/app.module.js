(function() {
    'use strict';

    angular
        .module('blastApp', [
            'ngStorage',
            'tmh.dynamicLocale',
            'pascalprecht.translate',
            'ngResource',
            'ngCookies',
            'ngAria',
            'ngCacheBuster',
            'ngFileUpload',
            'ui.bootstrap',
            'ui.bootstrap.datetimepicker',
            'ui.router',
            'infinite-scroll',
            'ngAnimate',
            'ngMaterial',
            // jhipster-needle-angularjs-add-module JHipster will add new module here
            'angular-loading-bar',
            'ng-file-model',
            'ng-facebook-api'
        ])
        .config(facebookConfig)
        .run(run);
    facebookConfig.$inject = ['facebookProvider'];

    function facebookConfig(facebookProvider) {
		facebookProvider.setInitParams('120769275152754',true,true,true,'v2.10');
// 		//if your app use extra permissions set it
// 		facebookProvider.setPermissions(['email','read_stream']);
    }

    run.$inject = ['stateHandler', 'translationHandler'];

    function run(stateHandler, translationHandler) {
    	(function(){
		     // If we've already installed the SDK, we're done
		     if (document.getElementById('facebook-jssdk')) {return;}
		
		     // Get the first script element, which we'll use to find the parent node
		     var firstScriptElement = document.getElementsByTagName('script')[0];
		
		     // Create a new script element and set its id
		     var facebookJS = document.createElement('script'); 
		     facebookJS.id = 'facebook-jssdk';
		
		     // Set the new script's source to the source of the Facebook JS SDK
		     facebookJS.src = '//connect.facebook.net/en_US/sdk.js';
		
		     // Insert the Facebook JS SDK into the DOM
		     firstScriptElement.parentNode.insertBefore(facebookJS, firstScriptElement);
    	}());
    	
        stateHandler.initialize();
        translationHandler.initialize();
    }
})();
