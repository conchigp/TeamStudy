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
					MessageChatCRUD.get({
						groupId : $scope.groupId
					}, function(result) {
						$scope.messagesChat = result.data;
						
		    			$scope.students.forEach(function (student) {
		    				
		    				//student.nameUsuario;

			    			$scope.messagesChat.forEach(function (message) {
			    							   
			    				message.nameUser;
//			    				console.log(message.userId);
//			    				console.log(student.id);
			    				
			    				if(student.id == message.userId ){
			    					message.nameUser = student.firstName;
			    					console.log(message.nameUser);
			    				}
			    				
			    			});
		    				
		    			});


					});

				});

				// messageChat


				$scope.createMessageChat = function() {
		    	   	$scope.messageChatAux = {
							content : $scope.chat.content,
						};
		    	   	MessageChatCRUD.create({groupId : localStorage.getItem('groupId')},$scope.messageChatAux, function() {
						// $scope.clear();
						$state.reload();
					});
				};
				
				


			});

		});
