//'use strict';
//
//angular.module('teamstudyApp')
//    .config(function ($stateProvider) {
//        $stateProvider
//            .state('group', {
//                parent: 'site',
//                url: '/group',
//                data: {
//                    roles: [],
//                    pageTitle: 'group.title'
//                },
//                views: {
//                    'content@': {
//                        templateUrl: 'scripts/app/group/group.html',
//                        controller: 'GroupController'
//                    }
//                },
//                resolve: {
//                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
//                        $translatePartialLoader.addPart('group');
//                        return $translate.refresh();
//                    }]
//                }
//            });
//    });