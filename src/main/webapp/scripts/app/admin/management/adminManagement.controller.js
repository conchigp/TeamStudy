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
					
					 $scope.loadAll = function() {
						 StudentsListAll.query(function(result) {
								$scope.studentsAll = result.data;

							});
				        };
						
					

					
					$scope.deleteStudent = function (studentId) {
						StudentsCRUD.delete({groupId: $stateParams.groupId,studentId: studentId},function(){
							$state.reload();
			    		});
			    	};
			        
			        $scope.addStudent = function (studentId) {
						
						StudentsCRUD.update({groupId: $stateParams.groupId,studentId: studentId},function () {
		                  $state.reload();
		                });
			             
//						$state.transitionTo($state.current, $stateParams, { reload: true, inherit: false, notify: true });
//						$state.reload();
						
					
					
						

			        };
			        
			        $scope.addTeacher = function (teacherId) {
						
			        	teachersCRUD.update({groupId: $stateParams.groupId,teacherId: teacherId}
			             );
			        	$state.reload();
			        };
			        
			        $scope.deleteTeacher = function (teacherId) {
						
			        	teachersCRUD.delete({groupId: $stateParams.groupId},function(){
			            	 $state.reload();
			             });
			        };
			        
				}).filter('AlumsNotAsign', function() {
					
					 return function (items,students) {
						 if(students != undefined){
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
						 }};
				});
	