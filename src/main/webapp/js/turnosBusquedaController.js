app.controller('turnosBusquedaController', function ($scope, $http) {

    //INIT TURNOS
    $scope.init = function(){
       $scope.cliente={};
       $scope.empleado={};
       $scope.getAllClientes();
       $scope.getAllEmpleados();
       $scope.getAllServicios();
    }



    //OBTENER LISTA DE CLIENTES
    $scope.getAllClientes = function() {
        $http.get("/rest/clientes").then(function (response) {
            $scope.clientes = response.data;
        });
    };


    //OBTENER LISTA DE EMPLEADOS
    $scope.getAllEmpleados = function() {
        fecha = ($scope.ngDateTurno==null) ? getToday() : getStringDate($scope.ngDateTurno);        
        $http.get("/rest/empleados/disponibles",{params:{fecha: fecha}}).then(function (response) {
            $scope.empleados = response.data;
        });
    };


    //OBTENER LISTA DE SERVICIOS
    $scope.getAllServicios = function() {
        $http.get("/rest/configuracion/servicios").then(function (response) {
            $scope.servicios = response.data;
        });
    };



    //BUSCAR
    $scope.search = function() {
        loading();
        if($scope.cliente == null) $scope.cliente={};
        if($scope.empleado == null) $scope.empleado={};
        if($scope.servicio == null) $scope.servicio={};
        

        $http.get('/rest/turnos/buscar',{params: { clienteId: $scope.cliente.id , empleadoId : $scope.empleado.id, servicio: $scope.servicio.descripcion ,cobrado: $scope.cobrado, pagado: $scope.pagado ,desde: $scope.desde , hasta: $scope.hasta}})
        .then(function successCallback(response) {
            $scope.turnos = response.data;
            loadComplete();
        });  
    };


});