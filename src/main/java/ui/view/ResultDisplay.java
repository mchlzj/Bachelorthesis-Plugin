package ui.view;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.jdt.core.dom.CompilationUnit;

import domain.results.Finding;
import domain.results.FindingResult;

public class ResultDisplay {

	private List<SingleResultDisplay> resultDisplay;
	
	public ResultDisplay() {
		this.resultDisplay = new ArrayList<>();
	}	
	
	public List<SingleResultDisplay> getResultDisplay() {
		return this.resultDisplay;
	}
	
	public void addResults(FindingResult result, CompilationUnit cu) {
		result.getFindings().forEach(finding -> resultDisplay.add(new SingleResultDisplay(finding, cu)));
	}
}

class SingleResultDisplay {
	
	private int severityLevel;
	private String category;
	private String fileStr;
	private IFile file;
	private int Line;
	private String fileLocation;
	
	SingleResultDisplay(Finding finding, CompilationUnit cu) {
		this.severityLevel = finding.getCategory().getSeverity().getSeverityLevel();
		this.category = finding.getCategory().getName();
		this.fileStr = cu.getJavaElement().getElementName();
		this.file = (IFile) cu.getJavaElement().getResource();
		this.Line = cu.getLineNumber(finding.getDisplayNode().getStartPosition());
		this.fileLocation = 
				cu.getJavaElement().getResource().getLocation().toString() 
				+ finding.getDisplayNode().getStartPosition() 
				+ ":"
				+ finding.getDisplayNode().getLength();
	}

	public int getSeverityLevel() {
		return severityLevel;
	}

	public String getCategory() {
		return category;
	}

	public String getFileStr() {
		return fileStr;
	}
	public IFile getFile() {
		return file;
	}

	public int getLine() {
		return Line;
	}

	public String getFileLocation() {
		return fileLocation;
	}
}
