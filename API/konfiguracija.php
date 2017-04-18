<?php
$veza = new PDO('mysql:host=localhost;dbname=fz2017', 
				 'fz2017', 
				 'fz2017', 
				 array(
				 PDO::MYSQL_ATTR_INIT_COMMAND =>"SET NAMES utf8;", //SET time_zone... ovdje baca mysql 2014 error
				 PDO::ATTR_ERRMODE=> PDO::ERRMODE_EXCEPTION,
				 PDO::ATTR_EMULATE_PREPARES=> false
				 		)		
 				);

 				
 				#baza problem timezone
 				#SET time_zone = '+00:00';
 				#SET GLOBAL time_zone = '+00:00';
 				