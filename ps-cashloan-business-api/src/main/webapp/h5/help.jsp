<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta
			content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0"
			name="viewport" />
	<meta name="format-detection" content="telephone=no" />
	<meta name="apple-mobile-web-app-capable" content="yes" />
	<meta name="apple-mobile-web-app-status-bar-style" content="#7CD88E" />
	<title>【帮助中心】</title>
	<script src="../static/js/mobile.js"></script>
	<script
			src="../static/js/zepto.min.js"></script>
	<%--<link rel="stylesheet" type="text/css"--%>
		  <%--href="/static/css/general.css1" />--%>
	<script>
        var _hmt = _hmt || [];
        (function() {
            var hm = document.createElement("script");
            hm.src = "https://hm.baidu.com/hm.js?48628ee4944a3955ccd3ab3c2d0e9cb8";
            var s = document.getElementsByTagName("script")[0];
            s.parentNode.insertBefore(hm, s);
        })();
	</script>
	<style type="text/css">
		body {
			font-size: 16px;
		}

		html, body, div, span, h1, h2, h3, p, em, img, dl, dt, dd, ol, ul, li,
		table, tr, th, td, form, input, select {
			margin: 0;
			padding: 0;
		}

		body {
			min-width: 320px;
			max-width: 480px;
			min-height: 100%;
			margin: 0 auto;
		}

		.bg {
			display: none;
			/*position: absolute;*/
			position: fixed;
			top: 0;
			left: 0;
			width: 100%;
			height: 100%;
			z-index: 9999;
			background-color: rgba(0, 0, 0, .7);
		}

		.bg img {
			display: block;
			position: absolute;
			width: 65%;
			top: 5px;
			height: auto;
			float: left;
			left: 25%;
		}
	</style>
	<script type="text/javascript">
        var u = navigator.userAgent;
        window.browser = {};
        window.browser.iPhone = u.indexOf('iPhone') > -1;
        window.browser.android = u.indexOf('Android') > -1
            || u.indexOf('Linux') > -1;//android or uc
        window.browser.ipad = u.indexOf('iPad') > -1;
        window.browser.isclient = u.indexOf('lyWb') > -1;
        window.browser.ios = u.match(/Mac OS/); //ios
        window.browser.width = window.innerWidth;
        window.browser.height = window.innerHeight;
        window.browser.wx = u.match(/MicroMessenger/);
        window.source_tag = getQueryString('source_tag');
	</script>
