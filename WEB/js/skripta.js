"use strict";

var putanjaAPIucitaj = "../API/dohvatiJSON.php";
var putanjaAPIposalji = "../API/insertJSON.php";

//koristim http://momentjs.com/
var currentTimezone = jstz.determine();
var timezone = currentTimezone.name();

function ucitajSServera(){
	jQuery.ajax ({
	    url: putanjaAPIucitaj + "?timezone=" + timezone,
	    type: "GET",
	    success: function(vratioServer){
	    	var instancaObjekta = JSON.parse(vratioServer);
	    	var datum = new Date(instancaObjekta.datumISO8601);
	    	$('#datepicker').datepicker('setDate', datum);
	    	var sati = datum.getHours();
	    	if(sati<10){
	    		sati = "0" + sati;
	    	}
	    	var minute = datum.getMinutes();
	    	if(minute<10){
	    		minute = "0" + minute;
	    	}
	    	$("#input-a").val(sati + ":" + minute);
	       // console.log(datum);
	    }
	});
}


//pozvano od afterDone na  clockpicker
function posaljiNaServer(){

	var d = $("#datepicker").datepicker( "getDate" );
	var s = dateAdd(d, 'hour', $("#input-a").val().split(":")[0]);
	var m = dateAdd(s, 'minute', $("#input-a").val().split(":")[1]);

	//moram se riješiti offseta jer ostaje +2. Slanjem timezone stringa na server osiguravam ispravan rad
	var userTimezoneOffset = m.getTimezoneOffset() * (-60000);
	var datumZaServer = new Date(m.getTime() + userTimezoneOffset);
	
	var jsonPodaci ={timezone: timezone, datum: datumZaServer.toISOString(), klijent: "javascript"};
	
	jQuery.ajax ({
	    url: putanjaAPIposalji,
	    type: "POST",
	    data: JSON.stringify(jsonPodaci),
	    dataType: "json",
	    contentType: "application/json; charset=utf-8",
	    success: function(){
	        console.log("OK");
	    }
	});
}

$("#ucitaj").click(function(){
	ucitajSServera();
	return false;
});

//https://jqueryui.com/datepicker/
$.datepicker.setDefaults({
	closeText: "Zatvori",
	prevText: "&#x3C;",
	nextText: "&#x3E;",
	currentText: "Danas",
	monthNames: [ "Siječanj","Veljača","Ožujak","Travanj","Svibanj","Lipanj",
	"Srpanj","Kolovoz","Rujan","Listopad","Studeni","Prosinac" ],
	monthNamesShort: [ "Sij","Velj","Ožu","Tra","Svi","Lip",
	"Srp","Kol","Ruj","Lis","Stu","Pro" ],
	dayNames: [ "Nedjelja","Ponedjeljak","Utorak","Srijeda","Četvrtak","Petak","Subota" ],
	dayNamesShort: [ "Ned","Pon","Uto","Sri","Čet","Pet","Sub" ],
	dayNamesMin: [ "Ne","Po","Ut","Sr","Če","Pe","Su" ],
	weekHeader: "Tje",
	dateFormat: "dd.mm.yy.",
	firstDay: 1,
	isRTL: false,
	showMonthAfterYear: false,
	yearSuffix: "" });

$( "#datepicker" ).datepicker({
	inline: true
});



//https://weareoutman.github.io/clockpicker/
var input = $('#input-a');
input.clockpicker({
    autoclose: true,
    afterDone: function() {
		posaljiNaServer();
    }
});
$('#button-a').click(function(e){
    e.stopPropagation();
    input.clockpicker('show')
            .clockpicker('toggleView', 'minutes');
});
$('#button-b').click(function(e){
    e.stopPropagation();
    input.clockpicker('show')
            .clockpicker('toggleView', 'hours');
});


//http://jsfiddle.net/pvkovalev/7mg9audg/
function dateAdd(date, interval, units) {
  var ret = new Date(date); //don't change original date
  var checkRollover = function() { if(ret.getDate() != date.getDate()) ret.setDate(0);};
  switch(interval.toLowerCase()) {
    case 'year'   :  ret.setFullYear(ret.getFullYear() + units); checkRollover();  break;
    case 'quarter':  ret.setMonth(ret.getMonth() + 3*units); checkRollover();  break;
    case 'month'  :  ret.setMonth(ret.getMonth() + units); checkRollover();  break;
    case 'week'   :  ret.setDate(ret.getDate() + 7*units);  break;
    case 'day'    :  ret.setDate(ret.getDate() + units);  break;
    case 'hour'   :  ret.setTime(ret.getTime() + units*3600000);  break;
    case 'minute' :  ret.setTime(ret.getTime() + units*60000);  break;
    case 'second' :  ret.setTime(ret.getTime() + units*1000);  break;
    default       :  ret = undefined;  break;
  }
  return ret;
}