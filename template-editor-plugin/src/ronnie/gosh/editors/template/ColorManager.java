package ronnie.gosh.editors.template;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.eclipse.jface.text.TextAttribute;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;


public class ColorManager
{
	static protected Map fColorTable = new HashMap( 10 );

	static public final TextAttribute COMMENT = new TextAttribute( ColorManager.getColor( new RGB( 128, 128, 128 ) ), null, SWT.NORMAL );
	static public final TextAttribute DEFAULT = new TextAttribute( null, null, SWT.NORMAL );
	static public final TextAttribute GROOVY_SCRIPTLET = new TextAttribute( null, ColorManager.getColor( new RGB( 250, 250, 160 ) ), SWT.NORMAL );
	static public final TextAttribute GROOVY_EXPRESSION = new TextAttribute( ColorManager.getColor( new RGB( 196, 96, 64 ) ), null, SWT.NORMAL );
	static public final TextAttribute GSTRING_EXPRESSION = new TextAttribute( ColorManager.getColor( new RGB( 64, 96, 255 ) ), null, SWT.NORMAL );
	static public final TextAttribute MESSAGE = new TextAttribute( ColorManager.getColor( new RGB( 32, 160, 160 ) ), null, SWT.NORMAL );

	private ColorManager()
	{
		
	}
	
	static public void dispose()
	{
		Iterator e = fColorTable.values().iterator();
		while( e.hasNext() )
			( (Color)e.next() ).dispose();
	}

	static public Color getColor( RGB rgb )
	{
		Color color = (Color)fColorTable.get( rgb );
		if( color == null )
		{
			color = new Color( Display.getCurrent(), rgb );
			fColorTable.put( rgb, color );
		}
		return color;
	}
}
