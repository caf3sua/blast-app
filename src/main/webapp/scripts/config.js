(function () {
    'use strict';
    // EDIT THIS FILE TO SETUP PROJECT
    angular
        .module('app')
        .constant('VERSION', "0.0.1-SNAPSHOT")
        .constant('DEBUG_INFO_ENABLED', true)
        .constant('BUILD_TIMESTAMP', "")
        .constant('API_SERVICE_PREFIX', "api,management")
        //.constant('API_SERVICE_URL', "https://vsmart-api.herokuapp.com")
        .constant('API_SERVICE_URL', "")
		.constant('FACEBOOK_APP_ID', '1987522348191963'); // https://blast-dev.herokuapp.com/
		//.constant('FACEBOOK_APP_ID', '120769275152754'); // http://localhost:8080/
})();
