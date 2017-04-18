<?php
include_once 'konfiguracija.php';

$sada = new DateTime('now', new DateTimeZone('Asia/Jakarta'));

$veza->query("SET time_zone = '". $sada->format("P") ."'");


try {
    $izraz = $veza->prepare("select datumvrijeme, datumvrijemetimezone from podatak limit 1");
	$izraz->execute();		
	$rez = $izraz->fetch(PDO::FETCH_OBJ);
	
	$datumvrijemetimezone = new DateTime($rez->datumvrijemetimezone,new DateTimeZone('Asia/Jakarta'));
	$datumvrijeme = new DateTime($rez->datumvrijeme);
	
	
	$instanca = new stdClass();

	$instanca->instancaObjektaDateTime = $datumvrijemetimezone;
	$instanca->datumRCF3339 = date_format($datumvrijeme,"Y-m-d\TH:i:sP"); // RFC 3339 is listed as a profile of ISO 8601
	$instanca->datumRFC2822 = date_format($datumvrijeme,"r");
	$instanca->datumISO8601 = $datumvrijemetimezone->format("c") ; 
	$instanca->unixEpochTime = date_format($datumvrijeme,"U");
	
	echo json_encode($instanca);
	
	
} catch (Exception $e) {
    print_r($e);
}


