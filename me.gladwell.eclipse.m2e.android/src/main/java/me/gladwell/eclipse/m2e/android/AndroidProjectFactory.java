package me.gladwell.eclipse.m2e.android;

import org.apache.maven.project.MavenProject;
import org.eclipse.core.resources.IProject;

import me.gladwell.eclipse.m2e.android.model.AndroidProject;

public interface AndroidProjectFactory {

	AndroidProject createAndroidProject(MavenProject mavenProject, IProject project);

}
