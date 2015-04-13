'use strict';

angular.module('teamstudyApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('management', {
                parent: 'site',
                url: '/:groupId',
                data: {
                    roles: ['ROLE_ADMIN'],
                    pageTitle: 'Management'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/admin/management/adminManagement.html',
                        controller: 'AdminManagementController'
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