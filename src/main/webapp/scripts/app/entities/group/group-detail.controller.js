'use strict';

angular.module('teamstudyApp')
    .controller('GroupDetailController', function ($scope, $stateParams, Group) {
        $scope.group = {};
        $scope.load = function (id) {
            Group.get({id: id}, function(result) {
              $scope.group = result;
            });
        };
        $scope.load($stateParams.id);
    });
