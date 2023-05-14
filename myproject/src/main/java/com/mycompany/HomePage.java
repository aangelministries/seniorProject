package com.mycompany;

import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.TextField;
import service.Service;

public class HomePage extends WebPage {
	private static final long serialVersionUID = 1L;
	private TextField usernameField;
	private TextField passwordField;
	private TextField depositField;
	private TextField dispenseField;
	Label errorLabel;
	private String username;
	int userId;
	private String password;
	private Button loginSubmit;
	private Button logout;
	private Button depositOption;
	private Button dispenseOption;
	private Button depositAction;
	private Button dispenseAction;

	String transactionAmount;

	private WebMarkupContainer loginCont;
	private WebMarkupContainer homeCont;
	private WebMarkupContainer depositCont;
	private WebMarkupContainer dispenseCont;

	public HomePage(final PageParameters parameters) {
		super(parameters);
		addLoginComponents();
		addLogoutButton();
		addHomeComponents();
		addDepositComponents();
		addDispenseComponents();
	}
	public void addLogoutButton(){
		logout = new Button("logout");
		logout.add(new AjaxEventBehavior("click") {
			@Override
			protected void onEvent(AjaxRequestTarget ajaxRequestTarget) {
				loginCont.setVisible(true);
				loginCont.setOutputMarkupId(true);
				ajaxRequestTarget.add(loginCont);
				homeCont.setVisible(false);
				ajaxRequestTarget.add(homeCont);
				depositCont.setVisible(false);
				ajaxRequestTarget.add(depositCont);
				dispenseCont.setVisible(false);
				ajaxRequestTarget.add(dispenseCont);
				logout.setVisible(false);
				ajaxRequestTarget.add(logout);
			}
		});
		logout.setOutputMarkupPlaceholderTag(true);
		logout.setVisible(false);
		add(logout);
	}
	public void addLoginComponents(){
		loginCont = new WebMarkupContainer("loginContainer");
		loginCont.setOutputMarkupId(true);
		add(loginCont);
		errorLabel = new Label("loginError", "Error Logging In");
		errorLabel.setOutputMarkupPlaceholderTag(true);
		errorLabel.setVisible(false);
		loginCont.add(errorLabel);
		loginSubmit = new Button("submit");
		loginSubmit.add(new AjaxEventBehavior("click") {
			@Override
			protected void onEvent(AjaxRequestTarget ajaxRequestTarget) {
				Service service = new Service();
//				int userId = service.getUserLogin(username, password);
//				if(userId!=0) { //todo uncomment for real behavior instead of simple testing
				if(true) {
					homeCont.setVisible(true);
					ajaxRequestTarget.add(homeCont);
					loginCont.setVisible(false);
					ajaxRequestTarget.add(loginCont);
					logout.setVisible(true);
					ajaxRequestTarget.add(logout);
				}
				else {
					errorLabel.setVisible(true);
					ajaxRequestTarget.add(errorLabel);
				}
			}
		});
		loginSubmit.setOutputMarkupId(true);
		loginCont.add(loginSubmit);

		usernameField = new TextField("username", Model.of(""));
		usernameField.setOutputMarkupId(true);
		loginCont.add(usernameField);
		usernameField.add(new AjaxEventBehavior("change") {
			@Override
			protected void onEvent(AjaxRequestTarget ajaxRequestTarget) {
				username = usernameField.getValue();
			}
		});
		passwordField = new TextField("password", Model.of(""));
		passwordField.setOutputMarkupId(true);
		loginCont.add(passwordField);
		passwordField.add(new AjaxEventBehavior("change") {
			@Override
			protected void onEvent(AjaxRequestTarget ajaxRequestTarget) {
				password = passwordField.getValue();
			}
		});
	}

	public void addHomeComponents(){
		homeCont = new WebMarkupContainer("homeContainer");
		homeCont.setOutputMarkupPlaceholderTag(true);
		homeCont.setVisible(false);
		add(homeCont);
		depositOption = new Button("depositOption");
		depositOption.add(
				new AjaxEventBehavior("click") {
					@Override
					protected void onEvent(AjaxRequestTarget ajaxRequestTarget) {
						homeCont.setVisible(false);
						ajaxRequestTarget.add(homeCont);
						depositCont.setVisible(true);
						ajaxRequestTarget.add(depositCont);
					}
				});
		depositOption.setOutputMarkupId(true);
		homeCont.add(depositOption);

		dispenseOption = new Button("dispenseOption");
		dispenseOption.add(
				new AjaxEventBehavior("click") {
					@Override
					protected void onEvent(AjaxRequestTarget ajaxRequestTarget) {
						dispenseCont.setVisible(true);
						ajaxRequestTarget.add(dispenseCont);
						homeCont.setVisible(false);
						ajaxRequestTarget.add(homeCont);
					}
				});
		dispenseOption.setOutputMarkupId(true);
		homeCont.add(dispenseOption);
	}
	public void addDepositComponents(){
		depositCont = new WebMarkupContainer("depositContainer");
		depositCont.setOutputMarkupPlaceholderTag(true);
		depositCont.setVisible(false);
		add(depositCont);
		depositField = new TextField("depositAmount");
		depositField.setOutputMarkupId(true);
		depositCont.add(depositField);
		depositField.add(new AjaxEventBehavior("change") {
			@Override
			protected void onEvent(AjaxRequestTarget ajaxRequestTarget) {
				transactionAmount = depositField.getValue();
			}
		});
		depositAction = new Button("depositAction");
		depositAction.add(
				new AjaxEventBehavior("click") {
					@Override
					protected void onEvent(AjaxRequestTarget ajaxRequestTarget) {
						Service service = new Service();
						service.deposit(Long.parseLong(transactionAmount),userId);
					}
				});
		depositAction.setOutputMarkupId(true);
		depositCont.add(depositAction);
	}
	public void addDispenseComponents() {
		dispenseCont = new WebMarkupContainer("dispenseContainer");
		dispenseCont.setOutputMarkupPlaceholderTag(true);
		dispenseCont.setVisible(false);
		add(dispenseCont);
		dispenseField = new TextField("dispenseAmount");
		dispenseField.setOutputMarkupId(true);
		dispenseField.add(new AjaxEventBehavior("change") {
			@Override
			protected void onEvent(AjaxRequestTarget ajaxRequestTarget) {
				transactionAmount = dispenseField.getValue();
			}
		});
		dispenseCont.add(dispenseField);
		dispenseAction = new Button("dispenseAction");
		dispenseAction.add(
				new AjaxEventBehavior("click") {
					@Override
					protected void onEvent(AjaxRequestTarget ajaxRequestTarget) {
						Service service = new Service();
						boolean showErrorMessage = service.dispense(Long.parseLong(transactionAmount),userId);
						errorLabel.setVisible(showErrorMessage);
						ajaxRequestTarget.add(errorLabel);
					}
				});
		dispenseAction.setOutputMarkupId(true);
		dispenseCont.add(dispenseAction);
	}
}
