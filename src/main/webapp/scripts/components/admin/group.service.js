'use strict';

angular.module('teamstudyApp').factory('GroupAdmin', function($resource) {
	return $resource('api/groups', {}, {
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

		}

	});
});