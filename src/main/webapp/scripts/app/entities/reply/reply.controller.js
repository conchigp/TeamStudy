'use strict';

angular.module('teamstudyApp')
    .controller('ReplyController', function ($scope, Reply, ParseLinks) {
        $scope.replys = [];
        $scope.page = 1;
        $scope.loadAll = function() {
            Reply.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.replys = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.create = function () {
            Reply.update($scope.reply,
                function () {
                    $scope.loadAll();
                    $('#saveReplyModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            Reply.get({id: id}, function(result) {
                $scope.reply = result;
                $('#saveReplyModal').modal('show');
            });
        };

        $scope.delete = function (id) {
            Reply.get({id: id}, function(result) {
                $scope.reply = result;
                $('#deleteReplyConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Reply.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteReplyConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.reply = {title: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
