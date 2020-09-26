package com.nls.app.security.controller;

import java.awt.event.WindowStateListener;
import java.net.URISyntaxException;
import java.util.Map;

import org.springframework.util.StringUtils;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;
import org.zkoss.zul.Button;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.nls.app.security.model.User;
import com.nls.app.security.service.UserService;

@VariableResolver(DelegatingVariableResolver.class)
public class UserFormController extends SelectorComposer<Component> {
	@Wire
	private Textbox txtId;
	@Wire
	private Textbox txtUsername;
	@Wire
	private Textbox txtFullname;
	@Wire
	private Button btnSave;
	@Wire
	private Button btnCancel;
	@Wire
	private Window userform;
	private User user;
	@WireVariable
	private UserService userService;
	private String action;

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		// TODO Auto-generated method stub
		super.doAfterCompose(comp);
		
		Map<?, ?> params = Executions.getCurrent().getArg();
		action = (String) params.get("action");

		if (action.equals("EDIT")) {
			user = (User) params.get("user");

			bindUI();
		} else if (action.equals("ADD")) {

		}

	}

	public User getUser() {
		if (user == null)
			user = new User();
		return user;
	}

	@Listen("onClick=#btnSave")
	public void saveClick() throws URISyntaxException {
		if (!validate())
			return;
		bindModel();

		if (action.equals("EDIT")) {
			userService.updateUser(user);
			Clients.showNotification("Success edit user : " + user.getFullname());
			userform.detach();
		} else {
			User newUser = userService.addUser(user);
			Clients.showNotification("Success add new user : " + newUser.getFullname());
			userform.detach();
		}

	}

	@Listen("onClick=#btnCancel")
	public void cancelClick() {
		userform.detach();
	}
	


	private void bindModel() {
		User user = getUser();
		user.setId(Long.parseLong(txtId.getValue()));
		user.setUsername(txtUsername.getValue());
		user.setFullname(txtFullname.getValue());
	}

	private void bindUI() {
		// TODO Auto-generated method stub
		txtId.setValue(user.getId().toString());
		txtUsername.setValue(user.getUsername());
		txtFullname.setValue(user.getFullname());
		if (action.equals("EDIT")) {
			txtId.setReadonly(true);
		}
	}

	private boolean validate() {
		if (StringUtils.isEmpty(txtId.getValue())) {
			Clients.showNotification("id is require", "error", null, null, 0);
			return false;
		}
		if (StringUtils.isEmpty(txtFullname.getValue())) {
			Clients.showNotification("fullname is require", "error", null, null, 0);
			return false;

		}
		if (StringUtils.isEmpty(txtUsername.getValue())) {
			Clients.showNotification("username is require", "error", null, null, 0);
			return false;

		}

		return true;
	}

}
