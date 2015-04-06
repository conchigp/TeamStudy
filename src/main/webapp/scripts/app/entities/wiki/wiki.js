'use strict';

angular.module('teamstudyApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('wiki', {
                parent: 'entity',
                url: '/wiki',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'teamstudyApp.wiki.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/wiki/wikis.html',
                        controller: 'WikiController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('wiki');
                        return $translate.refresh();
                    }]
                }
            })
            .state('wikiDetail', {
                parent: 'entity',
                url: '/wiki/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'teamstudyApp.wiki.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/wiki/wiki-detail.html',
                        controller: 'WikiDetailController'
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
