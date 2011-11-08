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
import org.eclipse.core.runtime.IPath;
import org.eclipse.m2e.core.lifecyclemapping.model.IPluginExecutionMetadata;
import org.eclipse.m2e.core.project.IMavenProjectFacade;
import org.eclipse.m2e.core.project.configurator.AbstractBuildParticipant;
import org.eclipse.m2e.jdt.AbstractJavaProjectConfigurator;

public class AndroidProjectConfigurator2 extends AbstractJavaProjectConfigurator {
	@Override
	public AbstractBuildParticipant getBuildParticipant(IMavenProjectFacade projectFacade, MojoExecution execution,
			IPluginExecutionMetadata executionMetadata) {
		if("default-unpack".equals(execution.getExecutionId())){
			return new AndroidDefaultUnpackBuildParticipant(execution);
		}
		else{
			//default-generate-sources
			return new AndroidDefaultGenerateSourcesBuildParticipant(execution);
		}
	}

	@Override
	protected IPath getFullPath(IMavenProjectFacade facade, File file) {
		// TODO Auto-generated method stub
		return null;
	}

}
