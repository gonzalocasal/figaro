var app = angular.module('figaro', []);
app.controller('stockController', function ($scope, $http) {
    
    //OBTENER LISTA DE PRODUCTOS
    $scope.getAll = function() {
        $http.get("/rest/stock/todos").then(function (response) {
            $scope.productos = response.data;
        });
    };

    //CLICK NUEVO PRODUCTO
    $scope.newProducto = function() {
        $scope.isNuevoProducto = true
    	openModal("modal-stock");
        $scope.ngProducto={}; 
    };

    //ELIMINTAR PRODUCTO
    $scope.deleteTarget = function(id) {      
        $http.delete('/rest/stock/eliminar/'+id).then(function (response) {	           
            closeModal("modal-confirmarDelete");
            $scope.getAll();
        });
    };

    //CONFIRMA ELIMINTAR PRODUCTO
    $scope.confirmDelete = function(id) {
        $scope.idTarget = id;
        openModal("modal-confirmarDelete");
    };

    //DESCARTAR PRODUCTO
    $scope.discardConfirm = function(event){
	    $scope.ngProducto = {};
	    closeModal("modal-confirmarDelete");
	};

    //ACTUALIZAR CANTIDAD DISPONIBLE
    $scope.updateCantidadProducto = function(id, cantidad){
        $http.patch('/rest/stock/editar/'+id,{} ,{params: { cantidad: cantidad }}).then(function (response) {
            $scope.getAll();
        });
    };

    //CLICK FILA PRODUCTO
    $scope.detailProducto = function(event){
        $scope.isNuevoProducto = false
        $scope.productoId = event.currentTarget.getAttribute("data-id");
        $http.get('/rest/stock/'+$scope.productoId).then(function (response) {
            $scope.ngProducto = response.data;
            openModal("modal-stock");
	    });
    };

    //CLICK ACEPTAR FORMULARIO
    $scope.sendProducto = function() {
        $scope.message='';
        if($scope.isNuevoProducto === true){
            $http.post('/rest/stock/alta', $scope.ngProducto)
            .then(function successCallback(response) {
                $scope.productos.push(response.data);
                discardProducto();
                $scope.ngProducto={};
            }, function errorCallback(response){
                $scope.message=response.data.message;
            });
        }
        else{
            $http.put('/rest/stock/actualizar/'+ $scope.productoId, $scope.ngProducto)
            .then(function successCallback(response) {
                    $scope.getAll();
                    discardProducto();
                    $scope.ngProducto={};
                }, function errorCallback(response){
                $scope.message=response.data.message;
            });
        }
    };

    //DESCARTAR FORMULARIO
    $scope.discardProducto = function(event){
        $scope.ngProducto = {};
        $scope.message='';
        closeModal("modal-stock");
    };
    
    //BUSCAR POR NOMBRE Y DESCRIPCION
    $scope.searchProducto = function() {        
        $http.get('/rest/stock/buscar',{params: { search: $scope.search }})
        .then(function successCallback(response) {
            $scope.productos = response.data;
        })
    };

    //BUSCAR PRUDUCTOS CON FALTANTES DE STOCK
    $scope.searchProductoFaltante = function() {
        $http.get('/rest/stock/faltante').then(function successCallback(response){
            $scope.productos = response.data;
        })
    };

    //APRETAR ESCAPE
    document.addEventListener('keyup', function(e) {
        if (e.keyCode == 27) {
            $scope.discardProducto();
        }
    });

    //INIT
    $scope.activeStock = true;
    $scope.search = '';
    $scope.ngProducto = {};
    $scope.getAll();

});