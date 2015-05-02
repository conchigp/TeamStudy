'use strict';

angular.module('teamstudyApp').controller('MainController',
		function($scope, GroupList, Principal,$stateParams,$state) {
			
			Principal.identity().then(function(account) {
				$scope.account = account;
				$scope.isAuthenticated = Principal.isAuthenticated;
				$scope.isInRole = Principal.roles;
				/*carrusel */
			                
				 $scope.myInterval = 5000;
				  var slides = $scope.slides = [];
				  $scope.addSlide = function() {
				    var newWidth = 800 + slides.length + 1;
				    slides.push({
				      image: 'http://lorempixel.com/' + newWidth + '/500',
				      text: ['More','Extra','Lots of','Surplus'][slides.length % 4] + ' ' +
				        ['Cats', 'Kittys', 'Felines', 'Cutes'][slides.length % 4]
	    
				   
				    });
				  };
				  for (var i=0; i<4; i++) {
				    $scope.addSlide();
				  }
			}).then(function() {
				
				
				
			});
			
		
			
	        
	    

		});