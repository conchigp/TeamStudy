'use strict';

angular.module('teamstudyApp')
		.controller(
				'AdminManagementController',
				function($stateParams, $scope, StudentsListAll, StudentsCRUD,
						Principal) {

					Principal.identity().then(function(account) {
						$scope.account = account;
						$scope.isAuthenticated = Principal.isAuthenticated;
						$scope.isInRole = Principal.isInRole;

					}).then(function() {

					});
					
					var prueba = $stateParams;
					
					$scope.groupId = prueba;

					$scope.getStudentsByGroup = function(groupId) {
						StudentsCRUD.get({groupId : groupId}, function(result) {
							$scope.students = result.data;
							
						});
					};

					$scope.update = function(id) {
						Group.get({
							id : id
						}, function(result) {
							$scope.group = result;
							$('#saveGroupModal').modal('show');
						});
					};

				});