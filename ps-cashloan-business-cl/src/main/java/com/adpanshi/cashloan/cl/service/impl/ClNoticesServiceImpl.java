package com.adpanshi.cashloan.cl.service.impl;

import com.adpanshi.cashloan.cl.domain.AppMsgTpl;
import com.adpanshi.cashloan.cl.mapper.AppMsgTplMapper;
import com.adpanshi.cashloan.cl.mapper.BorrowRepayMapper;
import com.adpanshi.cashloan.cl.model.BorrowRepayModel;
import com.adpanshi.cashloan.cl.service.ClNoticesService;
import com.adpanshi.cashloan.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.core.common.service.impl.BaseServiceImpl;
import com.adpanshi.cashloan.core.common.util.DateUtil;
import com.adpanshi.cashloan.core.domain.BorrowMain;
import com.adpanshi.cashloan.core.domain.Notices;
import com.adpanshi.cashloan.core.mapper.BorrowMainMapper;
import com.adpanshi.cashloan.core.mapper.NoticesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: fenqidai
 * @description: 个人消息服务实现
 * @author: Mr.Wange
 * @create: 2018-07-25 14:45
 **/
@Service("clNoticesService")
public class ClNoticesServiceImpl extends BaseServiceImpl<Notices,Long> implements ClNoticesService {
    public static final Logger logger = LoggerFactory.getLogger(ClNoticesServiceImpl.class);
    @Resource
    private BorrowMainMapper borrowMainMapper;
    @Resource
    private AppMsgTplMapper appMsgTplMapper;
    @Resource
    private NoticesMapper noticesMapper;
    @Resource
    private BorrowRepayMapper borrowRepayMapper;

    /**
     * 主动还款消息
     *
     * @param userId
     * @param borrowMainId
     * @param settleTime
     * @param repayAmount
     * @throws
     * @method: activePayment
     * @return: void
     * @Author: Mr.Wange
     * @Date: 2018/7/25 14:44
     */
    @Override
    public void activePayment(long userId, long borrowMainId, Date settleTime, Double repayAmount) {
        try{
            BorrowMain borrowMain = borrowMainMapper.findById(borrowMainId);
            if( borrowMain!= null){
                Map<String, Object> searchTpl = new HashMap<>(16);
                searchTpl.put("type", "activePayment");
                searchTpl.put("state", "10");
                AppMsgTpl tpl = appMsgTplMapper.findSelective(searchTpl);
                String[] date = settleTime.toString().split(" ");
                String time = date[1]+" "+date[2]+" "+date[5];
                String content = tpl.getContent().replace("{$bank}",borrowMain.getBank())
                        .replace("{$cardNo}",borrowMain.getCardNo().substring(borrowMain.getCardNo().length()-4))
                        .replace("{$loan}",repayAmount+"")
                        .replace("{$time}",time);
                save(userId,tpl.getName(),content);
            }
        } catch (Exception e) {
            logger.error("生成消息异常，异常原因:",e);
        }
    }

    /**
     * 审核未通过
     *
     * @param userId
     * @param day
     * @throws
     * @method: refuse
     * @return: void
     * @Author: Mr.Wange
     * @Date: 2018/7/25 15:59
     */
    @Override
    public void refuse(long userId, int day) {
        try{
            Map<String, Object> searchTpl = new HashMap<>(16);
            searchTpl.put("type", "refuse");
            searchTpl.put("state", "10");
            AppMsgTpl tpl = appMsgTplMapper.findSelective(searchTpl);
            String content = tpl.getContent().replace("{$day}",day+"");
            save(userId,tpl.getName(),content);
        } catch (Exception e) {
            logger.error("生成消息异常，异常原因:",e);
        }
    }

    /**
     * 放款
     *
     * @param userId
     * @param borrowMainId
     * @param amount
     * @throws
     * @method: payment
     * @return: void
     * @Author: Mr.Wange
     * @Date: 2018/7/25 16:13
     */
    @Override
    public void payment(Long userId, Long borrowMainId, Double amount) {
        try{
            BorrowMain borrowMain = borrowMainMapper.findById(borrowMainId);
            if(borrowMain != null){
                Map<String, Object> searchTpl = new HashMap<>(16);
                searchTpl.put("type", "payment");
                searchTpl.put("state", "10");
                AppMsgTpl tpl = appMsgTplMapper.findSelective(searchTpl);
                String[] date = borrowMain.getRepayTime().toString().split(" ");
                String time = date[1]+" "+date[2]+" "+date[5];
                String content = tpl.getContent().replace("{$loan}",amount+"")
                        .replace("{$time}",time);
                save(userId,tpl.getName(),content);
            }
        } catch (Exception e) {
            logger.error("生成消息异常，异常原因:",e);
        }
    }

    /**
     * 逾期
     *
     * @param borrowId
     * @param userId
     * @throws
     * @method: overdue
     * @return: void
     * @Author: Mr.Wange
     * @Date: 2018/7/25 16:25
     */
    @Override
    public void overdue(long borrowId, long userId) {
        try{
            BorrowRepayModel brm = borrowRepayMapper.findOverdue(borrowId);
            if(brm != null){
                Map<String, Object> searchTpl = new HashMap<>(16);
                searchTpl.put("type", "overdue");
                searchTpl.put("state", "10");
                AppMsgTpl tpl = appMsgTplMapper.findSelective(searchTpl);
                String content = tpl.getContent().replace("{$day}",brm.getPenaltyDay());
                save(userId,tpl.getName(),content);
            }
        } catch (Exception e) {
            logger.error("生成消息异常，异常原因:",e);
        }
    }

    /**
     * 还款提醒
     *
     * @param userId
     * @param borrowId
     * @throws
     * @method: repayBefore
     * @return: void
     * @Author: Mr.Wange
     * @Date: 2018/7/25 16:29
     */
    @Override
    public void repayBefore(long userId, long borrowId) {
        try{
            BorrowRepayModel brm = borrowRepayMapper.findOverdue(borrowId);
            if(brm != null){
                Map<String, Object> searchTpl = new HashMap<>(16);
                searchTpl.put("type", "repayBefore");
                searchTpl.put("state", "10");
                AppMsgTpl tpl = appMsgTplMapper.findSelective(searchTpl);
                String[] date = brm.getRepayTime().toString().split(" ");
                String time = date[1]+" "+date[2]+" "+date[5];
                String content = tpl.getContent().replace("{$time}",time)
                        .replace("{$loan}",brm.getAmount()+"");
                save(userId,tpl.getName(),content);
            }
        } catch (Exception e) {
            logger.error("生成消息异常，异常原因:",e);
        }
    }

    private void save(Long userId, String title, String content) throws Exception{
        Notices notices = new Notices();
        notices.setUserId(userId);
        notices.setTitle(title);
        notices.setType("1");
        notices.setContent(content);
        notices.setCreateTime(DateUtil.getNow());
        notices.setState("10");
        noticesMapper.save(notices);
    }
    @Override
    public BaseMapper<Notices, Long> getMapper() {
        return noticesMapper;
    }
}
