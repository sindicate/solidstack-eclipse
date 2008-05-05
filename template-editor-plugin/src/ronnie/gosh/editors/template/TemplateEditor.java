package ronnie.gosh.editors.template;

import org.eclipse.ui.editors.text.TextEditor;


public class TemplateEditor extends TextEditor
{
	@Override
	protected void initializeEditor()
	{
		setSourceViewerConfiguration( new Configuration() );
		super.initializeEditor();
//		( (ITextViewerExtension2)getSourceViewer() ).addPainter( new Painter() );
	}
}
