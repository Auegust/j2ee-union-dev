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
			src="./../resources/scripts/ux/ST.ux.ParamsUtil.js"></script>
		<script type="text/javascript"
			src="./../resources/scripts/ux/DateTimePicker.js"></script>
		<script type="text/javascript"
			src="./../resources/scripts/admin/base/ST.Base.Cacl.js"></script>
	</head>
	<body>
		<h3>
				业绩和奖金的计算说明（定时--系统启动时默认开启/手动---关闭定时器即可，但是下次重启服务器定时还会自动开启）
		</h3><br>
		<p>
			※<font color=red>请务必每次在计算前做好，数据库的备份，以便计算出现错误的时候，保证数据安全，快速恢复</font>
		</p><br>
		<p>
			※<font color=red>定时功能可根据情况自行修改，系统在每次启动时默认都启动定时器（每月25号），您也可以取消定时，自己手动计算</font>
		</p><br>
		<p>
			※请注意，计算时的日期已经卡死在当天进行，早一天晚一天系统都会提示不能进行计算
		</p><br>
		<p>
			※计算成功后，会提示计算成功，请最好不要重复计算，重复计算会影响性能，计算时间可能会稍长，请耐心等候处理
		</p><br>
		<p>
			※计算完毕后，您可以到报表管理工具栏查看业绩和奖金等报表，来查看计算结果，若有问题及时反馈
		</p><br>
		<div id="angel-calc"></div>
	</body>
</html>