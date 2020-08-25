<?php

if($_SERVER['REQUEST_METHOD'] == 'POST') {

    $id = $_POST['id'];
    $photo = $_POST['photo'];

    $path = "profile_image/$id.jpeg";
    $finalPath = "http://192.168.8.100/myProjects/sellBook/".$path;

    require_once 'connect.php';

    $sql = "UPDATE users_table SET photo='$finalPath' WHERE id='$id' ";

    if (mysqli_query($connection, $sql)) {
        
        if ( file_put_contents( $path, base64_decode($photo) ) ) {
            
            $result['success'] = "1";
            $result['message'] = "success";

            echo json_encode($result);
            mysqli_close($connection);
            

        }

    }

}

?>