//金额大写转换
function xTod(n) {
    var fraction = ['角', '分'];
    var digit = ['零', '壹', '贰', '叁', '肆', '伍', '陆', '柒', '捌', '玖'];
    var unit = [
        ['元', '万', '亿'],
        ['', '拾', '佰', '仟']
    ];
    var head = n < 0 ? '欠' : '';
    n = Math.abs(n);

    var s = '';

    for(var i = 0; i < fraction.length; i++) {
        s += (digit[Math.floor(n * 10 * Math.pow(10, i)) % 10] + fraction[i]).replace(/零./, '');
    }
    s = s || '整';
    n = Math.floor(n);

    for(var i = 0; i < unit[0].length && n > 0; i++) {
        var p = '';
        for(var j = 0; j < unit[1].length && n > 0; j++) {
            p = digit[n % 10] + unit[1][j] + p;
            n = Math.floor(n / 10);
        }
        s = p.replace(/(零.)*零$/, '').replace(/^$/, '零') + unit[0][i] + s;
    }
    var money = head + s.replace(/(零.)*零元/, '元').replace(/(零.)+/g, '零').replace(/^整$/, '零元整');
    return money.indexOf('整') > -1 ? money : (money + '元');
}

//转换日期格式
function formatDate(dateSTR) {
    dateSTR = dateSTR.substring(0, 10);
    var date = new Date(dateSTR);
    var year = date.getFullYear();
    var month = date.getMonth() + 1;
    var day = date.getDate();
    return year + "年" + formatTen(month) + "月" + formatTen(day) + "日";

    function formatTen(num) {
        return num > 9 ? (num + "") : ("0" + num);
    }
}

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

$(function() {
    (function(){
        //設計稿720px
        var deviceWidth = document.documentElement.clientWidth;
        if(deviceWidth > 720) deviceWidth = 720;
        document.documentElement.style.fontSize = deviceWidth / 7.2 + 'px';
    })();

    $.ajax({
        type: "get",
        url: "../../com.adpanshi.cashloan.api/act/borrow/agreement/find.htm",
        dataType: "json",
        data: {
            userId: request('userId'),
            borrowMainId: request('borrowMainId')
        },
        timeout: 10000,
        success: function(data) {
            if(data.code == 200) {
                var dataJSON = data.data;

                var ulHTML = '<li>协议编号: ' + (dataJSON.signOrderNo || "-") + '</li>' +
                    '<li>甲方（借款人）姓名: ' + (dataJSON.borName || "-") + '（以下简称“借款人”）</li>' +
                    '<li>甲方（借款人）身份证号：' + (dataJSON.borIDCard || "-") + '</li>' +
                    '<li>甲方（借款人）注册手机号码：' + (dataJSON.borPhone || "-") + '</li>' +
                    '<li>甲方（借款人）电子邮箱：' + (dataJSON.borEmail || "-") + '</li>' +
                    '<li>乙方（出借人）姓名: ' + (dataJSON.lenderName || "-") + '（以下简称“出借人”）</li>' +
                    '<li>乙方（出借人）身份证号：' + (dataJSON.lenderIDCard || "-") + '</li>' +
                    '<li>丙方（居间服务人）: 浙江齐融金融信息服务有限公司（以下简称“91买呗”）</li>';

                var signHTML = '<p>&nbsp;</p>' +
                    '<p>甲方签章：' + (dataJSON.borSignature || "-") + '</p>' +
                    '<p>' + (dataJSON.signatureDate || "-") + '</p>' +
                    '<p>&nbsp;</p>' +
                    '<p>乙方签章：' + (dataJSON.lenderPartySign || "-") + '</p>' +
                    '<p>' + (dataJSON.signatureDate || "-") + '</p>' +
                    '<p>&nbsp;</p>' +
                    '<p>丙方签章：浙江齐融金融信息服务有限公司</p>' +
                    '<p>' + (dataJSON.signatureDate || "-") + '</p>' +
                    '<p>&nbsp;</p>';

                var tableHTML = '<tbody>\
							<tr>\
								<td>借款本金</td>\
								<td>' + (dataJSON.borMoney || 0) + '元人民币(大写)</td>\
							</tr>\
							<tr>\
								<td>借款期限</td>\
								<td>' + (dataJSON.borDays || 0) + '天</td>\
							</tr>\
							<tr>\
								<td>借款期间</td>\
								<td>' + (dataJSON.borStartDate || 0) + '（起息日）至' + (dataJSON.borEndDate || 0) + '（借款结束日）</td>\
							</tr>\
							<tr>\
								<td>借款利率</td>\
								<td>年利率' + (dataJSON.borAnnualRate || 0) + '，根据实际借款天数按日计息（日利率为年利率/360)</td>\
							</tr>\
							<tr>\
								<td>借款人服务费</td>\
								<td>借款金额的' + (dataJSON.borServiceFee || 0) + '，若服务费金额不足一分钱的将按一分钱收取</td>\
							</tr>\
							<tr>\
								<td>借款用途</td>\
								<td>' + (dataJSON.borIntent || 0) + '</td>\
							</tr>\
							<tr>\
								<td>还款方式</td>\
								<td>每期等额本息，明细如下：</td>\
							</tr>\
                		</tbody>';

                var listHTML = [], $o = null, $tableHTML = $(tableHTML);
                if(dataJSON.stages && (typeof(dataJSON.stages) == 'object')) {
                    var stagesHTML =  '<tr>\
						<td colspan="2" style="border:none; padding: 0;">\
							<table style="width:100%;">\
								<thead>\
									<tr>\
										<th><b>还款期数</b></th>\
										<th><b>' + (dataJSON.totalStages || "-") + '期</b></th>\
										<th><b>每期时间</b></th>\
										<th><b>' + (dataJSON.stagesDays || "-") + '天</b></th>\
									</tr>\
								</thead>\
								<tbody>\
								</tbody>\
							</table>\
						</td>\
					</tr>';
                    $o = $(stagesHTML);
                    $.each(dataJSON.stages, function (index, item) {
                        listHTML.push('<tr>\
							<td style="width:1.5rem;">第' + (index + 1) + '期还款日</td>\
							<td>' + (item.stagesDate || ' - ') + '</td>\
							<td>第' + (index + 1) + '期应还本息</td>\
							<td>' + (item.stagesInterest || ' - ') + '元人民币</td>\
						</tr>');
                    });
                    $o.find('tbody').html(listHTML.join(''));

                    if(dataJSON.totalStages >= 1){
                        $tableHTML.append($o);
                    }
                }

                $('#ulHTML').html(ulHTML);
                $('#signHTML').html(signHTML);
                $('#rateHTML').html(dataJSON.penaltyRate);
                $('#tableHTML').empty().append($tableHTML);

            } else {
                $('body').html('数据出错，请重新申请!');
            }
        }
    });
});