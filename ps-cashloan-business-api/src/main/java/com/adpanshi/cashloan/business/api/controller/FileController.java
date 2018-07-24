package com.adpanshi.cashloan.business.api.controller;

import com.adpanshi.cashloan.business.cl.domain.UserAuth;
import com.adpanshi.cashloan.business.cl.service.UserAuthService;
import com.adpanshi.cashloan.business.core.common.cache.RedissonClientUtil;
import com.adpanshi.cashloan.business.core.common.context.Constant;
import com.adpanshi.cashloan.business.core.common.exception.BussinessException;
import com.adpanshi.cashloan.business.core.common.util.ServletUtils;
import com.adpanshi.cashloan.business.core.common.web.controller.BaseController;
import com.adpanshi.cashloan.business.system.domain.SysAttachment;
import com.adpanshi.cashloan.business.system.service.SysAttachmentService;
import org.redisson.core.RLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

/***
 ** @category 附件上传的通用类...
 ** @author qing.yunhui
 ** @email: qingyunhui@fentuan360.com
 ** @createTime: 2018年2月2日上午9:41:19
 **/
@Controller
@Scope("prototype")
@RequestMapping(FileController.ACTION_PATH)
public class FileController extends BaseController{

	final Logger logger = LoggerFactory.getLogger(FileController.class);

    final static String ACTION_PATH= "/api/act/file";
    
    @Autowired
    UserAuthService userAuthService; 
    
    @Autowired
    SysAttachmentService sysAttachmentService;
	
    /**
     * <p>附件上传</p>
     *@param file 待上传的附件
     *@param userId 
     *@param classify 分类
     *@param subClassify 子类
     * */
    @RequestMapping(value = "/upload.htm", method = RequestMethod.POST)
  	public void upload(@RequestParam(value="file")MultipartFile file,@RequestParam(value="userId")Long userId,Integer classify,Integer subClassify)throws Exception {
  		RLock rlock=RedissonClientUtil.getInstance().getLock("upload_"+userId);
  		Map<String,Object> result=new HashMap<>();
		try {
			UserAuth userAuth=userAuthService.findSelective(userId);
	  		if(null==userAuth)throw new BussinessException("非法用户已被歹住！");
	  		SysAttachment attachment= sysAttachmentService.uploadFileWithSave(file, userId, classify, subClassify);
	  		result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
  			result.put(Constant.RESPONSE_CODE_MSG,Constant.OPERATION_SUCCESS);
	  		result.put(Constant.RESPONSE_DATA, attachment);
		}finally {
			rlock.unlock();
		}
  		ServletUtils.writeToResponse(response,result);
  	}
    
    /**
     * <p>逻辑删除</p>
     * @param id 待删除的id
     * */
    @RequestMapping(value = "/remove.htm", method = RequestMethod.POST)
    public void remove(Long id){
    	Map<String,Object> result=new HashMap<>();
    	int count=sysAttachmentService.remove(id);
    	result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
		result.put(Constant.RESPONSE_CODE_MSG,Constant.OPERATION_SUCCESS);
    	result.put(Constant.RESPONSE_DATA, count>0?"操作成功":"操作失败");
    	ServletUtils.writeToResponse(response,result);
    }
}
