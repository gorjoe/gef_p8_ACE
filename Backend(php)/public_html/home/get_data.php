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

$sql = "SELECT * FROM home WHERE processed=0";
$result = mysqli_query($conn, $sql);
$json = array();

if (mysqli_num_rows($result) > 0) {
  // output data of each row
  while($row = mysqli_fetch_assoc($result)){
      array_push($json, $row);
  }
}
    
echo json_encode($json);

mysqli_close($conn);
?>
