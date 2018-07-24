package com.adpanshi.cashloan.business.system.service.impl;

import com.adpanshi.cashloan.business.core.common.exception.ServiceException;
import com.adpanshi.cashloan.business.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.business.core.common.service.impl.BaseServiceImpl;
import com.adpanshi.cashloan.business.core.common.util.ListUtil;
import com.adpanshi.cashloan.business.core.common.util.MapUtil;
import com.adpanshi.cashloan.business.core.common.util.StringUtil;
import com.adpanshi.cashloan.business.system.domain.SysMenu;
import com.adpanshi.cashloan.business.system.mapper.SysMenuMapper;
import com.adpanshi.cashloan.business.system.mapper.SysRoleMenuMapper;
import com.adpanshi.cashloan.business.system.service.SysMenuService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(value = "sysMenuServiceImpl")
public class SysMenuServiceImpl extends BaseServiceImpl<SysMenu, Long> implements SysMenuService {

    @Resource
    private SysMenuMapper sysMenuMapper;

    @Resource
    private SysRoleMenuMapper sysRoleMenuMapper;

    protected String getLoginName() {
        // 增加用户登录判断
        UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user.getUsername();
    }

    @Override
    public BaseMapper<SysMenu, Long> getMapper() {
        return sysMenuMapper;
    }

    public List queryTreeNodeIds(String leafIds) {
        List<Map<String, Object>> parents;
        String menuLeafIds = leafIds;
        String[] Ids = menuLeafIds.split(",");
        List rIds = new ArrayList();
        do {
            parents = sysMenuMapper.getMenuParentId(Ids);
            rIds.addAll(parents);
            menuLeafIds = StringUtil.toString(MapUtil.collectProperty(parents, "id", false));
            Ids = menuLeafIds.split(",");
        } while (!parents.isEmpty());
        List rlist = MapUtil.collectProperty(rIds, "id", false);
        for (String id : leafIds.split(",")) {
            rlist.add(id);
        }
        return rlist;
    }

    @Override
    public List<Map<String, Object>> fetchRoleMenus(String sysType, Long roleId) {
        Map<String,Object> serchParam=new HashMap<>();
        serchParam.put("roleId",roleId);
        serchParam.put("isMenu",1);
        //根据id查询菜单
        List<Map<String, Object>> menuList = sysMenuMapper.getMenuByRoleId(serchParam);
        //将菜单按照（父节点ID）value=（子节点的父节点ID）parentId排列成树
        menuList = ListUtil.list2Tree(menuList, "value", "parentId");
        serchParam.put("isMenu",0);
        //获取角色所有的权限
        List<Map<String, Object>> btnPerms = sysMenuMapper.getMenuByRoleId(serchParam);
        for (Map<String, Object> menuMap : menuList) {
            List<Map> children = (List<Map>) menuMap.get("children");
            if(null == children || children.size() <= 0 ){
                continue;
            }
            for (Map mp : children) {
                //根据 菜单menuId 查询菜单下的按钮权限
                long menuId = Long.parseLong(mp.get("value").toString());
                if (menuId <= 0) continue;
                Map<String,ArrayList<Map<String, Object>>> btnMap=new HashMap<>();
                for (Map<String, Object> btnPerm : btnPerms) {
                    long roleMenuId = Long.parseLong(String.valueOf(
                            null == btnPerm.get("parentId") ? "0" : btnPerm.get("parentId")));
                    if (roleMenuId <= 0) continue;
                    if (roleMenuId == menuId) {
                        ArrayList<Map<String, Object>> btn=btnMap.get(String.valueOf(
                              null == btnPerm.get("menuType") ?"0" : btnPerm.get("menuType")));
                        if(null != btn){
                            btn.add(btnPerm);
                        }else{
                            btn = new ArrayList<>();
                            btn.add(btnPerm);
                        }
                        btnMap.put(String.valueOf(btnPerm.get("menuType")),btn);
                    }
                }
                mp.put("BtnPermList", btnMap);
            }
        }
        return menuList;
    }

    @Override
    public boolean updateRoleMenuHas(Long roleId, Long... menuIds) {
        //删除原有的关系
        sysRoleMenuMapper.deleteByRoleId(roleId.longValue());
        //批量新增
        return sysRoleMenuMapper.saveBantch(roleId.longValue(),menuIds) > 0;
    }

    /**
     * @param roleId
     * @return List<Map<String,Object>>
     * @throws ServiceException
     * @description 菜单页的查询
     * @since 1.0.0
     */
    @Override
    public List<Map<String, Object>> fetchRoleMenuHas(Long roleId) {
        return sysMenuMapper.fetchRoleMenuHas(roleId);
    }

    @Override
    public List<Map<String, Object>> fetchRolebtnHas(Long roleId) {
        return sysMenuMapper.fetchRolebtnHas(roleId);
    }
}