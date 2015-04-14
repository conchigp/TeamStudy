'use strict';

angular.module('teamstudyApp').controller('SidebarleftController',
		function($scope,$state,$stateParams, GroupList, GroupListAdmin, GroupCRUDAdmin, Principal) {

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
					// ESTO LO ESTABA PROBANDO PARA QUE FUNCIONE EL LISTAR
					// ALUMNOS EN USER (DIEGO)
// var groupId = $stateParams.groupId;
// $scope.groupId = groupId;
//					
// StudentsCRUD.get({
// groupId : groupId
// }, function(result) {
// $scope.students = result.data;
//
// });
					
				}

			});

			$scope.create = function() {
				GroupCRUDAdmin.update($scope.group, function() {
//					$scope.clear();
				});
				$('#saveGroupModal').modal('hide');
				$state.reload();
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
//	                    $scope.clear();
	            		
	                });
	            $state.reload();
	           

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
				localStorage.setItem('groupId', groupId);
				 $state.transitionTo($state.current, $stateParams, { reload: true, inherit: false, notify: true });

			};

			

		});
