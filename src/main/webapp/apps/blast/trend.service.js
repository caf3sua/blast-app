(function() {
    'use strict';
    angular
        .module('app')
        .factory('Trend', Trend);

    Trend.$inject = ['$resource'];

    function Trend ($resource) {
        var resourceUrl =  'api/trends/:id';

        return $resource(resourceUrl, {}, {
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
