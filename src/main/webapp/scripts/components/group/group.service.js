(function() {
	'use strict';

	function GroupListAdmin($resource, $q) {
		var defered = $q.defer();
		var promise = defered.promise;
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
				interceptor : {
					response : function(response) {
						// expose response
						var deferred = $q.defer();
						deferred.resolve(response);

						return deferred.promise;
					}
				}
			}

		});
	}

	angular.module('teamstudyApp').factory('GroupList', GroupList).factory(
			'GroupListAdmin', GroupListAdmin).factory('GroupCRUDAdmin',
			GroupCRUDAdmin);

})();
