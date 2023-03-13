package ui.logic;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;

public class PackageTraverser {
	
    public static void traverse(IPackageFragmentRoot packageFragmentRoot) throws JavaModelException {
        for (IJavaElement element : packageFragmentRoot.getChildren()) {
            if (element instanceof IPackageFragment) {
                traversePackage((IPackageFragment) element);
            }
        }
    }

	private static void traversePackage(IPackageFragment packageFragment) throws JavaModelException {
	    for (IJavaElement element : packageFragment.getChildren()) {
	        if (element instanceof IPackageFragment) {
	            traversePackage((IPackageFragment) element);
	        } else if (element instanceof ICompilationUnit) {
	            ICompilationUnit unit = (ICompilationUnit) element;
	            for (IType type : unit.getAllTypes()) {
	                // Do something with the class
	                System.out.println("Traversing class: " + type.getFullyQualifiedName());
	            }
	        }
	    }
	}
}


