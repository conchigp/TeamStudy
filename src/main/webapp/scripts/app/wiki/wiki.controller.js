'use strict';

angular.module('teamstudyApp').controller('WikiController',
		function($stateParams, $scope, GroupCRUDAdmin) {

			var groupId = sessionStorage.getItem('groupId');

			$scope.groupId = groupId;

			$scope.group = GroupCRUDAdmin.get({
				groupId : groupId
			});

		});
