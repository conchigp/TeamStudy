'use strict';

angular.module('teamstudyApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('repository', {
                parent: 'site',
                url: '/repository/:groupId',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'repository'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/repository/repository.html',
                        controller: 'RepositoryController'
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
                        $translatePartialLoader.addPart('repository');
                        return $translate.refresh();
                    }]
                }
            });
    });
