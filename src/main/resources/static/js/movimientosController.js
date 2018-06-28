app.controller('movimientosController', function ($scope, $http) {
 	 
 
	//OBTENER LISTA DE MOVIMIENTOS
	    $scope.getAll = function() {    	
	    	$scope.searchMovimiento();
	    };

	    //CLICK NUEVO MOVIMIENTO
	    $scope.newMovimiento = function() {
	        $scope.isNuevoMovimiento = true
	    	openModal("modal-caja");
	        $scope.ngMovimiento={};
	        $scope.ngMovimiento.descuento = 0;
	        $scope.ngMovimiento.fecha = new Date(getToday()); 	        
	        $scope.message='';
	    };

	    //CLICK FILA MOVIMIENTO
	    $scope.detailMovimiento = function(event){
	        $scope.message='';
	        $scope.isNuevoMovimiento = false
	        $scope.movimientoID = event.currentTarget.getAttribute("data-id");
	        $http.get('/rest/movimientos/'+$scope.movimientoID).then(function (response) {
	            $scope.ngMovimiento = response.data;
	            $scope.ngMovimiento.fecha = new Date($scope.ngMovimiento.fecha);	
	            $scope.ngMovimiento.precio = $scope.ngMovimiento.precio + $scope.ngMovimiento.descuento;           
	            openModal("modal-caja");
		    });
	    };

	    //CLICK DETALLE VENTA
	    $scope.detailMovimientoVenta = function(event){
	        $scope.message='';
	        $scope.isNuevoMovimiento = false
	        $scope.movimientoID = event.currentTarget.getAttribute("data-id");
	        $http.get('/rest/movimientos/'+$scope.movimientoID).then(function (response) {
	            $scope.ngMovimiento = response.data;
	            $scope.ngMovimiento.fecha = new Date($scope.ngMovimiento.fecha);
	            $scope.ngDetalle = $scope.ngMovimiento.detalle;
	            $http.get("/rest/movimientos/listaDeItems",{params: { id: $scope.ngDetalle}}).then(function (response) {
	            	$scope.ngMovimientoVenta = response.data;
	               	openModal("modal-caja-venta");
	            });                    
	            
		    });
	    };	  
 		
 		//CLICK DETALLE TURNO
	    $scope.detailTurno = function(movimiento,modal){
	        $scope.message='';
	        $scope.isNuevoMovimiento = false;
	        $scope.ngDescuento = movimiento.descuento;
            $http.get("/rest/turnos/"+movimiento.turnoId).then(function (response) {
            	$scope.ngMovimientoTurno = response.data;
            	$scope.ngMovimientoSetTrabajos = response.data.trabajos;
               	openModal(modal);
            });                    
	    };

	

	    $scope.calculaTotalVenta = function(){
	        var total = 0;
	        angular.forEach($scope.ngMovimientoVenta, function(ngMovimiento){	          
	        	total = total + (ngMovimiento.precioTotal);
	        })
	        return total;
	    }
	    
	    $scope.discardMovimientoVenta = function(event){
	        $scope.ngMovimiento = {};
	        closeModal("modal-caja-venta");
	    };  

	    $scope.calculaTotalTurno = function(){
	        var total = 0;
	        angular.forEach($scope.ngMovimientoSetTrabajos, function(ngMovimientoSetTrabajos){	          
	        	total = total + (ngMovimientoSetTrabajos.servicio.precio);
	        })
	        return total - $scope.ngMovimiento.descuento;
	    }
	    
	    $scope.discardMovimientoTurno = function(event){
	        $scope.ngMovimiento = {};
	        closeModal("modal-caja-turno");
	    };

	    $scope.calculaComisionEmpleado = function(event){	   
	    	var precio = event.servicio.precio;
	    	var comi = event.comision * 0.01;
	    	var total = precio * comi;   
	        return total;
	    }

	    $scope.calculaTotalEmpleado = function(){
	        var total = 0;
	        angular.forEach($scope.ngMovimientoSetTrabajos, function(ngMovimientoSetTrabajos){
	        	total = total + $scope.calculaComisionEmpleado(ngMovimientoSetTrabajos);
	        })
	        return total;
	    }
	    
	    $scope.discardMovimientoEmpleado = function(event){
	        $scope.ngMovimiento = {};
	        closeModal("modal-caja-empleado");
	    };


	    //CLICK ACEPTAR FORMULARIO
	    $scope.sendMovimiento = function() {	    	        
	        if($scope.isNuevoMovimiento === true){
	            $http.post('/rest/movimientos/alta', $scope.ngMovimiento).then(function (response) {
	                $scope.movimientos.push(response.data);
	                $scope.formatTipoPago();
	            });
	            $scope.ngMovimiento={};
	        }else{
	            $http.put('/rest/movimientos/actualizar/'+ $scope.movimientoID, $scope.ngMovimiento).then(function (response) {
	                $scope.getAll();
	            });
	        }
	        closeModal("modal-caja");
	    };

	    //DESCARTAR FORMULARIO
	    $scope.discardMovimiento = function(event){
	        $scope.ngMovimiento = {};
	        closeModal("modal-caja");
	    };

    
	    //OBTENER LISTA DE CATEGORIAS
	    $scope.getAllCategorias = function() {
	        $http.get("/rest/configuracion/categorias").then(function (response) {
	        	$scope.categorias = {};
	            $scope.categorias = response.data;
	        });
	    };
	    
	    //APRETAR ESCAPE
	    document.addEventListener('keyup', function(e) {
	        if (e.keyCode == 27) {
	            $scope.discardMovimiento();
	            $scope.discardMovimientoVenta();
	            $scope.discardMovimientoTurno();
	            $scope.discardMovimientoEmpleado();
	        }
	    });

	    


	  //ELIMINTAR MOVIMIENTO
	    $scope.deleteTarget = function(id) {	       
	        //$scope.movimientoID = event.currentTarget.getAttribute("data-id");
	        $http.delete('/rest/movimientos/eliminar/'+ id).then(function (response) {	           
	        	closeModal("modal-confirmarDelete");
				$scope.getAll();
	        });
	    };
	    
	  //CONFIRMA ELIMINTAR MOVIMIENTO
	    $scope.confirmDelete = function(id) {
	    	$scope.idTarget = id;
	    	openModal("modal-confirmarDelete");
	        
	    };
	    
	  //DESCARTAR FORMULARIO
	    $scope.discardConfirm = function(event){
	        $scope.ngMovimiento = {};
	        closeModal("modal-confirmarDelete");
	    };
	    
	    //CALCULAR EL TOTAL DE CAJA
	    $scope.calculaTotal = function(){
	        var total = 0;
	        angular.forEach($scope.movimientos, function(ngMovimiento){	          
	          if (ngMovimiento.isGasto == true){
	        	  total = total - (ngMovimiento.precio);
	          } else {
	        	  total = total + (ngMovimiento.precio);
	          }	          
	        })
	        return total;
	    }

	    //CALCULAR EL TOTAL NEGATIVO
	    $scope.calculaTotalNegativo = function(){
	        var total = 0;
	        angular.forEach($scope.movimientos, function(ngMovimiento){	          
	          if (ngMovimiento.isGasto == true){
	        	  total = total - (ngMovimiento.precio);
	          }          
	        })
	        return total;
	    }

	    //CALCULAR EL TOTAL POSITIVO
	    $scope.calculaTotalPositivo = function(){
	        var total = 0;
	        angular.forEach($scope.movimientos, function(ngMovimiento){	          
	          if (ngMovimiento.isGasto != true){	        	 
	        	  total = total + (ngMovimiento.precio);
	          }	          
	        })
	        return total;
	    }

	    //CALCULAR EL TOTAL DE CAJA
	    $scope.calculaTotalEfectivo = function(){
	        var total = 0;
	        angular.forEach($scope.movimientos, function(ngMovimiento){	          
	       	  if (ngMovimiento.tipoPago == 'contado'){
	          	if (ngMovimiento.isGasto == true){
	        		total = total - (ngMovimiento.precio);
	          	} else {
	        		total = total + (ngMovimiento.precio);
	          	}	 
	          }         
	        })
	        return total;
	    }

	    //CALCULAR EL TOTAL DE CAJA
	    $scope.calculaNegativoEfectivo = function(){
	        var total = 0;
	        angular.forEach($scope.movimientos, function(ngMovimiento){	          
	       	  if (ngMovimiento.tipoPago == 'contado'){
	          	if (ngMovimiento.isGasto == true){
	        		total = total - (ngMovimiento.precio);
	          	} 	 
	          }         
	        })
	        return total;
	    }

	    $scope.calculaPositivoEfectivo = function(){
	        var total = 0;
	        angular.forEach($scope.movimientos, function(ngMovimiento){	          
	       	  if (ngMovimiento.tipoPago == 'contado'){
	          	if (ngMovimiento.isGasto != true){
	        		total = total + (ngMovimiento.precio);
	          	} 	 
	          }         
	        })
	        return total;
	    }

	    //CALCULAR EL TOTAL DE CAJA
	    $scope.calculaTotalTarjeta = function(){
	        var total = 0;
	        angular.forEach($scope.movimientos, function(ngMovimiento){
	          if (ngMovimiento.tipoPago != 'contado'){     
	          	if (ngMovimiento.isGasto == true){
	        	  	total = total - (ngMovimiento.precio);
	          	} else {
	        	  	total = total + (ngMovimiento.precio);
	          	}	     
	          }     
	        })
	        return total;
	    }

	    $scope.calculaNegativoTarjeta = function(){
	        var total = 0;
	        angular.forEach($scope.movimientos, function(ngMovimiento){	          
	       	  if (ngMovimiento.tipoPago != 'contado'){
	          	if (ngMovimiento.isGasto == true){
	        		total = total - (ngMovimiento.precio);
	          	} 	 
	          }         
	        })
	        return total;
	    }

	    $scope.calculaPositivoTarjeta = function(){
	        var total = 0;
	        angular.forEach($scope.movimientos, function(ngMovimiento){	          
	       	  if (ngMovimiento.tipoPago != 'contado'){
	          	if (ngMovimiento.isGasto != true){
	        		total = total + (ngMovimiento.precio);
	          	} 	 
	          }         
	        })
	        return total;
	    }

	    // MUESTRA DESCUENTO
	    $scope.mostrarDescuento = function(item){	 
	    	if (item.tipoPago != 'contado') {  	
	    		if (item.descuento == 0){
	    			return item.tipoPago;
	    		} else {
		    		return item.tipoPago + " ( -" + item.descuento + " )";
	    		}
	    	} else {
	    		if (item.descuento == 0){
	    			return "";
	    		} else {
		    		return "( -" + item.descuento + " )";
	    		}
	    	}
	    }

	    //ORDENAR POR FECHA	    
	    $scope.sortFecha = function(ngMovimiento) {
	        var date = new Date(ngMovimiento.fecha);
	        return date;
	    };

	  // LIMPIAR SELECT CATEGORIA
	  $scope.limpiarSelect = function(){
	    	$scope.searchC = $scope.busqueda.categoria;	
	  }
	    
	  //LIMPIA FILTRO FECHA
	  $scope.limpiaFecha = function() {
		    $scope.search = '';
		    $scope.busqueda.fechaInicio = getDateFormated();
		    $scope.busqueda.fechaFin = getDateFormated();
		    $scope.searchMovimiento();
	  }  
	    
	  //LIMPIA FILTRO CATEGORIA
	  $scope.limpiaCategoria = function() {
		  	$scope.searchC = '';
		    $scope.busqueda.categoria = '';		
		    $scope.searchMovimiento();
	  }  
	    
	  //FILTRO DIA
	    $scope.searchMovimientoDia = function() {	    
		    $scope.busqueda.fechaInicio = getStringDate(new Date($scope.searchInicio));
		    $scope.busqueda.fechaFin = getStringDate(new Date($scope.searchFin));
		    $scope.searchMovimiento();
	    } 


	  //FILTRO CATEGORIA    
	    $scope.searchCategoria = function() {
	    	$scope.busqueda.categoria = $scope.categoriasBusqueda;
	    	$scope.searchMovimiento();
	    }	    




	  //FILTRO TOTAL
	    $scope.searchMovimiento = function() {
	    	loading();
	    	$http.get('/rest/movimientos/buscar',{params: { from: $scope.busqueda.fechaInicio, to: $scope.busqueda.fechaFin, category: $scope.busqueda.categoria }})		        
		        .then(function successCallback(response) {	  	        	
		            $scope.movimientos = response.data;
		            $scope.formatTipoPago();
		            loadComplete();
		        })
	    }
	    	 
	    $scope.formatTipoPago = function() {
	    	for(var key in $scope.movimientos ){
	    		if ($scope.movimientos[key].tipoPago == 'debito') {
	    			$scope.movimientos[key].tipoPago = 'D';
	    		} else if ($scope.movimientos[key].tipoPago == 'credito') {
	    			$scope.movimientos[key].tipoPago = $scope.movimientos[key].cuotas;
	    		}
	    	}
	    }

	    //MOSTRAR O NO MOSTRAR DIV DE BUSQUEDA
	    $scope.IsHiddenDia = true;
	    $scope.IsHiddenCategoria = true;

	    
        $scope.ShowHideDia = function () {
        	$scope.IsHiddenCategoria = true;
            $scope.IsHiddenDia = $scope.IsHiddenDia ? false : true;
            
            $scope.searchMovimientoDia();
            if($scope.IsHiddenDia === true){
            	$scope.limpiaFecha();
      
            	$scope.limpiaCategoria();
            	$scope.getAll();	                    	
	        }
        }        
        
        $scope.ShowHideCategoria = function () {         
        	$scope.IsHiddenDia = true;
            $scope.IsHiddenCategoria = $scope.IsHiddenCategoria ? false : true;  
            $scope.getAllCategorias();           
			$scope.categorias.push({id:$scope.categorias.length + 1, nombre:'Turnos'});
			$scope.categorias.push({id:$scope.categorias.length + 1, nombre:'Empleados'});
			$scope.categorias.push({id:$scope.categorias.length + 1, nombre:'Ventas'});		
			$scope.searchCategoriaPrevio();
			if ($scope.listavacia){
				$scope.categoriasLista = [];			
				for (var i = 0; i < $scope.categorias.length; i++) {
					$scope.categoriasLista.push({nombre: $scope.categorias[i].nombre, checked:'false'});
				}
			}
            if($scope.IsHiddenCategoria === true){
            	$scope.getAllCategorias();
            	$scope.limpiaFecha();
            	$scope.limpiaCategoria();
            	$scope.getAll();
	        }
        }        

        $scope.searchCategoriaPrevio = function () {
        	$scope.categoriasBusqueda = '';
        	$scope.listavacia=true
			for (var i = 0; i < $scope.categoriasLista.length; i++) {
					var prueba = $scope.categoriasLista[i];
                    if (prueba.checked === true) {  
                    	$scope.listavacia=false
                        if ($scope.categoriasBusqueda == ''){
							$scope.categoriasBusqueda = $scope.categoriasLista[i].nombre;
                        } else {
                        	$scope.categoriasBusqueda = $scope.categoriasBusqueda + "," + $scope.categoriasLista[i].nombre;
                        }                  
                    }
            }
            $scope.searchCategoria();
		};

        $scope.ShowMas = function () {
        	$scope.IsHiddenMenos = false;
        	$scope.IsHiddenMas = true;
            $scope.IsHiddenMas = $scope.IsHiddenMas ? true : false;  
        }        

        $scope.ShowMenos = function () {
        	$scope.IsHiddenMenos = true;
        	$scope.IsHiddenMas = false;
            $scope.IsHiddenMenos = $scope.IsHiddenMenos ? true : false;  
        }   

        // LEER TURNOS DESDE LA TABLA HTML
    function leerCaja(){
        var movimientosCaja = [];
        var rows = document.querySelectorAll("table tr");
        var movsLength = rows.length - 8;
        for (var i = 1; i < movsLength; i++) {
            let movimientoCaja = {};
            cols = rows[i].querySelectorAll("td, th");
            movimientoCaja.fecha = cols[0].innerText;
            movimientoCaja.categoria = cols[1].innerText;            
            var montoColor = cols[2].firstChild.className;
            if (montoColor == 'ng-binding red') {
            	movimientoCaja.monto = "-" + cols[2].innerText;
            } else {
            	movimientoCaja.monto = cols[2].innerText;
            }            
            var pagoPrevio = cols[3].innerText;
            if (pagoPrevio.includes('credit_cardD')) {
            	var res = pagoPrevio.replace("credit_cardD", "D ");
            } else {
            	if (pagoPrevio.includes('credit_card')) {
            		var res = pagoPrevio.replace("credit_card", "C ");            		
            	} else {
            		var res = pagoPrevio;
            	}
            }
            movimientoCaja.pago = res;            
            movimientoCaja.detalle = cols[4].innerText;            
            movimientosCaja.push(movimientoCaja);
        }        
        return movimientosCaja;
    }

    function leerCaja2(){
        var movimientosCaja = [];
        var rows = document.querySelectorAll("table tr");
        var movsini = rows.length - 6;
        var movsLength = movsini + 3;
        for (var i = movsini; i < movsLength; i++) {
            let movimientoCaja = {};
            cols = rows[i].querySelectorAll("td, th");
            movimientoCaja.categoria = cols[0].innerText;
            movimientoCaja.efectivo = cols[1].innerText;
            movimientoCaja.tarjeta = cols[2].innerText;                      
            movimientoCaja.general = cols[3].innerText;            
            movimientosCaja.push(movimientoCaja);
        }        
        return movimientosCaja;
    }

        //EXPORTAR A PDF
    $scope.exportPDF = function() {
        var columns = [
            {title: "FECHA", dataKey: "fecha"}, 
            {title: "CATEGORIA", dataKey: "categoria"},
            {title: "MONTO", dataKey: "monto"},
            {title: "PAGO", dataKey: "pago"},
            {title: "DETALLE", dataKey: "detalle"}            
        ];
        var doc = new jsPDF('l', 'pt');
        var movs = leerCaja();
        doc.autoTable(columns, movs,{headerStyles: {fillColor: [41,41,97]}});
        
        var columns2 = [
        	{title: "", dataKey: "categoria"}, 
            {title: "EFECTIVO", dataKey: "efectivo"}, 
            {title: "TARJETA", dataKey: "tarjeta"},
            {title: "GENERAL", dataKey: "general"}          
        ];
        var movs2 = leerCaja2();	
		doc.autoTable(columns2, movs2, {headerStyles: {fillColor: [41,41,97]},startY: doc.autoTableEndPosY() + 50});
		doc.save('figaro-movimientos.pdf');
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
        var movsLength = rows.length - 8;
        for (var i = 1; i < movsLength; i++) {
            var row = [], cols = rows[i].querySelectorAll("td, th");
            row.push(cols[0].innerText);
            row.push(cols[1].innerText);
            var montoColor = cols[2].firstChild.className;
            if (montoColor == 'ng-binding red') {
            	var monto = "-" + cols[2].innerText;
            } else {
            	var monto = cols[2].innerText;
            } 
            row.push(monto);
            var pagoPrevio = cols[3].innerText;
            if (pagoPrevio.includes('credit_cardD')) {
            	var res = pagoPrevio.replace("credit_cardD", "D ");
            } else {
            	if (pagoPrevio.includes('credit_card')) {
            		var res = pagoPrevio.replace("credit_card", "C ");            		
            	} else {
            		var res = pagoPrevio;
            	}
            }
            row.push(res);            
            row.push(cols[4].innerText);           
            csv.push(row.join(";"));             
        }     
        var movsini = rows.length - 7;
        var movsLength = movsini + 4;
        for (var i = movsini; i < movsLength; i++) {        	
            var row = [], cols = rows[i].querySelectorAll("td, th");
            row.push(cols[0].innerText);
            row.push(cols[1].innerText);            
            row.push(cols[2].innerText);
            row.push(cols[3].innerText);
            csv.push(row.join(";")); 
        }   

        csv=csv.join("\n")
        var link = window.document.createElement("a");
		link.setAttribute("href", "data:text/csv;charset=utf-8,%EF%BB%BF" + encodeURI(csv));
		link.setAttribute("download", "figaro-movimientos.csv");
		link.click();
    } 
        
       
	    //INIT
        $scope.IsHiddenMenos = true;
        $scope.IsHiddenMas = false;	    
	    $scope.activeCaja = true;
	    $scope.search = '';
	    $scope.busqueda = {};
	    $scope.categoriasLista = [];
	    $scope.busqueda.fechaInicio = getDateFormated();
	    $scope.busqueda.fechaFin = getDateFormated();
	    let date = new Date();
	    $scope.searchInicio = new Date(date.getFullYear(), date.getMonth(), 1);
        $scope.searchFin = new Date(date.getFullYear(), date.getMonth() + 1, 0); 
	    $scope.busqueda.categoria = '';
	    $scope.ngMovimiento = {};
	    $scope.getAllCategorias();
	    $scope.getAll();

	});
