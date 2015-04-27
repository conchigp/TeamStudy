'use strict';

angular.module('teamstudyApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('condiciones', {
                parent: 'site',
                url: '/:condiciones',
                data: {
                    roles: [],
                    pageTitle: 'condiciones'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/condiciones/condiciones.html',
                        controller: 'CondicionesController'
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
                        $translatePartialLoader.addPart('condiciones');
                        return $translate.refresh();
                    }]
                }
            });
    });