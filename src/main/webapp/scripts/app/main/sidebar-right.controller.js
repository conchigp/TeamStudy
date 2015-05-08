'use strict';

angular.module('teamstudyApp').controller(
		'SidebarrightController',
		function($stateParams, $state, $scope, StudentsCRUD, StudentsListAll,
				MessageChatCRUD, UserById, Principal, $interval) {

			Principal.identity().then(function(account) {
				$scope.account = account;
				$scope.isAuthenticated = Principal.isAuthenticated;
				$scope.isInRole = Principal.isInRole;

			}).then(function() {
								
				var groupId = localStorage.getItem('groupId');
				$scope.groupId = localStorage.getItem('groupId');

				StudentsCRUD.get({
					groupId : localStorage.getItem('groupId')
				}, function(result) {
					$scope.students = result.data;
					if (Principal.isInRole('ROLE_USER')) {
						$interval(function() {
							MessageChatCRUD.get({
								groupId : localStorage.getItem('groupId')
							}, function(result) {
								$scope.messagesChat = result.data;
								
								$scope.students.forEach(function(student) {
									console.log('ABCD');
									$scope.messagesChat.forEach(function(message) {
										console.log('1234');
										message.nameUser;

										if (student.id == message.userId) {
											message.nameUser = student.firstName;
										}

									});

								});

							});
						}, 20000);
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
