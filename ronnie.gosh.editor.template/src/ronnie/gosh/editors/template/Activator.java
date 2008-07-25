package ronnie.gosh.editors.template;

import org.eclipse.core.runtime.ILog;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;



/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin
{
	static public final String PLUGIN_ID = "ronnie.gosh.editors.template"; // The plug-in ID

	static private Activator plugin; // The shared instance
	
	static public ILog log;
	
	static public void info( String message )
	{
		log.log( new Status( Status.INFO, PLUGIN_ID, message ) );
	}

	/**
	 * The constructor
	 */
	public Activator()
	{
		log = getLog();
	}

	@Override
	public void start( BundleContext context ) throws Exception
	{
		super.start( context );
		plugin = this;
	}

	@Override
	public void stop( BundleContext context ) throws Exception
	{
		plugin = null;
		ColorManager.dispose();
		super.stop( context );
	}

	/**
	 * Returns the shared instance
	 * 
	 * @return the shared instance
	 */
	public static Activator getDefault()
	{
		return plugin;
	}

	/**
	 * Returns an image descriptor for the image file at the given plug-in relative path
	 * 
	 * @param path the path
	 * @return the image descriptor
	 */
	public static ImageDescriptor getImageDescriptor( String path )
	{
		return imageDescriptorFromPlugin( PLUGIN_ID, path );
	}
}
