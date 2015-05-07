'use strict';

//Archivo retocado por DIEGO para que mande al home en el momento que se pulsa atrás o se recarga.
angular.module('teamstudyApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('error', {
                parent: 'site',
                url: '/',
                data: {
                    roles: [],
                    pageTitle: 'teamStudy'
                },
                views: {
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
                resolve: {
                    mainTranslatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate,$translatePartialLoader) {
                        $translatePartialLoader.addPart('wiki');
                        return $translate.refresh();
                    }]
                }
            })
            .state('accessdenied', {
                parent: 'site',
                url: '/',
                data: {
                    roles: []
                },
                views: {
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
                resolve: {
                    mainTranslatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate,$translatePartialLoader) {
                        $translatePartialLoader.addPart('wiki');
                        return $translate.refresh();
                    }]
                }
            });
    });
