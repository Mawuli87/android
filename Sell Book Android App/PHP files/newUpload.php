<?php

	if($_SERVER['REQUEST_METHOD']=='POST'){
		
		$image = $_POST['image'];
         $name = $_POST['name'];
		
		require_once('connect.php');
		
		$sql ="SELECT id FROM newtable ORDER BY id ASC";
		
		$res = mysqli_query($connection,$sql);
		
		$id = 0;
		
		while($row = mysqli_fetch_array($res)){
				$id = $row['id'];
		}
		
		$path = "uploads/$id.png";
		
		$actualpath = "http://192.168.8.100/myProjects/sellBook/$path";
		
		$sql = "INSERT INTO newtable (photo,name) VALUES ('$actualpath','$name')";
		
		if(mysqli_query($connection,$sql)){
			file_put_contents($path,base64_decode($image));
			echo "Successfully Uploaded";
		}
		
		mysqli_close($connection);
	}else{
		echo "Error";
	}
