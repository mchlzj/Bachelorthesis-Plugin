package ui.view;

import org.eclipse.jface.widgets.WidgetFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

import ui.logic.Metrics;
import ui.logic.MetricsType;

public class ResultInfo extends Composite {

	private Text NOHCDVal;
	private Text NOIDVal;
	private Text ROIVal;
	private Text NOMDVal;
	private Text ROMVal;
	private Text NDVal;
	private Text ROTVal;
	
	public ResultInfo(Composite parent) {
		super(parent, SWT.None);

		
		RowLayout mainLayout = new RowLayout(SWT.HORIZONTAL);
		mainLayout.marginTop = 5;
		mainLayout.marginBottom = 5;
		this.setLayout(mainLayout);
				
		initNOHCDComp();		
		initNOIDComp();
		initROIComp();	
		initNOMDComp();		
		initROMComp();		
		initNDComp();		
		initROTComp();
	}

	private void initROTComp() {
		Composite ROTComp = WidgetFactory.composite(SWT.None).layout(new RowLayout(SWT.HORIZONTAL)).create((Composite) this);
		MetricsType metricType5 = MetricsType.ROT;
		WidgetFactory.label(SWT.None).text(metricType5.getName()).tooltip(metricType5.getDescription()).create(ROTComp);
		ROTVal = WidgetFactory.text(SWT.None).create(ROTComp);
	}

	private void initNDComp() {
		Composite NDComp = WidgetFactory.composite(SWT.None).layout(new RowLayout(SWT.HORIZONTAL)).create((Composite) this);
		MetricsType metricType4 = MetricsType.ND;
		WidgetFactory.label(SWT.None).text(metricType4.getName()).tooltip(metricType4.getDescription()).create(NDComp);
		NDVal = WidgetFactory.text(SWT.None).create(NDComp);
	}

	private void initROMComp() {
		Composite ROMComp = WidgetFactory.composite(SWT.None).layout(new RowLayout(SWT.HORIZONTAL)).create((Composite) this);
		MetricsType metricType3 = MetricsType.ROM;
		WidgetFactory.label(SWT.None).text(metricType3.getName()).tooltip(metricType3.getDescription()).create(ROMComp);
		ROMVal = WidgetFactory.text(SWT.None).create(ROMComp);
	}

	private void initNOMDComp() {
		Composite NOMDComp = WidgetFactory.composite(SWT.None).layout(new RowLayout(SWT.HORIZONTAL)).create((Composite) this);
		MetricsType metricType2 = MetricsType.NOMD;
		WidgetFactory.label(SWT.None).text(metricType2.getName()).tooltip(metricType2.getDescription()).create(NOMDComp);
		NOMDVal = WidgetFactory.text(SWT.None).create(NOMDComp);
	}

	private void initROIComp() {
		Composite ROIComp = WidgetFactory.composite(SWT.None).layout(new RowLayout(SWT.HORIZONTAL)).create((Composite) this);
		MetricsType metricType1 = MetricsType.ROI;
		WidgetFactory.label(SWT.None).text(metricType1.getName()).tooltip(metricType1.getDescription()).create(ROIComp);
		ROIVal = WidgetFactory.text(SWT.None).create(ROIComp);
	}

	private void initNOIDComp() {
		Composite NOIDComp = WidgetFactory.composite(SWT.None).layout(new RowLayout(SWT.HORIZONTAL)).create((Composite) this);
		MetricsType metricType = MetricsType.NOID;
		WidgetFactory.label(SWT.None).text(metricType.getName()).tooltip(metricType.getDescription()).create(NOIDComp);
		NOIDVal = WidgetFactory.text(SWT.None).create(NOIDComp);
	}

	private void initNOHCDComp() {
		Composite NOHCDComp = WidgetFactory.composite(SWT.None).layout(new RowLayout(SWT.HORIZONTAL)).create((Composite) this);
		WidgetFactory.label(SWT.None).text(MetricsType.NOHCD.getName()).tooltip(MetricsType.NOHCD.getDescription()).create(NOHCDComp);
		NOHCDVal = WidgetFactory.text(SWT.None).create(NOHCDComp);
	}

	public void updateView(Metrics metrics) {
		updateValues(metrics);
	}

	private void updateValues(Metrics metrics) {
		resetValues();
		NOHCDVal.setText(String.format("%.0f",metrics.getNOHCDVal()));
		NOIDVal.setText(String.format("%.0f",metrics.getNOIDVal()));
		NDVal.setText(String.format("%.0f", metrics.getNDVal()));
		NOMDVal.setText(String.format("%.0f",metrics.getNOMDVal()));		
		updateROI(metrics);
		updateROM(metrics);
		updateROT(metrics);
	}

	private void updateROI(Metrics metrics) {
		if(metrics.isROIValid()) {
			ROIVal.setText(String.format("%.2f", metrics.getROIVal()));			
		}
	}

	private void updateROM(Metrics metrics) {
		if(metrics.isROMValid()) {
			ROMVal.setText(String.format("%.2f", metrics.getROMVal()));			
		}
	}

	private void updateROT(Metrics metrics) {
		if(metrics.isROTValid()) {
			ROTVal.setText(String.format("%.2f", metrics.getROTVal()));
		}
	}

	private void resetValues() {
		ROIVal.setText("Calculation not possible");
		ROMVal.setText("Calculation not possible");
		ROTVal.setText("Calculation not possible");
	}
}
