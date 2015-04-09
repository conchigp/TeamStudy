'use strict';

angular.module('teamstudyApp').controller('SidebarrightController',
		function($scope, GroupList, Principal) {
			$scope.isInRole = Principal.isInRole;
			

		});
