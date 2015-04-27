(function() {
	'use strict';

	function Message($resource) {
		return $resource('api/message', {}, {
			'get' : {
				method : 'GET',
				params : {
					messageId : '@messageId'
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
					threadId : '@threadId'
				}
			},
			'delete' : {
				method : 'DELETE',
				params : {
					messageId : '@messageId'
				}
			}
		});
	}
	function MessageList($resource) {
		return $resource('api/message/thread', {}, {
			'get' : {
				method : 'GET',
				params : {
					threadId : '@threadId'
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

	angular.module('teamstudyApp').factory('Message', Message).factory(
			'MessageList', MessageList);

})();
