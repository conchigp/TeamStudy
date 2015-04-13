//'use strict';
//
//angular.module('teamstudyApp')
//    .controller('GroupController', function ($stateParams,$scope,Group,Principal) {
//    	
//    	
//    	$scope.create = function () {
//            Group.update($scope.group,
//                function () {
//                    $('#saveGroupModal').modal('hide');
//                    $scope.clear();
//                });
//        };
//        
//        $scope.update = function (id) {
//            Group.get({id: id}, function(result) {
//                $scope.group = result;
//                $('#saveGroupModal').modal('show');
//            });
//        };
//        
//        $scope.clear = function () {
//            $scope.group = {name: null, description: null};
//            $scope.editForm.$setPristine();
//            $scope.editForm.$setUntouched();
//        };
//               
//        });
