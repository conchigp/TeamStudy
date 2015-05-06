'use strict';

angular.module('teamstudyApp')
	.controller('FolderController', function ($scope, $state, $stateParams, Folder, FolderList, Archive, ArchiveList, ParseLinks, 
			fileUpload,Download,Principal) {
		
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
					id : $scope.folder.id,
					groupId : localStorage.getItem('groupId')
				};
			Folder.update($scope.folderAux, function() {
				// $scope.clear();
				$state.reload();
			});
			$('#saveFolderModal').modal('hide');
		};
	
		$scope.update = function(id) {
			Folder.get({folderId : id}, function(result) {
				$scope.folder = result.data;
				$('#saveFolderModal').modal('show');
			});
		};
			
		$scope.deleteFolder = function (folderId) {
    		Folder.delete({folderId: folderId});
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
		
		$scope.upload = function(folderId, file) {
			console.log(folderId);
			console.log(file);
// Archive.update({folderId : folderId}, {file : file});
//		     console.log('file is ' + JSON.stringify(file));
		     var uploadUrl = "api/archive";
		     fileUpload.uploadFileToUrl(file, uploadUrl,folderId);
			$state.reload();
		};
		
		$scope.uploadFile = function(){
	        var file = $scope.myFile;
	        console.log('file is ' + JSON.stringify(file));
	        var uploadUrl = "api/archive";
	        fileUpload.uploadFileToUrl(file, uploadUrl);
	    };
	    
	    $scope.download = function(folderId,gridId){
	    	Download.get({folderId : folderId},{gridId: gridId});
	    };
		
		$scope.deleteArchive = function (folderId,gridId) {
    		Archive.delete({folderId : folderId},{gridId: gridId});
    		$state.reload();
    	};
    	
	});

angular.module('teamstudyApp')
	.directive('uploaderModel', ["$parse", function ($parse) {
	return {
		restrict: 'A',
		link: function (scope, iElement, iAttrs) 
		{
			iElement.on("change", function(e)
			{
				$parse(iAttrs.uploaderModel).assign(scope, iElement[0].files[0]);
			});
		}
	};
}]);

angular.module('teamstudyApp').service('fileUpload', ['$http', function ($http) {
    this.uploadFileToUrl = function(file, uploadUrl,folderId){
        var fd = new FormData();
        fd.append('file', file);
        fd.append('folderId',folderId);
        $http.post(uploadUrl, fd, {
            transformRequest: angular.identity,
            headers: {'Content-Type': undefined}
        })
        .success(function(){
        })
        .error(function(){
        });
    }
}]);



