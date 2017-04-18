<?php
include_once 'konfiguracija.php';

$sada = new DateTime('now', new DateTimeZone('Asia/Jakarta'));

$veza->query("SET time_zone = '". $sada->format("P") ."'");


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


