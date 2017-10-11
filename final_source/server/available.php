<?php  
 
$con = mysql_connect("211.44.136.164:3306","admin","password");

if(!$con)
{
	die('do not connect:'.mysql_errot());
}   
else
{
mysql_select_db("available");
}
$sql = "select *from device";

$return_array =array();
$result = mysql_query($sql);

while ($row = mysql_fetch_array($result, MYSQL_ASSOC))
{
	  $row_array['device'] = $row['pot_device'];
  array_push($return_array, $row_array);}

echo json_encode(array("device_response"=>$return_array));
mysql_free_result($result);

mysql_close($con);

?>