<?php

 $user = $_POST['user'];
 $device = $_POST['pot_number'];
 $plant = $_POST['pot_plant'];


$servername = "211.44.136.164:3306";
$username = "admin";
$password = "password";
$dbname = "device";
// Create connection

$conn = mysql_connect($servername,$username,$password) or die ("db connection falie");
mysql_select_db($dbname,$conn);

 $sql = "INSERT INTO pot_number VALUES('DEFULT','$user','$device','$plant')";

if(mysql_query($sql)){
 echo 'successfully device registered';
 }else{
 echo 'oops! Please try again!';
  }
 

?>
