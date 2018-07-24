package com.adpanshi.cashloan.business.cl.service.impl;

import com.adpanshi.cashloan.business.cl.domain.BorrowAgreement;
import com.adpanshi.cashloan.business.cl.domain.BorrowRepay;
import com.adpanshi.cashloan.business.cl.domain.BorrowStagesAgreement;
import com.adpanshi.cashloan.business.cl.domain.StagesDetail;
import com.adpanshi.cashloan.business.cl.enums.SysDictEnum;
import com.adpanshi.cashloan.business.cl.extra.BorrowIntent;
import com.adpanshi.cashloan.business.cl.extra.HandleBorrowIntent;
import com.adpanshi.cashloan.business.cl.mapper.BorrowAgreementMapper;
import com.adpanshi.cashloan.business.cl.mapper.BorrowRepayMapper;
import com.adpanshi.cashloan.business.cl.model.TemplateInfoModel;
import com.adpanshi.cashloan.business.cl.service.BorrowAgreementService;
import com.adpanshi.cashloan.business.core.common.context.Constant;
import com.adpanshi.cashloan.business.core.common.context.Global;
import com.adpanshi.cashloan.business.core.common.util.DateUtil;
import com.adpanshi.cashloan.business.core.enjoysign.constants.EnjoysignConstant;
import com.adpanshi.cashloan.business.core.mapper.UserBaseInfoMapper;
import com.adpanshi.cashloan.business.system.domain.SysDictDetail;
import com.adpanshi.cashloan.business.system.mapper.SysDictDetailMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by cc on 2017/7/25.
 */
@Service("borrowAgreementCService")
public class BorrowAgreementServiceImpl implements BorrowAgreementService {

    static final Logger logger = LoggerFactory.getLogger(BorrowAgreementServiceImpl.class);

    @Resource private BorrowAgreementMapper borrowAgreementMapper;
    @Resource private UserBaseInfoMapper userBaseInfoMapper;
    @Resource private SysDictDetailMapper sysDictDetailMapper;
    @Resource private BorrowRepayMapper borrowRepayMapper;



    @Override
    public BorrowAgreement findByBowMainIdWithUserId(Long borrowMainId) {
        return borrowAgreementMapper.findByBowMainIdWithUserId(borrowMainId);
    }

    @Override
    public BorrowStagesAgreement findByBowMainId(Long borrowMainId) {
        BorrowAgreement agreement=findByBowMainIdWithUserId(borrowMainId);
        BorrowStagesAgreement stagesAgreement=new BorrowStagesAgreement();
        stagesAgreement.setSignatureDate(agreement.getLoanTime());//三方签署日期
        //甲方(借款方)
        stagesAgreement.setBorName(agreement.getRealName());
        stagesAgreement.setBorIDCard(agreement.getIdNo());
        stagesAgreement.setBorPhone(agreement.getPhone());
        stagesAgreement.setBorEmail(agreement.getEmail());
        stagesAgreement.setBorSignature(agreement.getRealName());
        //乙方(出借方)
        stagesAgreement.setLenderName(Global.getValue(Constant.BUSINESS_ENTITY));
        stagesAgreement.setLenderIDCard(Global.getValue(Constant.BUSINESS_ENTITY_IDCARD));
        stagesAgreement.setLenderPartySign(Global.getValue(Constant.BUSINESS_ENTITY));
        //丙方(居间服务方)
        stagesAgreement.setSignOrderNo(agreement.getOrderNo());
        stagesAgreement.setBorMoney(agreement.getRealAmount()+"");
        stagesAgreement.setBorDays(agreement.getTimeLimit());
        stagesAgreement.setBorStartDate(agreement.getLoanTime());
        stagesAgreement.setBorEndDate(agreement.getRepayTime());
        SysDictDetail dictDetail= sysDictDetailMapper.getDetailByTypeCodeWithItemCode(SysDictEnum.TYPE_CODE.BORROW_TYPE.getCode(), agreement.getBorrowType()+"");
        BorrowIntent borrowIntent= HandleBorrowIntent.dictDetailTransformBorrowIntent(dictDetail);
        stagesAgreement.setBorIntent(null==borrowIntent?"":borrowIntent.getData().getTitle());
        stagesAgreement.setBorAnnualRate(Global.getValue(EnjoysignConstant.BOR_ANNUAL_RATE));
        stagesAgreement.setBorServiceFee(Global.getValue(EnjoysignConstant.BOR_SERVICE_FEE));
        stagesAgreement.setPenaltyRate(Global.getValue(EnjoysignConstant.PENALTY_RATE));
        TemplateInfoModel template=new TemplateInfoModel().parseTemplateInfo(agreement.getRealAmount(),agreement.getTemplateInfo());
        stagesAgreement.setTotalStages(template.getStages()+"");
        stagesAgreement.setStagesDays(template.getCycle()+"");
        stagesAgreement.setServicePartySignature(Global.getValue(Constant.COMPANY_EMAIL));
        //--------------------->分期明细--------------------->
        List<BorrowRepay> borRepayList= borrowRepayMapper.queryBorrowRepayByBorMainId(borrowMainId);
        LinkedList<StagesDetail> stagesDetails=new LinkedList<>();
        for(BorrowRepay repay:borRepayList){
            StagesDetail detail=new StagesDetail(DateUtil.dateStr6(repay.getRepayTime()), repay.getAmount()+"");
            stagesDetails.add(detail);
        }
        stagesAgreement.setStages(stagesDetails);
        return stagesAgreement;
    }
}
