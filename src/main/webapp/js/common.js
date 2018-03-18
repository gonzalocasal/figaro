function getToday(){
	var d = new Date(),
	month = '' + (d.getMonth() + 1),
	day = '' + d.getDate(),
	year = d.getFullYear();	
	if (month.length < 2) month = '0' + month;
	if (day.length < 2) day = '0' + day;	
	return [year, month, day].join('-');
}

function getStringMonth(date){
	month = '' + (date.getMonth() + 1),
	day = ''
	year = date.getFullYear();
	if (month.length < 2) month = '0' + month;	
	return [year, month, day].join('-');

}

function getStringDate(date){
	month = '' + (date.getMonth() + 1),
	day = '' + date.getDate(),
	year = date.getFullYear();
	if (month.length < 2) month = '0' + month;
	if (day.length < 2) day = '0' + day;
	return [year, month, day].join('-');

}

function stringToDate(date){
	return new Date(date + " 00:00:00");
}

function stringToDateEnd(date){
	return new Date(date + " 23:59:00");
}

function getDateFormated(){
	return stringToDate(getToday());
}

function getDateFormatedEnd(date){
	fecha = getStringDate(date);
	return stringToDateEnd(fecha);
}

function getSemana(date){
	var dStart = new Date(date);
	var dEnd = new Date(date);
	var semana = new Date();
	dStart.setDate(dStart.getDate() - 3);
	dEnd.setDate(dEnd.getDate() + 3);
	
	semana.dStart = dStart;
	semana.dEnd = dEnd;
	
	return semana;
}


function getDayOfWeek(){
	var d = new Date();
	return d.getDay() + 1;
}

function getMes(date){
	var mes = {};
	mes.dStart = new Date(date.getFullYear(), date.getMonth(), 1);
	mes.dEnd = new Date(date.getFullYear(), date.getMonth() + 1, 0);
	
	return mes;
	
}

function openModal(modal){
	$('#'+modal).addClass("modal-on");
	$('#content').addClass("blur");
	$('#menu').addClass("blur");
	$('#'+modal+'-focus').focus();
};

function closeModal(modal){
	$('#'+modal).removeClass("modal-on");
	$('#'+modal).removeClass("modal-on-top");
	$('#content').removeClass("blur");
	$('#menu').removeClass("blur");
};

function logout(){
	$('#logout').addEventListener("click", function () {
		$('#logout').submit();
	});
};


function loadComplete(loaded){
	$('.loading').hide();
	$('.page-loading').hide();
	$('.ver-mas').show();
	loaded = true;
}


function loading(){
	$('.loading').show();
	$('.ver-mas').hide();
}

function loadingTurnos(){
	$('.loading-turnos').show();
}

function loadTurnosComplete(){
	$('.loading-turnos').hide();
}


var app = angular.module('figaro', []);