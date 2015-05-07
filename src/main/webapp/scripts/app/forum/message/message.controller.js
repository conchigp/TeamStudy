'use strict';

angular.module('teamstudyApp')
	.controller('MessageController', function ($scope, $state, $stateParams, Message , Reply , ReplyList ,MessageList, Principal) {
		
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
    			
    			$scope.messages.forEach(function (item) {
    				
    				ReplyList.get({
    					messageId : item.id
    				},function(result){
    					item.replies = result.data;
    				});
    				
    			});
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
				$state.reload();
			});
			$('#saveMessageModal').modal('hide');
			
		};
		
		$scope.createReply = function(messageId,reply) {
    	   	$scope.replyAux = {
					description : reply,
					messageId : messageId,
					userId : $scope.account.id
				};
			Reply.update($scope.replyAux, function() {
				// $scope.clear();
				$state.reload();
			});
			$('#saveReplyModal').modal('hide');
		};
	
		$scope.update = function(id) {
			Message.get({messageId : id}, function(result) {
				$scope.message = result.data;
				$('#saveMessageModal').modal('show');
			});
		};
		
		$scope.updateReply = function(id) {
			Reply.get({replyId : id}, function(result) {
				$scope.reply = result.data;
				$('#saveReplyModal').modal('show');
			});
		};
			
			$scope.deleteMessage = function (messageId) {
	    		Message.delete({messageId: messageId},function(){
	    			$state.reload();
	    		});
	    		
	    	};
	    	
	    	$scope.deleteReply = function (replyId) {
	    		Reply.delete({replyId: replyId},function(){
	    			$state.reload();
	    		});
	    	};
	
			$scope.clear = function() {
				$scope.message = {
					description : null
				};
				$scope.editForm.$setPristine();
				$scope.editForm.$setUntouched();
			};
			
			$scope.clearReply = function() {
				$scope.reply = {
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
