'use strict';

angular.module('teamstudyApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('thread', {
                parent: 'site',
                url: '/forum/:groupId',
                data: {
                    roles: [],
                    pageTitle: 'forum'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/thread/thread.html',
                        controller: 'ThreadController'
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
                        $translatePartialLoader.addPart('thread');
                        return $translate.refresh();
                    }]
                }
            });
    });