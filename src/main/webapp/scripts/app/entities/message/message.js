'use strict';

angular.module('teamstudyApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('message', {
                parent: 'entity',
                url: '/message',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'teamstudyApp.message.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/message/messages.html',
                        controller: 'MessageController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('message');
                        return $translate.refresh();
                    }]
                }
            })
            .state('messageDetail', {
                parent: 'entity',
                url: '/message/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'teamstudyApp.message.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/message/message-detail.html',
                        controller: 'MessageDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('message');
                        return $translate.refresh();
                    }]
                }
            });
    });
