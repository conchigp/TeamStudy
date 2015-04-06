'use strict';

angular.module('teamstudyApp')
    .controller('MessageChatDetailController', function ($scope, $stateParams, MessageChat) {
        $scope.messageChat = {};
        $scope.load = function (id) {
            MessageChat.get({id: id}, function(result) {
              $scope.messageChat = result;
            });
        };
        $scope.load($stateParams.id);
    });
