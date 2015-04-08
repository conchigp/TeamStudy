'use strict';

angular.module('teamstudyApp')
    .controller('FolderController', function ($scope, Folder, ParseLinks) {
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

        $scope.create = function () {
            Folder.update($scope.folder,
                function () {
                    $scope.loadAll();
                    $('#saveFolderModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            Folder.get({id: id}, function(result) {
                $scope.folder = result;
                $('#saveFolderModal').modal('show');
            });
        };

        $scope.delete = function (id) {
            Folder.get({id: id}, function(result) {
                $scope.folder = result;
                $('#deleteFolderConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Folder.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteFolderConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.folder = {title: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
