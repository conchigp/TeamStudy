'use strict';

angular.module('teamstudyApp')
		.controller(
				'AdminManagementController',
				function($stateParams,$state, $scope, StudentsListAll, StudentsCRUD,
						Principal,teachersListAll,teachersCRUD) {

					Principal.identity().then(function(account) {
						$scope.account = account;
						$scope.isAuthenticated = Principal.isAuthenticated;
						$scope.isInRole = Principal.isInRole;

					}).then(function() {
//						if (Principal.isInRole('ROLE_ADMIN')) {
//							$scope.groups = GroupListAdmin.get();
//						} else {
//							if(Principal.isInRole('ROLE_USER')){
//								$scope.groups = GroupList.get({
//									userId : $scope.account.id
//								});
//							}
//							
//						}

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
						
						teachersListAll.get(function(result) {
							$scope.teachersAll = result.data;

						});
						
						teachersCRUD.get({
							groupId : groupId
						}, function(result) {
							$scope.teacherGroup = result.data;

						});
						
						
						
						

					});

					
					$scope.deleteStudent = function (studentId) {
						
						StudentsCRUD.delete({groupId: $stateParams.groupId,studentId: studentId}
			                );
			        };
			        
			        $scope.addStudent = function (studentId) {
						
						StudentsCRUD.update({groupId: $stateParams.groupId,studentId: studentId}
			             );
						$state.transitionTo($state.current, $stateParams, { reload: true, inherit: false, notify: true });

			        };
			        
			        $scope.addTeacher = function (teacherId) {
						
			        	teachersCRUD.update({groupId: $stateParams.groupId,teacherId: teacherId}
			             );
			        };
			        
			        $scope.deleteTeacher = function (teacherId) {
						
			        	teachersCRUD.delete({groupId: $stateParams.groupId}
			             );
			        };
			        
			        
			       

				});

angular.module('teamstudyApp')
	.filter('AlumsNotAsign', function() {
		 return function (items,students) {
	            var result = [];

	            if (items) {
	                items.forEach(function (item) {
	                	var entra = true;
	                	students.forEach(function(student){
	                		if(student.id == item.id){
	                			entra = false;
	                		}
	                	});
	                    if (entra == true) {
	                        result.push(item);
	                    };
	                });
	            }

	            return result;
	        };
	});