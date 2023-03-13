package util;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;

public class WorkspaceHelper {

	public static IJavaElement getJavaElement(String pathAsString) throws JavaModelException {
		IWorkspace ws = ResourcesPlugin.getWorkspace();
		IProject project = ws.getRoot().getProject("TestProject");
		IJavaProject javaProject = JavaCore.create(project);
		IPath path = Path.fromOSString(pathAsString);
		return javaProject.findElement(path);
	}
}
