'use strict';

angular.module('teamstudyApp').factory('Group', function($resource) {
	return $resource('api/groups', {}, {
		'get' : {
			method : 'GET',
			params : {userId:'@userId'},
			isArray : true,
			interceptor : {
				response : function(response) {
					// expose response
					return response;
				}
			}

		},
		'update' : {method : 'PUT'}
		

	});
});