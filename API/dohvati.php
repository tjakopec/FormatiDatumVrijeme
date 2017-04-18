<?php
include_once 'konfiguracija.php';


$timezone= isset($_GET["timezone"]) ? $_GET["timezone"] : "Europe/Zagreb";

$sada = new DateTime('0000-00-00', new DateTimeZone($timezone)); //umjesto 0000-00-00 sam imao now - prijer s neta i dodavao je +2, izgubio sat vremena
//što je najgore, taj $sada mi ne treba zbog ničeg drugog već zbog postavke na bazu koji je timezone
$veza->query("SET time_zone = '". $sada->format("P") ."'");

//echo $sada->format("P");
try {
    $izraz = $veza->prepare("select datumvrijeme, datumvrijemetimezone from podatak order by sifra desc limit 1");
	$izraz->execute();		
	$rez = $izraz->fetch(PDO::FETCH_OBJ);
	
	$datumvrijemetimezone = new DateTime($rez->datumvrijemetimezone,new DateTimeZone($timezone));
	$datumvrijeme = new DateTime($rez->datumvrijeme,new DateTimeZone($timezone));
	
	
	$instanca = new stdClass();

	$datumvrijemeInstanca = new stdClass();
	$datumvrijemeInstanca->instancaObjektaDateTime = $datumvrijeme;
	$datumvrijemeInstanca->datumRFC2822 = date_format($datumvrijeme,"r");
	$datumvrijemeInstanca->datumISO8601 = $datumvrijeme->format("c") ; 
	$datumvrijemeInstanca->datumRCF3339 = date_format($datumvrijeme,"Y-m-d\TH:i:sP"); // RFC 3339 is listed as a profile of ISO 8601
	$datumvrijemeInstanca->unixEpochTime = date_format($datumvrijeme,"U");
	
	$instanca->datumvrijeme = $datumvrijemeInstanca;
	
	
	
	
	
	$datumvrijemetimezoneInstanca = new stdClass();
	$datumvrijemetimezoneInstanca->instancaObjektaDateTime = $datumvrijemetimezone;
	
	$datumvrijemetimezoneInstanca->datumRFC2822 = date_format($datumvrijemetimezone,"r");
	$datumvrijemetimezoneInstanca->datumISO8601 = $datumvrijemetimezone->format("c") ; 
	$datumvrijemetimezoneInstanca->datumRCF3339 = date_format($datumvrijemetimezone,"Y-m-d\TH:i:sP"); // RFC 3339 is listed as a profile of ISO 8601
	$datumvrijemetimezoneInstanca->unixEpochTime = date_format($datumvrijemetimezone,"U");
	
	$instanca->datumvrijemetimezone = $datumvrijemetimezoneInstanca;

	
	
} catch (Exception $e) {
    print_r($e);
}


