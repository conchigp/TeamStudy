'use strict';

angular.module('teamstudyApp').controller('SidebarrightController',
		function($stateParams,$state, $scope,StudentsCRUD, StudentsListAll, MessageChatListForGroup, MessageChatCRUD, UserById, Principal) {
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
								
					StudentsListAll.get(function(result) {
						$scope.studentsAll = result.data;
						
					});
					
					//messageChat
					
					MessageChatListForGroup.get({
						groupId : $scope.groupId
					},function(result){
						$scope.messagesChat = result.data;
						
		    			$scope.messagesChat.forEach(function (item) {
		    				
		    				UserById.get({
		    					userId : item.userId
		    				},function(result){
		    					item.username = result.data.login;
		    				});
		    				
		    			});
						
					});
					
//					$scope.userId = messageChat.userId;
//					
//					UserById.get({
//						userId : $scope.userId
//					},function(result){
//						$scope.userId = result.data.id;
//					});


						  
					$scope.createMessageChat = function(id) {
						MessageChatCRUD.update({groupId: id}, function() {
						});
						$state.reload();
					};
					
					
				});
				

		});

