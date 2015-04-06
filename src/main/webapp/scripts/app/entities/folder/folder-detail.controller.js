'use strict';

angular.module('teamstudyApp')
    .controller('FolderDetailController', function ($scope, $stateParams, Folder) {
        $scope.folder = {};
        $scope.load = function (id) {
            Folder.get({id: id}, function(result) {
              $scope.folder = result;
            });
        };
        $scope.load($stateParams.id);
    });
