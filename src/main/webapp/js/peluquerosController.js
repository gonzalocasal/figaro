app.controller('peluquerosController', function ($scope, $http) {
    
    //INIT PELUQUEROS
    $scope.init = function(){
        $scope.activePeluqueros = true;
        $scope.loaded = false;
        $scope.ngPeluquero = {"trabajos" :[]};
        loading();
        $scope.getAll();
        $scope.getAllCiudades();
        $scope.getAllTrabajos();
    }

    //INIT TURNOS DE PELUQUERO
    $scope.initTurnosPeluquero = function(){
        $scope.index = 0;
        $scope.activeTurnos = true;
        $scope.turnos=[];
        path = window.location.href.split("/").pop();
        $scope.peluqueroId = window.location.href.split("/")[5];
        if (path == 'sinpagar'){
            $scope.sinPagar = true;
            $scope.emptyMessage = 'No existen turnos para pagar a este peluquero.';
            $scope.getTurnosSinPagar();
        }else{
            $scope.sinPagar = false;
            $scope.emptyMessage = 'No existen turnos para este peluquero.';
            $scope.getCantidadTurnos();
            $scope.getTurnosPeluquero();
        }
    }

    //CLICK VER MAS
    $scope.verMas = function(){
        $scope.index ++;
        $scope.getTurnosPeluquero();
        
    }

    //GET TURNOS PELUQUEROS PAGINADO
    $scope.getTurnosPeluquero = function(){
        loading();
        url = '/rest/turnos/peluquero/'+ $scope.peluqueroId;
        $http.get(url,{params: {index: $scope.index}})
        .then(function successCallback(response) {
            loadComplete();
            $scope.loaded = true;
            turnos = response.data;
            Array.prototype.push.apply($scope.turnos, turnos);
            if ($scope.turnos.length > 0){
                $scope.peluquero = ($scope.turnos[0].peluquero.nombre +' '+ $scope.turnos[0].peluquero.apellido) 
                
            }
        });
    }    


    //GET TURNOS SIN PAGAR PELUQUERO PELUQUERO
    $scope.getTurnosSinPagar = function(){
    url = '/rest/turnos/peluquero/'+ $scope.peluqueroId +'/sinpagar';
    $http.get(url)
        .then(function successCallback(response) {
            loadComplete();
            $scope.loaded = true;
            turnos = response.data;
            Array.prototype.push.apply($scope.turnos, turnos);
            if ( $scope.turnos.length > 0){
                $scope.peluquero = ($scope.turnos[0].peluquero.nombre +' '+ $scope.turnos[0].peluquero.apellido) 
               
            }
        });
    }


    //GET CANTIDAD TURNOS PELUQUEROS 
    $scope.getCantidadTurnos = function(){
        url = '/rest/turnos/peluquero/'+ $scope.peluqueroId+'/cantidad';
        $http.get(url,{params: {index: $scope.index}})
        .then(function successCallback(response) {
            $scope.cantidadTurnosPeluquero = response.data;
        });
    }   


  
    //OBTENER TOTAL DE TURNO
    $scope.getTotalTurno = function(trabajos) {
        var total = 0;
        for(var i = 0; i < trabajos.length; i++)
            total += trabajos[i].servicio.precio;
    return total;
    };

  
   

    //OBTENER PAGO A PELUQUERO POR TURNO
    $scope.getTotalPagoTurnosPeluquero = function (turnos) {
        var total = 0;
        for(var i = 0; i < turnos.length; i++)
            total += turnos[i].montoPago
        return total;
    };

    


    //OBTENER LISTA DE PELUQUEROS
    $scope.getAll = function() {
        $http.get("/rest/peluqueros/habilitados").then(function (response) {
            $scope.peluqueros = response.data;
            if ($scope.peluqueros.length>0){
                $scope.peluqueroSelected = $scope.peluqueros[0];
                $scope.peluqueros[0].checked =true;
            }
            loadComplete();
        });
    };

    //CLICK NUEVO PELUQUERO
    $scope.newPeluquero = function() {
        $scope.message='';
        $scope.allChecked = false;
        $scope.checkAllTrabajos();
        ($scope.isModalOpen == true) ? $('#modal-peluqueros').addClass("modal-on-top") : openModal("modal-peluqueros");
        $('#modal-peluqueros-focus').focus();
    }

    //CLICK FILA PELUQUERO
    $scope.detailPeluquero = function(idPeluquero){
        $scope.message='';
        $scope.allChecked = false;
        $scope.update = true;
        $scope.peluqueroId = idPeluquero;
        $http.get('/rest/peluqueros/'+$scope.peluqueroId).then(function (response) {
            $scope.ngPeluquero = response.data;
            $scope.servicios.forEach(function(trabajo) {
                trabajo.selected = $scope.isInPeluquero(trabajo);
            });
            openModal("modal-peluqueros");
	    });
    };
    
    //CLICK ACEPTAR FORMULARIO
    $scope.sendPeluquero = function() {

        $scope.actualizarTrabajos();
    
        if($scope.update == null){
            $scope.ngPeluquero.fechaIngreso = getToday();
            $http.post('/rest/peluqueros/alta', $scope.ngPeluquero)
            .then(function successCallback(response) {
                $scope.peluqueros.push(response.data);
                $scope.discardPeluquero();
              }, function errorCallback(response) {
                $scope.message=response.data.message;
            });
        }else{
            $http.put('/rest/peluqueros/actualizar/'+ $scope.peluqueroId, $scope.ngPeluquero)
            .then(function successCallback(response) {
                $scope.getAll();
                $scope.discardPeluquero();
              }, function errorCallback(response) {
                $scope.message=response.data.message;
            });
        }
    };

    //BUSCA UN TRABAJO EN PELUQUERO
    $scope.isInPeluquero = function (servicio){ 
        let found = false;
        $scope.ngPeluquero.trabajos.forEach(function(trabajo) {
            if(trabajo.servicio != null && trabajo.servicio.id==servicio.id){
                found = true;
                servicio.comision = trabajo.comision
            }
        });
    return found;
    };


    //ACTUALIZAR SELECCION
    $scope.actualizarSeleccion = function(position, peluqueros) {
      angular.forEach(peluqueros, function(peluquero, index) {
        if (position != index) 
          peluquero.checked = false;
        else
          $scope.peluqueroSelected = peluquero;
      });
    }


    //ASIGNAR TRABAJOS SELECCIONADOS A PELUQUEROS
    $scope.actualizarTrabajos = function() {
        $scope.ngPeluquero.trabajos = [];
        $scope.servicios.forEach(function(servicio) {
            if (servicio.selected){
                var trabajo = {}; 
                trabajo.servicio = servicio;
                trabajo.comision = servicio.comision;
                $scope.ngPeluquero.trabajos.push(trabajo);
            }
        });    
    };

    //SELECCIONAR TODOS
    $scope.checkAllTrabajos = function (){ 
        $scope.servicios.forEach(function(servicio) {
            servicio.selected = $scope.allChecked
        });    
    };
    
    //DESCARTAR FORMULARIO
    $scope.discardPeluquero = function(event){
        $scope.update = null;
        $scope.ngPeluquero = {"servicios" :[]};
        closeModal("modal-peluqueros");
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
            $scope.discardPeluquero("modal-peluqueros");
        }
    });

});