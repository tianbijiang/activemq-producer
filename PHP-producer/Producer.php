<?php

class Producer {
  public $id;
  public $stomp;
    private $queue = '/queue/AUTO_ANNOTATOR';
    private $stomp_url = 'tcp://10.250.10.140:61613';

  public function __construct($i) {
    $this->id = $i;
  }

  public function sendMessage($message) {
    $this->stomp->send($this->queue, $message);
    echo $message."\n";
  }

  public function tryConnecting() {
    try {
        $this->stomp = new Stomp($this->stomp_url);
    } catch(StompException $e) {
      die('Connection failed: ' . $e->getMessage());
    }
  }

  public function cleanUp() {
    unset($this->stomp);
  }
}

?>