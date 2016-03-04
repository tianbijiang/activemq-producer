<?php

include ("Producer.php");

$producer = new Producer(1);

$producer->tryConnecting();

for($k = 0; $k < 10000; $k++) {
	$producer->sendMessage("haha");
}

$producer->cleanUp();

?>