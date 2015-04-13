'use strict';

angular.module('teamstudyApp').controller('MainController',
		function($scope, GroupList, Principal,$stateParams) {
			
			Principal.identity().then(function(account) {
				$scope.account = account;
				$scope.isAuthenticated = Principal.isAuthenticated;
				$scope.isInRole = Principal.isInRole;
			}).then(function() {
				
				$scope.groupIsSelected = function(){
					
					if($stateParams.groupId ==null){
						return false;
					}else{
						return true;
					}
					
				};
				
			});
			
		
			
	        
	    

		});
