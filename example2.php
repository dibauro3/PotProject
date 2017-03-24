<?php

  function unistr_to_xnstr($str){

    return preg_replace('/\\\u([a-z0-9]{4})/i', "&#x\\1;", $str);

    }


    $con=mysqli_connect("localhost","root","qmfkqh752!","data") or die ("MySQL 접속 실패!!");

    mysqli_set_charset($con,"utf8");

    $sql= "
          SELECT * FROM dataset
          ";

    $ret= mysqli_query($con, $sql);

    $result=array();

    if($ret) {
      //echo mysqli_num_rows($ret),"건이 조회됨.<br><br>";
    }
    else {
      echo "데이터 조회 실패!!!"."<br>";
      echo "실패 원인:".mysqli_error($con);
      exit();
    }

    while($row= mysqli_fetch_array($ret)){                    //가져온값은 row에 저장되어있다.
      array_push($result,array('num'=>$row[0],'val'=>$row[1]));

    //  echo $row['num']," ",$row['val']," ","<br>";
    }


    $json= json_encode(array("result"=>$result));
    echo $json;

    mysqli_close($con);

 ?>
