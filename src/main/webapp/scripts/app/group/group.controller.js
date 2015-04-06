'use strict';

angular.module('teamstudyApp')
    .controller('GroupController', function ($stateParams,$scope,Group,Principal) {
    	
    	
    	$scope.create = function () {
            Group.update($scope.group,
                function () {
                    $('#saveBookModal').modal('hide');
                    $scope.clear();
                });
        };
        
        $scope.update = function (id) {
            Group.get({id: id}, function(result) {
                $scope.group = result;
                $('#saveBookModal').modal('show');
            });
        };
        
        $scope.clear = function () {
        	//El teacherID lo acabo de iniciar a ver si mostraba algo de información sobre él.
            $scope.group = {name: null, description: null, teacherId: $scope.username};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };

       
        });
