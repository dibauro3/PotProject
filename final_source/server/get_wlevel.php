<?php
//JSON-3 시간대별 저장
  function unistr_to_xnstr($str){

    return preg_replace('/\\\u([a-z0-9]{4})/i', "&#x\\1;", $str);

    }


    $con=mysqli_connect("211.44.136.164:3306","admin","password","pot_project") or die ("MySQL 접속 실패!!");

    mysqli_set_charset($con,"utf8");

    $sql= "
    SELECT * FROM demo_test ORDER BY date DESC limit 1;
          ";

    $ret= mysqli_query($con, $sql);

    $result=array();


    if($ret) {
    //  echo mysqli_num_rows($ret),"건이 조회됨.<br><br>";
    }
    else {
      echo "데이터 조회 실패!!!"."<br>";
      echo "실패 원인:".mysqli_error($con);
      exit();
    }

    while($row= mysqli_fetch_array($ret)){                    //가져온값은 row에 저장되어있다.

      array_push($result,array('wlevel'=>$row['waterLV']

                                ));

    //  echo $row['num']," ",$row['val']," ","<br>";
    }

    $arr=array("result"=>$result);

    $json_temp= json_encode($arr);

    echo $json_temp;

    mysqli_close($con);

 ?>
