app.controller('notificacionesController', function ($scope, $http) {

	//INIT NOTIFICACIONES
    $scope.init = function(){
    	$scope.index = 0;
    	$scope.notificaciones = [];
    	$scope.icons= {"STOCK":"local_shipping"}
    	$scope.cantidadTotal();
    	$scope.activeNotificaciones = true;
        $scope.getNotificaciones();
    }

    //OBTENER NOTIFICACIONES
	$scope.getNotificaciones = function(){
		loading();
        $http.get("/rest/notificaciones",{params: {index: $scope.index}}).then(function (response) {
			notificaciones = response.data;
        	Array.prototype.push.apply($scope.notificaciones, notificaciones);
            $scope.leer();
        });
    }

    //MARCAR COMO LEIDAS TODAS LAS NOTIFICACIONES
    $scope.leer = function(){
        $http.put("/rest/notificaciones/leer").then(function (response) {
           $scope.cantidadNotificaciones=0;
           loadComplete();
        });
    }

    //OBTENER CANTIDAD DE NOTIFICACIONES SIN LEER
	$scope.cantidad = function(){
        $http.get("/rest/notificaciones/cantidad").then(function (response) {
            $scope.cantidadNotificaciones = response.data;
        });
    }

    //OBTENER CANTIDAD DE NOTIFICACIONES
    $scope.cantidadTotal = function(){
        $http.get("/rest/notificaciones/cantidad-total").then(function (response) {
            $scope.cantidadTotal = response.data;
        });
    }

    //MAPER CATEGORIA DE NOTIFICACION CON ICONO
    $scope.getIcon=function(notifcacion){
    	return $scope.icons[notifcacion.categoria];
    }

    //CLICK VER MAS
    $scope.verMas = function(){
        $scope.index ++;
        $scope.getNotificaciones();
    }


});