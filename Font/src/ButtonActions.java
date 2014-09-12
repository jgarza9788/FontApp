

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;



public class ButtonActions {

	public static void OpenInFinder( String PI)
	{
    	try
    	{
        ScriptEngineManager mgr = new ScriptEngineManager();        
        ScriptEngine engine = mgr.getEngineByName("AppleScript");
        engine.eval("property the_path : \"" + PI + "\" \n set the_folder to (POSIX file the_path) as alias \n tell application \"Finder\" \n activate \n reveal the_folder \n end tell");

    	}
    	catch(Exception e)
    	{
    		System.out.println("Error");
    	}

	}


}
