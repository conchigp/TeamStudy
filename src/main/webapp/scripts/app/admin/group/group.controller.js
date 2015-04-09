'use strict';

angular.module('teamstudyApp').controller('GroupAdminController',
		function($stateParams, $scope, GroupAdmin, Principal) {

			Principal.identity().then(function(account) {
				$scope.account = account;
				$scope.isAuthenticated = Principal.isAuthenticated;
				$scope.isInRole = Principal.isInRole;

			}).then(function() {
				if ($scope.isInRole('ROLE_ADMIN')) {
					$scope.groups = Group.get();
				}

			});

		});
