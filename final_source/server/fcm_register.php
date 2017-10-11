<?php

	if(isset($_POST["Token"])){

		$token = $_POST["Token"];
		require "pushconnect.php";
		$query = "INSERT INTO register Values (DEFAULT,'$token') ON DUPLICATE KEY UPDATE Token = '$token'; ";
		
if(mysql_query($query))
{
echo "<h3> Data insertion Success..</h3>";
}
else
{
echo " Data insertion failed.." .mysql_error($conn);
}

		mysqli_close($conn);
	}
?>