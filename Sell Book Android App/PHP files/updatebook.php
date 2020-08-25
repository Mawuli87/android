<?php

if($_SERVER['REQUEST_METHOD'] == 'POST'){

    $title = $_POST['title'];
    $type = $_POST['type'];
    $school = $_POST['school'];
    $price = $_POST['price'];
    $description = $_POST['description'];
    $phone = $_POST['phone'];
    $location = $_POST['location'];
    $image = $_POST['photo'];

    $id = $_POST['id'];
    $idmage = $_POST['idImage'];

     $path = "uploads/$idmage.jpg";
    
     $actualpath = "http://192.168.8.100/myProjects/sellBook/".$path;
    
    

    require_once 'connect.php';

    $sql = "UPDATE users_posts SET  title='$title', type='$type', school='$school', price = '$price', description = '$description', phone= '$phone', location ='$location', photo= '$actualpath' WHERE idImage = '$idmage' AND id= '$id'";

    if(mysqli_query($connection, $sql)) {

         
      file_put_contents($path, base64_decode($image));
      echo "Successfully Uploaded";

          $result["success"] = "1";
          $result["message"] = "success";

          echo json_encode($result);
          mysqli_close($connection);
      }
  }

else{

   $result["success"] = "0";
   $result["message"] = "error!";
   echo json_encode($result);

   mysqli_close($connection);
}

?>