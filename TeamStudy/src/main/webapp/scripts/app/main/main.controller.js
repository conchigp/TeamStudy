'use strict';

angular.module('teamstudyApp').controller('MainController',
		function($scope, Group, Principal) {

			Principal.identity().then(function(account) {
				$scope.account = account;
				$scope.isAuthenticated = Principal.isAuthenticated;
			}).then(function() {
				$scope.groups = Group.get({
					id : $scope.account.id
				});
			});

		});
