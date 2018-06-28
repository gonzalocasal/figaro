app.controller('configuracionController', function ($scope, $http) {

    //INIT CONFIGURACION
    $scope.init = function(){
        loading();
        $scope.activeConfiguracion = true;
        $scope.getAllServicios();
        $scope.getAllCiudades();
        $scope.getAllCategorias();
        $scope.getAllEmpleados();
        $scope.getEmail();
        $scope.getHorario();
        $scope.ngCiudad={};
        $scope.ngServicio={};
        $scope.ngCategoria={};
    }
  
    //AGREGAR CIUDAD
    $scope.addCiudad = function() {
        $http.post('/rest/configuracion/ciudades/alta', $scope.ngCiudad)
            .then(function successCallback(response) {
                $scope.ciudades.push(response.data);
                $scope.ngCiudad={};
                $scope.messageCiudad='';
              }, function errorCallback(response) {
                $scope.messageCiudad=response.data.message;
            });
    };

    //ELIMINAR CIUDAD
    $scope.removeCiudad = function(event) {
        var id = event.currentTarget.getAttribute("data-id");
        var ciudad = $scope.ciudades.filter(function( obj ) {
            return obj.id == id;
        });
        var index = $scope.ciudades.indexOf(ciudad);
        $scope.ciudades.splice(index, 1);  
        $http.delete('/rest/configuracion/ciudades/baja/'+ id)
            .then(function successCallback(response) {
                $scope.getAllCiudades();
                $scope.messageCiudad='';
              }, function errorCallback(response) {
                $scope.messageCiudad=response.data.message;
            });
    };

    //OBTENER LISTA DE CIUDADES
    $scope.getAllCiudades = function() {
        $http.get("/rest/configuracion/ciudades").then(function (response) {
            $scope.ciudades = response.data;
            loadComplete();
        });
    };


    //OBTENER HORARIO
    $scope.getHorario = function() {
        $http.get("/rest/configuracion/horario").then(function (response) {
            $scope.horario = response.data;
            loadComplete();
        });
    };


    //AGREGAR CATEGORIA
    $scope.updateHorario = function() {
        $http.put('/rest/configuracion/horario', $scope.horario)
            .then(function successCallback(response) {
                $scope.horario = response.data;
                $scope.messageHorario='';
                loadComplete();
              }, function errorCallback(response) {
                 $scope.messageHorario=response.data.message;
            });
    };



    //AGREGAR SERVICIO
    $scope.addServicio = function() {
        if($scope.ngServicio.id  === undefined){
            $http.post('/rest/configuracion/servicios/alta', $scope.ngServicio)
                .then(function successCallback(response) {
                    $scope.servicios.push(response.data);
                    $scope.ngServicio={};
                    $scope.messageservicio='';
                }, function errorCallback(response) {
                    $scope.messageservicio=response.data.message;
                });
        }else{
            $http.put('/rest/configuracion/servicios/actualizar/'+ $scope.ngServicio.id,$scope.ngServicio)
                .then(function successCallback(response) {
                    $scope.getAllServicios();
                    $scope.ngServicio={};
                    $scope.messageservicio='';
                }, function errorCallback(response) {
                    $scope.messageservicio=response.data.message;
                });
        }
    };

    //OBTENER LISTA DE SERVICIOS
    $scope.getAllServicios = function() {
        $http.get("/rest/configuracion/servicios").then(function (response) {
            $scope.servicios = response.data;
        });
    };

    //ELIMINAR SERVICIO
    $scope.removeServicio = function(event) {
        var id = event.currentTarget.getAttribute("data-id");
        var servicio = $scope.servicios.filter(function( obj ) {
            return obj.id == id;
        });
        var index = $scope.servicios.indexOf(servicio);
        $scope.servicios.splice(index, 1);  
        $http.delete('/rest/configuracion/servicios/baja/'+ id)
            .then(function successCallback(response) {
                $scope.getAllServicios();
                $scope.ngServicio={};
                $scope.messageServicio='';
              }, function errorCallback(response) {
                $scope.messageServicio=response.data.message;
            });
    };

    //OBTENER SERVICIO
    $scope.loadServicio = function(event) {
        $scope.servicioID = event.currentTarget.getAttribute("data-id");
        $http.get('/rest/configuracion/servicios/'+ $scope.servicioID)
            .then(function successCallback(response) {
                $scope.ngServicio=response.data;
            });
    };

    //AGREGAR CATEGORIA
    $scope.addCategoria = function() {
        $http.post('/rest/configuracion/categorias/alta', $scope.ngCategoria)
            .then(function successCallback(response) {
                $scope.categorias.push(response.data);
                $scope.ngCategoria={};
                $scope.messageCategoria='';
              }, function errorCallback(response) {
                $scope.messageCategoria=response.data.message;
            });
    };


    //OBTENER LISTA DE CATEGORIAS
    $scope.getAllCategorias = function() {
        $http.get("/rest/configuracion/categorias").then(function (response) {
            $scope.categorias = response.data;
        });
    };

    //ELIMINAR CATEGORIA
    $scope.removeCategoria = function(event) {
        var id = event.currentTarget.getAttribute("data-id");
        var categoria = $scope.categorias.filter(function( obj ) {
            return obj.id == id;
        });
        var index = $scope.categorias.indexOf(categoria);
        $scope.categorias.splice(index, 1);
        $http.delete('/rest/configuracion/categorias/baja/'+ id)
            .then(function successCallback(response) {
                $scope.getAllCategorias();
                $scope.messageCategoria='';
              }, function errorCallback(response) {
                $scope.messageCategoria=response.data.message;
            });
    };
    
    //OBTENER LISTA DE EMPLEADOS
    $scope.getAllEmpleados = function() {
        $http.get("/rest/empleados").then(function (response) {
            $scope.empleados = response.data;
        });
    };

    //HABILITAR - DESHABILITAR EMPLEADO
    $scope.toggleEmpleado = function(empleado) {
      $http.patch('/rest/empleados/'+empleado.id+'/habilitar');
    };



    //ACTUALIZAR CONTRASEÑA
    $scope.updatePassword = function() {
        $scope.ngCredential = {};
        $scope.ngCredential.pass = $scope.ngPassword;
        $scope.ngCredential.repass = $scope.ngRePassword;
        $http.patch('/rest/configuracion/pass', $scope.ngCredential)
            .then(function successCallback(response) {
                $scope.ngPassword='';
                $scope.ngRePassword='';
                $scope.messageCredentials='';
                alert('Su contraseña se actualizó correctamente');
              }, function errorCallback(response) {
                $scope.messageCredentials=response.data.message;
            });
    };

    //ACTUALIZAR EMAIL
    $scope.updateEmail = function() {
        $http.patch('/rest/configuracion/email', $scope.ngEmail)
            .then(function successCallback(response) {
                $scope.messageCredentials='';
                alert('Su Email se actualizó correctamente');
              }, function errorCallback(response) {
                $scope.messageCredentials=response.data.message;
            });
    };

    //OBTENER EMAIL
    $scope.getEmail = function() {
        $http.get("/rest/configuracion/email").then(function (response) {
            $scope.ngEmail = response.data.email;
        });
    };


});