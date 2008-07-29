package ronnie.gosh.editors.template;

import org.eclipse.jface.text.rules.ICharacterScanner;
import org.eclipse.jface.text.rules.IPredicateRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.Token;

public class ScriptletRule implements IPredicateRule
{
	protected IToken successToken;
	
	public ScriptletRule( IToken successToken )
	{
		this.successToken = successToken;
	}

	public IToken evaluate( ICharacterScanner scanner, boolean resume )
	{
		/* Example of blocking a resume. The partition scanner will then start normal scan.
		if( resume )
			return Token.UNDEFINED; // This rule can't resume
		*/
		return evaluate( scanner );
	}

	public IToken getSuccessToken()
	{
		return this.successToken;
	}
	
	protected boolean startDetected( ICharacterScanner scanner )
	{
		int c = scanner.read();
		if( c != '<' )
		{
			scanner.unread();
			return false;
		}
		c = scanner.read();
		if( c != '%' )
		{
			scanner.unread();
			scanner.unread();
			return false;
		}
		return true;
	}

	public IToken evaluate( ICharacterScanner scanner )
	{
		if( !startDetected( scanner ) )
			return Token.UNDEFINED;
		
		int c = scanner.read();
		while( c != ICharacterScanner.EOF )
		{
			if( c == '"' )
				readGString( scanner );
			else if( c == '\'' )
				readString( scanner );
			else if( c == '%' )
			{
				c = scanner.read();
				if( c == '>' )
					break;
				scanner.unread();
			}
			c = scanner.read();
		}
		return this.successToken;
	}

	protected void readGString( ICharacterScanner scanner )
	{
		int c = scanner.read();
		while( c != '"' && c != ICharacterScanner.EOF )
		{
			// TODO GString expression, but keep synchronized
			if( c == '\\' )
				scanner.read();
			else if( c == '$' )
			{
				c = scanner.read();
				if( c == '{' )
					readEuh( scanner );
				else
					scanner.unread();
			}
			c = scanner.read();
		}
	}

	protected void readString( ICharacterScanner scanner )
	{
		int c = scanner.read();
		while( c != '\'' && c != ICharacterScanner.EOF )
		{
			if( c == '\\' )
				scanner.read();
			c = scanner.read();
		}
	}
	
	// TODO Also read simple values like $value ?
	protected void readEuh( ICharacterScanner scanner )
	{
		int c = scanner.read();
		while( c != '}' && c != ICharacterScanner.EOF )
		{
			if( c == '"' )
				readGString( scanner );
			else if( c == '\'' )
				readString( scanner );
			c = scanner.read();
		}
	}
}
