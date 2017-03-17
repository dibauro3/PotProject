<?php
require "test2init.php";
$name = $_POST["name"];
$email = $_POST["email"];
$uid = $_POST["uid"];

$sql_query = "insert into user_info values('$name','$email','$uid');";

if(mysql_query($sql_query))
{
echo "<h3> Data insertion Success..</h3>";
}
else
{
echo " Data insertion failed.." .mysql_error($conn);
}

?>