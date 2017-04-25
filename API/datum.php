$datum = date("c");<br />
<?php



$datum = date("c"); //ISO 8601 date

var_dump($datum);

?>
<hr />

$datum = new DateTime();<br />
<?php
$datum = new DateTime();
var_dump($datum);

?>

<hr />

$datum = date("U");<br />
<?php
$datum = date("U");//unix timestamp
var_dump($datum); 

