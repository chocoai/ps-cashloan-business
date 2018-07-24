package com.adpanshi.cashloan.business.cr.service.impl;

import com.adpanshi.cashloan.business.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.business.core.common.service.impl.BaseServiceImpl;
import com.adpanshi.cashloan.business.cr.domain.Info;
import com.adpanshi.cashloan.business.cr.mapper.InfoMapper;
import com.adpanshi.cashloan.business.cr.model.InfoModel;
import com.adpanshi.cashloan.business.cr.service.InfoService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * 评分关联表ServiceImpl
 * 
 * @author
 * @version 1.0.0
 * @date 2017-01-04 15:05:09
 * Copyright 粉团网路  creditrank All Rights Reserved
 *
 * 
 *
 */
 
@Service("infoService")
public class InfoServiceImpl extends BaseServiceImpl<Info, Long> implements InfoService {
	
    @SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(InfoServiceImpl.class);
   
    @Resource
    private InfoMapper infoMapper;




	@Override
	public BaseMapper<Info, Long> getMapper() {
		return infoMapper;
	}




	@Override
	public Info findByTbNid(String tbNid) {
		return infoMapper.findByTbNid(tbNid);
	}




	@Override
	public int save(String tbNid, String tbName, String detail) {
		Info info = new Info();
		info.setTbNid(tbNid);
		info.setTbName(tbName);
		info.setDetail(detail);
		info.setState("10");
		info.setAddTime(new Date());
		return infoMapper.save(info);
	}




	@Override
	public Page<InfoModel> page(Map<String, Object> secreditrankhMap, int current,
			int pageSize) {
		PageHelper.startPage(current, pageSize);
		List<InfoModel> list = infoMapper.listSelect(secreditrankhMap);
		return (Page<InfoModel>)list;
	}




	@Override
	public int updateSelective(Map<String, Object> updateMap) {
		return infoMapper.updateSelective(updateMap);
	}




	@Override
	public List<Info> listSelective(Map<String, Object> map) {
		return infoMapper.listSelective(map);
	}
	
	 
	@Override
	public List<Map<String, Object>> findTable() {
		// TODO Auto-generated method stub
		List<Map<String, Object>> list = infoMapper.findTable();
		return list;
	}
	
	
	@Override
	public List<Map<String, Object>> findColumnByName(Map<String, Object> map) {
		// TODO Auto-generated method stub
		List<Map<String, Object>> list = infoMapper.findColumnByName(map);
		return list;
	}




	@Override
	public Info findByPrimary(long id) {
		return infoMapper.findByPrimary(id);
	}
}