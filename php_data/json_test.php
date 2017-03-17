<?php  
 
$con = mysql_connect("localhost","root","");

if(!$con)
{
	die('do not connect:'.mysql_errot());
}   
else
{
mysql_select_db("plant_db");
}
$sql = "select *from plant_info";

$return_array =array();
$result = mysql_query($sql);

while ($row = mysql_fetch_array($result, MYSQL_ASSOC))
{
	  $row_array['name'] = $row['name'];
  $row_array['water'] = $row['water'];
$row_array['sun'] = $row['sun'];
  array_push($return_array, $row_array);}

echo json_encode(array("server_response"=>$return_array));
mysql_free_result($result);

mysql_close($con);

?>