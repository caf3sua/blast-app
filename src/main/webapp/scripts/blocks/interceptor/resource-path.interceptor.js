(function() {
    'use strict';

    angular
        .module('app')
        .factory('resourcePathInterceptor', resourcePathInterceptor);

    resourcePathInterceptor.$inject = ['$rootScope', '$q', '$location', 'API_SERVICE_URL', 'API_SERVICE_PREFIX'];

    function resourcePathInterceptor($rootScope, $q, $location, API_SERVICE_URL, API_SERVICE_PREFIX) {
    		var service = {
            request: request
        };

        return service;

        function request (config) {
        		var apiPrefix = API_SERVICE_PREFIX.split(',');;
            var pathArray = config.url.split('/');
            var appContext = pathArray[0];
	        	
            if ($.inArray( appContext, apiPrefix ) > -1) {
            		// debugger
            		config.url = API_SERVICE_URL + "/" + config.url;
            		// console.log(API_SERVICE_URL);
            		// console.log(config.url);
            }
            
            return config;
        }
    }
})();
