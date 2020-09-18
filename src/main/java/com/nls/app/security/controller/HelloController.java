package com.nls.app.security.controller;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Label;
import org.zkoss.zul.Textbox;

public class HelloController extends SelectorComposer<Component>{
	@Wire private Label lblName;
	@Wire private Textbox txtName;
	@Wire private Button btnOK;
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		// TODO Auto-generated method stub
		super.doAfterCompose(comp);
		
		lblName.setValue("Hello : ");
	}
	
	@Listen("onClick=#btnOK")
	public void OkClick() {
		lblName.setValue("Hello : "+txtName.getText());
	}
}
