<?php

$app->get('/guestbook', function(){
    $db = new DB();
    $sql = "select * from guestbook order by id desc";
    $json = $db->select($sql);
    echo $json;
    $db->dc();
});

$app->post('/guestbook', function(){
	$db = new DB();

	$input = file_get_contents('php://input');	// get data from frontend
    $json = json_decode($input,true);			// explode json from frontend into array
    $name = $json['Name'];
    $name = $db->sanitize($name);	// clean the input fields
    $comment = $json['Comment'];
    $comment = $db->sanitize($comment);

    $date = date("F j, Y, g:i a");

    $sql = "insert into guestbook (name, comment, timestamp) values ('$name','$comment','$date')";
    $success = $db->insert($sql);
    echo $success;

	$db->dc();
});
?>