<?php
include_once 'konfiguracija.php';


$timezone= isset($_GET["timezone"]) ? $_GET["timezone"] : "Europe/Zagreb";

$sada = new DateTime('0000-00-00', new DateTimeZone($timezone));

$veza->query("SET time_zone = '". $sada->format("P") ."'");

echo $sada->format("P");
try {
    $izraz = $veza->prepare("select datumvrijeme, datumvrijemetimezone from podatak order by sifra desc limit 1");
	$izraz->execute();		
	$rez = $izraz->fetch(PDO::FETCH_OBJ);
	
	$datumvrijemetimezone = new DateTime($rez->datumvrijemetimezone,new DateTimeZone($timezone));
	$datumvrijeme = new DateTime($rez->datumvrijeme,new DateTimeZone($timezone));
	
	print_r($datumvrijemetimezone);
	echo "<hr />";
	print_r($datumvrijeme);
	
	
} catch (Exception $e) {
    print_r($e);
}


