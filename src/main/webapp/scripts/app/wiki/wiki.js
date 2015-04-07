'use strict';

angular.module('teamstudyApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('wiki', {
                parent: 'site',
                url: '/wiki',
                data: {
                    roles: [],
                    pageTitle: 'Wiki'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/wiki/wiki.html',
                        controller: 'WikiController'
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