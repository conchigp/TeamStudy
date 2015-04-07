'use strict';

angular.module('teamstudyApp').controller('SidebarleftController',
		function($scope, Group, Principal) {

			Principal.identity().then(function(account) {
				$scope.account = account;
				$scope.isAuthenticated = Principal.isAuthenticated;
			}).then(function() {
				$scope.groups = Group.get({
					userId : $scope.account.id
				});
			});
			
			$scope.create = function () {
	            Group.update($scope.group,
	                function () {
	                    $('#saveGroupModal').modal('hide');
	                    $scope.clear();
	                });
	        };
	        
	        $scope.update = function (id) {
	            Group.get({id: id}, function(result) {
	                $scope.group = result;
	                $('#saveGroupModal').modal('show');
	            });
	        };
	        
	        $scope.clear = function () {
	            $scope.group = {name: null, description: null};
	            $scope.editForm.$setPristine();
	            $scope.editForm.$setUntouched();
	        };
	        
	        $scope.update = function (id) {
	            Group.get({id: id}, function(result) {
	                $scope.group = result;
	                $('#saveGroupModal').modal('show');
	            });
	        };

		});