package org.jbpm.ee.services.ejb.remote;

import javax.ejb.Remote;

import org.jbpm.ee.services.AsyncCommandExecutor;

@Remote
public interface AsyncCommandExecutorRemote extends AsyncCommandExecutor {

}