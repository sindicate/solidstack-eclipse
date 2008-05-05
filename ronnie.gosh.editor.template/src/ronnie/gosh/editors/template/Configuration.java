package ronnie.gosh.editors.template;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextDoubleClickStrategy;
import org.eclipse.jface.text.presentation.IPresentationReconciler;
import org.eclipse.jface.text.presentation.PresentationReconciler;
import org.eclipse.jface.text.rules.DefaultDamagerRepairer;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.SourceViewerConfiguration;


public class Configuration extends SourceViewerConfiguration
{
	private DoubleClickStrategy doubleClickStrategy;

	public Configuration()
	{
	}

//	@Override
//	public String getConfiguredDocumentPartitioning( ISourceViewer sourceViewer )
//	{
//		return "groovy_template_partitioning";
//	}

	@Override
	public String[] getConfiguredContentTypes( ISourceViewer sourceViewer )
	{
		return new String[] { IDocument.DEFAULT_CONTENT_TYPE, PartitionScanner.COMMENT, /* XMLPartitionScanner.XML_TAG, */
				PartitionScanner.GROOVY_SCRIPTLET, PartitionScanner.GROOVY_EXPRESSION,
				PartitionScanner.GSTRING_EXPRESSION };
	}

	@Override
	public ITextDoubleClickStrategy getDoubleClickStrategy( ISourceViewer sourceViewer, String contentType )
	{
		if( doubleClickStrategy == null )
			doubleClickStrategy = new DoubleClickStrategy();
		return doubleClickStrategy;
	}

	@Override
	public IPresentationReconciler getPresentationReconciler( ISourceViewer sourceViewer )
	{
		PresentationReconciler reconciler = new PresentationReconciler();
		reconciler.setDocumentPartitioning( "groovy_template_partitioning" );

		DefaultDamagerRepairer dr = new DefaultDamagerRepairer( new DummyScanner( new Token( ColorManager.DEFAULT ) ) );
		reconciler.setDamager( dr, PartitionScanner.TEXT );
		reconciler.setRepairer( dr, PartitionScanner.TEXT );

		dr = new DefaultDamagerRepairer( new DummyScanner( new Token( ColorManager.COMMENT ) ) );
		reconciler.setDamager( dr, PartitionScanner.COMMENT );
		reconciler.setRepairer( dr, PartitionScanner.COMMENT );

		dr = new DefaultDamagerRepairer( new DummyScanner( new Token( ColorManager.GROOVY_EXPRESSION ) ) );
		reconciler.setDamager( dr, PartitionScanner.GROOVY_EXPRESSION );
		reconciler.setRepairer( dr, PartitionScanner.GROOVY_EXPRESSION );
		
		dr = new DefaultDamagerRepairer( new DummyScanner( new Token( ColorManager.GROOVY_SCRIPTLET ) ) );
		reconciler.setDamager( dr, PartitionScanner.GROOVY_SCRIPTLET );
		reconciler.setRepairer( dr, PartitionScanner.GROOVY_SCRIPTLET );

		dr = new DefaultDamagerRepairer( new DummyScanner( new Token( ColorManager.GSTRING_EXPRESSION ) ) );
		reconciler.setDamager( dr, PartitionScanner.GSTRING_EXPRESSION );
		reconciler.setRepairer( dr, PartitionScanner.GSTRING_EXPRESSION );

		dr = new DefaultDamagerRepairer( new DummyScanner( new Token( ColorManager.MESSAGE ) ) );
		reconciler.setDamager( dr, PartitionScanner.MESSAGE );
		reconciler.setRepairer( dr, PartitionScanner.MESSAGE );

		return reconciler;
	}
}
