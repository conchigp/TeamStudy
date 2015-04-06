'use strict';

angular.module('teamstudyApp')
    .controller('WikiController', function ($scope, Wiki, ParseLinks) {
        $scope.wikis = [];
        $scope.page = 1;
        $scope.loadAll = function() {
            Wiki.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.wikis = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.create = function () {
            Wiki.update($scope.wiki,
                function () {
                    $scope.loadAll();
                    $('#saveWikiModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            Wiki.get({id: id}, function(result) {
                $scope.wiki = result;
                $('#saveWikiModal').modal('show');
            });
        };

        $scope.delete = function (id) {
            Wiki.get({id: id}, function(result) {
                $scope.wiki = result;
                $('#deleteWikiConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Wiki.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteWikiConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.wiki = {title: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
