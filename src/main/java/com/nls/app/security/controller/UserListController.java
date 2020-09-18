package com.nls.app.security.controller;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;
import org.zkoss.zul.Button;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

import com.nls.app.security.model.User;
import com.nls.app.security.service.UserService;

@VariableResolver(DelegatingVariableResolver.class)
public class UserListController extends SelectorComposer<Component> {
	
	@Wire private Button btnSearch;
	@Wire private Listbox listData;
	
	@WireVariable private UserService userService;
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		// TODO Auto-generated method stub
		super.doAfterCompose(comp);
		
		List<User> sample = new ArrayList<>();
		
		renderListbox(sample);
	}
	
	@Listen("onClick=#btnSearch")
	public void clickSearch() {
		renderListbox(userService.getUsers());
	}
	private void renderListbox(List<User> users) {
		listData.setModel(new ListModelList<User>(users));
		listData.setItemRenderer(new ListitemRenderer<User>() {

			@Override
			public void render(Listitem item, User data, int index) throws Exception {
				// TODO Auto-generated method stub
				Listcell id = new Listcell(data.getId().toString());
				Listcell username = new Listcell(data.getUsername());
				Listcell fullname = new Listcell(data.getFullname());
				
				item.appendChild(id);
				item.appendChild(username);
				item.appendChild(fullname);
			}
			
		});
		
	}
}
