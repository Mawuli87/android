<?php 

if($_SERVER['REQUEST_METHOD']=='POST'){
    
    
    $email = $_POST['email'];
    $password = $_POST['password'];
    
    require_once 'connect.php';
    
    $sql = "SELECT * FROM users_table WHERE email = '$email'";
    
    $response = mysqli_query($connection,$sql);
    $result = array();
    $result['login'] = array();
    
    if(mysqli_num_rows($response) === 1) {
        $row = mysqli_fetch_assoc($response);
        if(password_verify($password, $row['password'])){
            $index['name'] = $row['name'];
            $index['email'] = $row['email'];
           
        
            $index['id'] = $row['id'];
            
            array_push($result['login'], $index);
            $result['success'] = "1";
            $result['message'] = "success";
            echo json_encode($result);
            
            mysqli_close($connection);
        }else{
            $result['success'] = "0";
            $result['message'] = "error";
            echo json_encode($result);
             mysqli_close($connection);
        }
    }
}






?>