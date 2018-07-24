package com.adpanshi.cashloan.business.cl.service.impl;

import com.adpanshi.cashloan.business.cl.domain.UserMessages;
import com.adpanshi.cashloan.business.cl.mapper.UserMessagesMapper;
import com.adpanshi.cashloan.business.cl.service.UserMessagesService;
import com.adpanshi.cashloan.business.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.business.core.common.service.impl.BaseServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 用户资料--联系人ServiceImpl
 * 

 * @version 1.0.0
 * @date 2017-03-04 11:54:57
 *
 *
 * 
 *
 */
 
@Service("clUserMessagesService")
public class UserMessagesServiceImpl extends BaseServiceImpl<UserMessages, Long> implements UserMessagesService {
	
    @SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(UserMessagesServiceImpl.class);
   
    @Resource
    private UserMessagesMapper clUserMessagesMapper;




	@Override
	public BaseMapper<UserMessages, Long> getMapper() {
		return clUserMessagesMapper;
	}




	@Override
	public Page<UserMessages> listMessages(long userId, int current,
			int pageSize) {
		PageHelper.startPage(current, pageSize);
		Map<String,Object> searchMap = new HashMap<>();
		searchMap.put("userId", userId);
		List<UserMessages> list = clUserMessagesMapper.listSelective(searchMap);
		for (UserMessages clUserMessages : list) {
			if ("10".equals(clUserMessages.getType())) {
				clUserMessages.setType("发送");
			}else {
				clUserMessages.setType("接收");
			}
		}
		return (Page<UserMessages>)list;
	}
	
}