<?php
$servername = "localhost";
$username = "username";
$password = "password";
$dbname = "dbname";

// Create connection
$conn = mysqli_connect($servername, $username, $password, $dbname);

// Check connection
if (!$conn) {
  die("Connection failed: " . mysqli_connect_error());
}
// echo "Database Connected";

$sql = "SELECT * FROM sensor ORDER BY id DESC";
$result = mysqli_query($conn, $sql);


if (mysqli_num_rows($result) > 0) {
  // output data of each row
    $row = mysqli_fetch_assoc($result);
    
    
	echo json_encode($row);
} else {
  echo "0 results";
}

mysqli_close($conn);
?>
