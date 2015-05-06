'use strict';

angular.module('teamstudyApp')
    .controller('LogoutController', function (Auth) {
    	//AQUI NO FUNCIONA NADA RELACIONADO CON EL LOGOUT, IR AL CONTROLLER DEL NAVBAR
    	Auth.logout();
        
    });
