'use strict';

angular.module('teamstudyApp').controller('MainController',
		function($scope, GroupList, Principal,$stateParams,$state) {
			
			Principal.identity().then(function(account) {
				$scope.account = account;
				$scope.isAuthenticated = Principal.isAuthenticated;
				$scope.groupId = localStorage.getItem('groupId');
				$scope.isInRole = Principal.isInRole;
				/*carrusel */
			                
				 $scope.myInterval = 5000;
				  var slides = $scope.slides = [];
				  $scope.addSlide = function() {
				    var newWidth = 800 + slides.length + 1;
//				    slides.push({
//				      image: 'http://lorempixel.com/' + newWidth + '/500',
//				      text: ['More','Extra','Lots of','Surplus'][slides.length % 4] + ' ' +
//				        ['Cats', 'Kittys', 'Felines', 'Cutes'][slides.length % 4]
//	    
//				   
//				    });
				  };
				  
				  $scope.slides=[
				   {
				     title:'imagen1',
				     image: '../../../img/repositorio.png',
				     text:'tikitiki'
				   },
				   {
					     title:'imagen2',
					     image: '../../../img/wiki.png',
					     text:'tikitiki'
				   },
				   {
					     title:'imagen3',
					     image: '../../../img/foro.png',
					     text:'tikitiki'
				   },
//				   {
//					     title:'imagen4',
//					     image: '../../../img/conchi2.png',
//					     text:'tikitiki'
//				   },
				   
				                 
				                 ];
				  for (var i=0; i<4; i++) {
				    $scope.addSlide();
				  }
			}).then(function() {
				
				
			});
	

		});