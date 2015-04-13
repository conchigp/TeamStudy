'use strict';

angular.module('teamstudyApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('wiki', {
                parent: 'site',
                url: '/wiki',
                data: {
                    roles: ['ROLE_USER','ROLE_ADMIN'],
                    pageTitle: 'Wiki'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/wiki/wiki.html',
                        controller: 'WikiController'
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
                        $translatePartialLoader.addPart('wiki');
                        return $translate.refresh();
                    }]
                }
            });
    });