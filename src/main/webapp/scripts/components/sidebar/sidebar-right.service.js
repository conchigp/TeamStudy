(function() {
	'use strict';

	function StudentsListAll($resource) {
		return $resource('api/students', {}, {
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
	
	function StudentsCRUD($resource) {
		return $resource('api/student', {}, {
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

	angular.module('teamstudyApp').factory('StudentsListAll', StudentsListAll)
	.factory('StudentsCRUD', StudentsCRUD);

})();
