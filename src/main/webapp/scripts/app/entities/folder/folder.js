'use strict';

angular.module('teamstudyApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('folder', {
                parent: 'entity',
                url: '/folder',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'teamstudyApp.folder.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/folder/folders.html',
                        controller: 'FolderController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('folder');
                        return $translate.refresh();
                    }]
                }
            })
            .state('folderDetail', {
                parent: 'entity',
                url: '/folder/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'teamstudyApp.folder.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/folder/folder-detail.html',
                        controller: 'FolderDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('folder');
                        return $translate.refresh();
                    }]
                }
            });
    });
