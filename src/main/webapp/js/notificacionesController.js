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

	$scope.getNotificaciones = function(){
        $http.get("/rest/notificaciones",{params: {index: $scope.index}}).then(function (response) {
			notificaciones = response.data;
        	Array.prototype.push.apply($scope.notificaciones, notificaciones);
            $scope.leer();
        });
    }

    $scope.leer = function(){
        $http.put("/rest/notificaciones/leer").then(function (response) {
           $scope.cantidadNotificaciones=0;
        });
    }


	$scope.cantidad = function(){
        $http.get("/rest/notificaciones/cantidad").then(function (response) {
            $scope.cantidadNotificaciones = response.data;
        });
    }

    $scope.cantidadTotal = function(){
        $http.get("/rest/notificaciones/cantidad-total").then(function (response) {
            $scope.cantidadTotal = response.data;
        });
    }

    $scope.getIcon=function(notifcacion){
    	return $scope.icons[notifcacion.categoria];
    }

    //CLICK VER MAS
    $scope.verMas = function(){
        $scope.index ++;
        $scope.getNotificaciones();
        
    }


});