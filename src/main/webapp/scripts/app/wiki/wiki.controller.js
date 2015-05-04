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
				News.get({
					userId : $scope.account.id
				}, function(result) {
					$scope.news = result.data;
				});
			});		
			
			$scope.createNews = function() {
	    	   	$scope.newsAux = {
						title : $scope.news.title,
						description : $scope.news.description
					};
	    	   	News.create({groupId : localStorage.getItem('groupId')},
	    	   			$scope.newsAux, function() {
					// $scope.clear();
					$state.reload();
				});
			};
			
			//WIKI EDITOR

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
