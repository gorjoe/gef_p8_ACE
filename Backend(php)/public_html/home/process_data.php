<?php
$arg1=$_GET["arg1"];

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

$sql = "UPDATE home SET timestamp = '" . time() . "', device = '" . $arg1 . "', processed=1 WHERE device = '" . $arg1 . "'";

if (mysqli_query($conn, $sql)) {
  echo "<br>";
  echo "New record processed successfully";
} else {
  echo "Error: " . $sql . "<br>" . mysqli_error($conn);
}

mysqli_close($conn);
?>
