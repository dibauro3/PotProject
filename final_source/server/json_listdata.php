<?php  
 
$con = mysql_connect("211.44.136.164:3306","admin","password");

if(!$con)
{
   die('do not connect:'.mysql_errot());
}   
else
{
mysql_select_db("pot_project");
}
$sql = "SELECT * FROM demo_test ORDER BY date DESC LIMIT 10";

$return_array =array();
$result = mysql_query($sql);

while ($row = mysql_fetch_array($result, MYSQL_ASSOC))
{
     $row_array['moi'] = $row['moi'];
  $row_array['ill'] = $row['ill'];
$row_array['temp'] = $row['temp'];
$row_array['humi'] = $row['humi'];
$row_array['date'] = $row['date'];
  array_push($return_array, $row_array);}

echo json_encode(array("server_response"=>$return_array));
mysql_free_result($result);

mysql_close($con);

?>