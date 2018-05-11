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
            $scope.ngCliente.nacimiento=stringToDate($scope.ngCliente.nacimiento);
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
           window.location.href = "/turnos/buscar?cliente="+$scope.clienteSelected.id
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

    // LEER CLIENTES DESDE LA TABLA HTML
    function leerClientes(){
        var clientes = [];
        var rows = document.querySelectorAll("table tr");
        for (var i = 1; i < rows.length; i++) {
            let cliente = {};
            cols = rows[i].querySelectorAll("td, th");
            cliente.nombre = cols[1].innerText;
            cliente.apellido = cols[2].innerText;
            cliente.telefono = cols[3].innerText;
            cliente.email = cols[4].innerText;
            cliente.ultimaVisita = cols[5].innerText;
            clientes.push(cliente);
        }
        return clientes;
    }

    //EXPORTAR A PDF
    $scope.exportPDF = function() {
        var columns = [
            {title: "NOMBRE", dataKey: "nombre"}, 
            {title: "APELLIDO", dataKey: "apellido"},
            {title: "TELÉFONO", dataKey: "telefono"},
            {title: "EMAIL", dataKey: "email"},
            {title: "ÚLTIMA VISITA", dataKey: "ultimaVisita"}
        ];
        var doc = new jsPDF('p', 'pt');
        var clientes = leerClientes();
        doc.autoTable(columns, clientes,{headerStyles: {fillColor: [41,41,97]}});
        doc.save('figaro-clientes.pdf');
    }


    //EXPORTAR A EXCEL
    $scope.exportExcel = function () {
        var csv = [];
        var rows = document.querySelectorAll("table tr");
        for (var i = 0; i < rows.length; i++) {
            var row = [], cols = rows[i].querySelectorAll("td, th");
            //SE IGNORA LA PRIMER Y ULTIMA COLUMNA
            for (var j = 1; j < cols.length-1; j++) 
                row.push(cols[j].innerText);
            csv.push(row.join(","));        
        }
        csv=csv.join("\n")
        var csvFile = new Blob([csv], {type: "text/csv"});
        var downloadLink = document.createElement("a");
        downloadLink.download = 'figaro-clientes.csv';
        downloadLink.href = window.URL.createObjectURL(csvFile);
        downloadLink.style.display = "none";
        document.body.appendChild(downloadLink);
        downloadLink.click();
    }


   

});