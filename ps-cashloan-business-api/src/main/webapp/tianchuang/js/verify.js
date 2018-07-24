var show = function(msg, type){
	type || $('.mint-indicator').show('500');
	type == 1 && (function(){
		$('.mui-popup-text').text(msg);
		$('.alertDialog').show('500');
	})();
	
	$('.mui-popup-button').click(close);
}

var close = function(){
	$('.mint-indicator').hide('300');
	$('.alertDialog').hide('300');
}


//表单验证
//phone
var verifyPhone = function() {
	var reg = /^1(3|4|5|7|8)\d{9}$/;
	var tel = $('#phone').val();
	if(tel === '') {
		show('请输入手机号', 1);
		return false;
	}

	if(!reg.test(tel)) {
		show('手机号不正确', 1);
		return false;
	}
	return tel;
};

//code 验证码
var verifyCode = function() {
    var code = $('#code').val();
    if($.trim(code) === '') {
        show('请输入验证码', 1);
        return false;
    }
    return code;
};

var verify = function() {
	var pwd = $('#passwd').val(); 

	var re = /^\d{6}$/;
	var sf = /\d{17}[\d|x]|\d{15}/;
	var code = $('#shenfzheng').val(); //身份证
	var name = $.trim($('#realname').val()); //姓名

	var tel = verifyPhone(); //手机号码

	if(!tel) {
		return false;
	}

	if(!re.test(pwd)) {
		show('必须输入6位数字的密码', 1);
		return false;
	}
	
	if(!sf.test(code)) {
		show('身份证号码不正确', 1);
		return false;
	}
	
	if(name == '') {
		show('姓名不正确', 1);
		return false;
	}

	return {
		'mobile': tel,
		'password': pwd,
		'code': code,
		'name': name
	};
};