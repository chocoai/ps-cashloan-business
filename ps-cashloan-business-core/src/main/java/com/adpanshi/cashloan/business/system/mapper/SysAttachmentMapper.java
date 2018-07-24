
package com.adpanshi.cashloan.business.system.mapper;

import com.adpanshi.cashloan.business.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.business.core.common.mapper.RDBatisDao;
import com.adpanshi.cashloan.business.core.model.AttachmentExtra;
import com.adpanshi.cashloan.business.system.domain.SysAttachment;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author qing.yunhui 
 * @Since 2011-2017
 * @create 2017-09-30 11:52:05
 * @history
 */
@RDBatisDao
public interface SysAttachmentMapper extends BaseMapper<SysAttachment,Long>{
	
	/**
	 * <p>根据给定条件查询附件信息</p>
	 * @param extra 待查询的条件
	 * @return SysAttachment
	 * */
	SysAttachment getAttachmentByTargetData(@Param("extra") AttachmentExtra extra); 

	/**
	 * <p>根据给定条件查询用户合同及收入证明附件</p>
	 * @param targetMode 待查询的目标对象
	 * @param userId  用户id
	 * @param tenementIncomeStates 合同收入证明状态集
	 * @return List<SysAttachment>
	 * */
	List<SysAttachment> queryAttachmentByPettyLoanScene(@Param("targetMode") AttachmentExtra targetMode, @Param("userId") Long userId,
                                                        @Param("tenementIncomeStates") List<String> tenementIncomeStates);

	/**
	 * <p>根据给定条件查询订单使用的场景及租房收入证明合同</p>
	 * <p>用户单击订单时需要展示该订单所关联的场景及租收入证明附件信息</p>
	 * @param targetMode 待查询的目标对象
	 * @param userId  用户id
	 * @param borrowMainId 订单id
	 * @param sceneTypes 场景
	 * @return List<SysAttachment>
	 * */
	List<SysAttachment> queryAttachmentByBorIdWithUserIdParams(@Param("targetMode") AttachmentExtra targetMode, @Param("userId") Long userId,
                                                               @Param("borrowId") Long borrowMainId, @Param("sceneTypes") List<Integer> sceneTypes);

	/**
	 * <p>根据给定条件查询订单使用的场景及租房收入证明合同</p>
	 * <p>用户单击订单时需要展示该订单所关联的场景及租收入证明附件信息</p>
	 * @param targetMode 待查询的目标对象
	 * @param userId  用户id
	 * @param sceneTypes 场景
	 * @return List<SysAttachment>
	 * */
	List<SysAttachment> queryAttachmentByWithUserIdParams(@Param("targetMode") AttachmentExtra targetMode, @Param("userId") Long userId, @Param("sceneTypes") List<Integer> sceneTypes);


	/**
	 * <p>根据给定条件把对应的附件记录置为失效(非正常-逻辑删除)</p>
	 * @param targetMode 待查询的目标对象
	 * @param classifys  待查询的分类
	 * @param remarks    修改后的remarks
	 * @return 受影响的行数
	 * */
	Integer updateAttachmentExpireByParameter(@Param("targetMode") AttachmentExtra targetMode, @Param("classifys") List<Integer> classifys, @Param("remarks") String remarks);

	/**
	 * <p>根据给定主键idList集查询附件</p>
	 * @param idList
	 * @return List<SysAttachment>
	 * */
	List<SysAttachment> queryByIdList(@Param("idList") Long... idList);

	/***
	 * <p>根据给定idList更新目标对象</p>
	 * @param targetMode
	 * @param idList
	 * @return
	 * */
	int updateTargetByIdList(@Param("targetMode") AttachmentExtra targetMode, @Param("idList") List<Long> idList);
	
}
