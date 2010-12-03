package ronnie.gosh.editors.template;

import org.eclipse.jface.text.rules.ICharacterScanner;
import org.eclipse.jface.text.rules.IPredicateRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.Token;

public class TextRule implements IPredicateRule
{
	protected IToken successToken;
	
	public TextRule( IToken successToken )
	{
		this.successToken = successToken;
	}

	public IToken evaluate( ICharacterScanner scanner, boolean resume )
	{
		/* Example of blocking resume. The partition scanner will then start normal scan.
		if( resume )
			return Token.UNDEFINED; // This rule can't resume
		*/
		return evaluate( scanner );
	}

	public IToken getSuccessToken()
	{
		return this.successToken;
	}

	public IToken evaluate( ICharacterScanner scanner )
	{
		boolean found = false;
		int c = scanner.read();
		while( c != ICharacterScanner.EOF )
		{
			if( c == '\\' )
			{
				c = scanner.read();
				if( c != '$' && c != '<' && c != '\\' )
					scanner.unread();
			}
			else if( c == '<' )
			{
				c = scanner.read();
				scanner.unread();
				if( c == '%' )
					break;
			}
			else if( c == '$' )
			{
				c = scanner.read();
				scanner.unread();
				if( c == '{' || c == '[' || Character.isJavaIdentifierStart( c ) && c != '$' )
					break;
			}
			found = true;
			c = scanner.read();
		}
		scanner.unread(); // always one read too many
		return found ? this.successToken : Token.UNDEFINED;
	}
}
