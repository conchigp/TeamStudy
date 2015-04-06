'use strict';

angular.module('teamstudyApp')
    .controller('ReplyDetailController', function ($scope, $stateParams, Reply) {
        $scope.reply = {};
        $scope.load = function (id) {
            Reply.get({id: id}, function(result) {
              $scope.reply = result;
            });
        };
        $scope.load($stateParams.id);
    });
