'use strict';

angular.module('teamstudyApp').controller('SidebarrightController',
		function($scope, Group, Principal) {
			$scope.isInRole = Principal.isInRole;
			

		});
