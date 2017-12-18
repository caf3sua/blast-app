(function() {
    'use strict';
    angular
        .module('app')
        .factory('SocialFollow', SocialFollow);

    SocialFollow.$inject = ['$resource'];

    function SocialFollow ($resource) {
        var resourceUrl =  'api/social-follows/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'checkFollowing': { url : 'api/social-follows/check-following', method: 'GET'},
            'getFollowing': {url : 'api/social-follows/following', method: 'GET', isArray: true},
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
