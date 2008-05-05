package ronnie.gosh.editors.template;

import org.eclipse.jface.text.rules.ICharacterScanner;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.Token;

public class MessageRule extends ScriptletRule
{
	public MessageRule( IToken successToken )
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
			if( c == '[' )
				return true;
			scanner.unread();
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
		while( c != ICharacterScanner.EOF )
		{
			if( c == ']' )
				break;
			c = scanner.read();
		}
		return this.successToken;
	}
}
