package org.jbpm.ee.services.model.task;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import org.jbpm.ee.services.model.TaskFactory;
import org.jbpm.ee.services.model.adapter.Initializable;
import org.jbpm.ee.services.model.adapter.JaxbSerializer;
import org.kie.api.task.model.PeopleAssignments;
import org.kie.api.task.model.TaskData;

@XmlRootElement(name="task")
@XmlAccessorType(XmlAccessType.FIELD)
public class Task implements Initializable<org.kie.api.task.model.Task>, org.kie.api.task.model.Task, Serializable {

	@XmlElement
	private Long id;
	
	@XmlElement
	private int priority;
	
	@XmlElementWrapper(name="names")
	@XmlElement(type=I18NText.class)
	private List<org.kie.api.task.model.I18NText> names;
	
	@XmlElementWrapper(name="subjects")
	@XmlElement(type=I18NText.class)
	private List<org.kie.api.task.model.I18NText> subjects;
	
	@XmlElementWrapper(name="descriptions")
	@XmlElement(type=I18NText.class)
	private List<org.kie.api.task.model.I18NText> descriptions;
	
	@XmlElement(type=org.jbpm.ee.services.model.task.PeopleAssignments.class)
	private PeopleAssignments peopleAssignments;
	
	@XmlElement
	private String taskType;
	
	@XmlElement(type=org.jbpm.ee.services.model.task.TaskData.class)
	private TaskData taskData;
	
	public Task() {
		// default
	}
	
	public Task(org.kie.api.task.model.Task task) {
		initialize(task);
	}
	
	public void initialize(org.kie.api.task.model.Task task) {
		this.id = task.getId();
		this.priority = task.getPriority();
		this.names = TaskFactory.convertI18NTextList(task.getNames());
		this.subjects = TaskFactory.convertI18NTextList(task.getSubjects());
		this.descriptions = TaskFactory.convertI18NTextList(task.getDescriptions());
		this.peopleAssignments = TaskFactory.convert(task.getPeopleAssignments());
		
		this.taskType = task.getTaskType();
		this.taskData = TaskFactory.convert(task.getTaskData());
	}
	

	public void setId(Long id) {
		this.id = id;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public void setNames(List<org.kie.api.task.model.I18NText> names) {
		this.names = names;
	}

	public void setSubjects(List<org.kie.api.task.model.I18NText> subjects) {
		this.subjects = subjects;
	}

	public void setDescriptions(List<org.kie.api.task.model.I18NText> descriptions) {
		this.descriptions = descriptions;
	}

	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}

	public void setTaskData(TaskData taskData) {
		this.taskData = taskData;
	}
	
	public void setPeopleAssignments(PeopleAssignments peopleAssignments) {
		this.peopleAssignments = peopleAssignments;
	}

	@Override
	public Long getId() {
		return this.id;
	}

	@Override
	public int getPriority() {
		return this.priority;
	}

	@Override
	public List<org.kie.api.task.model.I18NText> getNames() {
		return this.names;
	}

	@Override
	public List<org.kie.api.task.model.I18NText> getSubjects() {
		return this.subjects;
	}

	@Override
	public List<org.kie.api.task.model.I18NText> getDescriptions() {
		return this.descriptions;
	}

	@Override
	public PeopleAssignments getPeopleAssignments() {
		return peopleAssignments;
	}

	@Override
	public TaskData getTaskData() {
		return taskData;
	}

	@Override
	public String getTaskType() {
		return taskType;
	}

	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		JaxbSerializer.writeExternal(this, out);
	}

	@Override
	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
		JaxbSerializer.readExternal(this, in);
	}
}
