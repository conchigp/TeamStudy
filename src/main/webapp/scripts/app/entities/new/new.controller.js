'use strict';

angular.module('teamstudyApp')
    .controller('NewController', function ($scope, New, ParseLinks) {
        $scope.news = [];
        $scope.page = 1;
        $scope.loadAll = function() {
            New.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.news = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.create = function () {
            New.update($scope.new,
                function () {
                    $scope.loadAll();
                    $('#saveNewModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            New.get({id: id}, function(result) {
                $scope.new = result;
                $('#saveNewModal').modal('show');
            });
        };

        $scope.delete = function (id) {
            New.get({id: id}, function(result) {
                $scope.new = result;
                $('#deleteNewConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            New.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteNewConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.new = {title: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
