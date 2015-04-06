'use strict';

angular.module('teamstudyApp')
    .controller('ArchiveDetailController', function ($scope, $stateParams, Archive) {
        $scope.archive = {};
        $scope.load = function (id) {
            Archive.get({id: id}, function(result) {
              $scope.archive = result;
            });
        };
        $scope.load($stateParams.id);
    });
