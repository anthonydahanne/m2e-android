package me.gladwell.eclipse.m2e.android.test;

import org.eclipse.core.resources.IProject;

import com.android.ide.eclipse.adt.internal.sdk.Sdk;

public class LibraryAndroidMavenPluginTest extends AndroidMavenPluginTestCase {

    private static final String ANDROID_LIB_PROJECT_NAME = "apklib-project";

    private IProject project;

	@Override
	protected void setUp() throws Exception {
		super.setUp();

		deleteProject(ANDROID_LIB_PROJECT_NAME);
		project = importAndroidProject(ANDROID_LIB_PROJECT_NAME);
	}

	public void testConfigure() throws Exception {
		assertNoErrors(project);
		assertTrue(Sdk.getProjectState(project).isLibrary());
	}

}
