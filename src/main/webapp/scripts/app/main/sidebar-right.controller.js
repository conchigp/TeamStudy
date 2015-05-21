'use strict';

angular.module('teamstudyApp').controller(
		'SidebarrightController',
		function($stateParams, $state, $scope, StudentsCRUD, teachersCRUD, StudentsListAll,
				MessageChatCRUD, UserById, Principal, $interval) {

			Principal.identity().then(function(account) {
				$scope.account = account;
				$scope.isAuthenticated = Principal.isAuthenticated;
				$scope.isInRole = Principal.isInRole;

			}).then(function() {
								
				var groupId = localStorage.getItem('groupId');
				$scope.groupId = localStorage.getItem('groupId');		
				
//				teachersCRUD.get({groupId : groupId}, function(result){
//					$scope.teacher = result.data;
//						MessageChatCRUD.get({
//							groupId : $scope.groupId
//						}, function(result) {
//							$scope.messagesChat = result.data;
//								$scope.messagesChat.forEach(function(message) {
//									message.nameTeacher;
//									if (teacher.id == message.userId) {
//										message.nameTeacher = teacher.firstName;
//									}
//								});
//						});
//				
				
				StudentsCRUD.get({groupId : groupId}, function(result) {
					$scope.students = result.data;
					if (Principal.isInRole('ROLE_USER')) {
						teachersCRUD.get({groupId : groupId}, function(result){
							$scope.teacher = result.data;
						});
						$interval(function() {
							MessageChatCRUD.get({
								groupId : $scope.groupId
							}, function(result) {
								$scope.messagesChat = result.data;
								$scope.students.forEach(function(student) {
									$scope.messagesChat.forEach(function(message) {
										if (student.id == message.userId) {
											message.name = student.firstName;
//										}else{
//											message.name = $scope.teacher.firstName;
										}
									});
								});

							});
						}, 2000);
					};
				});

				// messageChat

				$scope.createMessageChat = function() {
					$scope.messageChatAux = {
						content : $scope.chat.content,
					};
					MessageChatCRUD.create({
						groupId : localStorage.getItem('groupId')
					}, $scope.messageChatAux, function() {
						// $scope.clear();
						$state.reload();
					});
				};

			});

		});
