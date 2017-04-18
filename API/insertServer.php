<?php
include_once 'konfiguracija.php';

$sada = new DateTime('0000-00-00', new DateTimeZone("Europe/Zagreb"));
$veza->query("SET time_zone = '". $sada->format("P") ."'");


$sada = new DateTime(date(), new DateTimeZone("Europe/Zagreb"));

try {
    $izraz = $veza->prepare("insert into podatak
    											(opis, samodatum, vrijeme, datumvrijeme,datumvrijemetimezone) 			
    								values 		(:opis, :samodatum, :vrijeme, :datumvrijeme, :datumvrijemetimezone)");
	$parametri=array(
					"opis"					=>	"Server insert",
					"samodatum"				=> 	$sada->format("Y-m-d"),
					"vrijeme"				=> 	$sada->format("H:i:s"),
					"datumvrijeme" 			=> 	$sada->format("Y-m-d H:i:s"),
					"datumvrijemetimezone"	=> 	$sada->format("Y-m-d H:i:s")
					);
							
	$izraz->execute($parametri);
	
} catch (Exception $e) {
    print_r($e);
}


