'use strict';

angular.module('teamstudyApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('messageChat', {
                parent: 'entity',
                url: '/messageChat',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'teamstudyApp.messageChat.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/messageChat/messageChats.html',
                        controller: 'MessageChatController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('messageChat');
                        return $translate.refresh();
                    }]
                }
            })
            .state('messageChatDetail', {
                parent: 'entity',
                url: '/messageChat/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'teamstudyApp.messageChat.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/messageChat/messageChat-detail.html',
                        controller: 'MessageChatDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('messageChat');
                        return $translate.refresh();
                    }]
                }
            });
    });
