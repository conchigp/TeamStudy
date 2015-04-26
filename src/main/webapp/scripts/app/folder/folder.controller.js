'use strict';

angular.module('teamstudyApp')
	.controller('FolderController', function ($scope, $state, $stateParams, Folder, FolderList, Archive, ArchiveList, ParseLinks, 
			Principal) {
		
		Principal.identity().then(function(account) {
    		$scope.account = account;
    		$scope.isAuthenticated = Principal.isAuthenticated;
    		$scope.isInRole = Principal.isInRole;

    	}).then(function(){
    	
			var groupId = localStorage.getItem('groupId')
			$scope.groupId = groupId;
			
			var folderId = $stateParams.folderId;
    		$scope.folderId = folderId;
	    	
			FolderList.get({
				groupId : groupId
			},function(result) {
    			$scope.folders = result.data;
    		});
	    	
	    	
    	});
	
       $scope.create = function() {
    	   	$scope.folderAux = {
					title : $scope.folder.title,
					groupId : localStorage.getItem('groupId')
				};
			Folder.update($scope.folderAux, function() {
				//$scope.clear();
			});
			$('#saveFolderModal').modal('hide');
			$state.reload();
		};
	
			$scope.update = function(id) {
				Folder.get({folderId : id}, function(result) {
					$scope.folder = result.data;
					$('#saveFolderModal').modal('show');
				});
			};
			
			$scope.deleteFolder = function (threadId) {
	    		Folder.delete({folderId: $stateParams.folderId});
	    		$state.reload();
	    	};
	
			$scope.clear = function() {
				$scope.folder = {
					title : null
				};
				$scope.editForm.$setPristine();
				$scope.editForm.$setUntouched();
			};
			
			$scope.local = function(folderId){
				localStorage.setItem('folderId', folderId);
				 $state.transitionTo($state.current, $stateParams, { reload: true, inherit: false, notify: true });

			};
	});
