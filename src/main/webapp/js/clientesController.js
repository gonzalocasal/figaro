app.controller('clientesController', function ($scope, $http) {
    
    //INIT CLIENTES
    $scope.init = function(){
        $scope.activeClientes = true;
        $scope.clientesScreen=true;
        $scope.search = '';
        $scope.ngCliente = {};
        $scope.getAllCiudades();
        $scope.getAll();
    }

    //INIT TURNOS DE CLIENTE
    $scope.getTurnosDeCliente = function(){
        $scope.activeTurnos = true;
        var clienteId = window.location.href.split("/").pop();
        $http.get('/rest/turnos/cliente/'+clienteId)
        .then(function successCallback(response) {
            $scope.turnos = response.data;
            loadComplete();
            if ( $scope.turnos.length > 0){
                $scope.cliente = ($scope.turnos[0].cliente.nombre +' '+ $scope.turnos[0].cliente.apellido) 
            }
        });
    }


    //OBTENER LISTA DE CLIENTES
    $scope.getAll = function() {
        loading();
        $http.get("/rest/clientes").then(function (response) {
            $scope.clientes = response.data;
            loadComplete();
        });
    };

    //CLICK NUEVO CLIENTE
    $scope.newClient = function() {
        $scope.ngCliente={};
        $scope.message='';
        ($scope.isModalOpen == true) ? $('#modal-clientes').addClass("modal-on-top") : openModal("modal-clientes");
        $('#modal-clientes-focus').focus();
    }

    //CLICK FILA CLIENTE
    $scope.detailClient = function(event){
        $scope.message='';
        $scope.update = true;
        $scope.clienteID = event.currentTarget.getAttribute("data-id");
        $http.get('/rest/clientes/'+$scope.clienteID).then(function (response) {
            $scope.ngCliente = response.data;
            openModal("modal-clientes");
	    });
    };

    //CLICK ACEPTAR FORMULARIO
    $scope.sendClient = function() {
        if($scope.update == null){
            $scope.ngCliente.fechaIngreso = getToday();
            $scope.ngCliente.ultimaVisita = getToday();
            $http.post('/rest/clientes/alta', $scope.ngCliente)
            .then(function successCallback(response) {
                $scope.clientes.push(response.data);
                $scope.discardClient();
              }, function errorCallback(response) {
                $scope.message=response.data.message;
            });
        }else{
            $http.put('/rest/clientes/actualizar/'+ $scope.clienteID, $scope.ngCliente)
            .then(function successCallback(response) {
                $scope.getAll();
                $scope.discardClient();
              }, function errorCallback(response) {
                $scope.message=response.data.message;
            });
        }
    };

    //BUSCAR
    $scope.searchCliente = function() {
        loading();
        $http.get('/rest/clientes/buscar',{params: { search: $scope.search }})
        .then(function successCallback(response) {
            $scope.clientes = response.data; 
            loadComplete();
        });  
    };

    //DESCARTAR FORMULARIO
    $scope.discardClient = function(event){
        $scope.update = null;
        $scope.ngCliente = {};
        ($scope.clientesScreen == null) ? $('#modal-clientes').removeClass("modal-on-top") : closeModal("modal-clientes");
    };

    //OBTENER LISTA DE CIUDADES
    $scope.getAllCiudades = function() {
        $http.get("/rest/configuracion/ciudades").then(function (response) {
            $scope.ciudades = response.data;
        });
    };
    
    //APRETAR ESCAPE
    document.addEventListener('keyup', function(e) {
        if (e.keyCode == 27) {
            $scope.discardClient("modal-clientes");
        }
    });

});