package org.jbpm.ee.test.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class OrderEligibility implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	private OrderDetails orderDetails = null;
	
	private Boolean orderEligibile = false;
	
	public OrderEligibility() {
		orderDetails = new OrderDetails();
	}
	
	public OrderEligibility(OrderDetails orderDetails) {
		this.orderDetails = orderDetails;
	}

	public OrderDetails getOrderDetails() {
		return orderDetails;
	}

	public void setOrderDetails(OrderDetails orderDetails) {
		this.orderDetails = orderDetails;
	}

	public Boolean getOrderEligibile() {
		return orderEligibile;
	}

	public void setOrderEligibile(Boolean orderEligibile) {
		this.orderEligibile = orderEligibile;
	}
}
