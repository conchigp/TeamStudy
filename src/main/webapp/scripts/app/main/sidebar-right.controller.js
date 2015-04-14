'use strict';

angular.module('teamstudyApp').controller('SidebarrightController',
		function($stateParams,$state, $scope,StudentsCRUD, StudentsListAll,Principal) {
			Principal.identity().then(function(account) {
				$scope.account = account;
				$scope.isAuthenticated = Principal.isAuthenticated;
				$scope.isInRole = Principal.isInRole;
				
				}).then(function() {
					
					var groupId = $stateParams.groupId;
					$scope.groupId = groupId;
					
					StudentsCRUD.get({
						groupId : groupId
					}, function(result) {
						$scope.students = result.data;

					});
								
					StudentsListAll.get(function(result) {
						$scope.studentsAll = result.data;
						
					});
					
				});

		});