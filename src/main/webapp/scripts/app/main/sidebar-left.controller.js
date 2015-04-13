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
					if(Principal.isInRole('ROLE_USER')){
						$scope.groups = GroupList.get({
							userId : $scope.account.id
						});
					}
					
				}

			});

			$scope.create = function() {
				GroupCRUDAdmin.update($scope.group, function() {
					$scope.clear();
					
				});
				$('#saveGroupModal').modal('hide');
			
			};

			$scope.update = function(id) {
				GroupCRUDAdmin.get({groupId : id}, function(result) {
					$scope.group = result.data;
					$('#saveGroupModal').modal('show');
				});
			};
			
			$scope.confirmDelete = function (id) {
	            GroupCRUDAdmin.delete({groupId: id},
	                function () {
	                    $scope.loadAll();
// $('#deleteBookConfirmation').modal('hide');
	                    $scope.clear();
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
			
			$scope.local = function(groupId){
				sessionStorage.setItem('groupId', groupId);
// sessionStorage.setItem('wiki', wiki);
			};

			

		});
