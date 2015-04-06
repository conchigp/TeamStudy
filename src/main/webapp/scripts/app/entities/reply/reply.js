'use strict';

angular.module('teamstudyApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('reply', {
                parent: 'entity',
                url: '/reply',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'teamstudyApp.reply.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/reply/replys.html',
                        controller: 'ReplyController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('reply');
                        return $translate.refresh();
                    }]
                }
            })
            .state('replyDetail', {
                parent: 'entity',
                url: '/reply/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'teamstudyApp.reply.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/reply/reply-detail.html',
                        controller: 'ReplyDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('reply');
                        return $translate.refresh();
                    }]
                }
            });
    });
