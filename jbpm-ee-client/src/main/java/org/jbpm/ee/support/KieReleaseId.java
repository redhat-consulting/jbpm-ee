package org.jbpm.ee.support;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.drools.compiler.kproject.ReleaseIdImpl;
import org.kie.api.builder.ReleaseId;

/**
 * 
 * @author bdavis, abaxter
 *
 * Serializable ReleaseId until the update to the Kie libraries 
 * making ReleaseIdImpl Serializable comes out
 *
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class KieReleaseId implements ReleaseId, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -609810880974230932L;
	
	private String groupId;
	private String artifactId;
	private String version;
	
	public KieReleaseId() {
		
	}
	
	public KieReleaseId(String deploymentId) {
		String[] splitId = deploymentId.split(":");
		this.groupId = splitId[0];
		this.artifactId = splitId[1];
		this.version = splitId[2];
	}
	
	public KieReleaseId(String groupId, String artifactId, String version) {
		this.groupId = groupId;
		this.artifactId = artifactId;
		this.version = version;
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((artifactId == null) ? 0 : artifactId.hashCode());
		result = prime * result + ((groupId == null) ? 0 : groupId.hashCode());
		result = prime * result + ((version == null) ? 0 : version.hashCode());
		return result;
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		KieReleaseId other = (KieReleaseId) obj;
		if (artifactId == null) {
			if (other.artifactId != null)
				return false;
		} else if (!artifactId.equals(other.artifactId))
			return false;
		if (groupId == null) {
			if (other.groupId != null)
				return false;
		} else if (!groupId.equals(other.groupId))
			return false;
		if (version == null) {
			if (other.version != null)
				return false;
		} else if (!version.equals(other.version))
			return false;
		return true;
	}



	@Override
	public String getGroupId() {
		return groupId;
	}

	@Override
	public String getArtifactId() {
		return artifactId;
	}

	@Override
	public String getVersion() {
		return version;
	}
	
	

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public void setArtifactId(String artifactId) {
		this.artifactId = artifactId;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	@Override
	public String toExternalForm() {
		return toString();
	}

	@Override
	public boolean isSnapshot() {
		return version.endsWith("-SNAPSHOT");
	}

	@Override
	public String toString() {
		return groupId + ":" + artifactId + ":" + version;
	}
	
	/**
	 * Returns the ReleaseIdImpl for times when BRMS expects it
	 * @return
	 */
	public ReleaseIdImpl toReleaseIdImpl() {
		return new ReleaseIdImpl(groupId, artifactId, version);
	}

}
