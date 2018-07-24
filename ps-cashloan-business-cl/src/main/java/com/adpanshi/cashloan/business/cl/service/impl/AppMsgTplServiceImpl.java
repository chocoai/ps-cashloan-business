package com.adpanshi.cashloan.business.cl.service.impl;

import com.adpanshi.cashloan.business.cl.domain.AppMsgTpl;
import com.adpanshi.cashloan.business.cl.domain.UserDeviceTokens;
import com.adpanshi.cashloan.business.cl.domain.appMsgPushLog;
import com.adpanshi.cashloan.business.cl.enums.UserDeviceTokensStateEnum;
import com.adpanshi.cashloan.business.cl.mapper.AppMsgPushLogMapper;
import com.adpanshi.cashloan.business.cl.mapper.AppMsgTplMapper;
import com.adpanshi.cashloan.business.cl.service.AppMsgTplService;
import com.adpanshi.cashloan.business.cl.service.UserDeviceTokensService;
import com.adpanshi.cashloan.business.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.business.core.common.service.impl.BaseServiceImpl;
import com.adpanshi.cashloan.business.core.mapper.UserMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author qing.yunhui
 * @Since 2011-2017
 * @create 2017-09-01 16:47:13
 * @history
 * @updateDate 2018-01-06
 * @updator huangqin
 */
@Service("appMsgTplService")
public class AppMsgTplServiceImpl extends BaseServiceImpl<AppMsgTpl, Long> implements AppMsgTplService {

    private Logger logger = LoggerFactory.getLogger(getClass());
    private HttpServletResponse response;
    @Resource
    private AppMsgTplMapper appMsgTplMapper;

    @Resource
    private AppMsgPushLogMapper appMsgPushLogMapper;

    @Resource
    private UserMapper userMapper;

    @Resource
    UserDeviceTokensService userDeviceTokensService;

    @Override
    public BaseMapper<AppMsgTpl, Long> getMapper() {
        return appMsgTplMapper;
    }

    public AppMsgTplServiceImpl() {
    }

    public AppMsgTplServiceImpl(HttpServletRequest request, HttpServletResponse response) throws Exception {
        this.response = response;
    }


    @Override
    public AppMsgTpl getByTypeWithState(Integer type, Integer state) {
        if (null == type || null == state) {
            logger.error("------------->Parameter cannot be null. type={},state={}", new Object[]{type, state});
            return null;
        }
        Map<String, Object> map = new HashMap<>();
        map.put("state", state);
        map.put("type", type);
        return appMsgTplMapper.getByTypeWithState(map);
    }

    @Override
    public Page<appMsgPushLog> listModel(Map<String, Object> params, int currentPage, int pageSize) {
        PageHelper.startPage(currentPage, pageSize);
        return (Page<appMsgPushLog>) appMsgPushLogMapper.listSelective(params);
    }

    /**
     * <p>根据给定条件查找最近一次登陆的设备</p>
     *
     * @param userId
     * @return {@link UserDeviceTokens}
     */
    protected UserDeviceTokens getUserDeviceTokens(long userId) {
        return userDeviceTokensService.getLastTimeByUserIdWithState(userId, UserDeviceTokensStateEnum.ENABLE.getCode());
    }


    public void saveUpload(String fileOutPath, String fileName, List<List<String>> arr) {
        // 保存文件
        File file = new File(fileOutPath);
        //目录不存在，则新增目录，
        if (!file.exists()) {
            file.mkdirs();
        }
        file = new File(fileOutPath + File.separator + fileName);
        OutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(file);
            Workbook wb = createWorkBook(arr, "pushMsgPhones_1");
            wb.write(outputStream);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            try {
                if (null != outputStream) {
                    outputStream.close();
                }
            } catch (IOException e) {
                logger.error(e.getMessage());
            }
        }
    }


    /**
     * 创建excel文档，
     * [@param](http://my.oschina.net/u/2303379) list 数据
     *
     * @param list
     * @param title sheet 页名
     */
    public static Workbook createWorkBook(List<List<String>> list, String title) {
        // 创建excel工作簿
        Workbook wb = new XSSFWorkbook();
        // 创建第一个sheet（页），并命名
        Sheet sheet = wb.createSheet(title);
        // 创建第一行
        sheet.createRow((short) 0);
        //设置每行每列的值
        for (short i = 1; i <= list.size(); i++) {
            // Row 行,Cell 方格 , Row 和 Cell 都是从0开始计数的
            // 创建一行，在页sheet上
            Row row = sheet.createRow((short) i);
            List<String> keys = list.get(i - 1);
            // 在row行上创建一个方格
            for (short j = 0; j < keys.size(); j++) {
                Cell cell = row.createCell(j);
                cell.setCellValue(keys.get(j));
            }
        }
        return wb;
    }
}