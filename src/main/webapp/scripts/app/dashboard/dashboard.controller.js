'use strict';

angular.module('teamstudyApp')
		.controller('DashboardController',
				function($stateParams,$state, $scope, Principal, Message,MessageList,ThreadCRUD,ThreadListForGroup) {

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
			    			$scope.threadsAll = result.data;

			    		});
			    		
			    		ThreadListForGroup.get({
			    			groupId : groupId
						},function(result) {
			    			$scope.threadsAll = result.data;
			    			var array = [];
			    						    			
			    			$scope.threadsAll.forEach(function (item) {
			    				
			    				MessageList.get({
			    					threadId : item.id
			    				},function(result){
			    					var mensajes = result.data;
			    					item.tamano = mensajes;
			    					array.push(item.title, item.tamano.length);
			    					$scope.pos0 = array[0];
			    					$scope.pos1 = array[1];
			    					$scope.data =[{title:$scope.pos0, tamano:$scope.pos1}];
			    				});
			    			});
			    			$scope.array=array;
			    		});
			    	}).then(function(){
			    		
			    		new Morris.Line({
	    				element : 'myfirstchart',
	    				data : $scope.data,
	    				xkey : 'title',
	    				ykeys : [ 'tamano' ],
	    				labels : ['Title', 'Tamano']
			    		
	    			});
			    	
			    	});
					
					
					
				});


