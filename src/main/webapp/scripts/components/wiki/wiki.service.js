'use strict';

angular.module('teamstudyApp').factory('Wiki', function($resource) {
	return $resource('api/groups/:id/wiki', {}, {
		'get' : {
			method : 'GET',
			params : {},
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