'use strict';

angular.module('teamstudyApp').controller(
		'WikiController',
		function($stateParams, $scope,$state, $sce,GroupCRUDAdmin, News, Principal) {

			var groupId = localStorage.getItem('groupId');
			$scope.groupId = groupId;
			$scope.group = GroupCRUDAdmin.get({
				groupId : groupId
			});
			
			$scope.save = function() {
				GroupCRUDAdmin.update($scope.group);
			};

			Principal.identity().then(function(account) {
				$scope.account = account;
				$scope.isAuthenticated = Principal.isAuthenticated;
				$scope.isInRole = account.roles;
			}).then(function() {
				$scope.news = News.get({
					userId : $scope.account.id
				});
			});		
			
			$scope.create = function() {
	    		$scope.newsAux = {
	    				id : $scope.news.id,
	    				title : $scope.news.title,
	    				description : $scope.news.description
//						groupId : localStorage.getItem('groupId')
					};
	    		News.create({groupId : localStorage.getItem('groupId')},$scope.newsAux, function(){
	    			//$scope.clear();
	    		})
	    		$('#saveNewsModal').modal('hide');
				$state.reload();
	    	};
			
	    	$scope.clear = function() {
				$scope.thread = {
					title : null,
					description : null
				};
				$scope.editForm.$setPristine();
				$scope.editForm.$setUntouched();
			};
//			$scope.reset = function() {
//	            $scope.groups = [];
//	        };
//	        
//			$scope.create = function() {
//				getNews.create($scope.group);
//				$scope.reset();
//				$('#saveNewsModal').modal('hide');
//				$scope.clear();
//			};
//			$scope.clear = function() {
//				$scope.group = {
//					title : null,
//					description : null,
//					id: null
//				};
//				$scope.editForm.$setPristine();
//				$scope.editForm.$setUntouched();
//			};

			$scope.editorOptions = {
				language : 'es'
			};
			
			$scope.trustAsHtml = function(string) {
			    return $sce.trustAsHtml(string);
			};
			
			$scope.editorOptions.toolbar = [
					{
						name : 'basicstyles',
						groups : [ 'basicstyles', 'cleanup' ],
						items : [ 'Bold', 'Italic', 'Underline', 'Strike', '-',
								'RemoveFormat' ]
					},
					{
						name : 'paragraph',
						groups : [ 'list', 'indent', 'blocks', 'align' ],
						items : [ 'NumberedList', 'BulletedList', '-',
								'Outdent', 'Indent', '-', 'Blockquote', '-',
								'JustifyLeft', 'JustifyCenter', 'JustifyRight',
								'JustifyBlock', ]
					}, {
						name : 'links',
						items : [ 'Link', 'Unlink', 'Anchor' ]
					}, '/', {
						name : 'styles',
						items : [ 'Format', 'FontSize' ]
					}, {
						name:'color' , items : ['TextColor','BGColor']
					} ,{
						name : 'clipboard',
						groups : [ 'undo' ],
						items : [ 'Undo', 'Redo' ]
					}, {
						name : 'tools',
						items : [ 'Maximize','Preview' ]
					}, ];
		});
