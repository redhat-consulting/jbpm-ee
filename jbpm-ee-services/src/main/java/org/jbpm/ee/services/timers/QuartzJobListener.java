package org.jbpm.ee.services.timers;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.jbpm.persistence.timer.GlobalJpaTimerJobInstance;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QuartzJobListener implements JobListener {

	private static final Logger LOG = LoggerFactory.getLogger(QuartzJobListener.class);
	
	private String name;
	
	@Override
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public void jobToBeExecuted(JobExecutionContext context) {

		if(LOG.isDebugEnabled()) {
			LOG.debug("JBPM Job Listener (To Be Executed): "+context.toString());
			LOG.debug("JBPM Job Detail: "+context.getJobDetail().toString());
			
			if(context.getJobDetail() != null && context.getJobDetail().getJobDataMap().containsKey("timerJobInstance")) {
				GlobalJpaTimerJobInstance quartzHandler = (GlobalJpaTimerJobInstance)context.getJobDetail().getJobDataMap().get("timerJobInstance");
				LOG.debug("JBPM Quartz Handler: "+ReflectionToStringBuilder.toString(quartzHandler));
			}
		}
		
	}

	@Override
	public void jobExecutionVetoed(JobExecutionContext context) {
		LOG.debug("JBPM Job Listener (Vetoed): "+context.toString());
	}

	@Override
	public void jobWasExecuted(JobExecutionContext context,
			JobExecutionException jobException) {
		LOG.debug("JBPM Job Listener (Was Executed): "+context.toString()+" -> "+jobException);
	}


}
