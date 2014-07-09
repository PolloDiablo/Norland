<?php
	//DB info
	$dbtable = 'scores';
	$dbcon = NULL;
	function connectToDB()
	{
		global $dbcon;
		$dbcon = mysql_connect("localhost","casualtc_norland","dragons?!1986");
		if(!$dbcon) {
			echo 'db error';
		} else {
			//echo 'db worked';
		}
		$db = mysql_select_db('casualtc_norland',$dbcon);
		return $db;
	}

	function doQuery($q)
	{
		global $dbcon;
		//echo '<br><pre>Doing Query {'.$q.'}</pre>';
		$result = mysql_query($q);
		return $result;
	}


	$LSALT = "f94m8ifn28usndi3m3";
	$RSALT = "9023njsa=-sdn930g5";
	if($_REQUEST['secret']=="a823iun342jksd89unsdfji3289")
	{
		//Gather all the necessary information
		$uid = $_REQUEST['uid'];
		if(!$uid){return;}
		echo 'uid_valid,';
		//make the uid hashed.
		$uid = hash("sha512",$LSALT.$uid.$RSALT);

		$uname = $_REQUEST['uname'];
		if(!$uname){return;}
		echo 'uname_valid,';

		$dragons = $_REQUEST['dragons'];
		if(!$dragons && $dragons!=0){return;};
		echo 'dragons_valid,';

		$bergs = $_REQUEST['bergs'];
		if(!$bergs && $bergs!=0){return;}
		echo 'bergs_valid,';

		$time = time();
		//2012-05-01 00:00:00
		$timeformatted = date("c",$time);

//		echo '<br>Dragons='.$dragons;
//		echo '<br>Icebergs='.$bergs;

		$worked = connectToDB();
		if(!$worked){return;}

//		echo '<br>Connected to db '.$dbname;

		//Now check and see if this exists already
		$q = "select * from scores where uid = '".$uid."'";
		$result = doQuery($q);

		//If it does, check that this score is newer and higher
		if($row = mysql_fetch_assoc($result))
		{
			//Always dump out latest information for user
			//print_r($row);

			//four cases
			//one is that bergs are higher
			if($bergs>$row['bergs'])	//<
			{
				$qbergs = "update scores set bergs='".$bergs."',bergtime='".$timeformatted."' where uid = '".$uid."'";
				doQuery($qbergs);
			}

			//one is that dragons are higher
			if($dragons>$row[dragons])	//<
			{
				$qdragons = "update scores set dragons='".$dragons."',dragontime='".$timeformatted."' where uid = '".$uid."'";
				doQuery($qdragons);
			}

			//dealt with automagically above.
			//one is that both are higher
			//one is that neither are higher
		} else {
			//If it does not, just add it.
			echo 'about to do insert.';
			$qq = "INSERT INTO `casualtc_norland`.`scores` (`id`, `uid`, `uname`, `dragons`, `dragontime`, `bergs`, `bergtime`) VALUES (NULL, '".$uid."', '".$uname."', '".$dragons."', '".$timeformatted."', '".$bergs."', '".$timeformatted."');";
			doQuery($qq);
		}
	}else
	{
		echo '<h1>User Scores</h1>';
		echo '<ul>';
		$worked = connectToDB();
		if(!$worked){return;}

		$q = "select * from scores";
		$result = doQuery($q);
		while($row = mysql_fetch_assoc($result))
		{
			echo '<li>'.$row['uname'].': dragons='.$row['dragons'].', icebergs='.$row['bergs'].'</li>';
		}
		echo '</ul>';
	}
	echo '<hr>';
?>