'use strict';

angular.module('teamstudyApp')
    .controller('WikiDetailController', function ($scope, $stateParams, Wiki) {
        $scope.wiki = {};
        $scope.load = function (id) {
            Wiki.get({id: id}, function(result) {
              $scope.wiki = result;
            });
        };
        $scope.load($stateParams.id);
    });
