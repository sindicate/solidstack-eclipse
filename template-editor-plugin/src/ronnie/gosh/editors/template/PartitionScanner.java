package ronnie.gosh.editors.template;

import org.eclipse.jface.text.rules.IPredicateRule;
import org.eclipse.jface.text.rules.RuleBasedPartitionScanner;
import org.eclipse.jface.text.rules.Token;


public class PartitionScanner extends RuleBasedPartitionScanner
{
	public final static String TEXT = "__text";
	public final static String COMMENT = "__groovy_comment";
	public final static String GROOVY_SCRIPTLET = "__groovy_scriptlet";
	public final static String GROOVY_EXPRESSION = "__groovy_expression";
	public final static String GSTRING_EXPRESSION = "__gstring_expression";
	public final static String MESSAGE = "__message";

//	@Override
//	public IToken nextToken()
//	{
//		IToken result = super.nextToken();
//		Activator.info( "XMLPartitionScanner.nextToken() -> " + result.getData() + ", offset: " + getTokenOffset() + ", length: " + getTokenLength()  );
//		return result;
//	}
//
//	@Override
//	public void setPartialRange( IDocument document, int offset, int length, String contentType, int partitionOffset )
//	{
//		Activator.info( "XMLPartitionScanner.setPartialRange()" );
//		super.setPartialRange( document, offset, length, contentType, partitionOffset );
//	}
//
//	@Override
//	public void setRange( IDocument document, int offset, int length )
//	{
//		Activator.info( "XMLPartitionScanner.setRange()" );
//		super.setRange( document, offset, length );
//	}

	public PartitionScanner()
	{
		IPredicateRule[] rules = new IPredicateRule[ 6 ];

		rules[ 0 ] = new CommentRule( new Token( COMMENT ) );
		rules[ 1 ] = new ScriptletExpressionRule( new Token( GROOVY_EXPRESSION ) );
		rules[ 2 ] = new ScriptletRule( new Token( GROOVY_SCRIPTLET ) );
		rules[ 3 ] = new GStringExpressionRule( new Token( GSTRING_EXPRESSION ) );
		rules[ 4 ] = new MessageRule( new Token( MESSAGE ) );
		rules[ 5 ] = new TextRule( new Token( TEXT ) );

		setPredicateRules( rules );
	}
}
