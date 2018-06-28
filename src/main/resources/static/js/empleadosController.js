app.controller('empleadosController', function ($scope, $http) {
    
    //INIT EMPLEADOS
    $scope.init = function(){
        $scope.activeEmpleados = true;
        $scope.loaded = false;
        $scope.ngEmpleado = {"trabajos" :[]};
        loading();
        $scope.getAll();
        $scope.getAllCiudades();
        $scope.getAllTrabajos();
    }

    //INIT TURNOS DE EMPLEADO
    $scope.initTurnosEmpleado = function(){
        $scope.index = 0;
        $scope.activeTurnos = true;
        $scope.turnos=[];
        path = window.location.href.split("/").pop();
        $scope.empleadoId = window.location.href.split("/")[5];
        if (path == 'sinpagar'){
            $scope.sinPagar = true;
            $scope.emptyMessage = 'No existen turnos para pagar a este empleado.';
            $scope.getTurnosSinPagar();
        }else{
            $scope.sinPagar = false;
            $scope.emptyMessage = 'No existen turnos para este empleado.';
            $scope.getCantidadTurnos();
            $scope.getTurnosEmpleado();
        }
    }

    //CLICK VER MAS
    $scope.verMas = function(){
        $scope.index ++;
        $scope.getTurnosEmpleado();
        
    }

    //GET TURNOS EMPLEADOS PAGINADO
    $scope.getTurnosEmpleado = function(){
        loading();
        url = '/rest/turnos/empleados/'+ $scope.empleadoId;
        $http.get(url,{params: {index: $scope.index}})
        .then(function successCallback(response) {
            loadComplete();
            $scope.loaded = true;
            turnos = response.data;
            Array.prototype.push.apply($scope.turnos, turnos);
            if ($scope.turnos.length > 0)
                $scope.empleado = ($scope.turnos[0].empleado) 
        });
    }    


    $scope.pagarTodos = function(){
        $http.put('/rest/turnos/empleados/'+$scope.empleado.id+'/pagartodos').then(
            function successCallback(response) {
                location.reload();
        }, function errorCallback(response) {
            $scope.message=response.data.message;
        });
    }


    //CALCULAR SI EL EMPLEADO TRABAJA HOY
    $scope.isDisponible = function(empleado) {
       return empleado.diasDisponible.indexOf(getDayOfWeek()) >-1;
    };
  
   
    //ACTUALIZAR SELECCION
    $scope.actualizarSeleccion = function(empleado) {
        $scope.empleadoSelected = empleado;
    }


    $scope.irTurnos = function() {
        if($scope.isSelectedEmpleado())
            window.location.href = "/turnos/buscar?empleado="+$scope.empleadoSelected.id
    };


    $scope.irEditar = function() {
        if($scope.isSelectedEmpleado())
            $scope.detailEmpleado($scope.empleadoSelected.id)  
    };

    $scope.irPagar = function() {
        if($scope.isSelectedEmpleado())
            window.location.href = "/turnos/buscar?empleado="+$scope.empleadoSelected.id+"&pagado=false"  
    };

    $scope.isSelectedEmpleado = function(){
        if (typeof $scope.empleadoSelected === "undefined"){
            alert('Seleccione un empleado');
            return false
        }
        return true
    }


    //OBTENER LISTA DE EMPLEADOS
    $scope.getAll = function() {
        $http.get("/rest/empleados/habilitados").then(function (response) {
            $scope.empleados = response.data;
            loadComplete();
        });
    };

    //CLICK NUEVO EMPLEADO
    $scope.newEmpleado = function() {
        $scope.message='';
        $scope.allChecked = false;
        $scope.checkAllTrabajos();
        openModal("modal-empleados");
        $('#modal-empleados-focus').focus();
    }

    //CLICK FILA EMPLEADO
    $scope.detailEmpleado = function(idEmpleado){
        $scope.message='';
        $scope.allChecked = false;
        $scope.update = true;
        $scope.empleadoId = idEmpleado;
        $http.get('/rest/empleados/'+$scope.empleadoId).then(function (response) {
            $scope.ngEmpleado = response.data;
            $scope.ngEmpleado.nacimiento=stringToDate($scope.ngEmpleado.nacimiento);
            $scope.servicios.forEach(function(trabajo) {
                trabajo.selected = $scope.isInEmpleado(trabajo);
            });
            openModal("modal-empleados");
	    });
    };
    
    //CLICK ACEPTAR FORMULARIO
    $scope.sendEmpleado = function() {

        $scope.actualizarTrabajos();
        $scope.actualizarDiasDisponible();

        if($scope.update == null){
            $scope.ngEmpleado.fechaIngreso = getToday();
            $http.post('/rest/empleados/alta', $scope.ngEmpleado)
            .then(function successCallback(response) {
                $scope.empleados.push(response.data);
                $scope.discardEmpleado();
              }, function errorCallback(response) {
                $scope.message=response.data.message;
            });
        }else{
            $http.put('/rest/empleados/actualizar/'+ $scope.empleadoId, $scope.ngEmpleado)
            .then(function successCallback(response) {
                $scope.getAll();
                $scope.discardEmpleado();
              }, function errorCallback(response) {
                $scope.message=response.data.message;
            });
        }
    };

    //BUSCA UN TRABAJO EN EMPLEADO
    $scope.isInEmpleado = function (servicio){ 
        let found = false;
        $scope.ngEmpleado.trabajos.forEach(function(trabajo) {
            if(trabajo.servicio != null && trabajo.servicio.id==servicio.id){
                found = true;
                servicio.comision = trabajo.comision
            }
        });
    return found;
    };

    //ASIGNAR TRABAJOS SELECCIONADOS A EMPLEADO
    $scope.actualizarTrabajos = function() {
        $scope.ngEmpleado.trabajos = [];
        $scope.servicios.forEach(function(servicio) {
            if (servicio.selected){
                var trabajo = {}; 
                trabajo.servicio = servicio;
                trabajo.comision = servicio.comision;
                $scope.ngEmpleado.trabajos.push(trabajo);
            }
        });    
    };


    //ASIGNAR TRABAJOS SELECCIONADOS A EMPLEADO
    $scope.actualizarDiasDisponible = function(dia) {
        $scope.ngEmpleado.diasDisponible = [];
        $(".dia-disponible:checked").each(function() {
            $scope.ngEmpleado.diasDisponible.push($(this).val());
        });
    };  

    //SELECCIONAR TODOS
    $scope.checkAllTrabajos = function (){ 
        $scope.servicios.forEach(function(servicio) {
            servicio.selected = $scope.allChecked
        });    
    };
    
    //DESCARTAR FORMULARIO
    $scope.discardEmpleado = function(event){
        $scope.update = null;
        $scope.ngEmpleado = {};
        closeModal("modal-empleados");
    };

    //OBTENER LISTA DE CIUDADES
    $scope.getAllCiudades = function() {
        $http.get("/rest/configuracion/ciudades").then(function (response) {
            $scope.ciudades = response.data;
        });
    };

    //OBTENER LISTA DE TRABAJOS
    $scope.getAllTrabajos = function() {
        $http.get("/rest/configuracion/servicios").then(function (response) {
            $scope.servicios = response.data;
        });
    };
    
    //APRETAR ESCAPE
    document.addEventListener('keyup', function(e) {
        if (e.keyCode == 27) {
            $scope.discardEmpleado("modal-empleados");
        }
    });

});