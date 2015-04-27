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
	function ThreadCRUD($resource) {
		return $resource('api/thread', {}, {
			'get' : {
				method : 'GET',
				params : {
					threadId : '@threadId'
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
				method : 'PUT',
				params : {
					groupId : '@groupId'
				}
			},
			'delete' : {
				method : 'DELETE',
				params : {
					threadId : '@threadId'
				}
			}

		});
	}


	angular.module('teamstudyApp').factory('ThreadListForGroup', ThreadListForGroup).factory('ThreadCRUD',
			ThreadCRUD);

})();

