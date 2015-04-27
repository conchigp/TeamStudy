'use strict';

angular.module('teamstudyApp')
	.controller('MessageController', function ($scope, $state, $stateParams, Message, MessageList, Principal) {
		
		Principal.identity().then(function(account) {
    		$scope.account = account;
    		$scope.isAuthenticated = Principal.isAuthenticated;
    		$scope.isInRole = Principal.isInRole;

    	}).then(function(){
    	
    		var threadId = $stateParams.threadId;
    		$scope.threadId = threadId;
			
			var messageId = $stateParams.messageId;
    		$scope.messageId = messageId;
	    	
			MessageList.get({
				threadId : threadId
			},function(result) {
    			$scope.messages = result.data;
    		});
	    	
	    	
    	});
	
       $scope.create = function() {
    	   	$scope.messageAux = {
					description : $scope.message.description,
					id : $scope.message.id,
					threadId : $stateParams.threadId,
					userId : $scope.account.id
				};
			Message.update($scope.messageAux, function() {
				// $scope.clear();
			});
			$('#saveMessageModal').modal('hide');
			$state.reload();
		};
	
		$scope.update = function(id) {
			Message.get({messageId : id}, function(result) {
				$scope.message = result.data;
				$('#saveMessageModal').modal('show');
			});
		};
			
			$scope.deleteMessage = function (messageId) {
	    		Message.delete({messageId: messageId});
	    		$state.reload();
	    	};
	
			$scope.clear = function() {
				$scope.message = {
					description : null
				};
				$scope.editForm.$setPristine();
				$scope.editForm.$setUntouched();
			};
			
			$scope.local = function(messageId){
				localStorage.setItem('messageId', messageId);
				 $state.transitionTo($state.current, $stateParams, { reload: true, inherit: false, notify: true });

			};
	});
