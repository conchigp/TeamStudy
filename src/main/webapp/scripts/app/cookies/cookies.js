'use strict';

angular.module('teamstudyApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('cookies', {
                parent: 'site',
                url: '/:cookies',
                data: {
                    roles: [],
                    pageTitle: 'cookies'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/cookies/cookies.html',
                        controller: 'CookiesController'
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
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('cookies');
                        return $translate.refresh();
                    }]
                }
            });
    });