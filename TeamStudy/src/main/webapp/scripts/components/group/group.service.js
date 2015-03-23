'use strict';

angular.module('teamstudyApp')
    .factory('Group', function ($resource) {
        return $resource('api/user/', {}, {
                'query': {method: 'GET', isArray: true},
                'get': {
                    method: 'GET',
                    transformResponse: function (data) {
                        data = angular.fromJson(data);
                        return data;
                    }
                }
            });
        });