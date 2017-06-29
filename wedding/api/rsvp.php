<?php 

$app->post('/addPlusOne', function(){
	try {
		
		$db = new DB();
		
		$input = file_get_contents('php://input');
		$json = json_decode($input,true);
		$first = $db->sanitize($json['FirstName']);
		$last = $db->sanitize($json['LastName']);
		$id = $json['GuestID'];

		$sql = "insert into guests values ('', '$first', '$last', -1)";
		$newID = $db->getID($sql);

		$sql = "update guests set plusOne=$newID where id=$id";
		$db->update($sql);
		
		$sql = "insert into plusone values ($newID, 0)";
		$db->insert($sql);
		
		echo "$newID";
	} catch (Exception $e) {
		echo "error";
	}
});

$app->get('/getPlusOne/:id',function($id){
	$db = new DB();
	
	try {
		$id = $db->sanitize($id);
		$sql = "select * from guests where id = $id";
		echo $db->select($sql);
	} catch (Exception $e){
		echo 'error';
	}
	
});

$app->get('/invitation/:id', function($id){
    global $hashids;

	for ($i=1; $i<=100; $i++)
	{
			echo $hashids->encode($i)."\n";
	}
	
});

$app->post('/rsvp', function(){
    global $hashids;
    try {
    	$db = new DB();
        $input = file_get_contents('php://input');  // get content from frontend
        $json = json_decode($input,true);   // dump json data from frontend into array
        $code = $json['code'];  // read the input
        $code = $db->sanitize($code);   // make sure the input is safe
        $decode = $hashids->decode($code)[0];   // decode the input
		if ($decode == 0) {
			echo 'error';
			$db->dc();
			exit;
		}
    }catch (Exception $e){  // if any error, exit
        echo 'error';
        $db->dc();
        exit;
    }
    // everything is ok, $decode has the invitation id
    // return json data of invitation id
    $json = $db->select("CALL invitationData($decode)");
    echo $json;
    $db->dc();
});

$app->get('/getDiet/:id', function($id){
	try {
		$db = new DB();
		$id = $db->sanitize($id);
		$sql = "select * from diet where invId = $id";
		echo $db->select($sql);
	} catch (Exception $e) {
		echo 'error';
	}
	$db->dc();
});

$app->post('/rsvp2', function(){
    global $hashids;
    try {
        $db = new DB();
        $input = file_get_contents('php://input');
        $json = json_decode($input, true);
        
        $guestIds = $json['GuestIDs'];
        $attending = $json['attending'];
        $meals = $json['meal'];

        for ($i=0; $i<count($json['guestId']); $i++){
            $id = $db->sanitize($guestIds[$i]);
            $a = $db->sanitize($attending[$i]);
            $m = $db->sanitize($meals[$i]);

            $sql = "update fulltable
                    set attending=$a, meal=$m
                    where id=$id";
            $db->update($sql);
        }
        echo '1';
    }catch (Exception $e){
        echo 'error';
    }
    $db->dc();
});

$app->post('/submitRSVP', function(){
	try {
		$db = new DB();
	
		$input = file_get_contents('php://input');
		
		//$string = '{"GuestIDs": "9,11,12,13,", "InvID": "4", "Attending": "1,1,1,1,", "Diet": "i cannot have shellfish"}';
		$json = json_decode($input,true);
		
		$guestIDs = explode(",",rtrim($json['GuestIDs'],","));
		$invID = $json['InvID'];
		$names = explode(",",rtrim($json['Names'],","));
		$attending = explode(",",rtrim($json['Attending'],","));
		$diet = $db->sanitize($json['Diet']);
		$plusOne = $json['PlusOneData'];
		
		if ($plusOne != null){
			$f = $db->sanitize($plusOne[0]);
			$l = $db->sanitize($plusOne[1]);
			// add guest into table
			$sql = "insert into guests values ('', '$f', '$l', -1)";
			$plusOneID = $db->getID($sql);

			// update guestIDs[last] to new plus one guest id
			$gid = $guestIDs[count($guestIDs)-1];
			$sql = "update guests set plusOne=$plusOneID where id=$gid";
			$db->update($sql);
			
		}
		
		for ($i=0; $i<count($guestIDs); $i++){
			$sql = "update invitation
					set attending = $attending[$i]
					where id = $invID and guestId = $guestIDs[$i]";
			$db->update($sql);
		}
		
		$sql = "delete from diet where invId = $invID";
		$db->update($sql);
		
		$sql = "insert into diet values($invID, '$diet')";
		$db->insert($sql);
		
		// send email
		$msg = "---- RSVP ----\r\n\r\n";
		for ($i=0; $i<count($guestIDs); $i++){
			$msg = $msg . "Name:\t\t$names[$i]\r\n";
			$yesno = "No";
			if ($attending[$i] == 1) $yesno = "Yes";
			$msg = $msg . "Attending:\t$yesno\r\n";
			$msg = $msg . "\r\n";
		}
		
		if ($plusOne != null){
			$msg = $msg. "Plus One:\t$plusOne[0] $plusOne[1]\r\n\r\n";
		}
		
		$msg = $msg . "\r\n";
		$msg = $msg . "Dietary Restrictions:\r\n$diet";
		
		$msg = wordwrap($msg, 70, "\r\n");
		
		mail('stephaniefarmer25@gmail.com', 'Wedding RSVP!', $msg);
		
		echo '1';
	} catch (Exception $e) {
		echo $e;
	}

});

?>