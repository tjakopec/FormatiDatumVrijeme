<?php
$veza = new PDO('mysql:host=localhost;dbname=fz2017', 
				 'root', 
				 '000000', 
				 array(
				 PDO::MYSQL_ATTR_INIT_COMMAND =>"SET NAMES utf8;", //SET time_zone... pvdje baca mysql 2014 error
				 PDO::ATTR_ERRMODE=> PDO::ERRMODE_EXCEPTION,
				 PDO::ATTR_EMULATE_PREPARES=> false
				 		)		
 				);
