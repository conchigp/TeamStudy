'use strict';

angular.module('teamstudyApp')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });


