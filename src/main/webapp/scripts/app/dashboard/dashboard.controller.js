'use strict';

angular.module('teamstudyApp').controller(
		'DashboardController',
		function($stateParams, $state, $scope, Principal, Message, MessageList,
				ThreadCRUD, ThreadListForGroup, Reply , ReplyList ) {

			Principal.identity().then(function(account) {
				$scope.account = account;
				$scope.isAuthenticated = Principal.isAuthenticated;
				$scope.isInRole = Principal.isInRole;

			}).then(function() {

				var groupId = localStorage.getItem('groupId')
				$scope.groupId = groupId;

				var threadId = $stateParams.threadId;
				$scope.threadId = threadId;

				ThreadListForGroup.get({
					groupId : groupId
				}, function(result) {
					$scope.threadsAll = result.data;

				});
				ThreadListForGroup.get({
					groupId : groupId
				}, function(result) {
					$scope.threadsAll = result.data;
					$scope.array = [];
					$scope.labels = [];
					$scope.data = [];
					

					$scope.threadsAll.forEach(function(item) {

						MessageList.get({
							threadId : item.id
						}, function(result) {
							var mensajes = result.data;
							item.tamano = mensajes;
							if (item.tamano.length != 0) {
								$scope.labels.push(item.title);
								$scope.data.push(item.tamano.length);
							}
						});
					});

				});
				
				$scope.labelsRepository = ["Apuntes de Inglés", "Exámenes resueltos de Inglés", "Ejercicios resueltos"];
				$scope.dataRepository = [4, 8, 5];
			});
			
			
			
//			function mensajes(threadId){
//				console.log('hola');
//				MessageList.get({
//					threadId : threadId
//				},function(result) {
//	    			$scope.messages = result.data;
//	    			$scope.labelsMessage = [];
//	    			$scope.dataMessage = [];
//	    			$scope.messages.forEach(function (item) {
//	    				
//	    				ReplyList.get({
//	    					messageId : item.id
//	    				},function(result){
//	    					item.replies = result.data;
//	    					$scope.labelsMessage.push(item.description);
//							$scope.dataMessage.push(item.replies.length);
//	    					
//	    				});
//	    				
//	    			});
//	    		});
//			};

		});
