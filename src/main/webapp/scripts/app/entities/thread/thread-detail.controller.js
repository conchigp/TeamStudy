'use strict';

angular.module('teamstudyApp')
    .controller('ThreadDetailController', function ($scope, $stateParams, Thread) {
        $scope.thread = {};
        $scope.load = function (id) {
            Thread.get({id: id}, function(result) {
              $scope.thread = result;
            });
        };
        $scope.load($stateParams.id);
    });
