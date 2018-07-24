package com.adpanshi.cashloan.business.rule.mapper;

import com.adpanshi.cashloan.business.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.business.core.common.mapper.RDBatisDao;
import com.adpanshi.cashloan.business.rule.domain.UserEmerContacts;

import java.util.List;
import java.util.Map;


/**
 * 紧急联系人表Dao
 * 
 * @author
 * @version 1.0.0
 * @date 2017-02-14 11:24:05
 *
 *
 * 
 *
 */
@RDBatisDao
public interface UserEmerContactsMapper extends BaseMapper<UserEmerContacts,Long> {

	public List<UserEmerContacts> getUserEmerContactsByUserId(Map<String, Object> paramMap);

}
