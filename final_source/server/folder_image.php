<?php

 $path = "./img_load"; // �����ϰ��� �ϴ� ���� 
 $entrys = array(); // �������� ������ �����ϱ� ���� �迭 
// $arrays = array(); // �������� ������ �����ϱ� ���� �迭 

 $dirs = dir($path); // �����ϱ� 
 while(false !== ($entry = $dirs->read())){ // �б� 
    if(($entry != '.') && ($entry != '..')) { 
	
      $entrys[] =array(
	"url"=> $entry
	);
 } 
 } 

$dirs->close(); // �ݱ� 
echo json_encode(array("response_data"=>$entrys));

#var_export($entrys);



?>