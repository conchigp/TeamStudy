'use strict';

angular.module('teamstudyApp').controller('SidebarleftController',
		function($scope, GroupList, GroupListAdmin, GroupCRUDAdmin, Principal) {

			Principal.identity().then(function(account) {
				$scope.account = account;
				$scope.isAuthenticated = Principal.isAuthenticated;
				$scope.isInRole = Principal.isInRole;
			}).then(function() {
				if (Principal.isInRole('ROLE_ADMIN')) {
					$scope.groups = GroupListAdmin.get();
				} else {

					$scope.groups = GroupList.get({
						userId : $scope.account.id
					});
				}

			});

			$scope.create = function() {
				GroupCRUDAdmin.update($scope.group, function() {
					$scope.clear();
					scope.groups = GroupListAdmin.get();
				});
				$('#saveGroupModal').modal('hide');
			};

			$scope.update = function(id) {
				GroupCRUDAdmin.get({groupId : id}, function(result) {
					$scope.group = result;
					$('#saveGroupModal').modal('show');
				});
			};

			$scope.clear = function() {
				$scope.group = {
					name : null,
					description : null
				};
				$scope.editForm.$setPristine();
				$scope.editForm.$setUntouched();
			};

			

		});