</head>
<body>
<div class="container">
	<style type="text/css">
		.container {
			margin: 16px;
		}
	</style>
	<div class="container">
		<style type="text/css">
			.container {
				margin: 0;
			}
		</style>
		<link rel="stylesheet" type="text/css" href="../static/css/general.css" />
		<script type="text/javascript" src="../static/js/jquery.js"></script>
		<script type="text/javascript" src="../static/js/common1.js"></script>
		<link rel="shortcut icon" href="../static/images/ico.ico" />
		<div class="kdlc_mobile_wraper container">
			<div class="bg _hidden">
				<img src="../static/images/logo_320.png" />
			</div>
			<div id="question_wraper">
				<div class="content">
					<div class=" padding info em__9">
						<ol class=" list-paddingleft-2" style="overflow:hidden; list-style-type: decimal;color:red;font-size:16px; white-space:nowrap; ">
							<li style="display:none; height: 24px; position: relative;" class="getServiceLi">
								<div class="getServiceDiv" style="position: absolute; line-height: 24px; left: 0;">
									<span class="getService"></span>
									<span class="getService"></span>
								</div>
							</li>
						</ol>
					</div>
					<div class=" padding info em__9">
						<ol class=" list-paddingleft-2" style="list-style-type: decimal;/*color:red;*/font-size:16px;">
							<li>
								<p>客服热线:<span class="phone phone1" style="padding-right: 10px"></span><br/>
								   <!--客服电话2:<span class="phone phone2"></span></p>-->
							</li>
						</ol>
					</div>

					<h3 class="em_1 _cursor">
							<span class="_inline_block _wraper"><img
									class="_hidden v_center"
									src="../static/images/top.png"
									width="100%" /><img class="v_center"
														src="../static/images/right.png"
														width="60%" /></span> <span class="_333 v_center"> 借款用途有哪些？</span>
					</h3>
					<div class="_hidden padding info em__9">
						<p>可申请借款的用途有：①租房分期，需提供租房合同、收入证明；②家装分期，需提供购房证明、装修合同或水电开户单；③消费分期，需提供购买订单截图；④自有房产分期，需上传房产证明和收入证明；⑤个体工商分期，需上传房产证明和收入证明。</p>
					</div>

					<h3 class="em_1 _cursor">
							<span class="_inline_block _wraper"><img
									class="_hidden v_center"
									src="../static/images/top.png"
									width="100%" /><img class="v_center"
														src="../static/images/right.png"
														width="60%" /></span> <span class="_333 v_center">审批大概需要多长时间？</span>
					</h3>
					<div class="_hidden padding info em__9">
						<p>当天提交的审核当天处理，下午18：00后提交的审核第二天处理，审核速度和当天的单量挂钩，若遇量多等待时间较长时，请耐心等待。</p>
					</div>

					<h3 class="em_1 _cursor">
							<span class="_inline_block _wraper"><img
									class="_hidden v_center"
									src="../static/images/top.png"
									width="100%" /><img class="v_center"
														src="../static/images/right.png"
														width="60%" /></span> <span class="_333 v_center"> 申请借款需要什么条件？</span>
					</h3>
					<div class="_hidden padding info em__9">
						<p>年龄25-40周岁的中国公民，拥有稳定的收入来源，无不良信用记录。</p>
					</div>


					<h3 class="em_1 _cursor">
							<span class="_inline_block _wraper"><img
									class="_hidden v_center"
									src="../static/images/top.png"
									width="100%" /><img class="v_center"
														src="../static/images/right.png"
														width="60%" /></span> <span class="_333 v_center"> 什么是91买呗？</span>
					</h3>
					<div class="_hidden padding info em__9">
						<p>91买呗是一款提供分期消费的金融服务平台，提供分期购物和现金分期等服务。91买呗有严格的风控系统，审核快，零误差，对用户的个人信息完全保密，用户仅需完成认证便可获得借款额度。</p>
					</div>
					<h3 class="em_1 _cursor">
							<span class="_inline_block _wraper"><img
									class="_hidden v_center"
									src="../static/images/top.png"
									width="100%" /><img class="v_center"
														src="../static/images/right.png"
														width="60%" /></span> <span class="_333 v_center">可借额度是多少？</span>
					</h3>
					<div class="_hidden padding info em__9">
						<p>借款额度根据用户提供的认证信息评估信用后评定，认证完成越多，额度越高，保持良好的还款记录可提高借款额度。</p>
					</div>
					<!--<h3 class="em_1 _cursor">-->
							<!--<span class="_inline_block _wraper"><img-->
									<!--class="_hidden v_center"-->
									<!--src="../static/images/top.png"-->
									<!--width="100%" /><img class="v_center"-->
														<!--src="../static/images/right.png"-->
														<!--width="60%" /></span> <span class="_333 v_center">可借期限是多少？</span>-->
					<!--</h3>-->
					<!--<div class="_hidden padding info em__9">-->
						<!--<p>借款期限有7天、14天两种。</p>-->
					<!--</div>-->
					<!--<h3 class="em_1 _cursor">-->
							<!--<span class="_inline_block _wraper"><img-->
									<!--class="_hidden v_center"-->
									<!--src="../static/images/top.png"-->
									<!--width="100%" /><img class="v_center"-->
														<!--src="../static/images/right.png"-->
														<!--width="60%" /></span> <span class="_333 v_center">借款手续费？</span>-->
					<!--</h3>-->
					<!--<div class="_hidden padding info em__9">-->
						<!--<p>借款手续费会在借款期限到期时，连带本金一起还。比如您申请一笔1000元的借款，手续费为70元，实际到账为1000元，到期还款1070元。</p>-->
					<!--</div>-->
					<h3 class="em_1 _cursor">
							<span class="_inline_block _wraper"><img
									class="_hidden v_center"
									src="../static/images/top.png"
									width="100%" /><img class="v_center"
														src="../static/images/right.png"
														width="60%" /></span> <span class="_333 v_center">注册或更改密码时验证码收不到怎么办？</span>
					</h3>
					<div class="_hidden padding info em__9">
						<p>重启手机或清理手机缓存；退订过短信的请联系客服回复短信发送。</p>
					</div>
					<h3 class="em_1 _cursor">
							<span class="_inline_block _wraper"><img
									class="_hidden v_center"
									src="../static/images/top.png"
									width="100%" /><img class="v_center"
														src="../static/images/right.png"
														width="60%" /></span> <span class="_333 v_center">申请借款的流程是什么？</span>
					</h3>
					<div class="_hidden padding info em__9">
						<p>本人实名认证的手机号注册91买呗—>完成全部基础认证—>三选一完成高级认证——>获得额度——>开始分期——>选择借款场景和提交借款信息——>审批——>放款——>还款</p>
					</div>
					<h3 class="em_1 _cursor">
							<span class="_inline_block _wraper"><img
									class="_hidden v_center"
									src="../static/images/top.png"
									width="100%" /><img class="v_center"
														src="../static/images/right.png"
														width="60%" /></span> <span class="_333 v_center">借款申请需要做哪些认证？</span>
					</h3>
					<div class="_hidden padding info em__9">
						<p>①基础认证：个人信息认证、绑定银行卡、手机运营商认证、紧急联系人认证、淘宝账号认证</p>
						<p>②高级认证：社保认证、公积金认证、银行卡账单认证（三选一认证即可）</p>
					</div>
					<h3 class="em_1 _cursor">
							<span class="_inline_block _wraper"><img
									class="_hidden v_center"
									src="../static/images/top.png"
									width="100%" /><img class="v_center"
														src="../static/images/right.png"
														width="60%" /></span> <span class="_333 v_center">为什么要提交这些资料？</span>
					</h3>
					<div class="_hidden padding info em__9">
						<p>为了防止别人盗用您的信息，并更精确的计算您的授信额度。我们不会将您的信息作为其他用途。会严格遵守监管部门规定，保护用户个人信息。</p>
					</div>
					<h3 class="em_1 _cursor">
							<span class="_inline_block _wraper"><img
									class="_hidden v_center"
									src="../static/images/top.png"
									width="100%" /><img class="v_center"
														src="../static/images/right.png"
														width="60%" /></span> <span class="_333 v_center">为什么会读取联系人失败？</span>
					</h3>
					<div class="_hidden padding info em__9">
						<p>由于您没有授权91买呗读取联系人的权限。1.苹果手机用户，可以通过设置-&gt;隐私-&gt;通讯录-&gt;91买呗开启权限；2.安卓手机用户，具体设置方式根据机型不同而不同。可以到设置里面开启读取权限。</p>
					</div>
					<h3 class="em_1 _cursor">
							<span class="_inline_block _wraper"><img
									class="_hidden v_center"
									src="../static/images/top.png"
									width="100%" /><img class="v_center"
														src="../static/images/right.png"
														width="60%" /></span> <span class="_333 v_center">如何更换手机号？</span>
					</h3>
					<div class="_hidden padding info em__9">
						<p>暂时还不支持用户自行更改注册手机号，谢谢关注。客服电话为：<span class='phone'> </span>（工作日9:00-18:00）。</p>
					</div>
					<!--<h3 class="em_1 _cursor">-->
							<!--<span class="_inline_block _wraper"><img-->
									<!--class="_hidden v_center"-->
									<!--src="../static/images/top.png"-->
									<!--width="100%" /><img class="v_center"-->
														<!--src="../static/images/right.png"-->
														<!--width="60%" /></span> <span class="_333 v_center">打开手机白屏是怎么回事？</span>-->
					<!--</h3>-->
					<!--<div class="_hidden padding info em__9">-->
						<!--<p>对于很多苹果手机用户，下载app之后打开app会发现没有数据，这是因为ios10系统新增了无线助理功能，可以通过如下方式解决：设置-蜂窝移动网络-无线局域网助理-打开，再去登陆那些软件，当有弹窗要记得点允许就ok了。</p>-->
					<!--</div>-->
					<!--<h3 class="em_1 _cursor">-->
							<!--<span class="_inline_block _wraper"><img-->
									<!--class="_hidden v_center"-->
									<!--src="../static/images/top.png"-->
									<!--width="100%" /><img class="v_center"-->
														<!--src="../static/images/right.png"-->
														<!--width="60%" /></span> <span class="_333 v_center">淘宝账户认证是必须的吗？</span>-->
					<!--</h3>-->
					<!--<div class="_hidden padding info em__9">-->
						<!--<p>91买呗产品目前要求用户必须有淘宝账户认证。否则无法完成借款。</p>-->
					<!--</div>-->
					<h3 class="em_1 _cursor">
							<span class="_inline_block _wraper"><img
									class="_hidden v_center"
									src="../static/images/top.png"
									width="100%" /><img class="v_center"
														src="../static/images/right.png"
														width="60%" /></span> <span class="_333 v_center">服务密码错误是什么意思？</span>
					</h3>
					<div class="_hidden padding info em__9">
						<p>服务密码为手机运营商密码，错误或忘记可以询问服务商查询或更改。</p>
					</div>
					<h3 class="em_1 _cursor">
							<span class="_inline_block _wraper"><img
									class="_hidden v_center"
									src="../static/images/top.png"
									width="100%" /><img class="v_center"
														src="../static/images/right.png"
														width="60%" /></span> <span class="_333 v_center">手机运营商认证时提示“无法获取运营商信息”</span>
					</h3>
					<div class="_hidden padding info em__9">
						<p>该步骤将会认证您当前注册手机号码是否是本人在运营商（移动、联通、电信）实名的。即手机实名信息必须为本人，如需更换手机号，可以联系客服修改。也可能是因为您在运营商那里开启了账单保护功能，联系运营商关闭改功能即可。</p>
					</div>

					<h3 class="em_1 _cursor">
							<span class="_inline_block _wraper"><img
									class="_hidden v_center"
									src="../static/images/top.png"
									width="100%" /><img class="v_center"
														src="../static/images/right.png"
														width="60%" /></span> <span class="_333 v_center">审核通过后多久放款？</span>
					</h3>
					<div class="_hidden padding info em__9">
						<p>审核通过后，1-3个工作日内放款，最快可即时放款。</p>
					</div>
					<h3 class="em_1 _cursor">
							<span class="_inline_block _wraper"><img
									class="_hidden v_center"
									src="../static/images/top.png"
									width="100%" /><img class="v_center"
														src="../static/images/right.png"
														width="60%" /></span> <span class="_333 v_center">审核被拒绝的原因一般有哪些？</span>
					</h3>
					<div class="_hidden padding info em__9">
						<p>①输入信息有误，这种一般被视为假数据所以被拒绝可能性很大；②信用记录不好的用户一般也会被拒绝。</p>
					</div>
					<h3 class="em_1 _cursor">
							<span class="_inline_block _wraper"><img
									class="_hidden v_center"
									src="../static/images/top.png"
									width="100%" /><img class="v_center"
														src="../static/images/right.png"
														width="60%" /></span> <span class="_333 v_center">被拒绝后是否可以再次申请？</span>
					</h3>
					<div class="_hidden padding info em__9">
						<p>7天后可重新申请借款</p>
					</div>
					<!--<h3 class="em_1 _cursor">-->
							<!--<span class="_inline_block _wraper"><img-->
									<!--class="_hidden v_center"-->
									<!--src="../static/images/top.png"-->
									<!--width="100%" /><img class="v_center"-->
														<!--src="../static/images/right.png"-->
														<!--width="60%" /></span> <span class="_333 v_center">审核通过后的打款时间？</span>-->
					<!--</h3>-->
					<!--<div class="_hidden padding info em__9">-->
						<!--<p>审核通过后即时打款。</p>-->
					<!--</div>-->
					<h3 class="em_1 _cursor">
							<span class="_inline_block _wraper"><img
									class="_hidden v_center"
									src="../static/images/top.png"
									width="100%" /><img class="v_center"
														src="../static/images/right.png"
														width="60%" /></span> <span class="_333 v_center">在哪查看我的借款金额？</span>
					</h3>
					<div class="_hidden padding info em__9">
						<p>审核通过后，平台会放款至用户所绑定的银行卡，请留意银行卡账单。</p>
					</div>
					<h3 class="em_1 _cursor">
							<span class="_inline_block _wraper"><img
									class="_hidden v_center"
									src="../static/images/top.png"
									width="100%" /><img class="v_center"
														src="../static/images/right.png"
														width="60%" /></span> <span class="_333 v_center">目前平台支持的银行卡有哪些？</span>
					</h3>
					<div class="_hidden padding info em__9">
						<p>工商银行、农业银行、光大银行、邮政储蓄银行、兴业银行、建设银行、招商银行、中国银行、浦发银行、平安银行、华夏银行、中信银行、交通银行、民生银行、广发银行。后继还会支持更多银行。</p>
					</div>
					<h3 class="em_1 _cursor">
							<span class="_inline_block _wraper"><img
									class="_hidden v_center"
									src="../static/images/top.png"
									width="100%" /><img class="v_center"
														src="../static/images/right.png"
														width="60%" /></span> <span class="_333 v_center">如何更换收款银行卡？</span>
					</h3>
					<div class="_hidden padding info em__9">
						<p>进入认证页，点击银行卡认证查看认证信息，可重新绑定银行卡，绑定的银行卡即是收款账户。</p>
					</div>
					<h3 class="em_1 _cursor">
							<span class="_inline_block _wraper"><img
									class="_hidden v_center"
									src="../static/images/top.png"
									width="100%" /><img class="v_center"
														src="../static/images/right.png"
														width="60%" /></span> <span class="_333 v_center">目前还款方式有哪些？</span>
					</h3>

					<div class="_hidden padding info em__9">
						<p>①【推荐—主动还款】在用户中心查看我的借款，进入未还款分期点击还款即可
							②【推荐—支付宝还款】部分银行卡无法扣款的用户可用前往支付宝转账到我司账户：xz@yinchenglicai.com（转账时需备注姓名+注册手机号，系统审核无误后会更新还款状态，可前往分期订单查看，系统审核大约需半小时左右）
							③【不推荐-打款至对公账户】（不推荐，需要提供截图，系统不会自动更新状态）通过还款到对公银行账户（招商银行高新支行   571910871510401）：提供同时包含转账金额以及转入银行卡信息的截图，并且告知客服注册手机号；
							<!--③【平台代扣】在还款日之前需将所需还款金额放在绑定银行卡，还款日平台将会在当天24小时之内进行实时扣款，扣款成功后会给您发送短信；-->
							<!--④【不推荐-打款至对公账户】（不推荐，需要提供截图，系统不会自动更新状态）通过还款到对公银行账户（招商银行高新支行   571910871510401）：提供同时包含转账金额以及转入银行卡信息的截图，并且告知客服注册手机号；-->
							<!--⑤【邮政储蓄银行用户注意事项】对于绑定银行卡是邮政储蓄银行的用户，由于邮政储蓄银行不支持主动发起还款与到期平台代扣，所以可以通过以下3种方式还款：1）支付宝还款；2）打款至对公账号两种还款方式；3）到身份认证更改绑定银行卡。-->
						</p>
					</div>
					<h3 class="em_1 _cursor">
							<span class="_inline_block _wraper"><img
									class="_hidden v_center"
									src="../static/images/top.png"
									width="100%" /><img class="v_center"
														src="../static/images/right.png"
														width="60%" /></span> <span class="_333 v_center">
								每种还款方式所需时间分别是？</span>
					</h3>
					<div class="_hidden padding info em__9">
						<p>①【主动发起还款】一般情况下，支付成功即刻更新借款状态。②【支付宝还款】用户选择用支付宝还款后，大致5分钟即可接收到已还款信息。</p>
					</div>
					<h3 class="em_1 _cursor">
							<span class="_inline_block _wraper"><img
									class="_hidden v_center"
									src="../static/images/top.png"
									width="100%" /><img class="v_center"
														src="../static/images/right.png"
														width="60%" /></span> <span class="_333 v_center">可以提前还款吗？</span>
					</h3>
					<div class="_hidden padding info em__9">
						<p>可以，点击进入未还款分期，操作还款（目前邮储卡会出现扣款失败，原因是未开通银联无卡支付业务，也无法在APP中自主还款，建议支付宝还款。）</p>
					</div>
					<!--<h3 class="em_1 _cursor">-->
							<!--<span class="_inline_block _wraper"><img-->
									<!--class="_hidden v_center"-->
									<!--src="../static/images/top.png"-->
									<!--width="100%" /><img class="v_center"-->
														<!--src="../static/images/right.png"-->
														<!--width="60%" /></span> <span class="_333 v_center">可以续期（延期）还款吗？</span>-->
					<!--</h3>-->
					<!--<div class="_hidden padding info em__9">-->
						<!--<p>平台暂不支持续期（延期）的功能。</p>-->
					<!--</div>-->
					<h3 class="em_1 _cursor">
							<span class="_inline_block _wraper"><img
									class="_hidden v_center"
									src="../static/images/top.png"
									width="100%" /><img class="v_center"
														src="../static/images/right.png"
														width="60%" /></span> <span class="_333 v_center">不及时还款或不还款会产生什么后果？</span>
					</h3>
					<div class="_hidden padding info em__9">
						<p>逾期会产生逾期费用；不还款将会被记录个人征信、将会影响日后的购房贷款、购车贷款、设置一切需要信用评分的服务申请。</p>
					</div>
					<h3 class="em_1 _cursor">
							<span class="_inline_block _wraper"><img
									class="_hidden v_center"
									src="../static/images/top.png"
									width="100%" /><img class="v_center"
														src="../static/images/right.png"
														width="60%" /></span> <span class="_333 v_center">账户是否可以注销？</span>
					</h3>
					<div class="_hidden padding info em__9">
						<p>不可以注销。根据监管法规规定，不能将用户个人信息泄露给第三方机构。所以大家不用担心信息泄露问题。</p>
					</div>
					<h3 class="em_1 _cursor">
							<span class="_inline_block _wraper"><img
									class="_hidden v_center"
									src="../static/images/top.png"
									width="100%" /><img class="v_center"
														src="../static/images/right.png"
														width="60%" /></span> <span class="_333 v_center">如何联系客服？其他联系方式？</span>
					</h3>
					<div class="_hidden padding info em__9">
						<p>客服热线：<span class='phone phone1' style="padding-right: 10px"></span></p>
						<!--客服电话2：<span class='phone phone2'></span></br>-->
						<!--微信公众号：91买呗</br>-->
						<!--微信订阅号：小额钱宝</br>-->
						<!--公司邮箱：xiaoeqiandai@smoney.cc</p>-->
					</div>
				</div>
			</div>
			<script type="text/javascript" src="../static/js/config.js" ></script>
			<script type="text/javascript">
                $(document).ready(function() {
                    Initialization();	
                });
                $(window).resize(function() {
                    Initialization();
                });
                function Initialization() {
                    fontSize();
                    isOneScreen();
                }
                $("#question_wraper .content h3").click(function() {
                    $(this).find("img").toggleClass("_hidden");
                    $(this).next(".info").toggleClass("_hidden");
                });
                $('.phone1').text(getPhone());
                $('.phone').text(getPhone());
                $('.phone2').text(getPhone2());
                $('.airPay').text(getAirpay());
                $('.getService').text(getService());
				var i = 0, left = 0;
                var timer  = setInterval(function(){
                    i = i + 10;
                    $('.getServiceDiv').css('left', -i);
                    left = parseInt($('.getServiceDiv').css('left'));
                    if(left <= -840) {
                        i = 0;
					}
				}, 300);


			</script>
		</div>
	</div>
	<!--!doctype-->
