<%@page contentType="text/html;charset=UTF-8"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
		<meta http-equiv="Cache-Control" content="no-store" />
		<meta http-equiv="Pragma" content="no-cache" />
		<meta http-equiv="Expires" content="0" />
		<link rel="stylesheet" type="text/css"
			href="./../resources/scripts/ext/resources/css/ext-all.css" />
		<script type="text/javascript"
			src="./../resources/scripts/ext/ext-base.js"></script>
		<script type="text/javascript"
			src="./../resources/scripts/ext/ext-all.js"></script>
		<link rel="stylesheet" type="text/css"
			href="./../resources/styles/icons.css" />
		<link rel="stylesheet" type="text/css"
			href="./../resources/styles/index.css" />
		<script type="text/javascript"
			src="./../resources/scripts/ux/ST.ux.util.js"></script>
		<script type="text/javascript"
			src="./../resources/scripts/ux/DateTimePicker.js"></script>
		<script type="text/javascript"
			src="./../resources/scripts/admin/base/ST.Base.Cacl.js"></script>
	</head>
	<body>
		<h3>
				业绩和奖金的计算的说明
		</h3><br>
		<p>
			※<font color=red>请务必在计算前做好，数据库的备份，以便计算出现错误的时候，保证数据没有丢失</font>
		</p><br>
		<p>
			※计算时的日期已经卡死在当天
		</p><br>
		<p>
			※计算成功后，请最好不要重复计算，重复计算会影响性能，计算时间会稍长！
		</p><br>
		<div id="angel-calc"></div>
	</body>
</html>