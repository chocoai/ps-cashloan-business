package com.adpanshi.cashloan.business.rc.service.impl;

import com.adpanshi.cashloan.business.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.business.core.common.service.impl.BaseServiceImpl;
import com.adpanshi.cashloan.business.core.domain.User;
import com.adpanshi.cashloan.business.core.mapper.UserMapper;
import com.adpanshi.cashloan.business.rc.domain.ContactCount;
import com.adpanshi.cashloan.business.rc.mapper.ContactCountMapper;
import com.adpanshi.cashloan.business.rc.mapper.RcUserContactsCountMapper;
import com.adpanshi.cashloan.business.rc.service.ContactCountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import tool.util.DateUtil;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 通讯录统计ServiceImpl
 * 
 * @author
 * @version 1.0.0
 * @date 2017-03-10 15:04:13
 *
 *
 * 
 *
 */
 
@Service("contactCountService")
public class ContactCountServiceImpl extends BaseServiceImpl<ContactCount, Long> implements ContactCountService {
	
    @SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(ContactCountServiceImpl.class);
   
    @Resource
    private ContactCountMapper contactCountMapper;
    @Resource
	private UserMapper userMapper;
    @Resource
    private RcUserContactsCountMapper rcUserContactsCountMapper;

	@Override
	public BaseMapper<ContactCount, Long> getMapper() {
		return contactCountMapper;
	}
	
	public int countContacts(Long userId){
		ContactCount cc = new ContactCount();
		cc.setUserId(userId);
		cc.setCount(rcUserContactsCountMapper.count(userId));//通讯录总条数
		cc.setCountOne(rcUserContactsCountMapper.countSucceed(userId));//通讯录借款未逾期人数
		cc.setCountTwo(rcUserContactsCountMapper.countFail(userId));//通讯录借款逾期人数
		cc.setCountThree(rcUserContactsCountMapper.countNinety(userId));//通讯录借款逾期大于90天人数
		cc.setCountFour(rcUserContactsCountMapper.countThirty(userId));//通讯录借款逾期大于30天小于90天人数
		cc.setCountFive(rcUserContactsCountMapper.countWithinThirty(userId));//通讯录借款逾期小于30天人数
		cc.setCreateTime(DateUtil.getNow());
		return contactCountMapper.save(cc);
	}

	@Override
	public int save() {
		int msg = 0;
		Map<String,Object> paramMap = new HashMap<>();
		List<User> list = userMapper.listSelective(paramMap);
		ContactCount cc = new ContactCount();
		for (User user : list) {
			cc.setUserId(user.getId());
			cc.setCount(rcUserContactsCountMapper.count(user.getId()));//通讯录总条数
			cc.setCountOne(rcUserContactsCountMapper.countSucceed(user.getId()));//通讯录借款未逾期人数
			cc.setCountTwo(rcUserContactsCountMapper.countFail(user.getId()));//通讯录借款逾期人数
			cc.setCountThree(rcUserContactsCountMapper.countNinety(user.getId()));//通讯录借款逾期大于90天人数
			cc.setCountFour(rcUserContactsCountMapper.countThirty(user.getId()));//通讯录借款逾期大于30天小于90天人数
			cc.setCountFive(rcUserContactsCountMapper.countWithinThirty(user.getId()));//通讯录借款逾期小于30天人数
			cc.setCreateTime(DateUtil.getNow());
			msg = contactCountMapper.save(cc);
		}
		return msg;
	}
	
}