(function() {
    'use strict';
    angular
        .module('blastApp')
        .factory('Keyword', Keyword);

    Keyword.$inject = ['$resource'];

    function Keyword ($resource) {
        var resourceUrl =  'api/keywords/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'info': {url : 'api/keyword-info', method: 'GET'},
            'getByName': {
            	url : 'api/keyword-by-name',
                method: 'GET'
            },
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
