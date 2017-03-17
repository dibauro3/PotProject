<?php
$servername = "localhost"; //replace it with your database server name
$username = "root";  //replace it with your database username
$password = "";  //replace it with your database password
$dbname = "user_register";
// Create connection
$conn = mysql_connect($servername,$username,$password) or die ("db connection falie");
mysql_select_db($dbname,$conn);