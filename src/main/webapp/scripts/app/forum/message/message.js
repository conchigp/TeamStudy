'use strict';

angular.module('teamstudyApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('message', {
                parent: 'site',
                url: '/:threadId',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'Message'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/forum/message/message.html',
                        controller: 'MessageController'
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
