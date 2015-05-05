'use strict';

angular.module('teamstudyApp').controller(
		'DashboardController',
		function($stateParams, $state, $scope, Principal, Message, MessageList,
				ThreadCRUD, ThreadListForGroup) {

			Principal.identity().then(function(account) {
				$scope.account = account;
				$scope.isAuthenticated = Principal.isAuthenticated;
				$scope.isInRole = Principal.isInRole;

			}).then(
					function() {

						var groupId = localStorage.getItem('groupId')
						$scope.groupId = groupId;

						var threadId = $stateParams.threadId;
						$scope.threadId = threadId;

						ThreadListForGroup.get({
							groupId : groupId
						}, function(result) {
							$scope.threadsAll = result.data;

						});

						$scope.labels = [ '2006', '2007', '2008', '2009',
								'2010', '2011', '2012' ];
						$scope.series = [ 'Series A', 'Series B' ];

						$scope.data = [ [ 65, 59, 80, 81, 56, 55, 40 ],
								[ 28, 48, 40, 19, 86, 27, 90 ] ];

						ThreadListForGroup.get({
							groupId : groupId
						}, function(result) {
							$scope.threadsAll = result.data;
							$scope.array = [];

							$scope.threadsAll.forEach(function(item) {

								MessageList.get({
									threadId : item.id
								}, function(result) {
									var mensajes = result.data;
									item.tamano = mensajes;
									var part = {};
									part.label = item.title;
									part.value = item.tamano.length;
									$scope.array.push(part);

									// array.push(item.title,
									// item.tamano.length);
									// $scope.pos0 = array[0];
									// $scope.pos1 = array[1];
									// $scope.data =[{title:$scope.pos0,
									// tamano:$scope.pos1}];
								});
							});

						});
					}).then(function() {

				// new Morris.Line({
				// element : 'myfirstchart',
				// data : $scope.data,
				// xkey : 'title',
				// ykeys : [ 'tamano' ],
				// labels : ['Title', 'Tamano']
				//			    		
				// });

			});

		});
