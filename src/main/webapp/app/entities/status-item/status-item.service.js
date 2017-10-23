(function() {
    'use strict';
    angular
        .module('blastApp')
        .factory('StatusItem', StatusItem);

    StatusItem.$inject = ['$resource', 'DateUtils'];

    function StatusItem ($resource, DateUtils) {
        var resourceUrl =  'api/status-items/:id';

        return $resource(resourceUrl, {}, {
        	'like': {url : 'api/status/like/:id', method: 'GET'},
        	'hate': {url : 'api/status/unlike/:id', method: 'GET'},
        	'checkStatus': {url : 'api/status/check/:id', method: 'GET'},
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.createDate = DateUtils.convertLocalDateFromServer(data.createDate);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.createDate = DateUtils.convertLocalDateToServer(copy.createDate);
                    return angular.toJson(copy);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.createDate = DateUtils.convertLocalDateToServer(copy.createDate);
                    return angular.toJson(copy);
                }
            }
        });
    }
})();
