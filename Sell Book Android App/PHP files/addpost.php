<?php 
if($_SERVER['REQUEST_METHOD'] =='POST'){
     $id = $_POST['id'];
    $title = $_POST["title"];
    $type = $_POST["type"];
    $school = $_POST["school"];
    $price = $_POST["price"];
    $description = $_POST["description"];
    $phone = $_POST["phone"];
    $location = $_POST["location"];
    $image = $_POST["image"];


      $idImage = 0;


       require_once 'connect.php';

  

                 $path = "uploads/$title.jpg";

				//$path = "uploads/$image.png";
		
		       $actualpath = "http://192.168.8.100/myProjects/sellBook/".$path;

             // $sql ="SELECT idImage FROM users_posts ORDER BY idImage ASC";
        
            //  $res = mysqli_query($connection,$sql);
                
              
             //  $idImage = 0;
             
    

   
  $sql = "INSERT INTO users_posts (id,idImage,title,type,school,price,description,phone,location,photo) VALUES('$id','$idImage','$title','$type','$school','$price','$description','$phone','$location','$actualpath')";
 
    
    if(mysqli_query($connection,$sql)){
       

    	file_put_contents($path, base64_decode($image));
    	echo "Successfully Uploaded";
        $result["success"] ="1";
        $result["message"] = "success";
        
        echo json_encode($result);
        mysqli_close($connection);
    }else{
         
        $result["success"] = "0";
        $result["message"] = "error";
        
        
        echo json_encode($result);
        mysqli_close($connection);
 
   
 }
  
}


?>