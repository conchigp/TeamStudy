'use strict';

angular.module('teamstudyApp')
    .controller('MessageController', function ($scope, Message, ParseLinks) {
        $scope.messages = [];
        $scope.page = 1;
        $scope.loadAll = function() {
            Message.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.messages = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.create = function () {
            Message.update($scope.message,
                function () {
                    $scope.loadAll();
                    $('#saveMessageModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            Message.get({id: id}, function(result) {
                $scope.message = result;
                $('#saveMessageModal').modal('show');
            });
        };

        $scope.delete = function (id) {
            Message.get({id: id}, function(result) {
                $scope.message = result;
                $('#deleteMessageConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Message.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteMessageConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.message = {title: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
