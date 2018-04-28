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
        (document.getElementById("modal-turnos")) ? $('#modal-clientes').addClass("modal-on-top") : openModal("modal-clientes");
        $('#modal-clientes-focus').focus();
    }


        //DESCARTAR FORMULARIO
    $scope.discardClient = function(event){
        $scope.update = null;
        $scope.ngCliente = {};
        (document.getElementById("modal-turnos")) ? $('#modal-clientes').removeClass("modal-on-top") : closeModal("modal-clientes");
    };


    //CLICK FILA CLIENTE
    $scope.detailClient = function(idCliente){
        $scope.message='';
        $scope.update = true;
        $scope.clienteID = idCliente;
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

    //ACTUALIZAR SELECCION
    $scope.actualizarSeleccion = function(position, clientes) {
      angular.forEach(clientes, function(cliente, index) {
      (position != index) ? cliente.checked = false : $scope.clienteSelected = cliente;
      });
    }

    //REDIRIGIR A HISTORIAL DE CLIENTE
    $scope.irTurnos = function() {
        if(typeof $scope.clienteSelected === "undefined")
            alert('Seleccione un cliente');
        else
           window.location.href = "/turnos/cliente/"+$scope.clienteSelected.id
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


    //EXPORTAR CLIENTES A PDF
    document.getElementById('export').addEventListener('click',exportPDF);

    var specialElementHandlers = {
        '.no-export': function(element, renderer) {
        return true;
        }
    };

    function exportPDF() {
        var columns = [
            {title: "Nombre", dataKey: "nombre"}, 
            {title: "Apellido", dataKey: "apellido"},
            {title: "Teléfono", dataKey: "telefono"},
            {title: "Email", dataKey: "email"},
            {title: "Última Visita", dataKey: "ultimaVisita"},
        ];
        var doc = new jsPDF('p', 'pt');
        doc.autoTable(columns, $scope.clientes,{headerStyles: {fillColor: [41,41,97]}});
        doc.save('figaro-clientes.pdf');
    }

});