'use strict';

angular.module('teamstudyApp').controller(
		'SidebarrightController',
		function($stateParams, $state, $scope, StudentsCRUD, StudentsListAll,MessageChatCRUD, UserById, Principal, $interval) {
			
			Principal.identity().then(function(account) {
				$scope.account = account;
				$scope.isAuthenticated = Principal.isAuthenticated;
				$scope.isInRole = Principal.isInRole;

			}).then(function() {
				
//				function scrollDiv(){
//				    var div = document.getElementById('scrolldiv');
//				    console.log('Estoy dentro');
//				    div.scrollTop = '9999';
//				}
				
				var groupId = localStorage.getItem('groupId');
				$scope.groupId = groupId;
				
				StudentsCRUD.get({
					groupId : groupId
				}, function(result) {
					$scope.students = result.data;
					
					$interval(function(){
					MessageChatCRUD.get({
						groupId : $scope.groupId
					}, function(result) {
						$scope.messagesChat = result.data;
						
		    			$scope.students.forEach(function (student) {
		    				
			    			$scope.messagesChat.forEach(function (message) {
			    							   
			    				message.nameUser;
			    				
			    				if(student.id == message.userId ){
			    					message.nameUser = student.firstName;
			    				}
			    				
			    			});
		    				
		    			});

					}); 
					}, 2000);

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
