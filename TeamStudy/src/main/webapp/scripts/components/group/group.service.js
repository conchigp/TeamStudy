'use strict';

angular.module('teamstudyApp')
    .factory('Group', function ($resource) {
        return $resource('api/user/:id/groups', {}, {
        	'get': { method: 'GET', params: {}, isArray: true,
                interceptor: {
                    response: function(response) {
                        // expose response
                        return response;
                    }
                }
            }
          
        });
        });