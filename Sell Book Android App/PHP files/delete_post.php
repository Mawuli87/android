<?php

if($_SERVER['REQUEST_METHOD'] == 'POST'){

      $id = $_POST['id'];
      $idImage = $_POST['idImage'];
      $photo = $_POST['photo'];

    


    

    $path = "uploads/$idmage.jpg";
    
     $actualpath = "http://192.168.8.100/myProjects/sellBook/".$path;

    require_once 'connect.php';

    $sql = "DELETE FROM users_posts WHERE id='$id' AND idImage ='$idImage' ";

    if(mysqli_query($connection, $sql)) {

         unlink($actualpath);
        
          $result["success"] = "1";
          $result["message"] = "success";

          echo json_encode($result);
          mysqli_close($connection);
      }

else{

   $result["success"] = "0";
   $result["message"] = "error!";
   echo json_encode($result);

   mysqli_close($connection);
}
}

?>

