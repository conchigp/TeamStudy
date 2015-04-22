'use strict';

angular.module('teamstudyApp').controller('WikiController',
		function($stateParams, $scope, GroupCRUDAdmin,Principal) {
			
			var groupId = localStorage.getItem('groupId');
			$scope.groupId = groupId;
			$scope.group = GroupCRUDAdmin.get({
				groupId : groupId
			});
			
			Principal.identity().then(function(account) {
				$scope.account = account;
				$scope.isAuthenticated = Principal.isAuthenticated;
				$scope.isInRole = account.roles;
			}).then(function() {
				
			});
		});
