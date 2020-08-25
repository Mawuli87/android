<?php 

         require_once 'connect.php';
              
          $sql = " SELECT * FROM users_posts ";

          $response = mysqli_query($connection,$sql);
          $result = array();
          while($row = mysqli_fetch_assoc($response)){
            $result[] = $row;
          }

         echo json_encode(['data'=>$result])


?>