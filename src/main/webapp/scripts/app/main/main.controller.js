'use strict';

angular.module('teamstudyApp').controller('MainController',
		function($scope, GroupList, Principal) {
			
			Principal.identity().then(function(account) {
				$scope.account = account;
				$scope.isAuthenticated = Principal.isAuthenticated;
				$scope.isInRole = Principal.isInRole;
			}).then(function() {
				
			});
			
		
	        
	    

		});
