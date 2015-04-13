(function() {
	'use strict';

	function teachersListAll($resource) {
		return $resource('api/teachers', {}, {
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

	function teachersCRUD($resource) {
		return $resource('api/teacher', {}, {
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
				params : {
					groupId : '@groupId',
					teacherId : '@teacherId'
				}
			},
			'delete' : {
				method : 'DELETE',
				params : {
					groupId : '@groupId',
				}
			}

		});
	}

	angular.module('teamstudyApp').factory('teachersListAll', teachersListAll).factory(
			'teachersCRUD', teachersCRUD);

})();
