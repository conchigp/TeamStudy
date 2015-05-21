'use strict';

angular.module('teamstudyApp').controller('indexController',
		function($stateParams, $scope, Principal) {

			Principal.identity().then(function(account) {
				$scope.account = account;
				$scope.isInRole = Principal.isInRole;
				
				$scope.screenSize = screen.width;

			}).then(function() {
				
			});
			

		});
