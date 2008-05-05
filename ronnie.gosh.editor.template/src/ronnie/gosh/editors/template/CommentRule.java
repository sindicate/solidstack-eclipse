package ronnie.gosh.editors.template;

import org.eclipse.jface.text.rules.ICharacterScanner;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.Token;

public class CommentRule extends ScriptletRule
{
	public CommentRule( IToken successToken )
	{
		super( successToken );
	}

	@Override
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
		c = scanner.read();
		if( c != '-' )
		{
			scanner.unread();
			scanner.unread();
			scanner.unread();
			return false;
		}
		c = scanner.read();
		if( c != '-' )
		{
			scanner.unread();
			scanner.unread();
			scanner.unread();
			scanner.unread();
			return false;
		}
		return true;
	}

	@Override
	public IToken evaluate( ICharacterScanner scanner )
	{
		if( !startDetected( scanner ) )
			return Token.UNDEFINED;
		
		readComment( scanner );
		return this.successToken;
	}
	
	protected void readComment( ICharacterScanner scanner )
	{
		int c = scanner.read();
		while( c != ICharacterScanner.EOF )
		{
			if( c == '-' )
			{
				c = scanner.read();
				if( c == '-' )
				{
					c = scanner.read();
					if( c == '%' )
					{
						c = scanner.read();
						if( c == '>' )
							return;
						scanner.unread();
					}
					scanner.unread();
				}
				scanner.unread();
			}
			else if( c == '<' )
			{
				c = scanner.read();
				if( c == '%' )
				{
					c = scanner.read();
					if( c == '-' )
					{
						c = scanner.read();
						if( c == '-' )
							readComment( scanner );
						else
						{
							scanner.unread();
							scanner.unread();
							scanner.unread();
						}
					}
					else
					{
						scanner.unread();
						scanner.unread();
					}
				}
				else
					scanner.unread();
			}
			c = scanner.read();
		}
	}
}
