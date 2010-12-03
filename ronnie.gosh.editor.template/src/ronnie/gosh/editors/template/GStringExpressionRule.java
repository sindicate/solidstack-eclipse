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
			if( c == '{' || Character.isJavaIdentifierStart( c ) && c != '$' )
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
			Parser.readGStringExpression( scanner, true );
		}
		else
		{
			while( c != ICharacterScanner.EOF )
			{
				if( !Character.isJavaIdentifierPart( c ) || c == '$' )
					break;
				c = scanner.read();
			}
			scanner.unread();
		}
		return this.successToken;
	}
}
