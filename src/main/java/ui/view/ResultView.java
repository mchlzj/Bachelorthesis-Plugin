package ui.view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

import ui.logic.Metrics;

public class ResultView extends ViewPart {

	ResultTable resultTable;
	ResultInfo resultInfo;
	
	public ResultView() {

	}

	@Override
	public void createPartControl(Composite parent) {
		RowLayout layout = new RowLayout(SWT.VERTICAL);
		layout.wrap = false;
		parent.setLayout(layout);
		this.resultInfo = new ResultInfo(parent);
		this.resultTable = new ResultTable(parent
//				, new FindingResult()
				);
		
	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub

	}
	
	public void updateView(Metrics metrics, ResultDisplay result) {
		resultInfo.updateView(metrics);
		resultTable.updateView(result);
		
	}

}
