'use strict';

angular.module('teamstudyApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('contacta', {
                parent: 'site',
                url: '/:contacta',
                data: {
                    roles: [],
                    pageTitle: 'contacta'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/contacta/contacta.html',
                        controller: 'ContactaController'
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
                        $translatePartialLoader.addPart('contacta');
                        return $translate.refresh();
                    }]
                }
            });
    });