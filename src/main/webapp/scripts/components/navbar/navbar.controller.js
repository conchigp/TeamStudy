'use strict';

angular.module('teamstudyApp')
    .controller('NavbarController', function ($scope, $location, $state, Auth, Principal) {
    	
    	 Principal.identity().then(function(account) {
             $scope.account = account;
         });
    	 
        $scope.isAuthenticated = Principal.isAuthenticated;
        $scope.isInRole = Principal.isInRole;
        $scope.$state = $state;
        $scope.screenSize = screen.width;

        $scope.logout = function () {
        	localStorage.clear();
            Auth.logout();
            $scope.account = "ABCD";
            $state.go('home');
        };
    });
