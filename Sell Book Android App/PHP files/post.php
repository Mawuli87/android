<?php

class Constants{
    
    static $DB_SERVER = "localhost";
    static $DB_NAME = "project";
    static $DB_USERNAME = "root";
    static $PASSWORD = "";
    
    //statements
    static $SQL_SELECT_ALL = "SELECT * FROM users_posts";
    
}


class sellBooks{
    
    public function connect(){
        
    $con = new mysqli(Constants::$DB_SERVER,Constants::$DB_USERNAME,Constants::$PASSWORD,Constants::$DB_NAME);
        if($con->connect_error){
            echo "Unable to connect";
        }else{
           // return $con;
            echo "Mawuli you are connected";
        }
      
        
    }
    public function insert(){
        $con = $this->connect();
        if($con != null){
            
        }else{
            
            
        }
    }
}

?>