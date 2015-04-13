(function() {
	'use strict';

	function ThreadListForGroup($resource) {
		return $resource('api/thread/group', {}, {
			'get' : {
				method : 'GET',
				params : {
					groupId : '@groupId'
				},
				isArray : true,
				interceptor : {
					response : function(response) {
						// expose response
						return response;
					}
				}

			}

		});
	}


	angular.module('teamstudyApp').factory('ThreadService', ThreadListForGroup);

})();

