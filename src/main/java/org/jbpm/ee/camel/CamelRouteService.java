package org.jbpm.ee.camel;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.camel.builder.RouteBuilder;
import org.drools.core.process.instance.impl.DefaultWorkItemManager;
import org.drools.persistence.info.WorkItemInfo;
import org.hibernate.Query;
import org.hibernate.Session;
import org.jbpm.ee.cdi.JBPMServiceBean;
import org.jbpm.workitem.camel.consumer.CamelConsumerHandler;
import org.kie.api.runtime.process.WorkItem;

@Stateless
public class CamelRouteService {
	
	@PersistenceContext(name="org.jbpm.persistence.jpa", unitName="org.jbpm.persistence.jpa")
	private EntityManager entityManagerMain;

	@Inject
	private JBPMServiceBean jbpmServiceBean;
	
	public void persistCamelRouteToWorkItem(Class workItemHandler, WorkItem workItem) {
		//look up the work item info.
		WorkItemInfo info = entityManagerMain.find(WorkItemInfo.class, workItem.getId());
		
		CamelRouteXWorkItem routeXWorkItem = new CamelRouteXWorkItem();
		routeXWorkItem.setWorkItem(info);
		routeXWorkItem.setWorkItemHandler(workItemHandler.getCanonicalName());
		
		entityManagerMain.persist(routeXWorkItem);
	}
	
	public Collection<RouteBuilder> loadCamelRoutesFromActiveWorkItems() {
		Collection<RouteBuilder> routeBuilders = new HashSet<RouteBuilder>();
		
		Session hibernateSession = (Session)entityManagerMain.getDelegate();

		Query query = hibernateSession.createQuery("from CamelRouteXWorkItem");
		List<CamelRouteXWorkItem> routeXWorkItems = query.list();
		
		for(CamelRouteXWorkItem routeXWorkItem : routeXWorkItems) {
			String workItemHandlerClass = routeXWorkItem.getWorkItemHandler();
			
			//look up the WorkItemHandler from the WorkItemManager.
			DefaultWorkItemManager wim = null;//(DefaultWorkItemManager)jbpmServiceBean.getWorkItemService();
			org.kie.api.runtime.process.WorkItemHandler handler = wim.getWorkItemHandler(workItemHandlerClass);
			
			WorkItem workItem = wim.getWorkItem(routeXWorkItem.getWorkItem().getId());
			
			if(handler instanceof CamelConsumerHandler) {
				routeBuilders.add(((CamelConsumerHandler)handler).createCamelRoute(workItem));
			}
			else {
				//this shouldn't happen.
				throw new RuntimeException("Work Item Handler should be a CamelConsumerHandler.");
			}
			
		}
		
		return routeBuilders;
	}
	
}
