'use strict';

angular.module('teamstudyApp').controller(
		'SidebarrightController',
		function($stateParams, $state, $scope, StudentsCRUD, StudentsListAll,
				MessageChatListForGroup, MessageChatCRUD, UserById, Principal) {
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

				MessageChatListForGroup.get({
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

				$scope.create = function() {
					// $scope.chat.id =
					// $scope.chat.groupId =
					// $scope.chat.userId = $scope.account;
					// $scope.chat.creationMoment = moment();
					MessageChatCRUD.update($scope.chat, function() {
						$state.reload();
					});
					// $scope.reset();
					// $scope.clear();

				};

				// Me he inventado esta parte, copiando de reply del foro

				// $scope.create = function() {
				// $scope.messageChatAux = {
				// content : $scope.messageChat.content,
				// id : $scope.messageChat,
				// groupId : $stateParams.groupId,
				// userId : $scope.account.id
				// };
				// MessageChatCRUD.update($scope.messageChatAux, function() {
				// // $scope.clear();
				// $state.reload();
				// });
				//							
				//							
				// };
				//					
				//					
				// $scope.createMessageChat =
				// function(messageChatId,messageChat) {
				// $scope.messageChatAux = {
				// content : messageChat,
				// messageChatId : messagechatId,
				// groupId : $stateParams.groupId,
				// userId : $scope.account.id
				// };
				// MessageChatCRUD.update($scope.messageChatAux, function() {
				// // $scope.clear();
				// $state.reload();
				// });
				//						
				// };
				//					
				// $scope.updateMessageChat = function(id) {
				// MessageChatCRUD.get({messageChatId : id}, function(result) {
				// $scope.messageChat = result.data;
				//							
				// });
				// };

			});

		});
