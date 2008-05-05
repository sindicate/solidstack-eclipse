package ronnie.gosh.editors.template;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.ITokenScanner;
import org.eclipse.jface.text.rules.Token;


public class DummyScanner implements ITokenScanner
{
	protected IToken token;
	protected int offset;
	protected int length;
	protected int lastOffset;
	protected int lastLength;
	
	public DummyScanner( IToken token )
	{
		this.token = token;
	}
	
	public void setRange( final IDocument document, int offset, int length )
	{
		this.offset = offset;
		this.length = length;
	}

	public int getTokenOffset()
	{
		return this.lastOffset;
	}

	public int getTokenLength()
	{
		return this.lastLength;
	}

	public IToken nextToken()
	{
		if( this.length > 0 )
		{
			this.lastOffset = this.offset;
			this.lastLength = this.length;
			this.offset += this.length;
			this.length = 0;
//			Activator.info( "nextToken() -> " + this.token.getData() + ", " + this.lastLength );
			return this.token;
		}
		
		return Token.EOF;
	}
}
