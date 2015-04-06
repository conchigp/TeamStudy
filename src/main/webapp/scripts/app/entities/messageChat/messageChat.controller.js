'use strict';

angular.module('teamstudyApp')
    .controller('MessageChatController', function ($scope, MessageChat, ParseLinks) {
        $scope.messageChats = [];
        $scope.page = 1;
        $scope.loadAll = function() {
            MessageChat.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.messageChats = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.create = function () {
            MessageChat.update($scope.messageChat,
                function () {
                    $scope.loadAll();
                    $('#saveMessageChatModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            MessageChat.get({id: id}, function(result) {
                $scope.messageChat = result;
                $('#saveMessageChatModal').modal('show');
            });
        };

        $scope.delete = function (id) {
            MessageChat.get({id: id}, function(result) {
                $scope.messageChat = result;
                $('#deleteMessageChatConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            MessageChat.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteMessageChatConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.messageChat = {title: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
