'use strict';

angular.module('teamstudyApp').controller('SidebarleftController',
		function($scope,$state,$stateParams, GroupList, GroupListAdmin, GroupCRUDAdmin, Principal,$q) {

			Principal.identity().then(function(account) {
				$scope.groups = [];
				$scope.account = account;
				$scope.isAuthenticated = Principal.isAuthenticated;
				$scope.isInRole = Principal.isInRole;
			}).then(function() {
				
				
				
				if (Principal.isInRole('ROLE_ADMIN')) {
					
					$scope.loadAllAdmin();
					
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
			
			$scope.loadAllAdmin = function() {
				$scope.groups.$promise = GroupListAdmin.get(function(result) {
	               
	                for (var i = 0; i < result.data.length; i++) {
	                    $scope.groups.push(result.data[i]);
	                }
	            });
        };
        
    	$scope.reset = function() {
	           
            $scope.groups = [];
            $scope.loadAllAdmin();
        };
	        
	    
				
				
				$scope.create = function() {
					GroupCRUDAdmin.update($scope.group);
					$scope.reset();
					$('#saveGroupModal').modal('hide');
					$scope.clear();
					
// $state.reload();
				};

				$scope.update = function(id) {
					GroupCRUDAdmin.get({groupId : id}, function(result) {
						$scope.group = result.data;
						$('#saveGroupModal').modal('show');
					});
					
				};
				
				$scope.clear = function() {
					$scope.group = {
						name : null,
						description : null,
						id: null
					};
					$scope.editForm.$setPristine();
					$scope.editForm.$setUntouched();
				};
				
				$scope.delete = function (id) {
					GroupCRUDAdmin.get({groupId: id}, function(result) {
		                $scope.group = result.data;
		                $('#deleteGroupConfirmation').modal('show');
		            });
		        };
		        
		        $scope.confirmDelete = function (id) {
		        	GroupCRUDAdmin.delete({groupId: id},
		                function () {
		                   
		                });
		        		$scope.reset();
		        		$('#deleteGroupConfirmation').modal('hide');
	                    $scope.clear();
	                   
	                   
		        };

				
				
				$scope.local = function(groupId){
					localStorage.setItem('groupId', groupId);
 $state.transitionTo($state.current, $stateParams, { reload: true, inherit:false, notify: true });

				};
				
				

			

			
			
			

			

		});
