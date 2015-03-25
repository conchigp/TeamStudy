'use strict';

angular.module('teamstudyApp')
    .controller('LogoutController', function (Auth) {
        Auth.logout();
    });
