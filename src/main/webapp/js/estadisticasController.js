
app.controller('estadisticasController', function ($scope, $http) {
		
	$scope.activeEstadisticas = true;

		//OBTENER LISTA DE CIUDADES
		    $scope.getClientesCiudad = function() {
		        $http.get("/rest/estadisticas/clientes/ciudad").then(function (response) {
		            $scope.clientesCiudad = response.data;
		        });			      
		    };		  

		//OBTENER LISTA DE CLIENTE SEXO
		    $scope.getClientesSexo = function() {
		        $http.get("/rest/estadisticas/clientes/sexo").then(function (response) {
		            $scope.clientesSexo = response.data;		            
		        });			      
		    };


		//OBTENER LISTA DE Producto mas vendido
		    $scope.getProductoMasVendido = function() {
		        $http.get("/rest/estadisticas/productos/masvendido",{params: { from: $scope.fechaInicio, to: $scope.fechaFin}}).then(function (response) {
		            $scope.productoMasVendido = response.data;		
		            seleccionado = $scope.productoMasVendido;
                	$scope.generarSeleccionado(seleccionado);             	            
		        });			      
		    };	

		//OBTENER LISTA DE Totales De Caja
		    $scope.getTotalesDeCaja = function() {
		        $http.get("/rest/estadisticas/caja/totales",{params: { from: $scope.fechaInicio, to: $scope.fechaFin}}).then(function (response) {
		            $scope.totalesDeCaja = response.data;		
		            seleccionado = $scope.totalesDeCaja;
                	$scope.generarSeleccionado(seleccionado);             	            
		        });			      
		    };	
     
     	//OBTENER LISTA DE Totales De Caja
		    $scope.getTurnosPorEmpleadoCant = function() {
		        $http.get("/rest/estadisticas/turnos/empleado/cantidad",{params: { from: $scope.fechaInicio, to: $scope.fechaFin}}).then(function (response) {
		            $scope.turnosPorEmpleadoCant = response.data;		
		            seleccionado = $scope.turnosPorEmpleadoCant;
                	$scope.generarSeleccionado(seleccionado);             	            
		        });			      
		    };	

		//OBTENER LISTA DE Totales De Caja
		    $scope.getTurnosPorEmpleadoIngreso = function() {
		        $http.get("/rest/estadisticas/turnos/empleado/ingreso",{params: { from: $scope.fechaInicio, to: $scope.fechaFin}}).then(function (response) {
		            $scope.turnosPorEmpleadoIngreso = response.data;		
		            seleccionado = $scope.turnosPorEmpleadoIngreso;
                	$scope.generarSeleccionado(seleccionado);             	            
		        });			      
		    };	

		//OBTENER LISTA DE Turno mas solicitado
		    $scope.getTurnoMasSolicitado = function() {
		        $http.get("/rest/estadisticas/turnos/massolicitados",{params: { from: $scope.fechaInicio, to: $scope.fechaFin}}).then(function (response) {
		            $scope.turnosMasSolicitado = response.data;			            
		            seleccionado = $scope.turnosMasSolicitado;
                	$scope.generarSeleccionado(seleccionado);             	            
		        });			      
		    };	
     
		//CASE SELECCION
			$scope.selectGrafico = function(item){				    			
				switch (item) {
            		case 'sexo':            		
            				seleccionado = $scope.clientesSexo;
            				$scope.muestra = false;
            				tipos = 'bar';
            				options = { scales: { yAxes: [{ ticks: { beginAtZero: true, min: 0 } }] } };             				
            				$scope.generarSeleccionado(seleccionado);           			
                			break;
            		case 'ciudad':
            				seleccionado = $scope.clientesCiudad;
            				$scope.muestra = false; 
            				tipos = 'bar';   
            				options = { scales: { yAxes: [{ ticks: { beginAtZero: true, min: 0 } }] } };         				
            				$scope.generarSeleccionado(seleccionado);          			
                			break;
                	case 'productoMasVendido':                		
                			$scope.fechaFin = $scope.searchTo;
                			$scope.fechaInicio = $scope.searchFrom;                			   
                			$scope.muestra = true; 
                			tipos = 'bar';
                			options = { scales: { yAxes: [{ ticks: { beginAtZero: true, min: 0 } }] } };                			        
                			$scope.getProductoMasVendido();   			
                			break;
                	case 'turnoMasSolicitado':       
                			$scope.fechaFin = getDateFormatedEnd($scope.searchTo);
                			$scope.fechaInicio = $scope.searchFrom;                			   
                			$scope.muestra = true;   
                			tipos = 'line';             			
                			options = { scales: { yAxes: [{ ticks: { beginAtZero: true, min: 0 } }] } };
                			$scope.getTurnoMasSolicitado();   			
                			break;
                	case 'totalesDeCaja':                		
                			$scope.fechaFin = $scope.searchTo;
                			$scope.fechaInicio = $scope.searchFrom;                			   
                			$scope.muestra = true;
                			tipos = 'bar';
                			options = { scales: { yAxes: [{ ticks: { beginAtZero: false} }] } };
                			$scope.getTotalesDeCaja();   			
                			break;
                	case 'turnosPorEmpleadoCant':  
                			$scope.fechaFin = getDateFormatedEnd($scope.searchTo);
                			$scope.fechaInicio = $scope.searchFrom;                			   
                			$scope.muestra = true;
                			tipos = 'bar';
                			options = { scales: { yAxes: [{ ticks: { beginAtZero: true, min: 0 } }] } };
                			$scope.getTurnosPorEmpleadoCant();   			
                			break;
                	case 'turnosPorEmpleadoIngreso':       
                			$scope.fechaFin = getDateFormatedEnd($scope.searchTo);
                			$scope.fechaInicio = $scope.searchFrom;                			   
                			$scope.muestra = true; 
                			tipos = 'bar';               			
                			options = { scales: { yAxes: [{ ticks: { beginAtZero: true, min: 0 } }] } };
                			$scope.getTurnosPorEmpleadoIngreso();   			
                			break;
            		default:
            	}
            	
			}

//GRAFICOS

		//BUSCAR CLIENTE CIUDAD
		    $scope.generarSeleccionado = function(seleccionado) {		    	
		    	arregloColumnaBar = [] ;
				arregloLabelBar = [] ;
				arregloDataBar = [] ;		    	

				for (var key in seleccionado) {
    				arregloColumnaBar.push(key);
    				arregloDataBar.push(seleccionado[key]);
				}
				arregloLabelBar = 'Total' ;		        
		        grafico1.destroy();
		        $scope.generarGraficoBarra1();
		    }		
			  

		
	   // GENERAR GRAFICOS DE BARRA 1
			$scope.generarGraficoBarra1 = function(){					
				data1 = { labels: arregloColumnaBar, datasets: [{ label: arregloLabelBar, backgroundColor: color11, borderColor: color14, borderWidth: 2, hoverBackgroundColor: color14, hoverBorderColor: color14, data: arregloDataBar}]}; 
			    grafico1 = new Chart(ctx1, { type: tipos, data: data1, options: options	});
			}
 
		//OPTIONS
		    var options = '';
		    var tipos = 'bar';

		    var color11 = 'rgba(41,41,97,0.1)';
		    var color14 = 'rgba(41,41,97,0.4)';

		var canvas1 = document.getElementById("grafico1");
		var ctx1 = canvas1.getContext('2d');
			    
		var arregloColumnaBar = [] ;
		var arregloLabelBar = [] ;
		var arregloDataBar = [] ;
		$scope.muestra = false;
		$scope.generarGraficoBarra1();	

	    $scope.fechaInicio = getToday();
	    $scope.fechaFin = getToday();
	    $scope.searchFrom = getDateFormated();
	    $scope.searchTo = getDateFormated();
		$scope.getClientesCiudad();
		$scope.getClientesSexo();



});