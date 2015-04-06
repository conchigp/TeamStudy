'use strict';

angular.module('teamstudyApp')
    .controller('ThreadController', function ($scope, Thread, ParseLinks) {
        $scope.threads = [];
        $scope.page = 1;
        $scope.loadAll = function() {
            Thread.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.threads = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.create = function () {
            Thread.update($scope.thread,
                function () {
                    $scope.loadAll();
                    $('#saveThreadModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            Thread.get({id: id}, function(result) {
                $scope.thread = result;
                $('#saveThreadModal').modal('show');
            });
        };

        $scope.delete = function (id) {
            Thread.get({id: id}, function(result) {
                $scope.thread = result;
                $('#deleteThreadConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Thread.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteThreadConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.thread = {title: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
