app.controller('turnosBusquedaController', function ($scope, $http) {

    //INIT TURNOS
    $scope.init = function(){
        let url = new URL(window.location.href);
        $scope.activeTurnos = true;
        $scope.cliente={};
        $scope.empleado={};
        $scope.getAllClientes(url);
        $scope.getAllEmpleados(url);
        $scope.getAllServicios();
        $scope.criterios = true;
    
        let date = new Date();
        $scope.desde = new Date(date.getFullYear(), date.getMonth(), 1);
        $scope.hasta = new Date(date.getFullYear(), date.getMonth() + 1, 0);
        $scope.URLsearch(url);
    }



    //OBTENER LISTA DE CLIENTES
    $scope.getAllClientes = function(url) {
        $http.get("/rest/clientes").then(function (response) {
            $scope.clientes = response.data;
            bindCliente(url);
        });
    };

    //BIND CLIENTE
    function bindCliente(url){
        let cliente = url.searchParams.get("cliente");
        for(let i = 0; i < $scope.clientes.length; i++)
            if(cliente== $scope.clientes[i].id)
                $scope.cliente = $scope.clientes[i];
    }

    //OBTENER LISTA DE EMPLEADOS
    $scope.getAllEmpleados = function(url) {
        fecha = ($scope.ngDateTurno==null) ? getToday() : getStringDate($scope.ngDateTurno);        
        $http.get("/rest/empleados",{params:{fecha: fecha}}).then(function (response) {
            $scope.empleados = response.data;
            bindEmpleado(url);
        });
    };

    //BIND EMPLEADO
    function bindEmpleado(url){
        let empleado = url.searchParams.get("empleado");
        for(let i = 0; i < $scope.empleados.length; i++)
            if(empleado== $scope.empleados[i].id)
                $scope.empleado = $scope.empleados[i];
    }

    //OBTENER LISTA DE SERVICIOS
    $scope.getAllServicios = function() {
        $http.get("/rest/configuracion/servicios").then(function (response) {
            $scope.servicios = response.data;
        });
    };

     $scope.URLsearch = function(url) {
        $scope.cliente.id = url.searchParams.get("cliente");
        $scope.empleado.id = url.searchParams.get("empleado");
        $scope.pagado = url.searchParams.get("pagado");
        $scope.search();
    }

    $scope.selectSearch = function(){
        window.history.pushState('', '', '/turnos/buscar');
        $scope.search();
    }

    //BUSCAR
    $scope.search = function() {
        if($scope.cliente == null) $scope.cliente={};
        if($scope.empleado == null) $scope.empleado={};
        if($scope.servicio == null) $scope.servicio={};

        if($scope.cliente.id == null && $scope.empleado.id == null && $scope.servicio.descripcion == null && $scope.cobrado == null && $scope.pagado == null && $scope.desde == null  && $scope.hasta == null){
            $scope.criterios = null;
            $scope.turnos = null;
            return;
        }

        $scope.criterios = true;

        loading();
        $http.get('/rest/turnos/buscar',{params: { clienteId: $scope.cliente.id , empleadoId : $scope.empleado.id, servicio: $scope.servicio.descripcion ,cobrado: $scope.cobrado, pagado: $scope.pagado ,desde: $scope.desde , hasta: $scope.hasta}})
        .then(function successCallback(response) {
            $scope.turnos = response.data;
            loadComplete();
        });  
    };


      //OBTENER TOTAL DIARIO
    $scope.getTotal = function(turnos) {
        var total = 0;
        for(var i = 0; i < turnos.length; i++)
            total += turnos[i].montoCobro;
    return total;
    };

    //OBTENER TOTAL PAGO A EMPLEADO PENDIENTE
    $scope.getTotalPago = function (turnos) {
        var total = 0;
        for(var i = 0; i < turnos.length; i++)
            total += turnos[i].montoPago
        return total;
    };


    //CONFIRMAR COBRO
    $scope.cobrar = function(turno){
        $http.put('/rest/turnos/'+turno.id+'/cobrado', $scope.ngMovimiento).then(
            function successCallback(response) {
                closeModal("modal-cobrar");
                $scope.initMovimiento();
          }, function errorCallback(response) {
                $scope.message=response.data.message;
        });
    };

    //POP UP COBRADO
    $scope.setCobrado = function (turno) {
        $scope.turnoTarget = turno;
        if(turno.cobrado)
            openModal("modal-cobrar");
        else
            openModal("modal-cancelar-cobro");
    }

    //CANCELAR COBRO
    $scope.discardCobro = function(turno){
        turno.cobrado = false;
        closeModal("modal-cobrar");
        $scope.initMovimiento();
    };
  
   //CANCELAR DESHACER COBRO
    $scope.discardCancelarCobro = function(turno){
        turno.cobrado = true;
        closeModal("modal-cancelar-cobro");
    };
    
    //CONFIRMAR DESHACER COBRO
    $scope.toggleCobro = function (turno) {
        $http.put('/rest/turnos/'+turno.id+'/cobrado/cancelar').then(
            function successCallback(response) {
                closeModal("modal-cancelar-cobro");
                $scope.initMovimiento();
          }, function errorCallback(response) {
                $scope.message=response.data.message;
        });

    }

    //POP UP PAGADO
    $scope.setPagado = function (turno) {
        $scope.turnoTarget = turno;
        if(!turno.pagado)
            openModal("modal-cancelar-pago");
        else
            $scope.togglePago(turno);
    }

   //CANCELAR DESHACER PAGO
    $scope.discardCancelarPago = function(turno){
        turno.pagado = true;
        closeModal("modal-cancelar-pago");
    };

    //CONFIRMAR PAGO
    $scope.togglePago = function (turno) {
        closeModal("modal-cancelar-pago");
        if(turno.pagado)
            $http.put('/rest/turnos/'+turno.id+'/pagar');
        else
            $http.put('/rest/turnos/'+turno.id+'/pagar/cancelar');
    }   




    // LEER TURNOS DESDE LA TABLA HTML
    function leerTurnos(){
        var turnos = [];
        var rows = document.querySelectorAll("table tr");
        for (var i = 1; i < rows.length; i++) {
            let turno = {};
            cols = rows[i].querySelectorAll("td, th");
            turno.fecha = cols[0].innerText;
            turno.cliente = cols[1].innerText;
            turno.trabajos = cols[2].innerText;
            turno.precio = cols[3].innerText;
            turno.pago = cols[4].innerText;
            turno.empleado = cols[5].innerText;
            turno.cobrado = (cols[6].children[0].checked) ? "Sí" : "No";
            turno.pagado =  (cols[7].children[0].checked) ? "Sí" : "No";
            turnos.push(turno);
        }
        return turnos;
    }

    //EXPORTAR A PDF
    $scope.exportPDF = function() {
        var columns = [
            {title: "FECHA", dataKey: "fecha"}, 
            {title: "CLIENTE", dataKey: "cliente"},
            {title: "TRABAJOS", dataKey: "trabajos"},
            {title: "PRECIO", dataKey: "precio"},
            {title: "PAGO", dataKey: "pago"},
            {title: "EMPLEADO", dataKey: "empleado"},
            {title: "C", dataKey: "cobrado"},
            {title: "P", dataKey: "pagado"}
        ];
        var doc = new jsPDF('l', 'pt');
        var turnos = leerTurnos();
        doc.autoTable(columns, turnos,{headerStyles: {fillColor: [41,41,97]}});
        doc.save('figaro-turnos.pdf');
    }


    //EXPORTAR A EXCEL
    $scope.exportExcel = function () {
        var csv = [];
        var rows = document.querySelectorAll("table tr");
        var cols = rows[0].querySelectorAll("td, th");
        //HEADER
        var row = [];
        for (var j = 0; j < cols.length; j++){       
            row.push(cols[j].innerText);
        }
        csv.push(row.join(";"));
        //BODY
        for (var i = 1; i < rows.length; i++) {
            var row = [], cols = rows[i].querySelectorAll("td, th");
            row.push(cols[0].innerText);
            row.push(cols[1].innerText);
            row.push(cols[2].innerText);
            row.push("\""+cols[3].innerText+"\"");
            row.push("\""+cols[4].innerText+"\"");
            row.push(cols[5].innerText);
            row.push((cols[6].children[0].checked) ? "Sí" : "No");
            row.push((cols[7].children[0].checked) ? "Sí" : "No");
            csv.push(row.join(";"));        
        }
        csv=csv.join("\n")        
        var link = window.document.createElement("a");
        link.setAttribute("href", "data:text/csv;charset=utf-8,%EF%BB%BF" + encodeURI(csv));
        link.setAttribute("download", "figaro-turnos.csv");
        link.click();
    }



});