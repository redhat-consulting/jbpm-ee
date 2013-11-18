package org.jbpm.ee.services.ejb.model;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.jbpm.ee.services.ejb.model.task.Group;
import org.jbpm.ee.services.ejb.model.task.PeopleAssignments;
import org.jbpm.ee.services.ejb.model.task.TaskAttachment;
import org.jbpm.ee.services.ejb.model.task.TaskComment;
import org.jbpm.ee.services.ejb.model.task.TaskData;
import org.jbpm.ee.services.ejb.model.task.User;
import org.kie.api.task.model.Attachment;
import org.kie.api.task.model.Comment;
import org.kie.api.task.model.Content;
import org.kie.api.task.model.I18NText;
import org.kie.api.task.model.Task;
import org.kie.api.task.model.TaskSummary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TaskFactory {
	private static final Logger LOG = LoggerFactory.getLogger(TaskFactory.class);

	public static Content convert(Content content) {
		if(content == null) {
			return null;
		}
		
		return new org.jbpm.ee.services.ejb.model.task.Content(content);
	}
	
	
	public static TaskSummary convert(TaskSummary summary) {
		if(summary == null) {
			return null;
		}
		LOG.info("Converting task summary: "+summary.toString());
		return new org.jbpm.ee.services.ejb.model.task.TaskSummary(summary);
	}
	
	public static List<TaskSummary> convertTaskSummaries(List<TaskSummary> summaries) {
		if(summaries == null) {
			return null;
		}

		List<TaskSummary> result = new LinkedList<TaskSummary>();
		for(TaskSummary summary : summaries) {
			TaskSummary s = convert(summary);
			LOG.info("Convered: "+s);
			result.add(s);
		}
		
		return result;
	}
	
	public static Task convert(Task task) {
		if(task == null) {
			return null;
		}
		
		return new org.jbpm.ee.services.ejb.model.task.Task(task);
	}
	

	
	public static org.kie.api.task.model.Group convert(org.kie.api.task.model.Group group) {
		if(group == null) {
			return null;
		}
		
		return new Group(group);
	}
	
	
	public static org.kie.api.task.model.User convert(org.kie.api.task.model.User user) {
		if(user == null) {
			return null;
		}
		
		return new User(user);
	}
	
	public static org.jbpm.ee.services.ejb.model.task.I18NText convert(I18NText original) {
		if(original == null) {
			return null;
		}
		return new org.jbpm.ee.services.ejb.model.task.I18NText(original);
	}

	public static org.kie.api.task.model.TaskData convert(org.kie.api.task.model.TaskData original) {
		if(original == null) {
			return null;
		}
		return new TaskData(original);
	}
	
	public static List<I18NText> convertI18NTextList(List<I18NText> original) {
		if(original == null) {
			return null;
		}
		
		List<I18NText> result = new LinkedList<I18NText>();
		for(I18NText text : original) {
			result.add(convert(text));
		}
		
		return Collections.unmodifiableList(result);
	}
	
	public static PeopleAssignments convert(org.kie.api.task.model.PeopleAssignments original) {
		if(original == null) {
			return null;
		}
		
		return new PeopleAssignments(original);
	}
	
	public static List<org.kie.api.task.model.OrganizationalEntity> convertOrganizationalEntityList(List<org.kie.api.task.model.OrganizationalEntity> original) {
		if(original == null) {
			return null;
		}
		
		List<org.kie.api.task.model.OrganizationalEntity> result = new LinkedList<org.kie.api.task.model.OrganizationalEntity>();
		for(org.kie.api.task.model.OrganizationalEntity text : original) {
			if(text instanceof org.kie.api.task.model.User) {
				result.add(convert((org.kie.api.task.model.User)text));
			}
			else if(text instanceof org.kie.api.task.model.Group) {
				result.add(convert((org.kie.api.task.model.Group)text));
			}
			else {
				LOG.warn("Unhandled type: "+text.getClass());
			}
		}
		
		return Collections.unmodifiableList(result);
	}
	
	
	

	public static List<org.kie.api.task.model.TaskData> convertTaskData(List<org.kie.api.task.model.TaskData> original) {
		if(original == null) {
			return null;
		}
		
		List<org.kie.api.task.model.TaskData> result = new LinkedList<org.kie.api.task.model.TaskData>();
		for(org.kie.api.task.model.TaskData text : original) {
			result.add(convert(text));
		}
		
		return Collections.unmodifiableList(result);
	}

	public static List<Comment> convertComments(List<Comment> comments) {
		if(comments == null) {
			return null;
		}
		
		List<Comment> result = new LinkedList<Comment>();
		for(Comment comment : comments) {
			result.add(convert(comment));
		}
		
		return Collections.unmodifiableList(result);
	}

	public static Comment convert(Comment comment) {
		if(comment == null){
			return null;
		}
		
		return new TaskComment(comment);
	}

	public static TaskAttachment convert(Attachment attachment) {
		if(attachment == null) {
			return null;
		}
		
		return new TaskAttachment(attachment);
	}
	
	public static List<Attachment> convertAttachments(List<Attachment> attachments) {
		if(attachments == null) {
			return null;
		}
		
		List<Attachment> result = new LinkedList<Attachment>();
		for(Attachment attachment : attachments) {
			result.add(convert(attachment));
		}
		
		return Collections.unmodifiableList(result);
	}
}
