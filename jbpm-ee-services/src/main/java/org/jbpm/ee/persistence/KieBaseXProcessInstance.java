package org.jbpm.ee.persistence;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Version;

/**
 * Links a given processInstanceId to the KieReleaseId that it was started from
 * 
 * @author bdavis, abaxter
 *
 */
@Entity
public class KieBaseXProcessInstance {

	@Id
	@Column(unique=true, nullable=false)
	private Long kieProcessInstanceId;
	
	@Column(unique=false, nullable=false)
	private String releaseGroupId;
	
	@Column(unique=false, nullable=false)
	private String releaseArtifactId;
	
	@Column(unique=false, nullable=false)
	private String releaseVersion;
	
    @Version
    @Column(name = "OPTLOCK")
    private int version;

	
	public String getReleaseGroupId() {
		return releaseGroupId;
	}
	public void setReleaseGroupId(String releaseGroupId) {
		this.releaseGroupId = releaseGroupId;
	}
	
	public String getReleaseArtifactId() {
		return releaseArtifactId;
	}
	public void setReleaseArtifactId(String releaseArtifactId) {
		this.releaseArtifactId = releaseArtifactId;
	}
	public String getReleaseVersion() {
		return releaseVersion;
	}
	public void setReleaseVersion(String releaseVersion) {
		this.releaseVersion = releaseVersion;
	}
	public Long getKieProcessInstanceId() {
		return kieProcessInstanceId;
	}
	
	public void setKieProcessInstanceId(Long kieProcessInstanceId) {
		this.kieProcessInstanceId = kieProcessInstanceId;
	}
}
