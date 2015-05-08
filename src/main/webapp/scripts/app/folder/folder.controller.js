'use strict';

angular.module('teamstudyApp')
	.controller('FolderController', function ($scope, $state, $stateParams, $http,Folder, FolderList, Archive, ArchiveList, ParseLinks, 
			fileUpload,Download,localStorageService,Principal) {
		
		Principal.identity().then(function(account) {
    		$scope.account = account;
    		$scope.isAuthenticated = Principal.isAuthenticated;
    		$scope.isInRole = Principal.isInRole;

    	}).then(function(){
    	
			var groupId = localStorage.getItem('groupId');
			$scope.groupId = groupId;
			
			var folderId = $stateParams.folderId;
    		$scope.folderId = folderId;
    		
    		 $scope.uploading = false;
	    	
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
    			Folder.delete({folderId: folderId},function(){
    				$state.reload();
    			});
    			
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
// Archive.update({folderId : folderId}, {file : file});
//		     console.log('file is ' + JSON.stringify(file));
		     var uploadUrl = "api/archive";
		     $scope.uploading = true;
		     fileUpload.uploadFileToUrl(file, uploadUrl,folderId);
		};
		
		$scope.uploadFile = function(){
	        var file = $scope.myFile;
	        var uploadUrl = "api/archive";
	        fileUpload.uploadFileToUrl(file, uploadUrl);
	    };
	    
	    $scope.download = function(folderId,archive){
//	    	Download.get({folderId : folderId},{gridId: gridId},function(result){
//	    		var blob = new Blob([result.data], {type: "text/plain;charset=utf-8"});
//	    		saveAs(blob,"probando.txt");
//	    		
//	    	});
	    	var gridId = archive.gridId;
	    	var contentType = archive.contentType;
	    	
	    	//primer metodo
	    	
//	    	 $http({
//	    		 url: "api/archive/download",
//	    		 params: {
//	    			 folderId: folderId,
//	    			 gridId: gridId
//	    		 },
//	    		 method: "GET"
//	    	 }, {responseType: 'arraybuffer'})
//	    	 
//	         .success(function(response){
//	        	 var blob = new Blob([response], {
//	        	        type: archive.contentType
//	        	      });
	        	 
	        	 
	        	 
//	        	 saveAs(blob, archive.title);
//	         })
//	         .error(function(){
//	         });
//	    	var blob = new Blob(["Hello, world!"], {type: "text/plain;charset=utf-8"});
//	    	saveAs(blob, "hello world.txt");
	    	
	    	
	    	//segundo metodo
	    	
//	    	var tokenfull = localStorageService.get('token');
//	    	var token = tokenfull.token;
//	    	console.log(token);
//	    	
//	 
//	    	    $.fileDownload("api/archive/download", 
//	    	    {
//	    	        httpMethod : "GET",
//	    	        data : {
//	    	            folderId : folderId,
//	    	            gridId : gridId
//	    	        }
//	    	        
//	    	    }).done(function(e, response)
//	    	    {
//	    	    	console.log("success");
//	    	     // success
//	    	    }).fail(function(e, response)
//	    	    {
//	    	     // failure
//	    	    	console.log("fail");
//	    	    });
	    	
	    	
	    	//tercer metodo
	    	
	    	 $http({
                 url : 'api/archive/download',
                 method : 'POST',
                 params : {
                	 folderId : folderId,
	    	         gridId : gridId
                 },
                 headers : {
                     'Content-type' : contentType,
                 },
                 responseType : 'arraybuffer'
             }).success(function(data, status, headers, config) {
                 var file = new Blob([ data ], {
                     type : contentType
                 });
                 //trick to download store a file having its URL
                 var fileURL = URL.createObjectURL(file);
                 var a         = document.createElement('a');
                 a.href        = fileURL; 
                 a.target      = '_blank';
                 a.download    = archive.title;
                 document.body.appendChild(a);
                 a.click();
             }).error(function(data, status, headers, config) {

             });
	    };
		
		$scope.deleteArchive = function (folderId,gridId) {
    		Archive.delete({folderId : folderId},{gridId: gridId},function(){
    			$state.reload();
    		});
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

angular.module('teamstudyApp').service('fileUpload', ['$http','$state', function ($http,$state) {
    this.uploadFileToUrl = function(file, uploadUrl,folderId){
        var fd = new FormData();
        fd.append('file', file);
        fd.append('folderId',folderId);
        $http.post(uploadUrl, fd, {
            transformRequest: angular.identity,
            headers: {'Content-Type': undefined}
        })
        .success(function(){
        	$state.reload();
        })
        .error(function(){
        });
    }
}]);



