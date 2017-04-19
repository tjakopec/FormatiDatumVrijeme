<?php
include_once 'konfiguracija.php';

$inputJSON = file_get_contents('php://input');
$primio = (object)json_decode($inputJSON, TRUE);

$vrijeme = new DateTime($primio->datum, new DateTimeZone($primio->timezone));//DateTime::createFromFormat(DateTime::ISO8601, $primio->datum);

//samo za postavljanje na bazi
$sada = new DateTime('0000-00-00', new DateTimeZone($primio->timezone));
$veza->query("SET time_zone = '". $sada->format("P") ."'");

try {
    $izraz = $veza->prepare("insert into podatak
    											(opis, samodatum, vrijeme, datumvrijeme,datumvrijemetimezone) 			
    								values 		(:opis, :samodatum, :vrijeme, :datumvrijeme, :datumvrijemetimezone)");
	$parametri=array(
					"opis"					=>	$primio->klijent,
					"samodatum"				=> 	$vrijeme->format("Y-m-d"),
					"vrijeme"				=> 	$vrijeme->format("H:i:s"),
					"datumvrijeme" 			=> 	$vrijeme->format("Y-m-d H:i:s"),
					"datumvrijemetimezone"	=> 	$vrijeme->format("Y-m-d H:i:s")
					);
							
	$izraz->execute($parametri);
} catch (Exception $e) {
    print_r($e);
}


