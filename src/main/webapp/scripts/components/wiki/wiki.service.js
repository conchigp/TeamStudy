'use strict';

angular.module('teamstudyApp').factory('Wiki', function($resource) {
	return $resource('api/group', {}, {
		'get' : {
			method : 'GET',
			params : {
				groupId : '@groupId'
			},
			isArray : false,
			interceptor : {
				response : function(response) {
					// expose response
					return response;
				}
			}

		},
		'update' : {
			method : 'PUT'
		}

	});
});