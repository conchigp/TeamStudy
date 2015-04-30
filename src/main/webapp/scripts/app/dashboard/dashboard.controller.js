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
			    				});
			    			});
			    			$scope.array=array;
			    		});
			    	}).then(function(){
			    		
			    		console.log($scope.array);
			    		new Morris.Line({
	    				// ID of the element in which to draw the chart.
	    				element : 'myfirstchart',
	    				// Chart data records -- each entry in this array corresponds to a point on
	    				// the chart.
	    				data : [
	    				        { year: '2008', value: 20 },
	    				        { year: '2009', value: 10 },
	    				        { year: '2010', value: 5 },
	    				        { year: '2011', value: 5 },
	    				        { year: '2012', value: 20 }
	    				      ],
	    				// The name of the data record attribute that contains x-values.
	    				xkey : 'year',
	    				// A list of names of data record attributes that contain y-values.
	    				ykeys : [ 'value' ],
	    				// Labels for the ykeys -- will be displayed when you hover over the
	    				// chart.
	    				labels : [ 'Value' ]
	    			});
			    	
			    	});
					
					
					
				});


