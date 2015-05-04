(function() {
	'use strict';

	function News($resource) {
		return $resource('api/news', {}, {
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

			},
			'create' : {
				method : 'POST'
			}

		});
	}

	angular.module('teamstudyApp').factory('News', News);

})();

