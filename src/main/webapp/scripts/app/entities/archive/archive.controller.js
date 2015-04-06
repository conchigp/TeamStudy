'use strict';

angular.module('teamstudyApp')
    .controller('ArchiveController', function ($scope, Archive, ParseLinks) {
        $scope.archives = [];
        $scope.page = 1;
        $scope.loadAll = function() {
            Archive.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.archives = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.create = function () {
            Archive.update($scope.archive,
                function () {
                    $scope.loadAll();
                    $('#saveArchiveModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            Archive.get({id: id}, function(result) {
                $scope.archive = result;
                $('#saveArchiveModal').modal('show');
            });
        };

        $scope.delete = function (id) {
            Archive.get({id: id}, function(result) {
                $scope.archive = result;
                $('#deleteArchiveConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Archive.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteArchiveConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.archive = {title: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
