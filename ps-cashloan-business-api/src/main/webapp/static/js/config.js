//邀请页面App下载的地址
function getInvite_a(){
    //return "http://a.app.qq.com/o/simple.jsp?pkgname=com.xiaoeqiandai.loan";
    return "https://a.app.qq.com/o/simple.jsp?pkgname=com.xiaoeqiandai.loan";
}
//邀请页面头部图片的地址
function getInvite_img(){
    return "../static/images/invite.png";
}
//首页头部图片的地址
function getIndex_img1(){
    return "../static/images/invite.png";
}
//首页什么是现金贷图片的地址
function getIndex_img2(){
    return "../static/images/txtTop.png";
}
//首页现金贷优势图片的地址
function getIndex_img3(){
    return "../static/images/txtBtm.png";
}
//帮助中心客服电话
function getPhone(){
    return '4008339188';
}

//帮助中心客服电话2
function getPhone2(){
    return '4008339188';
}
//支付宝账号
function getAirpay(){
    return 'xz@yinchenglicai.com';
}
//银行卡账号
function getBank(){
    return '杭州银行 571910871510401';
}
//对公银行卡
function getBank2(){
    return '杭州银行 571910871510401';
}

//帮助中心国庆服务中心
function getService(){
    var str = '';
    //str = '国庆期间10月1日-10月8日原客服座机电话暂停服务，可拨打客服值班电话：18042011759（服务时间：9:00-18:00）。';
    str != '' && $('.getServiceLi').show();
    return str;
}