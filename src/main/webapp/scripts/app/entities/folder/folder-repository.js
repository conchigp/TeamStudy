'use strict';

angular.module('teamstudyApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('folder', {
                parent: 'entity',
                url: '/repository',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'teamstudyApp.repository.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/folder/repository.html',
                        controller: 'RepositoryController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('repository');
                        return $translate.refresh();
                    }]
                }
            });
            /*.state('repositoryDetail', {
                parent: 'entity',
                url: '/repository',
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
            });*/
    });
