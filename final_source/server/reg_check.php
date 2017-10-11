<?php  
 
$con = mysql_connect("211.44.136.164:3306","admin","password");

if(!$con)
{
	die('do not connect:'.mysql_errot());
}   
else
{
mysql_select_db("user");
}
$sql = "select *from info";

$return_array =array();
$result = mysql_query($sql);

while ($row = mysql_fetch_array($result, MYSQL_ASSOC))
{
	  $row_array['email'] = $row['email'];
  
  array_push($return_array, $row_array);}

echo json_encode(array("server_response"=>$return_array));
mysql_free_result($result);

mysql_close($con);

?>