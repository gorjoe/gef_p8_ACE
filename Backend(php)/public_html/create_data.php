<?php
$arg1=$_GET["arg1"];
$arg2=$_GET["arg2"];

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
echo "Database Connected";

$sql = "INSERT INTO sensor (timestamp, temp, water) VALUES ('" . time() . "', '" . $arg1 . "', '" . $arg2 . "')";

if (mysqli_query($conn, $sql)) {
  echo "<br>";
  echo "New record created successfully";
} else {
  echo "Error: " . $sql . "<br>" . mysqli_error($conn);
}

mysqli_close($conn);
?>
