package ui.view;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.texteditor.IDocumentProvider;
import org.eclipse.ui.texteditor.ITextEditor;


public class ResultTable extends Composite {
	Table table;
	TableViewer viewer;
	TableItem item;
	public ResultTable(Composite parent	) {
		super(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		this.setLayout(layout);
		
		table = new Table (this, SWT.BORDER | SWT.FULL_SELECTION);
		table.setLinesVisible (true);
		table.setHeaderVisible (true);
		GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
		data.heightHint = 300;
		table.setLayoutData(data);
		table.addMouseListener(new MouseAdapter() {
		
		    @Override
		    public void mouseDoubleClick(MouseEvent event) {
		        TableItem[] selection = table.getSelection();
		        if (selection.length == 1) {
		            Object data = selection[0].getData("url");
		            IFile file = (IFile) selection[0].getData("file");
		            	int lineNumber = Integer.parseInt(selection[0].getText(3));
		                IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		                try {
		                    IDE.openEditor(page, file, true);
		                    IEditorPart editorPart = page.getActiveEditor();

		                    if (editorPart instanceof ITextEditor) {
		                        ITextEditor textEditor = (ITextEditor) editorPart;
		                        IDocumentProvider provider = textEditor.getDocumentProvider();
		                        IDocument document = provider.getDocument(textEditor.getEditorInput());
		                        IRegion lineRegion = document.getLineInformation(lineNumber - 1);
		                        textEditor.selectAndReveal(lineRegion.getOffset(), lineRegion.getLength());
		                    }
		                } catch (PartInitException | BadLocationException e) {
		                    e.printStackTrace();
		                }
		            }
		        }
		});
	}
	
	public void updateView(ResultDisplay result) {
		

		table.clearAll();
		table.setItemCount(0);

		String[] titles = {"Severity", "Category","File", "Line"};
		for (String title : titles) {
			TableColumn column = new TableColumn (table, SWT.NONE);
			column.setText (title);
		}

		
		for(SingleResultDisplay finding : result.getResultDisplay()) {
			item = new TableItem(table, SWT.NONE);
			item.setText(0,finding.getSeverityLevel() + "");
			item.setText(1, finding.getCategory());
			item.setText(2, finding.getFileStr());
			item.setText(3, finding.getLine() + "");
			item.setData("url", finding.getFileLocation());
			item.setData("file", finding.getFile());
			
		}
		for (int i=0; i<titles.length; i++) {
			table.getColumn (i).pack ();
		}
		table.redraw();
	}
}
