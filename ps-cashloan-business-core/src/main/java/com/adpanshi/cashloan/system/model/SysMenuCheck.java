package com.adpanshi.cashloan.system.model;

import com.adpanshi.cashloan.system.domain.SysMenu;

import java.util.List;

/**
 * @author 8452
 */
public class SysMenuCheck extends SysMenu {

	private static final long serialVersionUID = 73161925067707896L;

	private boolean checked;
	  
	private List<SysMenuCheck> children;

	public List<SysMenuCheck> getChildren() {
		return children;
	}

	public void setChildren(List<SysMenuCheck> children) {
		this.children = children;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}
     
	public SysMenuCheck(){
		
	}
}
