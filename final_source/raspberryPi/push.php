<?php
$ch = curl_init("https://fcm.googleapis.com/fcm/send");
$message = $argv[1];
$header=array('Content-Type: application/json',
"Authorization: key=AAAAOoqb7mo:APA91bGbT-Aqr4h2gMBBm60ljWZy1qoTZmr8BkTSi0mhiJ7canHXlDaK4gZeaCLDstvLz-eGKqhZA4cxrH-mWy_GmuLBZFJPsvRD5e4Za11oVpu6JU79_NYsVY0Z4AG76QPWtwxjp4Ad");
curl_setopt($ch, CURLOPT_HTTPHEADER, $header);
curl_setopt( $ch,CURLOPT_SSL_VERIFYPEER, false );

curl_setopt($ch, CURLOPT_POST, 1);
if($message==1){
	curl_setopt($ch, CURLOPT_POSTFIELDS, "{ \"notification\": {    \"title\": \"MYPOT\",    \"text\": \"Not enough! re-pill water plz! :(\"  },    \"to\" : \"fvSl4TjBkxA:APA91bF4ooQMY9tCCMItYCjkbWwPQZj_ARF9Gkam0O1DqQLMGB1WzNn-Xg4VfYD6riu7K5DZXyB4b5warkzBbNPRBdjP-vFgw7YEYjBY6DF5kkI_zK0zqnM-qehKp0Mt2B2vxKj2B2fI\"}");
} else if($message==2){
	curl_setopt($ch, CURLOPT_POSTFIELDS, "{ \"notification\": {    \"title\": \"MYPOT\",    \"text\": \"MYPOT is watering now! :)\"  },    \"to\" : \"fvSl4TjBkxA:APA91bF4ooQMY9tCCMItYCjkbWwPQZj_ARF9Gkam0O1DqQLMGB1WzNn-Xg4VfYD6riu7K5DZXyB4b5warkzBbNPRBdjP-vFgw7YEYjBY6DF5kkI_zK0zqnM-qehKp0Mt2B2vxKj2B2fI\"}");
}

curl_exec($ch);
curl_close($ch);
?>

