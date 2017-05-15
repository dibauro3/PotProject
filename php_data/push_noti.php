<?php

$ch = curl_init("https://fcm.googleapis.com/fcm/send");
$header=array('Content-Type: application/json',"Authorization: key=");
curl_setopt($ch, CURLOPT_HTTPHEADER, $header);
curl_setopt( $ch,CURLOPT_SSL_VERIFYPEER, false );
curl_setopt($ch, CURLOPT_POST, 1);
curl_setopt($ch, CURLOPT_POSTFIELDS, "{ \"notification\": {    \"title\": \"TEST\",    \"text\": \"Not enough! re-pill water plz! \"}, \"to\" : 
\"Token_id\"}");
curl_exec($ch);
curl_close($ch);
?>
