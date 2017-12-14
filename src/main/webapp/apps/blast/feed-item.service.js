(function() {
    'use strict';
    angular
        .module('app')
        .factory('FeedItem', FeedItem);

    FeedItem.$inject = ['$resource'];

    function FeedItem ($resource) {
        var resourceUrl =  'api/feed-items/:id';

        return $resource(resourceUrl, {}, {
        	// wiki + amazon 
        	'wikiInfo': {url : 'api/advice/wiki', method: 'POST', isArray: true},
        	'amazonInfo': {url : 'api/advice/items', method: 'POST', isArray: true},
        	// store cloud
        	'storeCloud': {url : 'api/feed/store-cloud', method: 'POST'},
        	// Feed info
        	'info': {url : 'api/feed/info/:id', method: 'GET'},
        	'visionDetect': {url : 'api/vision/detect', method: 'POST', isArray: true},
        	'topKeywordByFriend': {url : 'api/top-keyword-by-friend', method: 'GET', isArray: true},
        	'topKeywordByAll': {url : 'api/top-keyword-by-all', method: 'GET', isArray: true},
        	'trendFriend': {url : 'api/feeds-by-trend-friend', method: 'GET', isArray: true},
        	'trendAll': {url : 'api/feeds-by-trend-all', method: 'GET', isArray: true},
        	'queryByUserStatus': {url : 'api/feeds-by-user-status', method: 'GET', isArray: true},
        	'queryByUserPost': {url : 'api/feeds-by-user-post', method: 'GET', isArray: true},
        	'query': { method: 'GET', isArray: true},
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
