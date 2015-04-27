(function() {
	'use strict';

	function Reply($resource) {
		return $resource('api/reply', {}, {
			'get' : {
				method : 'GET',
				params : {
					replyId : '@replyId'
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
					messageId : '@messageId'
				}
			},
			'delete' : {
				method : 'DELETE',
				params : {
					replyId : '@replyId'
				}
			}
		});
	}
	function ReplyList($resource) {
		return $resource('api/reply/message', {}, {
			'get' : {
				method : 'GET',
				params : {
					messageId : '@messageId'
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

	angular.module('teamstudyApp').factory('Reply', Reply).factory(
			'ReplyList', ReplyList);

})();
