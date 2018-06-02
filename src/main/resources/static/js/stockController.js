
app.controller('stockController', function ($scope, $http) {
    
    //OBTENER LISTA DE PRODUCTOS
    $scope.getAll = function() {
        loading();
        $http.get("/rest/stock/todos").then(function (response) {
            $scope.productos = response.data;
            loadComplete();
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
                $scope.discardProducto();
                $scope.ngProducto={};
            }, function errorCallback(response){
                $scope.message=response.data.message;
            });
        }
        else{
            $http.put('/rest/stock/actualizar/'+ $scope.productoId, $scope.ngProducto)
            .then(function successCallback(response) {
                    $scope.getAll();
                    $scope.discardProducto();
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
        loading();        
        $http.get('/rest/stock/buscar',{params: { search: $scope.search }})
        .then(function successCallback(response) {
            $scope.productos = response.data;
            loadComplete();
        })
    };

    //BUSCAR PRUDUCTOS CON FALTANTES DE STOCK
    $scope.searchProductoFaltante = function() {
        loading();
        $http.get('/rest/stock/faltante').then(function successCallback(response){
            $scope.productos = response.data;
            loadComplete();
        })
    };

    //APRETAR ESCAPE
    document.addEventListener('keyup', function(e) {
        if (e.keyCode == 27) {
            $scope.discardProducto();
        }
    });

        // LEER STOCK DESDE LA TABLA HTML
    function leerStock(){
        var stocks = [];
        var rows = document.querySelectorAll("table tr");
        for (var i = 1; i < rows.length; i++) {
            let stock = {};
            cols = rows[i].querySelectorAll("td, th");
            stock.nombre = cols[0].innerText;
            stock.descripcion = cols[1].innerText;
            disponible = cols[2].innerHTML;
            disponible = disponible.substring(disponible.lastIndexOf("=")+2,disponible.lastIndexOf(">")-1);
            stock.disponible = disponible;
            stock.minimo = cols[3].innerText;
            stock.precioVenta = cols[4].innerText; 
            stocks.push(stock);
        }
        return stocks;
    }

    //EXPORTAR A PDF
    $scope.exportPDF = function() {
        var columns = [
            {title: "NOMBRE", dataKey: "nombre"}, 
            {title: "DESCRIPCION", dataKey: "descripcion"},
            {title: "DISPONIBLE", dataKey: "disponible"},
            {title: "MÍNIMO", dataKey: "minimo"},
            {title: "PRECIO VENTA", dataKey: "precioVenta"}
                        
        ];
        var doc = new jsPDF('l', 'pt');
        var stocks = leerStock();
        doc.autoTable(columns, stocks,{headerStyles: {fillColor: [41,41,97]}});
        doc.save('figaro-stock.pdf');
    }


    //EXPORTAR A EXCEL
    $scope.exportExcel = function () {
        var csv = [];
        var rows = document.querySelectorAll("table tr");
        
        var row = [], cols = rows[0].querySelectorAll("td, th");
            
        for (var j = 0; j < cols.length; j++) {
            row.push(cols[j].innerText);
        }                          
        csv.push(row.join(";"));       
    
        for (var i = 1; i < rows.length; i++) {
            var row = [], cols = rows[i].querySelectorAll("td, th");
            
            for (var j = 0; j < cols.length; j++) 
                if (j == 2) {
                    var disponible = cols[2].innerHTML;                   
                    disponible = disponible.substring(disponible.lastIndexOf("=")+2,disponible.lastIndexOf(">")-1);
                    row.push(disponible);
                } else {
                    row.push(cols[j].innerText);
                }                
            csv.push(row.join(";"));        
        }
        csv=csv.join("\n")   
        var link = window.document.createElement("a");
        link.setAttribute("href", "data:text/csv;charset=utf-8,%EF%BB%BF" + encodeURI(csv));
        link.setAttribute("download", "figaro-stock.csv");
        link.click();
    }

    //INIT
    

    //INIT STOCK
    $scope.init = function(){
    $scope.activeStock = true;
    $scope.search = '';
    $scope.ngProducto = {};
  
    $scope.getAll();

    }

});