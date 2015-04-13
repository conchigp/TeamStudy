'use strict';

angular.module('teamstudyApp').controller('WikiController',
		function($stateParams, $scope, GroupCRUDAdmin) {

			var groupId = localStorage.getItem('groupId');

			$scope.groupId = groupId;

			$scope.group = GroupCRUDAdmin.get({
				groupId : groupId
			});

		});
