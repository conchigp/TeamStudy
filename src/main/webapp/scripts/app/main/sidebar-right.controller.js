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
//					$scope.teacherGroup = result.data;
//						MessageChatCRUD.get({
//							groupId : $scope.groupId
//						}, function(result) {
//							$scope.messagesChat = result.data;
//								$scope.messagesChat.forEach(function(message) {
//									message.nameTeacher;
//									if (teacherGroup.id == message.userId) {
//										message.nameTeacher = teacherGroup.firstName;
//									}
//								});
//						});
//				});

				StudentsCRUD.get({groupId : groupId}, function(result) {
					$scope.students = result.data;
					if (Principal.isInRole('ROLE_USER')) {
						$interval(function() {
							MessageChatCRUD.get({
								groupId : $scope.groupId
							}, function(result) {
								$scope.messagesChat = result.data;
								
								$scope.students.forEach(function(student) {
									$scope.messagesChat.forEach(function(message) {
										message.nameUser;
										if (student.id == message.userId) {
											message.nameUser = student.firstName;
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
