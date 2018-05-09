app.controller('notificacionesController', function ($scope, $http) {

	 $scope.cantidad = function(){
        $http.get("/rest/notificaciones/cantidad").then(function (response) {
            $scope.cantidadNotificaciones = response.data;
        });
    }

});