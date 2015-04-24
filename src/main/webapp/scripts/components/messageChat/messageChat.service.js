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
