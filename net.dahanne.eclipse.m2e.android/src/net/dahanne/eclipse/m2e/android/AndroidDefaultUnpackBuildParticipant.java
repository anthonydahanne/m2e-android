package net.dahanne.eclipse.m2e.android;

import java.util.Set;

import org.apache.maven.plugin.MojoExecution;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.m2e.core.project.configurator.MojoExecutionBuildParticipant;

public class AndroidDefaultUnpackBuildParticipant extends MojoExecutionBuildParticipant {
	
	public AndroidDefaultUnpackBuildParticipant(MojoExecution execution) {
		super(execution, true);
	}
	@Override
	public Set<IProject> build(int kind, IProgressMonitor monitor) throws Exception {
		// execute mojo
		Set<IProject> result = super.build(kind, monitor);
		return result;
	}

}
