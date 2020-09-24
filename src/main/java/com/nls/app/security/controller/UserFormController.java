package com.nls.app.security.controller;

import java.net.URISyntaxException;

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

	private User user;
	@WireVariable
	private UserService userService;

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		// TODO Auto-generated method stub
		super.doAfterCompose(comp);
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

		User newUser = userService.addUser(user);
		Clients.showNotification("Success add new user : " + newUser.getFullname());
		Executions.sendRedirect("/users");
	}

	@Listen("onClick=#btnCancel")
	public void cancelClick() {
		Executions.sendRedirect("/users");
	}

	private void bindModel() {
		User user = getUser();
		user.setId(Long.parseLong(txtId.getValue()));
		user.setUsername(txtUsername.getValue());
		user.setFullname(txtFullname.getValue());
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
