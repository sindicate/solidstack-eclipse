package ronnie.gosh.editors.template;

import org.eclipse.core.filebuffers.IDocumentSetupParticipant;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IDocumentExtension3;
import org.eclipse.jface.text.IDocumentPartitioner;
import org.eclipse.jface.text.rules.FastPartitioner;



public class DocumentSetupParticipant implements IDocumentSetupParticipant
{
	public void setup( IDocument document )
	{
//		Activator.info( "DocumentSetupParticipant.setup()" );
		
		IDocumentExtension3 _document = (IDocumentExtension3)document;
		IDocumentPartitioner partitioner = new FastPartitioner( new PartitionScanner(), new String[] {
			PartitionScanner.COMMENT, 
			PartitionScanner.GROOVY_EXPRESSION, 
			PartitionScanner.GROOVY_SCRIPTLET,
			PartitionScanner.GSTRING_EXPRESSION,
			PartitionScanner.MESSAGE,
			PartitionScanner.TEXT } );
		partitioner.connect( document );
		_document.setDocumentPartitioner( "groovy_template_partitioning", partitioner );

//		Activator.info( "DocumentSetupParticipant.setup() done" );

		//		IDocumentPartitioner partitioner = new FastPartitioner( JavaEditorExamplePlugin.getDefault()
//				.getJavaPartitionScanner(), JavaPartitionScanner.JAVA_PARTITION_TYPES );
//		partitioner.connect( document );
	}
}
