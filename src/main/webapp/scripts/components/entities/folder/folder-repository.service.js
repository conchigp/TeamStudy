'use strict';

angular.module('teamstudyApp')
    .factory('Folder', function ($resource) {
        return $resource('api/folder/group', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            }
        });
    });
