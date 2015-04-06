'use strict';

angular.module('teamstudyApp')
    .controller('GroupController', function ($scope, Group, ParseLinks) {
        $scope.groups = [];
        $scope.page = 1;
        $scope.loadAll = function() {
            Group.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.groups = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.create = function () {
            Group.update($scope.group,
                function () {
                    $scope.loadAll();
                    $('#saveGroupModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            Group.get({id: id}, function(result) {
                $scope.group = result;
                $('#saveGroupModal').modal('show');
            });
        };

        $scope.delete = function (id) {
            Group.get({id: id}, function(result) {
                $scope.group = result;
                $('#deleteGroupConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Group.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteGroupConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.group = {title: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
