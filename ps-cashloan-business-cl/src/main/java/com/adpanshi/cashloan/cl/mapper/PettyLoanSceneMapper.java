
package com.adpanshi.cashloan.cl.mapper;

import com.adpanshi.cashloan.cl.domain.PettyLoanScene;
import com.adpanshi.cashloan.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.core.common.mapper.RDBatisDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author qing.yunhui 
 * @Since 2011-2017
 * @create 2017-12-03 20:30:04
 * @history
 */
@RDBatisDao
public interface PettyLoanSceneMapper extends BaseMapper<PettyLoanScene,Long>{
	
	/**
	 * <p>根据userId查询</p>
	 * @param userId
	 * @return PettyLoanScene
	 * */
	PettyLoanScene findpettyLoanSceneByUserId(Long userId);
	
	/**
	 * <p>根据给定用户查询最新的一条场景</p>
	 * @param userId 
	 * @param sceneTypes
	 * @param status
	 * @return PettyLoanScene
	 * */
	PettyLoanScene queryPettyLoanSceneByLast(@Param("userId") Long userId, @Param("sceneTypes") List<Integer> sceneTypes, @Param("status") Integer status);

	/**
	 * <p>更新指定用户对应的小额贷场景状态为失效</p>
	 * @param userId
	 * @param sceneTypes
	 * @return 受影响的行数
	 * */
	int updateLoanSceneExpiryByUserIdWithSceneType(@Param("userId") Long userId, @Param("sceneTypes") List<Integer> sceneTypes);

	/**
	 * <p>通过userId、borrowMainId、sceneType反查使用的租房场景</p>
	 * @param userId 用户id
	 * @param borrowMainId 借款订单id
	 * @param sceneTypes 场景类型
	 * @return PettyLoanScene
	 * */
	PettyLoanScene queryLoanSceneByBorIdScenTypeWithUserId(@Param("userId") Long userId, @Param("borrowId") Long borrowMainId, @Param("sceneTypes") List<Integer> sceneTypes);

	/**
	 * <p>查询指定用户的附件集</p>
	 * @param userId
	 * @return
	 * */
	List<PettyLoanScene> queryByUserId(Long userId);

	/**
	 * <p>根据给定用户查询用户最近一次提交的场景</p>
	 * @param userId
	 * @return
	 * */
	PettyLoanScene getPettyLoanSceneLastByUserId(@Param("userId") Long userId, @Param("sceneTypes") List<Integer> sceneTypes);
	/**
	 * <p>根据给定用户及主订单id查询用户最近一次提交的场景</p>
	 * @param userId
	 * @param borrowMainId
	 * @param sceneTypes
	 * @return
	 * */
	PettyLoanScene getPettyLoanSceneLastByUserIdWithBorrowMainId(@Param("userId") Long userId, @Param("borrowMainId") Long borrowMainId, @Param("sceneTypes") List<Integer> sceneTypes);

	/**
	 * 根据给定用户查询最新的一条场景-且场景没有关联借款订单的记录(借款前)
	 * @param userId
	 * @param sceneTypes
	 * @return
	 */
	PettyLoanScene queryPettyLoanSceneByBeforBorrow(@Param("userId") Long userId, @Param("sceneTypes") List<Integer> sceneTypes);
}
