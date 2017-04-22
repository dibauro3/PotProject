<?php

$servername="localhost";
$username = "root";
$password = "";
$dbname = "img";

$conn = mysql_connect($servername,$username,$password) or die ("db connection faile");
mysql_select_db($dbname);


$sql = "select * from uploads";

$return = array();
$result  = mysql_query($sql);



while($row = mysql_fetch_array($result,MYSQL_ASSOC)){
	$row_array['url'] = $row['image'];

array_push($return,$row_array);		
}


echo json_encode(array("response_data"=>$return));


mysql_free_result($result);

mysql_close($conn);


?>

<?php

 $path = "./img_load"; // 오픈하고자 하는 폴더 
 $entrys = array(); // 폴더내의 정보를 저장하기 위한 배열 
// $arrays = array(); // 폴더내의 정보를 저장하기 위한 배열 
$i=0;
 $dirs = dir($path); // 오픈하기 
 while(false !== ($entry = $dirs->read())){ // 읽기 
    if(($entry != '.') && ($entry != '..')) { 
	
      $entrys[] =array(
	"url".$i=> $entry
	);
	$i++;       
    } 
 } 

$dirs->close(); // 닫기 
echo json_encode(array("response_data"=>$entrys));

#var_export($entrys);



?>
