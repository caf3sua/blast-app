(function() {
    'use strict';
    angular
        .module('blastApp')
        .factory('KeywordUser', KeywordUser);

    KeywordUser.$inject = ['$resource', 'DateUtils'];

    function KeywordUser ($resource, DateUtils) {
        var resourceUrl =  'api/keyword-users/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.updatedDate = DateUtils.convertDateTimeFromServer(data.updatedDate);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
