'use strict';

angular.module('teamstudyApp')
    .controller('NewDetailController', function ($scope, $stateParams, New) {
        $scope.new = {};
        $scope.load = function (id) {
            New.get({id: id}, function(result) {
              $scope.new = result;
            });
        };
        $scope.load($stateParams.id);
    });
