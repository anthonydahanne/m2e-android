/*******************************************************************************
 * Copyright (c) 2009, 2010, 2011 Ricardo Gladwell and Hugo Josefson
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package me.gladwell.eclipse.m2e.android.test;

import java.io.File;

import org.eclipse.core.resources.ICommand;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.m2e.core.internal.IMavenConstants;

import com.android.ide.eclipse.adt.AdtConstants;
import com.android.ide.eclipse.adt.AdtPlugin;

/**
 * Test suite for configuring and building Android applications.
 * 
 * @author Ricardo Gladwell <ricardo.gladwell@gmail.com>
 */
public class ApplicationAndroidMavenPluginTest extends AndroidMavenPluginTestCase {

	private static final String ANDROID_15_PROJECT_NAME = "apidemos-15-app";

	private IProject project;
	private IJavaProject javaProject;

	@Override
	protected void setUp() throws Exception {
		super.setUp();

		project = importAndroidProject(ANDROID_15_PROJECT_NAME);
		javaProject = JavaCore.create(project);
	}

	@Override
	protected void tearDown() throws Exception {
		deleteProject(ANDROID_15_PROJECT_NAME);
		project = null;
		javaProject = null;

		super.tearDown();
	}

	public void testConfigure() throws Exception {
		assertNoErrors(project);
	}

	public void testConfigureAddsAndroidNature() throws Exception {
	    assertTrue("configurer failed to add android nature", project.hasNature(AdtConstants.NATURE_DEFAULT));
	}

	public void testConfigureApkBuilderBeforeMavenBuilder() throws Exception {
		boolean foundApkBuilder = false;
		for(ICommand command : project.getDescription().getBuildSpec()) {
			if("com.android.ide.eclipse.adt.ApkBuilder".equals(command.getBuilderName())) {
				foundApkBuilder = true;
			} else if(IMavenConstants.BUILDER_ID.equals(command.getBuilderName())) {
				assertTrue("project APKBuilder not configured before maven builder", foundApkBuilder);
				return;
			}
		}

		fail("project does not contain maven builder build command");
	}

	public void testConfigureDoesNotAddTargetDirectoryToClasspath() throws Exception {
		for(IClasspathEntry entry : javaProject.getRawClasspath()) {
			assertFalse("classpath contains reference to target directory: cause infinite build loops and build conflicts", entry.getPath().toOSString().contains("target"));
		}
	}

	public void testConfigureGeneratedResourcesFolderInRawClasspath() throws Exception {
		assertClasspathContains(javaProject, "gen");
	}

	public void testConfigureAddsCompileDependenciesToClasspath() throws Exception {
		assertClasspathContains(javaProject, "commons-lang-2.4.jar");
	}

	public void testConfigureDoesNotAddNonCompileDependenciesToClasspath() throws Exception {
		assertClasspathDoesNotContain(javaProject, "android-1.5_r4.jar");
	}

	public void testConfigureDoesNotAddNonCompileTransitiveDependenciesToClasspath() throws Exception {
		assertClasspathDoesNotContain(javaProject, "commons-logging-1.1.1.jar");
	}

	public void testBuildDirectoryContainsCompiledClasses() throws Exception {
		File outputLocation = new File(ResourcesPlugin.getWorkspace().getRoot().getRawLocation().toOSString(), javaProject.getPath().toOSString());
		File apiDemosApplication  = new File(outputLocation, "bin/classes/com/example/android/apis/ApiDemos.class");
		
		buildAndroidProject(project, IncrementalProjectBuilder.FULL_BUILD);

		assertTrue(apiDemosApplication.exists());
	}

}
