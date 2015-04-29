'use strict';

angular.module('teamstudyApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('equipo', {
                parent: 'site',
                url: '/:equipo',
                data: {
                    roles: [],
                    pageTitle: 'equipo'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/equipo/equipo.html',
                        controller: 'EquipoController'
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
                        $translatePartialLoader.addPart('equipo');
                        return $translate.refresh();
                    }]
                }
            });
    });