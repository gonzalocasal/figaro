app.controller('ventaController', function ($scope, $http) {
    
    //OBTENER LISTA DE PRODUCTOS
    $scope.getAllProductos = function() {
        $http.get("/rest/stock/todos").then(function (response) {
            $scope.productos = response.data;
        });
    };

    //OBTENER LISTA DE VENTAS
    $scope.getAllVentas = function() {
        searchVenta($scope.fechaInicio, $scope.fechaFin); 
    };

    
//----------------------  Inicio filtros vetas  ---------------------------//

    //MOSTRAR O NO MOSTRAR DIV DE BUSQUEDA
    $scope.IsHiddenDia = true;
    $scope.IsHiddenEntreDia = true;
    $scope.IsHiddenMes = true;
 
    $scope.ShowHideDia = function () {
        $scope.IsHiddenEntreDia = true;
        $scope.IsHiddenMes = true;
        $scope.IsHiddenDia = $scope.IsHiddenDia ? false : true;         
        if($scope.IsHiddenDia === true){
            $scope.limpiaFecha();
            $scope.getAllVentas();                  
        }
    }
 
    $scope.ShowHideEntreDia = function () {         
        $scope.IsHiddenDia = true;
        $scope.IsHiddenMes = true;
        $scope.IsHiddenEntreDia = $scope.IsHiddenEntreDia ? false : true;           
        if($scope.IsHiddenEntreDia === true){
             $scope.limpiaFecha();
             $scope.getAllVentas();
        }
    }        
 
    $scope.ShowHideMes = function () {  
        $scope.IsHiddenDia = true;
        $scope.IsHiddenEntreDia = true;
        $scope.IsHiddenMes = $scope.IsHiddenMes ? false : true;         
        if($scope.IsHiddenMes === true){
            $scope.limpiaFecha();
            $scope.getAllVentas();
        }
    }

    $scope.searchVentaDia = function() {        
        $scope.fechaInicio = getStringDate(new Date($scope.search));
        $scope.fechaFin = getStringDate(new Date($scope.search));
        searchVenta($scope.fechaInicio , $scope.fechaFin);
    }

    //FILTRO SEMANA
    $scope.searchVentaSem = function() {
        var semana = getSemana($scope.search);
        $scope.fechaInicio = getStringDate(semana.dStart);
        $scope.fechaFin = getStringDate(semana.dEnd);
        searchVenta($scope.fechaInicio , $scope.fechaFin);
    }

    //FILTRO MES
    $scope.searchVentaMes = function() {    
        var mes = getMes($scope.search);
        $scope.fechaInicio = getStringDate(mes.dStart);
        $scope.fechaFin = getStringDate(mes.dEnd);
        searchVenta($scope.fechaInicio , $scope.fechaFin);
    }

    //FILTRO TOTAL
    function searchVenta(fInicio, fFin ) {
        loading();
        $http.get('/rest/venta/historial-venta/buscar',{params: { from: fInicio, to: fFin}})                
        .then(function successCallback(response) {                  
            $scope.ngVentas = response.data;
            $scope.loaded = true;
            loadComplete();
        })
    }
        
    //LIMPIA FILTRO FECHA
    $scope.limpiaFecha = function() {
        $scope.search = '';
        $scope.fechaInicio = getDateFormated();
        $scope.fechaFin = getDateFormated();
        $scope.getAllVentas();
    } 
    
//----------------------  Fin filtros Historial ventas  ---------------------------//
    

//----------------------  Funciones creacion  ---------------------------//
    //VER DETALLE PRODUCTO VENTA
    $scope.verProducto = function(prodID) {
        $scope.ngProductoVenta = {};
        $http.get('/rest/stock/'+prodID).then(function(response){
            $scope.ngProductoVenta = response.data;
            if (existeEnCarrito($scope.ngProductoVenta.nombre, $scope.ngProductoVenta.descripcion) === true){
                actualizarVistaStock($scope.ngProductoVenta);
            }
            $scope.message="";
        })  
    }

   //SUMAR PRODUCTO AL CARRITO
    $scope.sumarCarrito = function(prodVenta, uniAVender){       
        if (alcanzaStock(prodVenta, uniAVender) === true){
            $scope.ngItem                       = {};
            $scope.ngItem.nombreProducto        = prodVenta.nombre;
            $scope.ngItem.descripcionProducto   = prodVenta.descripcion;
            $scope.ngItem.precioUnitario        = prodVenta.precio;
            $scope.ngItem.cantidad              = uniAVender;
            $scope.ngItem.precioTotal           = ($scope.ngItem.precioUnitario * $scope.ngItem.cantidad);
            $scope.ngCarrito.push($scope.ngItem);
            $scope.ngTotalVenta = calcularTotalVenta();
            resetDetalleProducto();
        }
        else{
            $scope.message="Cantidad necesaria no disponibe.";
        }
    }

    //CREAR VENTA
    $scope.crearVenta = function(){
        $scope.ngVenta = {};        
        $scope.ngVenta.precio   = $scope.ngTotalVenta; 
        $scope.ngVenta.fecha = getToday();
        $scope.ngVenta.items    = $scope.ngCarrito;
        $scope.message  = "";
        $scope.ngVentaDTO = {};
        $scope.ngVentaDTO.venta = $scope.ngVenta;
        $scope.ngVentaDTO.movimiento = $scope.ngMovimiento;
        $http.post('/rest/venta/alta', $scope.ngVentaDTO).then(
            function successCallback(response){
                closeModal("modal-cobrar");
                $scope.getAllProductos();
                $scope.message="La venta se realizó correctamente";
                $scope.messageError=false;
                $scope.getAllVentas();
        },  function errorCallback(response) {
                $scope.message=response.data.message;
                $scope.messageError=true;
        });
        resetCarrito();
    }

    //POP UP COBRO
    $scope.setTipoPago = function () {
        openModal("modal-cobrar");
    }
//----------------------  Fin Funciones creacion  ---------------------------//


//----------------------  Funciones eliminacion  ---------------------------//    
    //QUITAR ITEM DE CARRITO
    $scope.quitarItem = function(index){
       $scope.ngCarrito.splice(index,1);
       $scope.ngTotalVenta = calcularTotalVenta();
    };

    //ELIMINTAR VENTA
    $scope.deleteTarget = function(id) {      
        $http.delete('/rest/venta/eliminar/'+id).then(function (response) {            
            closeModal("modal-confirmarDelete");
            $scope.getAllVentas();
        });
    };

    //CONFIRMA ELIMINTAR VENTA
    $scope.confirmDelete = function(id) {
        $scope.idTarget = id;
        openModal("modal-confirmarDelete");
    };

    //DESCARTAR VENTA
    $scope.discardConfirm = function(event){
       $scope.ngVenta = {};
       closeModal("modal-confirmarDelete");
    };

//----------------------  Fin Funciones eliminacion  ---------------------------//


//----------------------  Utilidades  ---------------------------//
    //VERIFICA SI EXISTE STOCK SUFICIENTE PARA LA VENTA
    function alcanzaStock (producto, unidades){
        if (producto.cantidad >= unidades){
            return true;
        }
        else{
            return false;
        }
    }

    //VERIFICA SI EL ITEM YA EXISTE EN EL CARRITO
    function existeEnCarrito (iNombre, iDescripcion){
        for(var i = 0; i < $scope.ngCarrito.length; i++)
            if ($scope.ngCarrito[i].nombreProducto === iNombre
                 && $scope.ngCarrito[i].descripcionProducto === iDescripcion)
                return true;
        return false;    
    }

    //CALCULA TOTAL DE LA VENTA
    function calcularTotalVenta(){
        var total = 0.00;
        angular.forEach($scope.ngCarrito, function(key){
            total = total + key.precioTotal;
        })
        return total;
    }

    //LIMPIA MODAL DETALLE PRODUCTO
    function resetDetalleProducto(){
        $scope.producto = {};
        $scope.ngProductoVenta = {};
        $scope.aVender = 0;
    }

    //LIMPIA MODAL DETALLE PRODUCTO
    function resetCarrito(){
        $scope.ngVenta={};
        $scope.ngCarrito=[];
        $scope.ngTotalVenta = 0.00;
        resetDetalleProducto();
        $scope.initMovimiento();
    }

    //MODIFICA STOCK EN MODAL DETALLE PRODUCTO
    function actualizarVistaStock(producto){
        for(var i = 0; i < $scope.ngCarrito.length; i++)
            if ($scope.ngCarrito[i].nombreProducto === producto.nombre
                 && $scope.ngCarrito[i].descripcionProducto === producto.descripcion)
                producto.cantidad = producto.cantidad - $scope.ngCarrito[i].cantidad;
    }

    //INICIALIZAR MOVIMIENTO
    $scope.initMovimiento = function(){
        $scope.ngMovimiento = {};
        $scope.ngMovimiento.tipoPago='contado';
        $scope.ngMovimiento.cuotas=0;
        $scope.ngMovimiento.descuento=0;
    };

    //CANCELAR VENTA
    $scope.discardCobro = function(){
        closeModal("modal-cobrar");
        $scope.initMovimiento();
    };


    // LEER CLIENTES DESDE LA TABLA HTML
    function leerVentas(){
        var ventas = [];
        var rows = document.querySelectorAll("table tr");        
        let venta = {};
        cols = rows[0].querySelectorAll("td, th");
        colsLenght = cols.length;
        venta.titulo = cols[0].innerText;           
        ventas.push(venta);

        for (var i = 1; i < rows.length; i++) {
            let venta = {};
            cols = rows[i].querySelectorAll("td, th");
            colsLenght = cols.length;
            if (colsLenght == 6) {                
                venta.marca = cols[0].innerText;
                venta.detalle = cols[1].innerText;
                venta.precioUnitario = cols[2].innerText;
                venta.cantidad = cols[3].innerText;
                venta.precioTotal = cols[4].innerText; 
            } else {
                if (colsLenght == 3){
                    venta.titulo = cols[0].innerText;
                    venta.precioTotal = cols[1].innerText;
                } else {
                    venta.titulo = cols[0].innerText
                }
            }

            if (colsLenght == 3){
                var total = venta;
            } else {
                if (colsLenght == 2){
                    ventas.push(total);
                } 
                ventas.push(venta);
            }    
            
        }
        ventas.push(total);
        return ventas;
    }

    //EXPORTAR A PDF
    $scope.exportPDF = function() {
        var columns = [
            {title:"", dataKey: "titulo"}, 
            {title:"PRODUCTO", dataKey: "marca"},
            {title:"DETALLE", dataKey: "detalle"},
            {title:"PRECIO U.", dataKey: "precioUnitario"},
            {title:"CANTIDAD", dataKey: "cantidad"},
            {title:"PRECIO TOTAL", dataKey: "precioTotal"}
            
        ];
        var doc = new jsPDF('l', 'pt');
        var ventas = leerVentas();
        doc.autoTable(columns,ventas,{headerStyles: {fillColor: [41,41,97]},columnStyles: {titulo: {fontStyle: 'bold'}}});
        doc.save('figaro-ventas.pdf');
    }


    //EXPORTAR A EXCEL
    $scope.exportExcel = function () {
        var csv = [];
        var rows = document.querySelectorAll("table tr");
        var cols = rows[0].querySelectorAll("td, th");
        //HEADER
        var row = []; 
        row.push('');
        row.push('PRODUCTO');
        row.push('DETALLE');
        row.push('PRECIO U.');
        row.push('CANTIDAD');
        row.push('PRECIO TOTAL');

        csv.push(row.join(";"));
        //BODY
        let venta = {};
        cols = rows[0].querySelectorAll("td, th");
        
        for (var i = 0; i < rows.length; i++) {
            var row = [], cols = rows[i].querySelectorAll("td, th");
            colsLenght = cols.length;
            if (colsLenght == 6) {   
                row.push('');           
                row.push(cols[0].innerText);
                row.push(cols[1].innerText);          
                row.push(cols[2].innerText);
                row.push(cols[3].innerText);
                row.push(cols[4].innerText);
            } else {
                if (colsLenght == 3){
                    row.push(cols[0].innerText); 
                    row.push('');
                    row.push('');
                    row.push('');
                    row.push('');
                    row.push('');                  
                    row.push(cols[1].innerText);  
                } else {  
                    row.push(cols[0].innerText);                   
                    row.push('');
                    row.push('');
                    row.push('');
                    row.push('');
                    row.push('');                                      
                }
            }           

            csv.push(row.join(";"));             
        }     

        csv=csv.join("\n")
        var link = window.document.createElement("a");
        link.setAttribute("href", "data:text/csv;charset=utf-8,%EF%BB%BF" + encodeURI(csv));
        link.setAttribute("download", "figaro-ventas.csv");
        link.click();
    } 

//----------------------  Fin Utilidades  ---------------------------//

//----------------------  Inicializacion  ---------------------------//
    $scope.activeVenta = true;
    $scope.loaded = false;
    $scope.ngProductoVenta = {};
    $scope.ngCarrito = [];
    $scope.ngVenta={}; 
    $scope.ngVentas = [];
    $scope.message='';
    $scope.fechaInicio = getDateFormated();
    $scope.fechaFin = getDateFormated();
    $scope.getAllProductos();
    $scope.getAllVentas();

});