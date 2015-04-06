'use strict';

angular.module('teamstudyApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('thread', {
                parent: 'entity',
                url: '/thread',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'teamstudyApp.thread.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/thread/threads.html',
                        controller: 'ThreadController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('thread');
                        return $translate.refresh();
                    }]
                }
            })
            .state('threadDetail', {
                parent: 'entity',
                url: '/thread/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'teamstudyApp.thread.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/thread/thread-detail.html',
                        controller: 'ThreadDetailController'
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
