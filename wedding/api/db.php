<?php
class DB
{
	public $server = '';
	private $username = '';
	private $password = '';
	private $database = '';
	

	private $conn;

	function __construct() {
		$this->connect();
	}

	private function connect () {
		$this->conn = new mysqli($this->server, $this->username, $this->password, $this->database);
		// Check connection
		if ($this->conn->connect_error) {
	    	die("Connection failed: " . $conn->connect_error);
		}
	}

	public function sanitize ($string) {
		return $this->conn->real_escape_string($string);
	}

	public function dc () {
		$this->conn->close();
	}
	
	public function sel($sql) {
		return $this->conn->query($sql);
	}

	public function select ($sql) {
		$jsonRow = array();
		$result = $this->conn->query($sql);

		if ($result->num_rows > 0) {
	    	// output data of each row
	    	while($row = $result->fetch_assoc()) {
	        	$jsonRow[] = $row;
	        	//echo "id: " . $row["id"]. " - Name: " . $row["name"]. " " . $row["timestamp"]. "<br>";
	    	}
		} else {
	    	return 0;
		}

		$json = json_encode($jsonRow);
		return $json;
	}

	public function insert ($sql) {
		if ($this->conn->query($sql) === TRUE) {
			return true;
		}else{
			return false;
		}
	}

	public function update ($sql) {
		if ($this->conn->query($sql) === TRUE) {
			return true;
		}else{
			return false;
		}
	}
	
	public function getID ($sql){
		$this->insert($sql);
//		return $retVal;
		return $this->conn->insert_id;
	}

	public function selectSP ($spName) {
		return $this->select("CALL $spName()");
	}

}
?>