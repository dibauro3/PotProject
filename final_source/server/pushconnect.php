<?php
$servername = "211.44.136.164:3306"; //replace it with your database server name
$username = "admin";  //replace it with your database username
$password = "password";  //replace it with your database password
$dbname = "fcm";
// Create connection
$conn = mysql_connect($servername,$username,$password) or die ("db connection falie");
mysql_select_db($dbname,$conn);