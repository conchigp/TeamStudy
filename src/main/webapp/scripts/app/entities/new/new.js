'use strict';

angular.module('teamstudyApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('new', {
                parent: 'entity',
                url: '/new',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'teamstudyApp.new.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/new/news.html',
                        controller: 'NewController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('new');
                        return $translate.refresh();
                    }]
                }
            })
            .state('newDetail', {
                parent: 'entity',
                url: '/new/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'teamstudyApp.new.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/new/new-detail.html',
                        controller: 'NewDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('new');
                        return $translate.refresh();
                    }]
                }
            });
    });
