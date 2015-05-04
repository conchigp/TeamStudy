(function() {
	'use strict';

	function getNews($resource) {
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
				method : 'POST',
				params : {
					groupId : '@groupId'
				}
			}

		});
	}

	angular.module('teamstudyApp').factory('getNews', getNews);

})();

