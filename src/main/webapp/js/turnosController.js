app.controller('turnosController', function ($scope, $http) {
  
    //INIT TURNOS
    $scope.init = function(){
        $scope.ngDateTurno = stringToDate(getToday());
        $scope.getTurnos();
        $scope.getHorario();
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
        $scope.trabajosEmpleado = [];
        $scope.trabajosSeleccionados = [];
        $scope.isNuevoTurno = true;
        openModal("modal-turnos");
    };




    //OBTENER TURNO
    $scope.getTurno = function(turnoId) {
     
        $http.get('/rest/turnos/'+turnoId)
        .then(function successCallback(response) {
            $scope.ngTurno = response.data;
            
        
        $scope.startHour = $scope.ngTurno.desde.split(' ')[1];
        $scope.endHour = $scope.ngTurno.hasta.split(' ')[1];
        
        //BIND CLIENTE
        if ("Cliente" == $scope.ngTurno.cliente.nombre && "Desconocido" == $scope.ngTurno.cliente.apellido)
            $scope.desconocido = true;
        else
            $scope.queryCliente = $scope.ngTurno.cliente.nombre+' '+$scope.ngTurno.cliente.apellido;

        //BIND EMPLEADO 
        if($scope.ngTurno.empleado.habilitado){
            for(var i = 0; i < $scope.empleados.length; i++)
            if($scope.ngTurno.empleado.id == $scope.empleados[i].id)
                $scope.empleado = $scope.empleados[i];
        }else{
            $scope.empleados.push($scope.empleado);
        }
        
        //BIND FORMULARIO TRABAJOS EN TURNO CHECKS
        $scope.trabajosSeleccionados = $scope.ngTurno.trabajos;
        $scope.totalTrabajosSeleccionados = $scope.getTotalTurno($scope.trabajosSeleccionados);
        $scope.trabajosEmpleado=[];
        $scope.ngTurno.trabajos.forEach(function(trabajo) {
            trabajo.selected = true;
            $scope.trabajosEmpleado.push(trabajo);
        });
        
        //BIND TRABAJOS EN FORMULARIO 
        $scope.ngTurno.empleado.trabajos.forEach(function(trabajo) {
            for(var i = 0; i < $scope.ngTurno.empleado.trabajos.length; i++)
                if(!isInListaTrabajos(trabajo))
                $scope.trabajosEmpleado.push(trabajo);
        });  
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



    //BUSCA TRABAJO EN EMPLEADO
    function isInListaTrabajos(trabajo){
        for(var i = 0; i < $scope.trabajosEmpleado.length; i++)  
            if(trabajo.servicio.descripcion == $scope.trabajosEmpleado[i].servicio.descripcion )
                return true;
        return false;
    }

    //ACTULIZA FORMULARIO CON LOS TRABAJOS DEL TURNO
    $scope.bindTrabajos = function() {
        $scope.trabajosEmpleado=[];
        $scope.trabajosSeleccionados = [];
        $scope.totalTrabajosSeleccionados=0;
        if ($scope.empleado!=null)
            for(var i = 0; i < $scope.empleado.trabajos.length; i++){
                $scope.empleado.trabajos[i].selected=false;
                $scope.trabajosEmpleado.push($scope.empleado.trabajos[i])
            }
    };


    //CLICK ACEPTAR FORMULARIO
    $scope.sendTurno = function() {
        $scope.ngTurno.desde = getStringDate($scope.ngDateTurno)+" "+$scope.startHour;
        $scope.ngTurno.hasta = getStringDate($scope.ngDateTurno)+" "+$scope.endHour;
        $scope.ngTurno.empleado = $scope.empleado;
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
        $scope.getAllEmpleados();
        loading();
        if ($scope.ngDateTurno== null)
             $scope.getTurnosHoy();
        $http.get('/rest/turnos',{params:{fecha: getStringDate($scope.ngDateTurno)}})
        .then(function successCallback(response) {
            $scope.turnos = response.data;
            $scope.getTotalDiario($scope.turnos);
            loadComplete();
        });
    };




    //VALIDAR SI EMPLEADO YA TIENE UN TURNO
    $scope.isEmpleadoOcupado = function() {
        $scope.ngTurno.desde = getStringDate($scope.ngDateTurno)+" "+$scope.startHour;
        $scope.ngTurno.hasta = getStringDate($scope.ngDateTurno)+" "+$scope.endHour;
        $scope.ngTurno.empleado = $scope.empleado;
        if($scope.empleado!=null && $scope.startHour!=null && $scope.endHour!=null){
            $http.post('/rest/turnos/ocupado', $scope.ngTurno)
            .then(function successCallback(response) {
                $scope.warnmessage='';
              }, function errorCallback(response) {
                $scope.warnmessage=response.data.message;
            });
        }     
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

    //OBTENER PAGO A EMPLEADO POR TURNO
    $scope.getPago = function(trabajos) {
        var total = 0;
        for(var i = 0; i < trabajos.length; i++)
            total += (trabajos[i].servicio.precio * trabajos[i].comision) /100 ;
        return total;
    };

    //OBTENER TOTAL PAGO A EMPLEADO PENDIENTE
    $scope.getTotalPago = function (turnos) {
        var total = 0;
        for(var i = 0; i < turnos.length; i++)
            total += turnos[i].montoPago
        return total;
    };

    //VALIDAR FORMULARIO
    $scope.validateTurno = function() {
        if ($scope.ngTurno.cliente === null && !$scope.desconocido ){
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
        $scope.cliente = cliente;
        $scope.queryCliente=cliente.nombre+' '+cliente.apellido;
        $scope.clientes=[];
    }
    
    //SET CLIENTE
    $scope.setClienteDesconocido = function (cliente) {
        $scope.ngTurno.cliente = null;
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

    //OBTENER LISTA DE EMPLEADOS
    $scope.getAllEmpleados = function() {
        $scope.empleados=[];
        fecha = ($scope.ngDateTurno==null) ? getToday() : getStringDate($scope.ngDateTurno);        
        $http.get("/rest/empleados/disponibles",{params:{fecha: fecha}}).then(function (response) {
            $scope.empleados = response.data;
        });
    };

    //OBTENER HORARIO
    $scope.getHorario = function() {
        $http.get("/rest/configuracion/horario").then(function (response) {
            $scope.horario = response.data;
            loadComplete();
        });
    };

  
    //IR A BUSCAR
    $scope.irBuscar = function() {
        window.location.href = "/turnos/buscar";
    };

    //DESCARTAR FORMULARIO
    $scope.discardTurno = function(event){
        if ($scope.empleado !=null && !$scope.empleado.habilitado){
            let index = $scope.empleados.indexOf($scope.empleado);
            $scope.empleados.splice((index), 1);
        }
        $scope.startHour= null;
        $scope.endHour = null; 
        $scope.empleado = {};
        $scope.clientes=[];
        $scope.trabajos=[];
        $scope.trabajosSeleccionados=[];      
        $scope.queryCliente ='';
        $scope.queryTrabajo ='';
        $scope.desconocido =false;
        $scope.message='';
        $scope.warnmessage='';
        $scope.update=false;
        closeModal("modal-turnos");
    };

    //TECLADO 
    document.addEventListener('keyup', function(e) {
        if (e.keyCode == 27) {
            $scope.discardTurno();
            $scope.discardSearch();
        }
        if ((e.keyCode == 39 || e.keyCode == 38) && !$scope.focus && !$(".modal-on")[0])
            $scope.getTurnosDiaSiguiente();
        if ((e.keyCode == 37 || e.keyCode == 40) && !$scope.focus && !$(".modal-on")[0])
            $scope.getTurnosDiaAnterior();
    });
    
});