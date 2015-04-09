(function() {
	'use strict';
	
	function GroupListAdmin($resource) {
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
	}

	function GroupList($resource) {
		return $resource('api/group/user', {}, {
			'get' : {
				method : 'GET',
				params : {
					userId : '@userId'
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

	

	function GroupCRUDAdmin($resource) {
		return $resource('api/group', {}, {
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

			},
			'update' : {
				method : 'PUT'
			}

		});
	}

	angular.module('teamstudyApp').factory('GroupList', GroupList).factory(
			'GroupListAdmin', GroupListAdmin).factory('GroupCRUDAdmin',
			GroupCRUDAdmin);

})();
