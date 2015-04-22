'use strict';

angular.module('teamstudyApp')
    .controller('ThreadController', function ($stateParams,$state, $scope, ThreadCRUD, ThreadListForGroup,
			Principal) {
    	
    	Principal.identity().then(function(account) {
    		$scope.account = account;
    		$scope.isAuthenticated = Principal.isAuthenticated;
    		$scope.isInRole = Principal.isInRole;

    	}).then(function(){
    		
    		
    		var groupId = localStorage.getItem('groupId');

			$scope.groupId = groupId;
    		
    		var threadId = $stateParams.threadId;
    		$scope.threadId = threadId;
    		
//    		ThreadCRUD.get({
//    			threadId : threadId
//    		}, function(result) {
//    			$scope.threads = result.data;
//
//    		});
    		
    		ThreadListForGroup.get({
				groupId : groupId
			},function(result) {
    			$scope.threadsAll = result.data;

    		});

    	});
    
//    	$scope.deleteThread = function (threadId) {
//    		ThreadCRUD.delete({threadId: $stateParams.threadId});
//    		$state.reload();
//    	};
//    	
//    	$scope.addThread = function (threadId) {
//    		ThreadCRUD.update({threadId: $stateParams.threadId},function () {
//    	      $state.reload();
//        });
//    	};		
});