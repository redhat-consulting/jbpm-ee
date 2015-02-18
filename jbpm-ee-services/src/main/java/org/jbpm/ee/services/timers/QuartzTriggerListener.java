package org.jbpm.ee.services.timers;

import org.quartz.JobExecutionContext;
import org.quartz.Trigger;
import org.quartz.TriggerListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QuartzTriggerListener implements TriggerListener {

	private String name;
	
	private static final Logger LOG = LoggerFactory.getLogger(QuartzTriggerListener.class);
	
	@Override
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public void triggerComplete(Trigger trigger, JobExecutionContext context, int triggerInstructionCode) {
		LOG.debug("JBPM Trigger Listener (Completed): "+trigger.toString()+" -> "+context.toString()+" -> "+triggerInstructionCode);
		
	}

	@Override
	public void triggerFired(Trigger trigger, JobExecutionContext context) {
		LOG.debug("JBPM Trigger Listener (Fired): "+trigger.toString()+" -> "+context.toString());
		
	}

	@Override
	public void triggerMisfired(Trigger trigger) {
		LOG.debug("JBPM Trigger Listener (Misfired): "+trigger.toString());
		
	}

	@Override
	public boolean vetoJobExecution(Trigger trigger, JobExecutionContext context) {
		return false;
	}

}
