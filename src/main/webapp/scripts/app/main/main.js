'use strict';

angular.module('teamstudyApp').config(
		function($stateProvider) {
			$stateProvider.state('home', {
				parent : 'site',
				url : '/',
				data : {
					roles : []
				},
				views : {
					'content@' : {
						templateUrl : 'scripts/app/main/main.html',
						controller : 'MainController'
					},
					'sidebar-left@' : {
						templateUrl : 'scripts/app/main/sidebar-left.html',
						controller : 'SidebarleftController'
					},
					'sidebar-right@' : {
						templateUrl : 'scripts/app/main/sidebar-right.html',
						controller : 'SidebarrightController'
					}

				},
				resolve : {
					mainTranslatePartialLoader : [ '$translate',
							'$translatePartialLoader',
							function($translate, $translatePartialLoader) {
								$translatePartialLoader.addPart('main');
								return $translate.refresh();
							} ]
				}
			});
		});
