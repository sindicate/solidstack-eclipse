package ronnie.gosh.editors.template;

import org.eclipse.jface.text.rules.ICharacterScanner;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.Token;

public class GStringExpressionRule extends ScriptletRule
{
	public GStringExpressionRule( IToken successToken )
	{
		super( successToken );
	}

	@Override
	protected boolean startDetected( ICharacterScanner scanner )
	{
		int c = scanner.read();
		if( c == '$' )
		{
			c = scanner.read();
			scanner.unread();
			if( c == '{' || Character.isJavaIdentifierStart( c ) )
				return true;
		}
		scanner.unread();
		return false;
	}

	@Override
	public IToken evaluate( ICharacterScanner scanner )
	{
		if( !startDetected( scanner ) )
			return Token.UNDEFINED;
		
		int c = scanner.read();
		if( c == '{' )
		{
			c = scanner.read();
			while( c != ICharacterScanner.EOF )
			{
				if( c == '"' )
					readGString( scanner );
				else if( c == '\'' )
					readString( scanner );
				else if( c == '}' )
					break;
				c = scanner.read();
			}
			return this.successToken;
		}
		else
		{
			if( !Character.isJavaIdentifierStart( c ) )
				throw new RuntimeException( "Expecting a javaIdentifierStart character" );
			while( c != ICharacterScanner.EOF )
			{
				if( !Character.isJavaIdentifierPart( c ) )
					break;
				c = scanner.read();
			}
			scanner.unread();
			return this.successToken;
		}
	}
}
