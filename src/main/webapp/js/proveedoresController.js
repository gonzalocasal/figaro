app.controller('proveedoresController', function ($scope, $http) {
    
    //INIT PROVEEDORES
    $scope.init = function(){
        $scope.activeProveedores = true;
        $scope.search = '';
        $scope.ngProveedor = {};
        $scope.getAll();
        $scope.getAllCiudades();
    }



    //OBTENER LISTA DE PROVEEDORES
    $scope.getAll = function() {
        loading();
        $http.get("/rest/proveedores").then(function (response) {
            $scope.proveedores = response.data;
            loadComplete();
        });
    };

    //CLICK NUEVO PROVEEDOR
    $scope.newProveedor = function() {
        $scope.ngProveedor={};
        $scope.message='';
        openModal("modal-proveedores");
        $('#modal-proveedores-focus').focus();
    }

    //CLICK FILA PROVEEDOR
    $scope.detailProveedor = function(event){
        $scope.message='';
        $scope.update = true;
        $scope.proveedorID = event.currentTarget.getAttribute("data-id");
        $http.get('/rest/proveedores/'+$scope.proveedorID).then(function (response) {
            $scope.ngProveedor = response.data;
            openModal("modal-proveedores");
	    });
    };

    //CLICK ACEPTAR FORMULARIO
    $scope.sendProveedor = function() {
        if($scope.update == null){
            $http.post('/rest/proveedores/alta', $scope.ngProveedor)
            .then(function successCallback(response) {
                $scope.proveedores.push(response.data);
                $scope.discardProveedor();
              }, function errorCallback(response) {
                $scope.message=response.data.message;
            });
        }else{
            $http.put('/rest/proveedores/actualizar/'+ $scope.proveedorID, $scope.ngProveedor)
            .then(function successCallback(response) {
                $scope.getAll();
                $scope.discardProveedor();
              }, function errorCallback(response) {
                $scope.message=response.data.message;
            });
        }
    };

    //BUSCAR
    $scope.searchProveedor = function() {
        loading();
        $http.get('/rest/proveedores/buscar',{params: { search: $scope.search }})
        .then(function successCallback(response) {
            $scope.proveedores = response.data; 
            loadComplete();
        });  
    };

    //DESCARTAR FORMULARIO
    $scope.discardProveedor = function(event){
        $scope.update = null;
        $scope.ngProveedor = {};
        closeModal("modal-proveedores");
    };



    //ELIMINTAR PROVEEDOR
    $scope.deleteTarget = function(id) {                
        $http.delete('/rest/proveedores/eliminar/'+id).then(function (response) {
            $scope.getAll();
        });
        closeModal("modal-confirmarDelete");
    };
   
    //CONFIRMA ELIMINTAR PROVEEDOR
    $scope.confirmDelete = function(id) {
        $scope.idTarget = id;
        openModal("modal-confirmarDelete");
        
    };

    //CANCELAR ELIMINAR
    $scope.discardConfirm = function(event){
        $scope.ngMovimiento = {};
        closeModal("modal-confirmarDelete");
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
            $scope.discardProveedor("modal-proveedores");
        }
    });

});