</div>

<div class='_hidden'>
	<img src="../static/images/logo_321.png" />
</div>
<div class='bg'>
	<!-- <img src="../static/image/wx_download.png" /> -->
</div>
<script type="text/javascript">
    function downLoad() {
        if (window.browser.iPhone || window.browser.ipad
            || window.browser.ios) {
            iosDownload();
        } else {
            // 安卓3.4.1以下版本 由于is_share固定为1，特殊处理，
            androidDownload();
        }
    }
    function iosDownload() {
        if (!window.browser.wx) {
            window.location.href = "https://itunes.apple.com/cn/app/id953061503?mt=8";
            // window.location.href = "itms-services://?action=download-manifest&url=https://app.irongbao.com/iosdown/koudai/koudai.plist";
        } else {
            //alert('请点击右上角按钮选择在Safari浏览器中打开并下载！');
            //window.location.href = "http://mp.weixin.qq.com/mp/redirect?url=https%3A%2F%2Fitunes.apple.com%2Fcn%2Fapp%2Fid953061503%3Fmt%3D8";
            window.location.href = "http://a.app.qq.com/o/simple.jsp?pkgname=com.kdkj.koudailicai";
        }
    }
    function androidDownload() {
        if (!window.browser.wx) {
            if (window.source_tag) {
                window.location.href = "http://www.koudailc.com/attachment/download/koudailicai_"
                    + window.source_tag + ".apk";
            } else {
                window.location.href = "http://www.koudailc.com/attachment/download/koudailicai.apk";
            }
        } else {
            window.location.href = "http://a.app.qq.com/o/simple.jsp?pkgname=com.kdkj.koudailicai";
        }
    }
    window.onload = function() {
        if (window.afterOnload) {
            window.afterOnload();
        }
    }
    function Initialization() {
    }
    Initialization();
</script>
</body>
</html>