'use strict';

angular.module('teamstudyApp')
    .controller('ThreadController', function ($stateParams,$state, $scope, ThreadCRUD, ThreadListForGroup, MessageList, Message,
			Principal) {
    	
    	Principal.identity().then(function(account) {
    		$scope.account = account;
    		$scope.isAuthenticated = Principal.isAuthenticated;
    		$scope.isInRole = Principal.isInRole;

    	}).then(function(){
    		
    		
    		var groupId = localStorage.getItem('groupId')
			$scope.groupId = groupId;
    		
    		var threadId = $stateParams.threadId;
    		$scope.threadId = threadId;
    		
    		ThreadListForGroup.get({
    			groupId : groupId
			},function(result) {
    			$scope.threads = result.data;
    			$scope.threads.forEach(function (item) {
    				
    				MessageList.get({
    					threadId : item.id
    				},function(result){
    					item.messages = result.data;
    				});
    				
    			});
    		});

    	});
    	
    	$scope.create = function() {
    		$scope.threadAux = {
    				title : $scope.thread.title,
    				description : $scope.thread.description,
					id : $scope.thread.id,
					groupId : localStorage.getItem('groupId')
				};
    		ThreadCRUD.update($scope.threadAux, function(){
    			//$scope.clear();
    			$state.reload();
    		});
    		$('#saveThreadModal').modal('hide');
    	};
    	
    	$scope.update = function(id){
    		ThreadCRUD.get({threadId : id}, function(result) {
    			$scope.thread = result.data;
    			$('#saveThreadModal').modal('show');
    		});
    	};
    	
    	$scope.clear = function() {
			$scope.thread = {
				title : null,
				description : null
			};
			$scope.editForm.$setPristine();
			$scope.editForm.$setUntouched();
		};		

    	$scope.deleteThread = function (threadId) {
    		ThreadCRUD.delete({threadId: threadId},function(){
    		$state.reload();
    		});
    	};
		
		$scope.local = function(threadId){
			localStorage.setItem('threadId', threadId);
			 $state.transitionTo($state.current, $stateParams, { reload: true, inherit: false, notify: true });

		};
    
});