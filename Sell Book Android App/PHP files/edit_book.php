<?php 
if($_SERVER['REQUEST_METHOD']=='POST'){
    
    $id = $_POST["id"];
    $imageId = $_POST["idImage"];
    $imageTitle = $_POST["title"];

    require_once 'connect.php';
    
    $sql = "SELECT * FROM users_posts WHERE idImage = '$imageId'";
   
    $response = mysqli_query($connection,$sql);
    $result['editPost'] = array();
    
    if(mysqli_num_rows($response) === 1){
        
        
       if($row = mysqli_fetch_assoc($response)){
           
           $sh['id'] = $row['id'];
           $sh['idImage'] = $row['idImage'];
           $sh['title'] = $row['title'];
           $sh['type'] = $row['type'];
           $sh['school'] = $row['school'];
           $sh['price'] = $row['price'];
           $sh['description'] = $row['description'];
           $sh['phone'] = $row['phone'];
           $sh['location'] = $row['location'];
           $sh['photo'] = $row['photo'];
           
           array_push($result["editPost"],$sh);
           $result["success"] = "1";
           echo json_encode($result);
       }
    }
        
    
}else{
    
    $result["success"] = "0";
    $result["message"] = "Error!";
    echo json_encode($result);
    mysqli_close($connection);
}

?>