
app.controller('estadisticasController', function ($scope, $http) {
		
	$scope.activeEstadisticas = true;


        //EXPORTAR A PDF
            $scope.exportPDF = function() {

                var canvas = $("#grafico1").get(0);
                var dataURL = canvas.toDataURL();
                var doc = new jsPDF('landscape');
                doc.addImage(dataURL, 'JPEG', 25, 25);
                doc.save('figaro-estadisticas.pdf');
            }

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

		//OBTENER LISTA DE Turno por sexo por mes
		    $scope.getTurnoMes = function() {
		        $http.get("/rest/estadisticas/turnos/turnomes",{params: { from: $scope.fechaInicio, to: $scope.fechaFin}}).then(function (response) {
		            $scope.turnomes = response.data;			            
		            seleccionado = $scope.turnomes;
                	$scope.generarSeleccionado(seleccionado);             	            
		        });			      
		    };	

		//OBTENER LISTA DE Turno por sexo por mes
		    $scope.getTurnoSemana = function() {
		        $http.get("/rest/estadisticas/turnos/turnosemana",{params: { from: $scope.fechaInicio, to: $scope.fechaFin}}).then(function (response) {
		            $scope.turnosemana = response.data;			            
		            seleccionado = $scope.turnosemana;
                	$scope.generarSeleccionado(seleccionado);             	            
		        });			      
		    };	

		//OBTENER LISTA DE Turno por sexo por mes
		    $scope.getTurnoSexoMes = function() {
		        $http.get("/rest/estadisticas/turnos/turnosexomes",{params: { from: $scope.fechaInicio, to: $scope.fechaFin}}).then(function (response) {
		            $scope.turnosexomes = response.data;			            
		            seleccionado = $scope.turnosexomes;
                	$scope.generarSeleccionadoConTresDatos();             	            
		        });			      
		    };	

		//OBTENER LISTA DE Turno por sexo por mes
		    $scope.getTurnoSexoSemana = function() {
		        $http.get("/rest/estadisticas/turnos/turnosexosemana",{params: { from: $scope.fechaInicio, to: $scope.fechaFin}}).then(function (response) {
		            $scope.turnosexosemana = response.data;			            
		            seleccionado = $scope.turnosexosemana;
                	$scope.generarSeleccionadoConTresDatos();             	            
		        });			      
		    };	

        //OBTENER LISTA DE Turno por sexo por mes
            $scope.getTrabajoMasRequerido = function() {
                $http.get("/rest/estadisticas/turnos/trabajomasrequerido",{params: { from: $scope.fechaInicio, to: $scope.fechaFin}}).then(function (response) {
                    $scope.trabajomasrequerido = response.data;      
                    seleccionado = $scope.trabajomasrequerido;
                    $scope.generarSeleccionado(seleccionado); 
                });               
            };  
            
     
		//CASE SELECCION
			$scope.selectGrafico = function(item){		
                $scope.muestraSuma  = false;
                $scope.muestraConSuma2 = false;
                $scope.muestraConSuma3 = false;
                $scope.muestraConSuma4 = false;	    			
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
                	case 'turnomes':       
                			$scope.fechaFin = getDateFormatedEnd($scope.searchTo);
                			$scope.fechaInicio = $scope.searchFrom;                			   
                			$scope.muestra = true;  
                            $scope.muestraSuma  = true; 
                			tipos = 'line';             			
                			options = { scales: { yAxes: [{ ticks: { beginAtZero: true, min: 0 } }] } };
                			$scope.getTurnoMes();   			
                			break;
                	case 'turnosemana':       
                			$scope.fechaFin = getDateFormatedEnd($scope.searchTo);
                			$scope.fechaInicio = $scope.searchFrom;                			   
                			$scope.muestra = true;   
                            $scope.muestraSuma  = true;
                			tipos = 'line';             			
                			options = { scales: { yAxes: [{ ticks: { beginAtZero: true, min: 0 } }] } };
                			$scope.getTurnoSemana();   			
                			break;
                	case 'turnosexomes':       
                			$scope.fechaFin = getDateFormatedEnd($scope.searchTo);
                			$scope.fechaInicio = $scope.searchFrom;                			   
                			$scope.muestra = true;   
                			tipos = 'line';             			
                			options = { scales: { yAxes: [{ ticks: { beginAtZero: true, min: 0 } }] } };
                			$scope.getTurnoSexoMes();   			
                			break;
                	case 'turnosexosemana':       
                			$scope.fechaFin = getDateFormatedEnd($scope.searchTo);
                			$scope.fechaInicio = $scope.searchFrom;                			   
                			$scope.muestra = true;   
                			tipos = 'line';             			
                			options = { scales: { yAxes: [{ ticks: { beginAtZero: true, min: 0 } }] } };
                			$scope.getTurnoSexoSemana();   			
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
                    case 'trabajomasrequerido':
                            $scope.fechaFin = getDateFormatedEnd($scope.searchTo);
                            $scope.fechaInicio = $scope.searchFrom;                            
                            $scope.muestra = true;   
                            tipos = 'line';                         
                            options = { scales: { yAxes: [{ ticks: { beginAtZero: true, min: 0 } }] } };
                            $scope.getTrabajoMasRequerido();             
                            break;
            		default:
            	}
            	
			}

            $scope.muestraSumaMesSemana = function(item) {
                $scope.muestraConSuma2 = true; 
                $scope.muestra= false;
                if (item == "turnosemana"){
                    $scope.muestraSuma2 = true;
                } else {
                    $scope.muestraSuma2 = false;                   
                }
            }

        //CASE SELECCION coo dos datos
        //OBTENER LISTA DE Turno por sexo por mes
            $scope.getTurnoMes2 = function() {
                $http.get("/rest/estadisticas/turnos/turnomes",{params: { from: $scope.fechaInicio, to: $scope.fechaFin}}).then(function (response) {
                    $scope.turnomes = response.data;                        
                    seleccionado = $scope.turnomes;                                               
                });
                $http.get("/rest/estadisticas/turnos/turnomes",{params: { from: $scope.fechaInicio2, to: $scope.fechaFin2}}).then(function (response) {
                    $scope.turnomes = response.data;                        
                    seleccionado2 = $scope.turnomes;
                    $scope.generarSeleccionado2(seleccionado,seleccionado2);                           
                });                
            };  

        //OBTENER LISTA DE Turno por sexo por mes
            $scope.getTurnoSemana2 = function() {
                $http.get("/rest/estadisticas/turnos/turnosemana",{params: { from: $scope.fechaInicio, to: $scope.fechaFin}}).then(function (response) {
                    $scope.turnosemana = response.data;                     
                    seleccionado = $scope.turnosemana;                                             
                });     
                $http.get("/rest/estadisticas/turnos/turnosemana",{params: { from: $scope.fechaInicio2, to: $scope.fechaFin2}}).then(function (response) {
                    $scope.turnosemana = response.data;                     
                    seleccionado2 = $scope.turnosemana;
                    $scope.generarSeleccionado2(seleccionado,seleccionado2);                           
                });           
            };

            $scope.selectGrafico2 = function(item){      
                $scope.muestraSuma  = false;
                $scope.muestraConSuma2 = false;
                $scope.muestraConSuma3 = false;
                $scope.muestraConSuma4 = false;                 
                switch (item) {
                    case 'turnomes':       
                            $scope.fechaFin = getDateFormatedEnd($scope.searchTo);
                            $scope.fechaInicio = $scope.searchFrom;    
                            $scope.fechaFin2 = getDateFormatedEnd($scope.searchTo2);
                            $scope.fechaInicio2 = $scope.searchFrom2;                         
                            $scope.muestraSuma = true;                              
                            $scope.muestraConSuma2  = true;  
                            $scope.label1='Mes 1';
                            $scope.label2='Mes 2';
                            tipos = 'line';                         
                            options = { scales: { yAxes: [{ ticks: { beginAtZero: true, min: 0 } }] } };                                
                            $scope.getTurnoMes2();            
                            break;
                    case 'turnosemana':       
                            $scope.fechaFin = getDateFormatedEnd($scope.searchTo);
                            $scope.fechaInicio = $scope.searchFrom;    
                            $scope.fechaFin2 = getDateFormatedEnd($scope.searchTo2);
                            $scope.fechaInicio2 = $scope.searchFrom2;                          
                            $scope.muestraSuma = true; 
                            $scope.muestraConSuma2  = true; 
                            $scope.label1='Semana 1';
                            $scope.label2='Semana 2';
                            tipos = 'line';                         
                            options = { scales: { yAxes: [{ ticks: { beginAtZero: true, min: 0 } }] } };                             
                            $scope.getTurnoSemana2();           
                            break;                                       
                    default:
                }
                
            }


        //CASE SELECCION coo tres datos
        //OBTENER LISTA DE Turno por sexo por mes
            $scope.getTurnoMes3 = function() {
                $http.get("/rest/estadisticas/turnos/turnomes",{params: { from: $scope.fechaInicio, to: $scope.fechaFin}}).then(function (response) {
                    $scope.turnomes = response.data;                        
                    seleccionado = $scope.turnomes;                                               
                });
                $http.get("/rest/estadisticas/turnos/turnomes",{params: { from: $scope.fechaInicio2, to: $scope.fechaFin2}}).then(function (response) {
                    $scope.turnomes = response.data;                        
                    seleccionado2 = $scope.turnomes;                                            
                });  
                $http.get("/rest/estadisticas/turnos/turnomes",{params: { from: $scope.fechaInicio3, to: $scope.fechaFin3}}).then(function (response) {
                    $scope.turnomes = response.data;                        
                    seleccionado3 = $scope.turnomes;
                    $scope.generarSeleccionado3(seleccionado,seleccionado2,seleccionado3);                           
                });               
            };  

        //OBTENER LISTA DE Turno por sexo por mes
            $scope.getTurnoSemana3 = function() {
                $http.get("/rest/estadisticas/turnos/turnosemana",{params: { from: $scope.fechaInicio, to: $scope.fechaFin}}).then(function (response) {
                    $scope.turnosemana = response.data;                     
                    seleccionado = $scope.turnosemana;                                             
                });     
                $http.get("/rest/estadisticas/turnos/turnosemana",{params: { from: $scope.fechaInicio2, to: $scope.fechaFin2}}).then(function (response) {
                    $scope.turnosemana = response.data;                     
                    seleccionado2 = $scope.turnosemana;                                              
                });  
                $http.get("/rest/estadisticas/turnos/turnosemana",{params: { from: $scope.fechaInicio3, to: $scope.fechaFin3}}).then(function (response) {
                    $scope.turnosemana = response.data;                     
                    seleccionado3 = $scope.turnosemana;
                    $scope.generarSeleccionado3(seleccionado,seleccionado2,seleccionado3);                            
                });          
            };

            $scope.selectGrafico3 = function(item){      
                $scope.muestraSuma  = false;
                $scope.muestraConSuma2 = false;
                $scope.muestraConSuma3 = false;
                $scope.muestraConSuma4 = false;                 
                switch (item) {
                    case 'turnomes':       
                            $scope.fechaFin = getDateFormatedEnd($scope.searchTo);
                            $scope.fechaInicio = $scope.searchFrom;    
                            $scope.fechaFin2 = getDateFormatedEnd($scope.searchTo2);
                            $scope.fechaInicio2 = $scope.searchFrom2;
                            $scope.fechaFin3 = getDateFormatedEnd($scope.searchTo3);
                            $scope.fechaInicio3 = $scope.searchFrom3;                         
                            $scope.muestraSuma = true;                              
                            $scope.muestraConSuma3  = true;
                            $scope.label1='Mes 1';
                            $scope.label2='Mes 2';
                            $scope.label3='Mes 3';  
                            tipos = 'line';                         
                            options = { scales: { yAxes: [{ ticks: { beginAtZero: true, min: 0 } }] } };                                
                            $scope.getTurnoMes3();            
                            break;
                    case 'turnosemana':       
                            $scope.fechaFin = getDateFormatedEnd($scope.searchTo);
                            $scope.fechaInicio = $scope.searchFrom;    
                            $scope.fechaFin2 = getDateFormatedEnd($scope.searchTo2);
                            $scope.fechaInicio2 = $scope.searchFrom2; 
                            $scope.fechaFin3 = getDateFormatedEnd($scope.searchTo3);
                            $scope.fechaInicio3 = $scope.searchFrom3;                          
                            $scope.muestraSuma = true; 
                            $scope.muestraConSuma3  = true; 
                            $scope.label1='Semana 1';
                            $scope.label2='Semana 2';
                            $scope.label3='Semana 3';
                            tipos = 'line';                         
                            options = { scales: { yAxes: [{ ticks: { beginAtZero: true, min: 0 } }] } };                             
                            $scope.getTurnoSemana3();           
                            break;                                       
                    default:
                }
                
            }

            //CASE SELECCION coo cuatro datos
            //OBTENER LISTA DE Turno por sexo por mes
            $scope.getTurnoMes4 = function() {
                $http.get("/rest/estadisticas/turnos/turnomes",{params: { from: $scope.fechaInicio, to: $scope.fechaFin}}).then(function (response) {
                    $scope.turnomes = response.data;                        
                    seleccionado = $scope.turnomes;                                               
                });
                $http.get("/rest/estadisticas/turnos/turnomes",{params: { from: $scope.fechaInicio2, to: $scope.fechaFin2}}).then(function (response) {
                    $scope.turnomes = response.data;                        
                    seleccionado2 = $scope.turnomes;                                            
                });  
                $http.get("/rest/estadisticas/turnos/turnomes",{params: { from: $scope.fechaInicio3, to: $scope.fechaFin3}}).then(function (response) {
                    $scope.turnomes = response.data;                        
                    seleccionado3 = $scope.turnomes;                                            
                });    
                $http.get("/rest/estadisticas/turnos/turnomes",{params: { from: $scope.fechaInicio4, to: $scope.fechaFin4}}).then(function (response) {
                    $scope.turnomes = response.data;                        
                    seleccionado4 = $scope.turnomes;
                    $scope.generarSeleccionado4(seleccionado,seleccionado2,seleccionado3,seleccionado4);                           
                });            
            };  

            //OBTENER LISTA DE Turno por sexo por mes
            $scope.getTurnoSemana4 = function() {
                $http.get("/rest/estadisticas/turnos/turnosemana",{params: { from: $scope.fechaInicio, to: $scope.fechaFin}}).then(function (response) {
                    $scope.turnosemana = response.data;                     
                    seleccionado = $scope.turnosemana;                                             
                });     
                $http.get("/rest/estadisticas/turnos/turnosemana",{params: { from: $scope.fechaInicio2, to: $scope.fechaFin2}}).then(function (response) {
                    $scope.turnosemana = response.data;                     
                    seleccionado2 = $scope.turnosemana;                                              
                });  
                $http.get("/rest/estadisticas/turnos/turnosemana",{params: { from: $scope.fechaInicio3, to: $scope.fechaFin3}}).then(function (response) {
                    $scope.turnosemana = response.data;                     
                    seleccionado3 = $scope.turnosemana;                                               
                });        
                $http.get("/rest/estadisticas/turnos/turnosemana",{params: { from: $scope.fechaInicio4, to: $scope.fechaFin4}}).then(function (response) {
                    $scope.turnosemana = response.data;                     
                    seleccionado4 = $scope.turnosemana;
                    $scope.generarSeleccionado4(seleccionado,seleccionado2,seleccionado3,seleccionado4);                             
                });    
            };

            $scope.selectGrafico4 = function(item){      
                $scope.muestraSuma  = false;
                $scope.muestraConSuma2 = false;
                $scope.muestraConSuma3 = false;
                $scope.muestraConSuma4 = false;                 
                switch (item) {
                    case 'turnomes':       
                            $scope.fechaFin = getDateFormatedEnd($scope.searchTo);
                            $scope.fechaInicio = $scope.searchFrom;    
                            $scope.fechaFin2 = getDateFormatedEnd($scope.searchTo2);
                            $scope.fechaInicio2 = $scope.searchFrom2;
                            $scope.fechaFin3 = getDateFormatedEnd($scope.searchTo3);
                            $scope.fechaInicio3 = $scope.searchFrom3;  
                            $scope.fechaFin4 = getDateFormatedEnd($scope.searchTo4);
                            $scope.fechaInicio4 = $scope.searchFrom4;                       
                            $scope.muestraSuma = true;                              
                            $scope.muestraConSuma4  = true;  
                            $scope.label1='Mes 1';
                            $scope.label2='Mes 2';
                            $scope.label3='Mes 3';
                            $scope.label4='Mes 4';
                            tipos = 'line';                         
                            options = { scales: { yAxes: [{ ticks: { beginAtZero: true, min: 0 } }] } };                                
                            $scope.getTurnoMes4();            
                            break;
                    case 'turnosemana':       
                            $scope.fechaFin = getDateFormatedEnd($scope.searchTo);
                            $scope.fechaInicio = $scope.searchFrom;    
                            $scope.fechaFin2 = getDateFormatedEnd($scope.searchTo2);
                            $scope.fechaInicio2 = $scope.searchFrom2; 
                            $scope.fechaFin3 = getDateFormatedEnd($scope.searchTo3);
                            $scope.fechaInicio3 = $scope.searchFrom3; 
                            $scope.fechaFin4 = getDateFormatedEnd($scope.searchTo4);
                            $scope.fechaInicio4 = $scope.searchFrom4;                         
                            $scope.muestraSuma = true; 
                            $scope.muestraConSuma4  = true; 
                            $scope.label1='Semana 1';
                            $scope.label2='Semana 2';
                            $scope.label3='Semana 3';
                            $scope.label4='Semana 4';
                            tipos = 'line';                         
                            options = { scales: { yAxes: [{ ticks: { beginAtZero: true, min: 0 } }] } };                             
                            $scope.getTurnoSemana4();           
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
                arregloColumnaBar1 = [] ;
                arregloLabelBar1 = [] ;
                arregloDataBar1 = [] ;   
                arregloColumnaBar2 = [] ;
                arregloLabelBar2 = [] ;
                arregloDataBar2 = [] ;   
                arregloColumnaBar3 = [] ;
                arregloLabelBar3 = [] ;
                arregloDataBar3 = [] ;  		    	

				for (var key in seleccionado) {
    				arregloColumnaBar.push(key);
    				arregloDataBar.push(seleccionado[key]);
				}
				arregloLabelBar = 'Total' ;		        
		        grafico1.destroy();
		        $scope.generarGraficoBarra1();
		    }		
			  

		
	   // GENERAR GRAFICOS DE BARRA
			$scope.generarGraficoBarra1 = function(){					
				data1 = { labels: arregloColumnaBar, datasets: [{ label: arregloLabelBar, backgroundColor: color11, borderColor: color14, borderWidth: 2, hoverBackgroundColor: color14, hoverBorderColor: color14, data: arregloDataBar}]}; 
			    grafico1 = new Chart(ctx1, { type: tipos, data: data1, options: options	});
			}

            $scope.generarSeleccionado2 = function(seleccionado,seleccionado2){
                arregloColumnaBar = [] ;
                arregloLabelBar = [] ;
                arregloDataBar = [] ;
                arregloColumnaBar1 = [] ;
                arregloLabelBar1 = [] ;
                arregloDataBar1 = [] ;   
                arregloColumnaBar2 = [] ;
                arregloLabelBar2 = [] ;
                arregloDataBar2 = [] ;   
                arregloColumnaBar3 = [] ;
                arregloLabelBar3 = [] ;
                arregloDataBar3 = [] ;                  

                for (var key in seleccionado) {
                    arregloColumnaBar.push(key);
                    arregloDataBar.push(seleccionado[key]);
                }
                for (var key in seleccionado2) {
                    arregloColumnaBar1.push(key);
                    arregloDataBar1.push(seleccionado2[key]);
                }
                arregloLabelBar = 'Total' ;             
                grafico1.destroy();
                $scope.generarGraficoBarra2();
            }

            $scope.generarGraficoBarra2 = function() {    

                grafico1 = new Chart(ctx1, {
                    type: 'line',
                    data: {
                        datasets: [{
                            data: arregloDataBar,
                            label: $scope.label1,
                            // This binds the dataset to the left y axis
                            
                            backgroundColor: color11, 
                            borderColor: color14
                        }, {
                            data: arregloDataBar1,
                            label: $scope.label2,
                            // This binds the dataset to the right y axis
                            
                            backgroundColor: color21, 
                            borderColor: color24
                        }],
                    labels: arregloColumnaBar
                    },
                    options: options
                });
            }

            $scope.generarSeleccionado3 = function(seleccionado,seleccionado2,seleccionado3){
                arregloColumnaBar = [] ;
                arregloLabelBar = [] ;
                arregloDataBar = [] ;
                arregloColumnaBar1 = [] ;
                arregloLabelBar1 = [] ;
                arregloDataBar1 = [] ;   
                arregloColumnaBar2 = [] ;
                arregloLabelBar2 = [] ;
                arregloDataBar2 = [] ;   
                arregloColumnaBar3 = [] ;
                arregloLabelBar3 = [] ;
                arregloDataBar3 = [] ;                  

                for (var key in seleccionado) {
                    arregloColumnaBar.push(key);
                    arregloDataBar.push(seleccionado[key]);
                }
                for (var key in seleccionado2) {
                    arregloColumnaBar1.push(key);
                    arregloDataBar1.push(seleccionado2[key]);
                }
                for (var key in seleccionado3) {
                    arregloColumnaBar2.push(key);
                    arregloDataBar2.push(seleccionado3[key]);
                }
                arregloLabelBar = 'Total' ;             
                grafico1.destroy();
                $scope.generarGraficoBarra3();
            }

            $scope.generarGraficoBarra3 = function() {    

                grafico1 = new Chart(ctx1, {
                    type: 'line',
                    data: {
                        datasets: [{
                            data: arregloDataBar,
                            label: $scope.label1,
                            // This binds the dataset to the left y axis
                            
                            backgroundColor: color11, 
                            borderColor: color14
                        }, {
                            data: arregloDataBar1,
                            label: $scope.label2,
                            // This binds the dataset to the right y axis
                            
                            backgroundColor: color21, 
                            borderColor: color24
                        }, {
                            data: arregloDataBar2,
                            label: $scope.label3,
                            // This binds the dataset to the right y axis
                            
                            backgroundColor: color31, 
                            borderColor: color34
                        }],
                    labels: arregloColumnaBar
                    },
                    options: options
                });
            }

            $scope.generarSeleccionado4 = function(seleccionado,seleccionado2,seleccionado3,seleccionado4){
                arregloColumnaBar = [] ;
                arregloLabelBar = [] ;
                arregloDataBar = [] ;
                arregloColumnaBar1 = [] ;
                arregloLabelBar1 = [] ;
                arregloDataBar1 = [] ;   
                arregloColumnaBar2 = [] ;
                arregloLabelBar2 = [] ;
                arregloDataBar2 = [] ;   
                arregloColumnaBar3 = [] ;
                arregloLabelBar3 = [] ;
                arregloDataBar3 = [] ;                  

                for (var key in seleccionado) {
                    arregloColumnaBar.push(key);
                    arregloDataBar.push(seleccionado[key]);
                }
                for (var key in seleccionado2) {
                    arregloColumnaBar1.push(key);
                    arregloDataBar1.push(seleccionado2[key]);
                }
                for (var key in seleccionado3) {
                    arregloColumnaBar2.push(key);
                    arregloDataBar2.push(seleccionado3[key]);
                }
                for (var key in seleccionado4) {
                    arregloColumnaBar3.push(key);
                    arregloDataBar3.push(seleccionado4[key]);
                }
                arregloLabelBar = 'Total' ;             
                grafico1.destroy();
                $scope.generarGraficoBarra4();
            }

            $scope.generarGraficoBarra4 = function() {    

                grafico1 = new Chart(ctx1, {
                    type: 'line',
                    data: {
                        datasets: [{
                            data: arregloDataBar,
                            label: $scope.label1,
                            // This binds the dataset to the left y axis
                            
                            backgroundColor: color11, 
                            borderColor: color14
                        }, {
                            data: arregloDataBar1,
                            label: $scope.label2,
                            // This binds the dataset to the right y axis
                            
                            backgroundColor: color21, 
                            borderColor: color24
                        }, {
                            data: arregloDataBar2,
                            label: $scope.label3,
                            // This binds the dataset to the right y axis
                            
                            backgroundColor: color31, 
                            borderColor: color34
                        }, {
                            data: arregloDataBar3,
                            label: $scope.label4,
                            // This binds the dataset to the right y axis
                            
                            backgroundColor: color41, 
                            borderColor: color44
                        }],
                    labels: arregloColumnaBar
                    },
                    options: options
                });
            }

            $scope.generarSeleccionadoConTresDatos = function() {   

                arregloColumnaBar = [] ;
                arregloLabelBar = [] ;
                arregloDataBar = [] ;               
                arregloColumnaBar1 = [] ;
                arregloLabelBar1 = [] ;
                arregloDataBar1 = [] ;   
                arregloColumnaBar2 = [] ;
                arregloLabelBar2 = [] ;
                arregloDataBar2 = [] ;   
                arregloColumnaBar3 = [] ;
                arregloLabelBar3 = [] ;
                arregloDataBar3 = [] ;   

                lista1 = seleccionado[0];
                lista2 = seleccionado[1];
                lista3 = seleccionado[2];

                for (var key in lista1) {
                    arregloColumnaBar1.push(key);
                    arregloDataBar1.push(lista1[key]);
                }

                for (var key in lista2) {
                    arregloColumnaBar2.push(key);
                    arregloDataBar2.push(lista2[key]);
                }

                for (var key in lista3) {
                    arregloColumnaBar3.push(key);
                    arregloDataBar3.push(lista3[key]);
                }

                arregloLabelBar = 'Total' ;             
                grafico1.destroy();  

                grafico1 = new Chart(ctx1, {
                    type: 'bar',
                    data: {
                        datasets: [{
                            label: 'Mujer',
                            data: arregloDataBar3,
                            backgroundColor: color21, 
                            borderColor: color24

                        }, {
                            label: 'Hombre',
                            data: arregloDataBar2,
                            backgroundColor: color11, 
                            borderColor: color14

                        }, {
                            label: 'Total',
                            data: arregloDataBar1,          
                            backgroundColor: color31,
                            borderColor: color34,                            
                            type: 'line'
                        }],
                        labels: arregloColumnaBar1
                    },
                    options: options
                });
            }
 
		//OPTIONS
		    var options = '';
		    var tipos = 'bar';

		    var color11 = 'rgba(41,41,97,0.1)';
		    var color14 = 'rgba(41,41,97,0.4)';
            var color21 = 'rgba(255,99,132,0.1)';
            var color24 = 'rgba(255,99,132,0.4)';
            var color31 = 'rgba(70,191,189,0.1)';
            var color34 = 'rgba(70,191,189,0.4)';
            var color41 = 'rgba(162,183,58,0.1)';
            var color44 = 'rgba(162,183,58,0.4)';
      
		var canvas1 = document.getElementById("grafico1");
		var ctx1 = canvas1.getContext('2d');
			    
		var arregloColumnaBar = [] ;
		var arregloLabelBar = [] ;
		var arregloDataBar = [] ;
        var arregloColumnaBar1 = [] ;
        var arregloLabelBar1 = [] ;
        var arregloDataBar1 = [] ;   
        var arregloColumnaBar2 = [] ;
        var arregloLabelBar2 = [] ;
        var arregloDataBar2 = [] ;   
        var arregloColumnaBar3 = [] ;
        var arregloLabelBar3 = [] ;
        var arregloDataBar3 = [] ;  
		$scope.muestra = false;
        $scope.muestraSuma = false;
        $scope.muestraSuma2 = false; 
        $scope.muestraConSuma2 = false;
        $scope.muestraConSuma3 = false;
        $scope.muestraConSuma4 = false;
		$scope.generarGraficoBarra1();	
        let date = new Date();
        $scope.searchFrom = new Date(date.getFullYear(), date.getMonth(), 1);
        $scope.searchTo = new Date(date.getFullYear(), date.getMonth() + 1, 0); 
        $scope.searchFrom2 = new Date(date.getFullYear(), date.getMonth(), 1);
        $scope.searchTo2 = new Date(date.getFullYear(), date.getMonth() + 1, 0); 
        $scope.searchFrom3 = new Date(date.getFullYear(), date.getMonth(), 1);
        $scope.searchTo3 = new Date(date.getFullYear(), date.getMonth() + 1, 0); 
        $scope.searchFrom4 = new Date(date.getFullYear(), date.getMonth(), 1);
        $scope.searchTo4 = new Date(date.getFullYear(), date.getMonth() + 1, 0); 
	    $scope.fechaInicio = getToday();
	    $scope.fechaFin = getToday();
		$scope.getClientesCiudad();
		$scope.getClientesSexo();

});