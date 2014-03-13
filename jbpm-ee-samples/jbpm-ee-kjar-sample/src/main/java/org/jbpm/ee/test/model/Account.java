package org.jbpm.ee.test.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Account implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@XmlElement(name="account-status", required=true)
	private String accountStatus = null;
	
	@XmlElement(name="account-eligibility", required=true)
	private Boolean accountEligible = null;
	
	public Account() {
		accountStatus = "";
		accountEligible = false;
		
	}
	public Account(String accountStatus) {
		this.accountStatus = accountStatus;
		accountEligible = false;
	}

	public Boolean getAccountEligible() {
		return accountEligible;
	}

	public void setAccountEligible(Boolean accountEligibile) {
		this.accountEligible = accountEligibile;
	}

	public String getAccountStatus() {
		return accountStatus;
	}

	public void setAccountStatus(String accountStatus) {
		this.accountStatus = accountStatus;
	}

}
