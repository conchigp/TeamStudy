'use strict';

angular.module('teamstudyApp').controller(
		'SidebarrightController',
		function($stateParams, $state, $scope, StudentsCRUD, StudentsListAll,MessageChatCRUD, UserById, Principal) {
			
			Principal.identity().then(function(account) {
				$scope.account = account;
				$scope.isAuthenticated = Principal.isAuthenticated;
				$scope.isInRole = Principal.isInRole;

			}).then(function() {

				var groupId = localStorage.getItem('groupId');
				$scope.groupId = groupId;

				StudentsCRUD.get({
					groupId : groupId
				}, function(result) {
					$scope.students = result.data;

				});

				// messageChat

				MessageChatCRUD.get({
					groupId : $scope.groupId
				}, function(result) {
					$scope.messagesChat = result.data;
					$scope.messagesChat.forEach(function(item) {
						UserById.get({
							userId : item.userId
						}, function(result) {
							item.username = result.data.login;
						});

					});

				});
				
				$scope.createMessageChat = function() {
		    	   	$scope.messageChatAux = {
							content : $scope.chat.content,
						};
		    	   	MessageChatCRUD.create({groupId : localStorage.getItem('groupId')},
		    	   			$scope.messageChatAux, function() {
						// $scope.clear();
						$state.reload();
					});
				};
				
				


			});

		});
