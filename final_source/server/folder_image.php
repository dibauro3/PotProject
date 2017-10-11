<?php

 $path = "./img_load"; // 오픈하고자 하는 폴더 
 $entrys = array(); // 폴더내의 정보를 저장하기 위한 배열 
// $arrays = array(); // 폴더내의 정보를 저장하기 위한 배열 

 $dirs = dir($path); // 오픈하기 
 while(false !== ($entry = $dirs->read())){ // 읽기 
    if(($entry != '.') && ($entry != '..')) { 
	
      $entrys[] =array(
	"url"=> $entry
	);
 } 
 } 

$dirs->close(); // 닫기 
echo json_encode(array("response_data"=>$entrys));

#var_export($entrys);



?>