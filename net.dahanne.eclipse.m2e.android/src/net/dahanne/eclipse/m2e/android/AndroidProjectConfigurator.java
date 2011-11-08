/*******************************************************************************
 * Copyright (c) 2008 Sonatype, Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package net.dahanne.eclipse.m2e.android;

import java.io.File;

import org.apache.maven.plugin.MojoExecution;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.m2e.core.lifecyclemapping.model.IPluginExecutionMetadata;
import org.eclipse.m2e.core.project.IMavenProjectFacade;
import org.eclipse.m2e.core.project.configurator.AbstractBuildParticipant;
import org.eclipse.m2e.core.project.configurator.ProjectConfigurationRequest;
import org.eclipse.m2e.jdt.AbstractJavaProjectConfigurator;

public class AndroidProjectConfigurator extends AbstractJavaProjectConfigurator {
	@Override
	public AbstractBuildParticipant getBuildParticipant(IMavenProjectFacade projectFacade, MojoExecution execution,
			IPluginExecutionMetadata executionMetadata) {
		return new AndroidDefaultGenerateSourcesBuildParticipant(execution);
	}

	@Override
	protected String getOutputFolderParameterName() {
		return "genDirectory";
	}

	@Override
	protected File[] getSourceFolders(ProjectConfigurationRequest request, MojoExecution mojoExecution)
			throws CoreException {
		// there is no variable to reference aidl generated classes, so we have
		// to guess
		File aidlGeneratedSourcesFolder = new File(request.getProject().getLocation().toOSString() + "/../"
				+ request.getMavenProjectFacade().getOutputLocation().toOSString() + "/../generated-sources/aidl");
		// not yet implemented in android maven plugin (as of 3.0.0-alpha13)
		// File aidlGeneratedSourcesFolder = getParameterValue(
		// "genDirectoryAidl", File.class, request.getMavenSession(),
		// mojoExecution );
		File[] generatedSourcesFolders = new File[] {
				getParameterValue("genDirectory", File.class, request.getMavenSession(), mojoExecution),
				aidlGeneratedSourcesFolder };

		return generatedSourcesFolders;
	}

}
