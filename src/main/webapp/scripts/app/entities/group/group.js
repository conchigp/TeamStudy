'use strict';

angular.module('teamstudyApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('group', {
                parent: 'entity',
                url: '/group',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'teamstudyApp.group.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/group/groups.html',
                        controller: 'GroupController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('group');
                        return $translate.refresh();
                    }]
                }
            })
            .state('groupDetail', {
                parent: 'entity',
                url: '/group/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'teamstudyApp.group.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/group/group-detail.html',
                        controller: 'GroupDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('group');
                        return $translate.refresh();
                    }]
                }
            });
    });
