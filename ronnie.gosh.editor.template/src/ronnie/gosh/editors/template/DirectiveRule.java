package ronnie.gosh.editors.template;

import org.eclipse.jface.text.rules.ICharacterScanner;
import org.eclipse.jface.text.rules.IToken;

public class DirectiveRule extends ScriptletRule
{
	public DirectiveRule( IToken successToken )
	{
		super( successToken );
	}

	@Override
	protected boolean startDetected( ICharacterScanner scanner )
	{
		if( !super.startDetected( scanner ) )
			return false;
		
		int c = scanner.read();
		if( c != '@' )
		{
			scanner.unread();
			scanner.unread();
			scanner.unread();
			return false;
		}
		return true;
	}
}
