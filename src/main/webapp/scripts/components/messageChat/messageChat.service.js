(function() {
	'use strict';



	function MessageChatListForGroup($resource) {
		return $resource('api/chat', {}, {
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
	
	function UserById($resource) {
		return $resource('api/users/id', {}, {
			'get' : {
				method : 'GET',
				params : {
					userId : '@userId'
				},
				isArray : false,
				interceptor : {
					response : function(response) {
						// expose response
						return response;
					}
				}

			}

		});
	}
	
	

	function MessageChatCRUD($resource) {
		return $resource('api/chat', {}, {
			'update' : {
				method : 'PUT',
				params : {
					groupId : '@groupId'
				}
			}

		});
	}

	angular.module('teamstudyApp').factory('MessageChatListForGroup', MessageChatListForGroup).factory('MessageChatCRUD',
			MessageChatCRUD);

})();
