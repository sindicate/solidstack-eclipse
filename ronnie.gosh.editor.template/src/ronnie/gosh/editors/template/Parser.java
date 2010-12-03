package ronnie.gosh.editors.template;

import org.eclipse.jface.text.rules.ICharacterScanner;


public class Parser
{
	static public void readString( ICharacterScanner reader, int quote )
	{
		// String can be read in any mode
		// Expecting $, ", ' and \
		// " within ${} should not end this string
		// \ is used to escape $, " and itself, and special characters

		boolean multiline = false;
		int c = reader.read();
		if( c == quote )
		{
			c = reader.read();
			if( c == quote )
				multiline = true;
			else
			{
				reader.unread();
				reader.unread();
			}
		}
		else
			reader.unread();

		while( true )
		{
			c = reader.read();

			if( c == ICharacterScanner.EOF )
				return;
			if( !multiline )
				if( c == '\n' || c == '\r' )
				{
					reader.unread();
					return;
				}

			if( c == '\\' )
			{
				c = reader.read();
				if( !( "bfnrt'\"\\$".indexOf( c ) >= 0 ) )
					reader.unread();
				continue;
			}

			if( quote == '"' && c == '$' )
			{
				c = reader.read();
				if( c != '{' )
					reader.unread();
				else
					readGStringExpression( reader, multiline );
				continue;
			}

			if( c == quote )
			{
				if( !multiline )
					return;

				c = reader.read();
				if( c == quote )
				{
					c = reader.read();
					if( c == quote )
						return;
					reader.unread();
					reader.unread();
				}
				else
					reader.unread();
				continue;
			}
		}
	}

	static public void readGStringExpression( ICharacterScanner reader, boolean multiline )
	{
		// GStringExpressions can be read in any mode
		// Expecting }, ", ' and {
		// } within a string should not end this expression

		while( true )
		{
			int c = reader.read();
			if( c == ICharacterScanner.EOF )
				return;
			if( !multiline )
				if( c == '\n' || c == '\r' )
				{
					reader.unread();
					return;
				}
			if( c == '}' )
				return;
			if( c == '"' || c == '\'' )
				readString( reader, (char)c );
			else if( c =='{' )
				readBlock( reader, multiline );
		}
	}

	static public void readBlock( ICharacterScanner reader, boolean multiline )
	{
		// Expecting }, " or '
		// } within a string should not end this block

		while( true )
		{
			int c = reader.read();
			if( c == ICharacterScanner.EOF )
				return;
			if( !multiline && c == '\n' )
				return;
			if( c == '}' )
				return;
			if( c == '"' || c == '\'' )
				readString( reader, (char)c );
			else if( c =='{' )
				readBlock( reader, multiline );
		}
	}
}
