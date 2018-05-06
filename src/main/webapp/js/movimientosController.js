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

	    //CLICK DETALLE TURNO
	    $scope.detailMovimientoTurno = function(event){
	        $scope.message='';
	        $scope.isNuevoMovimiento = false
	        $scope.movimientoID = event.currentTarget.getAttribute("data-id");
	        $http.get('/rest/movimientos/'+$scope.movimientoID).then(function (response) {
	            $scope.ngMovimiento = response.data;
	            $scope.ngMovimiento.fecha = new Date($scope.ngMovimiento.fecha);
	            $scope.ngDetalle = $scope.ngMovimiento.detalle;
	            $http.get("/rest/movimientos/turno",{params: { id: $scope.ngDetalle}}).then(function (response) {
	            	$scope.ngMovimientoTurno = response.data;
	            	$http.get("/rest/movimientos/setDeTrabajos",{params: { id: $scope.ngDetalle}}).then(function (response) {
	            		$scope.ngMovimientoSetTrabajos = response.data;	            	
	            	});
	               	openModal("modal-caja-turno");
	            });                    
	            
		    });
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

	    
	    //CLICK DETALLE EMPLEADO
	    $scope.detailMovimientoEmpleado = function(event){
	        $scope.message='';
	        $scope.isNuevoMovimiento = false
	        $scope.movimientoID = event.currentTarget.getAttribute("data-id");
	        $http.get('/rest/movimientos/'+$scope.movimientoID).then(function (response) {
	            $scope.ngMovimiento = response.data;
	            $scope.ngMovimiento.fecha = new Date($scope.ngMovimiento.fecha);
	            $scope.ngDetalle = $scope.ngMovimiento.detalle;
	            $http.get("/rest/movimientos/turno",{params: { id: $scope.ngDetalle}}).then(function (response) {
	            	$scope.ngMovimientoTurno = response.data;
	            	$http.get("/rest/movimientos/setDeTrabajos",{params: { id: $scope.ngDetalle}}).then(function (response) {
	            		$scope.ngMovimientoSetTrabajos = response.data;	            	
	            	});
	               	openModal("modal-caja-empleado");
	            });                    
	            
		    });
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
            let date = new Date();
            $scope.searchInicio = new Date(date.getFullYear(), date.getMonth(), 1);
        	$scope.searchFin = new Date(date.getFullYear(), date.getMonth() + 1, 0);        	
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
			$scope.categoriasLista = [];
			for (var i = 0; i < $scope.categorias.length; i++) {
				$scope.categoriasLista.push({nombre: $scope.categorias[i].nombre, checked:'false'});
			}
            if($scope.IsHiddenCategoria === true){
            	$scope.getAllCategorias();
            	$scope.limpiaFecha();
            	$scope.limpiaCategoria();
            	$scope.getAll();
	        }
        }        

        $scope.checkItems = function () {
        	$scope.categoriasBusqueda = '';
			for (var i = 0; i < $scope.categoriasLista.length; i++) {
					var prueba = $scope.categoriasLista[i];
                    if (prueba.checked === true) {  
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
        
       
	    //INIT
        $scope.IsHiddenMenos = true;
        $scope.IsHiddenMas = false;	    
	    $scope.activeCaja = true;
	    $scope.search = '';
	    $scope.busqueda = {};
	    $scope.categoriasLista = [];
	    $scope.busqueda.fechaInicio = getDateFormated();
	    $scope.busqueda.fechaFin = getDateFormated();
	    $scope.busqueda.categoria = '';
	    $scope.ngMovimiento = {};
	    $scope.getAllCategorias();
	    $scope.getAll();

	});
