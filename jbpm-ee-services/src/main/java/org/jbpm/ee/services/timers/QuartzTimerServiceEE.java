package org.jbpm.ee.services.timers;

import java.util.Collection;

import javax.ejb.Singleton;
import javax.ejb.Startup;

import org.drools.core.time.InternalSchedulerService;
import org.drools.core.time.Job;
import org.drools.core.time.JobContext;
import org.drools.core.time.JobHandle;
import org.drools.core.time.TimerService;
import org.drools.core.time.Trigger;
import org.drools.core.time.impl.TimerJobInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
@Startup
public class QuartzTimerServiceEE implements TimerService, InternalSchedulerService {
	private static final Logger LOG = LoggerFactory.getLogger(QuartzTimerServiceEE.class);
	
	
	@Override
	public JobHandle scheduleJob(Job job, JobContext ctx, Trigger trigger) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean removeJob(JobHandle jobHandle) {
		return false;
	}

	@Override
	public long getCurrentTime() {
		return System.currentTimeMillis();
	}

	@Override
	public void shutdown() {
		LOG.info("Shutting down the timer service.");
	}

	@Override
	public long getTimeToNextJob() {
		return 0;
	}

	@Override
	public Collection<TimerJobInstance> getTimerJobInstances(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void internalSchedule(TimerJobInstance timerJobInstance) {
		// TODO Auto-generated method stub
		
	}

}
