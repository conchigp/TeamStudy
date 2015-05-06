'use strict';

angular.module('teamstudyApp')
    .controller('NavbarController', function ($scope, $location, $state, Auth, Principal) {
    	
    	 Principal.identity().then(function(account) {
             $scope.account = account;
         });
    	 
        $scope.isAuthenticated = Principal.isAuthenticated;
        $scope.isInRole = Principal.isInRole;
        $scope.$state = $state;

        $scope.logout = function () {
            Auth.logout();
            $state.go('home');
        };
    });
