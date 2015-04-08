'use strict';

angular.module('teamstudyApp')
    .controller('RepositoryController', function ($scope, Folder, ParseLinks) {
        $scope.folders = [];
        $scope.page = 1;
        $scope.loadAll = function() {
            Folder.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.folders = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.clear = function () {
            $scope.folder = {title: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
