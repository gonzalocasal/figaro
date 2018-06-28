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
            if($scope.cantidadNotificaciones > 0)
                document.title = "("+$scope.cantidadNotificaciones+") "+document.title ;
        });
    }

    //OBTENER CANTIDAD DE NOTIFICACIONES
    $scope.cantidadTotal = function(){
        $http.get("/rest/notificaciones/cantidad-total").then(function (response) {
            $scope.cantidadTotal = response.data;
        });
    }

    //ELIMINAR TODAS
    $scope.eliminarTodas = function(){
        $http.delete("/rest/notificaciones/eliminar/todas").then(function (response) {
            $scope.notificaciones = [];
            $scope.cantidadTotal=0;
        });
    }

     //ELIMINAR NOTIFICACION
    $scope.eliminar = function(notifcacion){
        $http.delete("/rest/notificaciones/eliminar/"+notifcacion.id).then(function (response) {
            var index = $scope.notificaciones.indexOf(notifcacion);
            $scope.notificaciones.splice(index, 1);  
            $scope.cantidadTotal--;
        });
    }

    //MOSTRAR ICONO ELIMINAR
    $scope.showDelete = function(notificacion){
       document.getElementById("notificacion-"+notificacion.id).style.display = "block";
    }

    //OCULTAR ICONO ELIMINAR
    $scope.hideDelete = function(notificacion){
       document.getElementById("notificacion-"+notificacion.id).style.display = "none";
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