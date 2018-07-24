package com.adpanshi.cashloan.business.core.model;

import com.adpanshi.cashloan.business.core.common.context.Global;
import com.adpanshi.cashloan.business.system.domain.SysAttachment;

import java.util.ArrayList;
import java.util.List;

/***
 ** @category 用于前端渲染...
 ** @author qing.yunhui
 ** @email: 280672161@qq.com
 ** @createTime: 2017年12月5日下午12:45:50
 **/
public class FileModel {

	/**路径*/
	private String path;
	
	/**文件名称*/
	private String name;
	
	/**文件类型*/
	private Integer type;
	
	private Long id;
	
	//分类
	private Integer classify;
	
	//子分类
	private Integer subClassify;
	
	
	public FileModel(){}
	
	public FileModel(String path,String name,Integer type){
		this.path=path;
		this.name=name;
		this.type=type;
	}
	
	public FileModel(Long id,Integer classify,Integer subClassify,String path,String name,Integer type){
		this.id=id;
		this.classify=classify;
		this.subClassify=subClassify;
		this.path=path;
		this.name=name;
		this.type=type;
	}
	
	/**
	 * <p>附件->FileModel的转换</p>
	 * @param attachments
	 * @return List<FileModel>
	 * */
	public static List<FileModel> handleAttachment(List<SysAttachment> attachments){
		List<FileModel> files=new ArrayList<>();
		if(null==attachments||attachments.size()==0) return files;
		String serverHost = Global.getValue("server_host");
		String request="/readFile.htm?path=";
		StringBuffer sb=new StringBuffer();
		for(SysAttachment att:attachments){
			sb.setLength(0);
			sb.append(serverHost).append(request).append(att.getFilePath());
			files.add(new FileModel(att.getId(),att.getClassify(),att.getSubClassify(),sb.toString(), att.getOriginName(), att.getClassify()));
		}
		return files;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getClassify() {
		return classify;
	}

	public void setClassify(Integer classify) {
		this.classify = classify;
	}

	public Integer getSubClassify() {
		return subClassify;
	}

	public void setSubClassify(Integer subClassify) {
		this.subClassify = subClassify;
	}
	
}
