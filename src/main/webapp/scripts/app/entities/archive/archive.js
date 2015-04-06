'use strict';

angular.module('teamstudyApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('archive', {
                parent: 'entity',
                url: '/archive',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'teamstudyApp.archive.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/archive/archives.html',
                        controller: 'ArchiveController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('archive');
                        return $translate.refresh();
                    }]
                }
            })
            .state('archiveDetail', {
                parent: 'entity',
                url: '/archive/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'teamstudyApp.archive.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/archive/archive-detail.html',
                        controller: 'ArchiveDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('archive');
                        return $translate.refresh();
                    }]
                }
            });
    });
