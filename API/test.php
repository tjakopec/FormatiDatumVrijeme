<?php

$vrijeme = DateTime::createFromFormat(DateTime::ISO8601, "1982-02-12T22:24:31+01:00");

$vrijeme -> setTimeZone("Europe/Zagreb");


print_r($vrijeme);

$sada = new DateTime("1982-02-12T22:24:31", new DateTimeZone("Europe/Zagreb"));

print_r($sada);