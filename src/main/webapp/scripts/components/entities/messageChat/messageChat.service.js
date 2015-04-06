'use strict';

angular.module('teamstudyApp')
    .factory('MessageChat', function ($resource) {
        return $resource('api/messageChats/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
