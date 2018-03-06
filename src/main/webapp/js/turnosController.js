app.controller('turnosController', function ($scope, $http) {
    
    $scope.horarios = ["08:00","08:15","08:30","08:45","09:00","09:15","09:30","09:45","10:00","10:15","10:30","10:45","11:00","11:15","11:30","11:45","12:00","12:15","12:30","12:45","13:00","13:15","13:30","13:45","14:00","14:15","14:30","14:45","15:00","15:15","15:30","15:45","16:00","16:15","16:30","16:45","17:00","17:15","17:30","17:45","18:00","18:15","18:30","18:45","19:00","19:15","19:30","19:45","20:00","20:15","20:30","20:45"];

    //INIT TURNOS
    $scope.init = function(){
        $scope.getAllPeluqueros();
        $scope.ngDateTurno = stringToDate(getToday());
        $scope.getTurnos();
        $scope.activeTurnos = true;
        $scope.queryCliente ='';
        $scope.trabajosSeleccionados=[];
        $scope.totalDiario=0;
        $scope.turnos={};
        $scope.ngTurno={};
        $scope.clientes=[];       
    }

    //CLICK NUEVO TURNO
    $scope.newTurno = function() {
        $scope.ngTurno = {};
        $scope.totalTrabajosSeleccionados=0;
        $scope.trabajosPeluquero = [];
        $scope.trabajosSeleccionados = [];
        $scope.isNuevoTurno = true;
        $scope.modalOpen = true;
        openModal("modal-turnos");
    };




    //OBTENER TURNO
    $scope.getTurno = function(turnoId) {
     
        $http.get('/rest/turnos/'+turnoId)
        .then(function successCallback(response) {
            $scope.ngTurno = response.data;
            
        
        $scope.startHour = $scope.ngTurno.desde.split(' ')[1];
        $scope.endHour = $scope.ngTurno.hasta.split(' ')[1];
        
        //BIND PELUQUERO 
        if($scope.ngTurno.peluquero.habilitado){
            for(var i = 0; i < $scope.peluqueros.length; i++)
            if($scope.ngTurno.peluquero.id == $scope.peluqueros[i].id)
                $scope.peluquero = $scope.peluqueros[i];
        }else{
            $scope.peluqueros.push($scope.peluquero);
        }
        
        //BIND FORMULARIO TRABAJOS EN TURNO CHECKS
        $scope.trabajosSeleccionados = $scope.ngTurno.trabajos;
        $scope.totalTrabajosSeleccionados = $scope.getTotalTurno($scope.trabajosSeleccionados);
        $scope.trabajosPeluquero=[];
        $scope.ngTurno.trabajos.forEach(function(trabajo) {
            trabajo.selected = true;
            $scope.trabajosPeluquero.push(trabajo);
        });
        
        //BIND TRABAJOS EN FORMULARIO 
        $scope.ngTurno.peluquero.trabajos.forEach(function(trabajo) {
            for(var i = 0; i < $scope.ngTurno.peluquero.trabajos.length; i++)
                if(!isInListaTrabajos(trabajo))
                $scope.trabajosPeluquero.push(trabajo);
        });  
        $scope.modalOpen = true;
        openModal("modal-turnos");

        });
    };


    //CLICK FILA TURNO
    $scope.detailTurno = function(event){
        $scope.update=true;
        $scope.isNuevoTurno = false;
        $scope.turnoId = event.currentTarget.getAttribute("data-id");
        $scope.getTurno($scope.turnoId);
    };



    //BUSCA TRABAJO EN PELUQUERO
    function isInListaTrabajos(trabajo){
        for(var i = 0; i < $scope.trabajosPeluquero.length; i++)  
            if(trabajo.servicio.descripcion == $scope.trabajosPeluquero[i].servicio.descripcion )
                return true;
        return false;
    }

    //ACTULIZA FORMULARIO CON LOS TRABAJOS DEL TURNO
    $scope.bindTrabajos = function() {
        $scope.trabajosPeluquero=[];
        $scope.trabajosSeleccionados = [];
        $scope.totalTrabajosSeleccionados=0;
        for(var i = 0; i < $scope.peluquero.trabajos.length; i++){
            $scope.peluquero.trabajos[i].selected=false;
            $scope.trabajosPeluquero.push($scope.peluquero.trabajos[i])
        }
    };


    //CLICK ACEPTAR FORMULARIO
    $scope.sendTurno = function() {
        $scope.ngTurno.desde = getStringDate($scope.ngDateTurno)+" "+$scope.startHour;
        $scope.ngTurno.hasta = getStringDate($scope.ngDateTurno)+" "+$scope.endHour;
        $scope.ngTurno.peluquero = $scope.peluquero;
        $scope.ngTurno.trabajos = $scope.trabajosSeleccionados;
        if($scope.isNuevoTurno === true && $scope.validateTurno() === true){
            $http.post('/rest/turnos/alta', $scope.ngTurno)
            .then(function successCallback(response) {
                $scope.discardTurno();
                $scope.getTurnos();
                closeModal("modal-turnos");
              }, function errorCallback(response) {
                $scope.message=response.data.message;
            });
        }else if ($scope.validateTurno() === true){
            $http.put('/rest/turnos/actualizar/'+ $scope.turnoId, $scope.ngTurno).then(
                function successCallback(response) {
                    $scope.getTurnos();
                    $scope.discardTurno();
                    closeModal("modal-turnos");
              }, function errorCallback(response) {
                $scope.message=response.data.message;
            });
        }     
    };

    //OBTENER TURNOS
    $scope.getTurnos = function() {
        loadingTurnos();
        if ($scope.ngDateTurno== null)
             $scope.getTurnosHoy();
        $http.get('/rest/turnos',{params:{fecha: getStringDate($scope.ngDateTurno)}})
        .then(function successCallback(response) {
            $scope.turnos = response.data;
            $scope.getTotalDiario($scope.turnos);
            loadTurnosComplete();
        });
    };




 
    //OBTENER TURNOS DIA ANTERIOR
    $scope.getTurnosDiaAnterior = function() {
        var date = new Date($scope.ngDateTurno.getTime());
        date.setDate(date.getDate()-1);
        $scope.ngDateTurno = date;
        $scope.getTurnos();
    };

    //OBTENER TURNOS DIA SIGUIENTE
    $scope.getTurnosDiaSiguiente = function() {
        var date = new Date($scope.ngDateTurno.getTime());
        date.setDate(date.getDate()+1);
        $scope.ngDateTurno = date;
        $scope.getTurnos();
    };

   
    //OBTENER TURNOS Hoy
    $scope.getTurnosHoy = function() {
        $scope.ngDateTurno = new Date();
        $scope.getTurnos();
    };

    //OBTENER TOTAL DE TURNO
    $scope.getTotalTurno = function(trabajos) {
        var total = 0;
        for(var i = 0; i < trabajos.length; i++)
            total += trabajos[i].servicio.precio;
    return total;
    };

    //OBTENER TOTAL DIARIO
    $scope.getTotalDiario = function(turnos) {
        var total = 0;
        for(var i = 0; i < turnos.length; i++)
            total += turnos[i].montoCobro;
    return total;
    };

    //OBTENER PAGO A PELUQUERO POR TURNO
    $scope.getPago = function(trabajos) {
        var total = 0;
        for(var i = 0; i < trabajos.length; i++)
            total += (trabajos[i].servicio.precio * trabajos[i].comision) /100 ;
        return total;
    };

    //OBTENER TOTAL PAGOS
    $scope.getTotalPago = function(turnos) {
        var total = 0;
        for(var i = 0; i < turnos.length; i++)
        for(var j = 0; j < turnos[i].trabajos.length; j++)
            total += (turnos[i].trabajos[j].servicio.precio * turnos[i].trabajos[j].comision) /100 ;
        return total;
    };

    //VALIDAR FORMULARIO
    $scope.validateTurno = function() {
        if (!("cliente" in $scope.ngTurno) || $scope.ngTurno.cliente === null){
            $scope.message="Seleccione un cliente.";
            return false
        }
        if (!($scope.trabajosSeleccionados.length > 0)){
            $scope.message="Seleccione al menos un trabajo.";
            return false
        }
        return true
    }

    //ELIMINTAR TURNO
    $scope.deleteTarget = function(id) {                
        $http.delete('/rest/turnos/eliminar/'+id).then(function (response) {
            $scope.getTurnos();
        });
        closeModal("modal-confirmarDelete");
    };
   
    //CONFIRMA ELIMINTAR TURNO
    $scope.confirmDelete = function(id) {
        $scope.idTarget = id;
        $scope.modalOpen = true;
        openModal("modal-confirmarDelete");
        
    };

    //CANCELAR ELIMINAR
    $scope.discardConfirm = function(event){
        $scope.ngMovimiento = {};
        closeModal("modal-confirmarDelete");
    };

    $scope.toggleTrabajo = function (trabajo){
        if (trabajo.selected)
            $scope.addTrabajo(trabajo)
        else
            $scope.removeTrabajo(trabajo)
    }

    //AGREGAR TRABAJOS
    $scope.addTrabajo = function (trabajo) {
        $scope.totalTrabajosSeleccionados += trabajo.servicio.precio;
        $scope.trabajosSeleccionados.push(trabajo);        
    };

    //QUITAR TRABAJOS
    $scope.removeTrabajo = function (trabajo) {
        if($scope.isSeleccionado(trabajo)){
            $scope.trabajosSeleccionados.splice((trabajo), 1);
            $scope.totalTrabajosSeleccionados -= trabajo.servicio.precio;
        }

    };

    //TRABAJO ESTA SELECCIONADO
    $scope.isSeleccionado = function (trabajo) {
        for(var i = 0; i < $scope.trabajosSeleccionados.length; i++)
        if ($scope.trabajosSeleccionados[i].descripcion === trabajo.descripcion)
            return true;
        return false;
    };

    //SET CLIENTE
    $scope.setCliente = function (cliente) {
        $scope.ngTurno.cliente = cliente;
        $scope.queryCliente='';
        $scope.clientes=[];
    }
    

    //INICIALIZAR MOVIMIENTO
    $scope.initMovimiento = function(){
        $scope.ngMovimiento = {};
        $scope.ngMovimiento.tipoPago='contado';
        $scope.ngMovimiento.cuotas=0;
        $scope.ngMovimiento.descuento=0;
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
        $scope.modalOpen = true;
        if(turno.cobrado)
            openModal("modal-cobrar");
        else
            openModal("modal-cancelar-cobro");
    }

    //CANCELAR COBRO
    $scope.discardCobro = function(turno){
        turno.cobrado = false;
        $scope.modalOpen = false;
        closeModal("modal-cobrar");
        $scope.initMovimiento();
    };
  
   //CANCELAR DESHACER COBRO
    $scope.discardCancelarCobro = function(turno){
        turno.cobrado = true;
        $scope.modalOpen = false;
        closeModal("modal-cancelar-cobro");
    };
    
    //CONFIRMAR DESHACER COBRO
    $scope.toggleCobro = function (turno) {
        $scope.modalOpen = false;
        $http.put('/rest/turnos/'+turno.id+'/cobrado/cancelar');
        closeModal("modal-cancelar-cobro");      
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
        $scope.modalOpen = false;
        closeModal("modal-cancelar-pago");
    };

    //CONFIRMAR PAGO
    $scope.togglePago = function (turno) {
        closeModal("modal-cancelar-pago");
        $scope.modalOpen = false;
        $http.put('/rest/turnos/'+turno.id+'/pagar');
    }

    //BUSCAR CLIENTE
    $scope.searchCliente = function() {
        if ($scope.queryCliente.length < 2) 
            return $scope.clientes=[];       
        $http.get('/rest/clientes/buscar',{params: { search: $scope.queryCliente }})
        .then(function successCallback(response) {
            $scope.clientes = response.data; 
        });  
    };

    //OBTENER LISTA DE PELUQUEROS
    $scope.getAllPeluqueros = function() {
        $http.get("/rest/peluqueros/habilitados").then(function (response) {
            $scope.peluqueros = response.data;
        });
    };

    //DESCARTAR FORMULARIO
    $scope.discardTurno = function(event){
        if ($scope.peluquero !=null && !$scope.peluquero.habilitado){
            let index = $scope.peluqueros.indexOf($scope.peluquero);
            $scope.peluqueros.splice((index), 1);
        }
        $scope.startHour= "";
        $scope.endHour = ""; 
        $scope.peluquero = {};
        $scope.clientes=[];
        $scope.trabajos=[];
        $scope.trabajosSeleccionados=[];      
        $scope.queryCliente ='';
        $scope.queryTrabajo ='';
        $scope.message='';
        $scope.update=false;
        $scope.modalOpen = false;
        closeModal("modal-turnos");
    };

    //TECLADO 
    document.addEventListener('keyup', function(e) {
        if (e.keyCode == 27) 
            $scope.discardTurno();
        if ((e.keyCode == 39 || e.keyCode == 38) && !$scope.focus && !$scope.modalOpen)
            $scope.getTurnosDiaSiguiente();
        if ((e.keyCode == 37 || e.keyCode == 40) && !$scope.focus && !$scope.modalOpen)
            $scope.getTurnosDiaAnterior();
    });
    
});