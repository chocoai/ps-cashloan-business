$(function(){

    //复选框美化
    $('input[type="checkbox"]').iCheck({
        checkboxClass: 'icheckbox_minimal-yellow',
        radioClass: 'iradio_minimal-yellow',
        increaseArea: '10%' // optional
    });

    //刷新图片
    $("#imgObj").on('click', function(ev){
        var times = (new Date()).getTime();
        $(this).attr("src", '/com.adpanshi.cashloan.api/h5/imgCode/generate.htm?timestamp='+times);
    });

    //url参数获取
    function request(strName) {
        var strHref = document.location.href;
        var intPos = strHref.indexOf("?");
        var strRight = strHref.substr(intPos + 1);
        var arrTmp = strRight.split("&");
        for(var i = 0; i < arrTmp.length; i++) {
            var arrTemp = arrTmp[i].split("=");
            if(arrTemp[0].toUpperCase() == strName.toUpperCase())
                return arrTemp[1];
        }
        return undefined;
    }

    //赋值邀请码
    (request('invitationCode') && request('invitationCode') != 'null') && $('#js_yqm').val(request('invitationCode')) || $('#js_yqm').closest('li').hide();

    //发送验证码方法封装
    function sendCode(obj, cur, fn) {
        clearInterval(obj.timer);
        obj.timer = setInterval(function() {
            if(cur > 0) {
                obj.html('(' + (cur--) + 's后获取)');
            } else {
                clearInterval(obj.timer);
                obj.html('获取验证码');
                fn&&fn();
            }
        }, 1000);
    }

    //发送短信验证码
    var msgURL = '/com.adpanshi.cashloan.api/user/h5SendSms.htm',
        isUserExistURL = '/com.adpanshi.cashloan.api/user/isPhoneExists.htm',//用户是否存在
        downloadURL = 'https://com.adpanshi.com.adpanshi.cashloan.api.xiaoeqiandai.com/com.adpanshi.com.adpanshi.cashloan.api/h5/app/latestDownload.htm';  //app下载地址

    $('#js_code').on('click', function(ev) { //发送验证码之前，先验证手机号是否存在，存在就不发送，不存在就发送验证码
        var f = false, params = verify(), $this = $(this);
        if(verifyPhone() && params) {
            $.ajax({
                url: isUserExistURL,
                type:'post',
                data: {phone: $('#js_phone').val()},
                dataType: 'json',
                async: false,
                success: function(result) {
                    if(result.code == 200){
                        if(result.data.isExists == 10) {
                            f = true;
                        }else{
                            window.location.href="./second.html"
                            // $('.dialog .yes').text('下载');
                            // show_download('此手机号已注册过,赶快去下载或登录App哦!');
                        }
                    }else{
                        show(result.msg);
                    }
                },
                error: function(data){
                    show("网络连接错误!");
                }
            });
        }

        if($this.html() == '获取验证码' && f) {
            $this.removeClass('boxCode');
            $.ajax({
                url: msgURL,
                type:'post',
                data: params,
                dataType: 'json',
                success: function(result) {
                    if(result.code == 200){
                        sendCode($this, 59, function(){
                            $this.addClass('boxCode');
                        });
                    }else{
                        show(result.msg);
                        $this.addClass('boxCode');
                    }
                },
                error: function(data){
                    show("网络连接错误!");
                    $this.addClass('boxCode');
                }
            });
        }
    });

    //立即申请  /com.adpanshi.cashloan.api/user/wxRegister.htm
    var regURL = '/com.adpanshi.cashloan.api/user/wxRegister.htm';
    $('#js_ljsq').on('click', function(ev){
        var params = verify(1);
        if (!params) {
            return false;
        }else{
            $.ajax({
                url: regURL,
                data: params,
                type: 'post',
                dataType: 'json',
                success: function(data) {
                    if (data.code == 200) {
                        console.log(data)
                        userId = data.userId;
                        $('.login').hide();
                        $('.second').show();
                        // window.location.href="./second.html?userId="+data.userId;
                        //注册成功
                        // $('.dialog .yes').text('下载');
                        // show_download('恭喜您注册成功,快去下载app登录吧!');
                    }else {
                        //报错
                        show(data.msg);
                    }
                },
                error: function(data){
                    show("网络连接错误!");
                }
            });
        }
    });


    var close = function(e) {
        $(this).parents('.popup').hide('300');
        setTimeout(function (){
            $('.dialog .yes').text('确定');
        }, 500);
        return false;
    };

    /**弹窗封装**/

    //show函数
    var show = function(msg) {
        $('.tips').show('500').find('h2').text(msg);
    };

    var blockScript = function(downloadURL) { /**将脚本写入全局js*/
        var script = document.createElement('script');
        script.type = 'text/javascript';
        script.src = downloadURL;
        document.body.appendChild(script);
        setTimeout(function(){
            document.getElementsByTagName('body')[0].removeChild(script);
        }, 600);
    };

    //show_download函数
    var show_download = function(msg) {
        $('.tips').show('500').find('h2').text(msg);
        $('.yes').on('click', function() {
            if($(this).text() == '下载') {
                var pStr = 'ios', appType = 1;

                if(browser.versions.iPhone) {
                    pStr = 'ios';
                }else if(browser.versions.android){
                    pStr = 'android';
                }

                blockScript(downloadURL + '?systemType=' + pStr + '&callback=download' + '&appType=' + appType);
            }
        });
    };

    //jsonp回调
    window.download = function(data){
        if(data.code == 200) {
            window.location.href = data.url;
        }else{
            show(data.msg);
        }
    };

    //关闭弹窗对话框
    $('.popup .close').click(close);
    $('.popup a.yes').click(close);

    //表单验证
    //phone
    var verifyPhone = function() {
        var reg = /^1(3|4|5|7|8)\d{9}$/;
        var tel = $.trim($('#js_phone').val());
        if (tel === '') {
            show('请输入手机号');
            return false;
        }

        if (!reg.test(tel)) {
            show('手机号不正确');
            return false;
        }
        return tel;
    };

    var verify = function(type) {
        var pwd = $.trim($('#js_passwd').val());
        //MD5加密
        var pwd_md5 = $.md5(pwd); // 密码
        var re = /(?!^[0-9]*$)(?!^[a-zA-Z]*$)^([a-zA-Z0-9]{6,16})$/;
        var msgCode = $.trim($('#js_msgCode').val());    //短信验证码
        var imgCode = $.trim($('#js_imgCode').val()); //图片验证码
        var invitationCode = $('#js_yqm').val();

        var dataJSON = {};

        var tel = verifyPhone();  //手机号码

        if (!tel) {
            return false;
        }

        if ($.trim(imgCode) === '') {
            show('图片验证码不能为空');
            return false;
        }


        if(type == 1) {  //立即提交
            if ($.trim(msgCode) === '') {
                show('短信验证码不能为空');
                return false;
            }
            if (!re.test(pwd)) {
                show('必须输入6-16位字母与数字组合的密码');
                return false;
            }
            // if(!$('#js_agree').prop('checked')) {
            //     show('请同意《91买呗》使用协议');
            //     return false;
            // }
            if($('#protocol').hasClass('dis-agree')){
                show('请同意《91买呗》使用协议');
                return false;
            }

            dataJSON = {
                'loginName': tel,
                'loginPwd': pwd_md5,
                'type': 'register',
                'code': imgCode,
                'invitationCode': invitationCode,
                'vcode': msgCode,
                'channelCode': request('channelCode') ? request('channelCode') : ''
            }
        }else{
            dataJSON = {
                'phone': tel,
                'loginPwd': pwd_md5,
                'code': imgCode,
                'type': 'register'
            }
        }

        return dataJSON;
    };

    $('.pglk').on('click',function () {
    var dateBirthday=$('#dateBirthday').val(),
        education=$('#education').val(),
        liveAddr=$('#liveAddr').val();
    $.ajax({
        url: '/com.adpanshi.cashloan.api/user/wxRegisterNex.htm ',
        data: {userId:userId,dateBirthday:dateBirthday,education:education,liveAddr:liveAddr},
        type: 'post',
        dataType: 'json',
        success: function(data) {
            console.log(data)
            if(data.data.code == 200){
                window.location.href="./second.html"
            }else{
                show(data.msg)
            }
           
        },
        error: function(data){
            // show("网络连接错误!");
        }
    });

})
	//判断访问终端 @copyRight fl
	var browser={
		versions:function(){
			var u = navigator.userAgent, app = navigator.appVersion;
			return {
				trident: u.indexOf('Trident') > -1, //IE内核
				presto: u.indexOf('Presto') > -1, //opera内核
				webKit: u.indexOf('AppleWebKit') > -1, //苹果、谷歌内核
				gecko: u.indexOf('Gecko') > -1 && u.indexOf('KHTML') == -1,//火狐内核
				mobile: !!u.match(/AppleWebKit.*Mobile.*/), //是否为移动终端
				ios: !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/), //ios终端
				android: u.indexOf('Android') > -1 || u.indexOf('Adr') > -1, //android终端
				iPhone: u.indexOf('iPhone') > -1 , //是否为iPhone或者QQHD浏览器
				iPad: u.indexOf('iPad') > -1, //是否iPad
				webApp: u.indexOf('Safari') == -1, //是否web应该程序，没有头部与底部
				weixin: u.indexOf('MicroMessenger') > -1, //是否微信 （2017-07-03新增）
				qq: u.match(/\sQQ/i) == " qq" //是否QQ
			};
		}(),
		language:(navigator.browserLanguage || navigator.language).toLowerCase()
	}
    if(browser.versions.weixin){
        $('.wxtip').show();
    }
});


