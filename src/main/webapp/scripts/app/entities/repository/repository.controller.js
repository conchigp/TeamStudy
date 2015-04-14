'use strict';

angular.module('teamstudyApp')
	.controller('RepositoryController', function ($scope, $stateParams, Folder, FolderList, Archive, ArchiveList, ParseLinks) {
    	
    	/*var groupId = localStorage.getItem('groupId')
    	
    	$scope.folders = FolderList.get({folderId : folderId});

        $scope.create = function() {
			Folder.update($scope.folder, function() {
				$scope.clear();
				
			});
			$('#saveFolderModal').modal('hide');
		
		};

		$scope.update = function(id) {
			Folder.get({folderId : id}, function(result) {
				$scope.folder = result.data;
				$('#saveFolderModal').modal('show');
			});
		};
		
		$scope.confirmDelete = function (id) {
            Folder.delete({folderId: id},
                function () {
                    $scope.loadAll();
                    $scope.clear();
                });
        };

		$scope.clear = function() {
			$scope.folder = {
				name : null,
				description : null
			};
			$scope.editForm.$setPristine();
			$scope.editForm.$setUntouched();
		};*/
		
    });
