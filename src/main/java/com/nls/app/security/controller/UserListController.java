package com.nls.app.security.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;
import org.zkoss.zul.Button;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Window;

import com.nls.app.security.model.User;
import com.nls.app.security.service.UserService;

@VariableResolver(DelegatingVariableResolver.class)
public class UserListController extends SelectorComposer<Component> {

	@Wire
	private Button btnSearch;
	@Wire
	private Listbox listData;
	@Wire
	private Button btnAdd;

	@WireVariable
	private UserService userService;

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

	@Listen("onClick=#btnAdd")
	public void addClick() {
		Map<String, Object> params = new HashMap<>();

		params.put("action", "ADD");
		Window window = (Window) Executions.createComponents("~./zul/user/user-form.zul", null, params);
		window.doModal();

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
				Listcell action = new Listcell();
				Button btnDelete = new Button("DELETE");
				btnDelete.addEventListener(Events.ON_CLICK, new EventListener<Event>() {

					@Override
					public void onEvent(Event event) throws Exception {
						// TODO Auto-generated method stub
						userService.deleteUser(data);
						Clients.showNotification(data.getUsername() + " DELETED");
						renderListbox(userService.getUsers());
					}
				});
				btnDelete.setParent(action);

				item.appendChild(id);
				item.appendChild(username);
				item.appendChild(fullname);
				item.appendChild(action);

				item.addEventListener(Events.ON_DOUBLE_CLICK, new EventListener<Event>() {

					@Override
					public void onEvent(Event event) throws Exception {
						Map<String, Object> params = new HashMap<>();
						params.put("user", data);
						params.put("action", "EDIT");

						// TODO Auto-generated method stub
						Clients.showNotification("Hello World: " + data.getUsername());
						Window window = (Window) Executions.createComponents("~./zul/user/user-form.zul", null, params);
						window.doModal();

					}

				});
			}

		});

	}
}
