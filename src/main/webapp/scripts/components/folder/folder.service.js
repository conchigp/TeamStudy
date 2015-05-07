(function() {
	'use strict';

	function Folder($resource) {
		return $resource('api/folder', {}, {
			'get' : {
				method : 'GET',
				params : {
					folderId : '@folderId'
				},
				isArray : false,
				interceptor : {
					response : function(response) {
						// expose response
						return response;
					}
				}

			},
			'update' : {
				method : 'PUT',
				params : {
					groupId : '@groupId'
				}
			},
			'delete' : {
				method : 'DELETE',
				params : {
					folderId : '@folderId'
				}
			}
		});
	}
	function FolderList($resource) {
		return $resource('api/folder/group', {}, {
			'get' : {
				method : 'GET',
				params : {
					groupId : '@groupId'
				},
				isArray : true,
				interceptor : {
					response : function(response) {
						// expose response
						return response;
					}
				}
			}
		});
	}

	function Archive($resource) {
		return $resource('api/archive', {}, {
			'update' : {
				method : 'POST',
				params : {
					folderId : '@folderId',
					file : '@file'
				}
			},
			'delete' : {
				method : 'DELETE',
				params : {
					folderId : '@folderId',
					gridId : '@gridId'
				}
			},
			

		});
	}

	function ArchiveList($resource) {
		return $resource('api/archive', {}, {
			'get' : {
				method : 'GET',
				params : {
					folderId : '@folderId'
				},
				isArray : true,
				interceptor : {
					response : function(response) {
						// expose response
						return response;
					}
				}

			}
		});
	}

	function Download($resource) {
		return $resource('api/archive/download', {}, {
			'get' : {
				method : 'GET',
				params : {
					folderId: '@folderId',
					gridId : '@gridId'
				},
				isArray : false,
				responseType: 'arraybuffer'
				
			}
		});
	}

	angular.module('teamstudyApp').factory('Folder', Folder).factory(
			'FolderList', FolderList).factory('Archive', Archive).factory(
			'ArchiveList', ArchiveList).factory('Download', Download);

})();
