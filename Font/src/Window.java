/***********************************************************************
Program Name: Window.java
Programmer's Name: Justin Garza
Program Description: Contains the window for the application
***********************************************************************/ 

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.ScrollPaneLayout;
import javax.swing.SwingConstants;

import java.awt.FontMetrics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;


public class Window
{

	public static JFrame ThisWindow = new JFrame("FontApp");
	
	public JPanel SearchArea = new JPanel();
	
	public JPanel Content = new JPanel();
	public JScrollPane ScrollPane = new JScrollPane(Content);
	
	private static int Width;
	private static int Height;
	private static int PosXofWindow;
	private static int PosYofWindow;
	
	public ArrayList<String> AllFontNames = new ArrayList<String>();
	public ArrayList<String> AllFontPaths = new ArrayList<String>();
	
	public ArrayList<JLabel> AllFontLabels = new ArrayList<JLabel>();
	
	public Window() 
	{
		AllFontLabels.clear();
		
		JFrame PleaseWait = new JFrame();
//		PleaseWait.getContentPane.setlayout(new BorderLayout());
		PleaseWait.getContentPane().add(new JLabel("Please Wait...Loading Fonts",JLabel.CENTER));
		
        ThisWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ThisWindow.setVisible(false);
        
        
        
        Content.setLayout(new GridBagLayout());
//        Content.setLayout(new BorderLayout(5,5));
        Content.setBackground(Color.WHITE);
        ScrollPane.getVerticalScrollBar().setUnitIncrement(10);
        
        ThisWindow.getContentPane().add(SearchArea, BorderLayout.PAGE_START);
        
        ThisWindow.getContentPane().add(ScrollPane, BorderLayout.CENTER);
        ScrollPane.setLayout(new ScrollPaneLayout());
        
        ThisWindow.setMinimumSize(new Dimension(350, 350));

		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		Width = (int) 350;
		Height = (int) 700;
		PosXofWindow  = (int) ((dimension.getWidth()/2)-(Width/2));
		PosYofWindow  = (int) ((dimension.getHeight()/2)-(Height/2));
		ThisWindow.setBounds( PosXofWindow, PosYofWindow,Width, Height);
		
		PleaseWait.setBounds( PosXofWindow, PosYofWindow,300, 150);
		PleaseWait.setVisible(true);
		
		
		AddSearchBar();
		CollectContent();
		AddContent();
		
		PleaseWait.setVisible(false);
		ThisWindow.setVisible(true);
		ThisWindow.repaint();
		
		PleaseWait.removeAll();
		
	}
	
	public void AddSearchBar()
	{
		JLabel ThisLabel = new JLabel("Search");
		SearchArea.add(ThisLabel, BorderLayout.LINE_START);
		
		final JTextField SearchBox =  new JTextField(20);
//		SearchBox.setSize(100,125);
		SearchArea.add(SearchBox, BorderLayout.LINE_END);
		
		SearchBox.addActionListener(new ActionListener() 
		{
		      public void actionPerformed(ActionEvent e) 
		      {
		    	  Boolean FoundOne = false;

		    	  for (int i = 0; i < AllFontLabels.size() ; i ++)
		    	  {
		    		  System.out.println(AllFontLabels.get(i).getText().toUpperCase().indexOf(SearchBox.getText().toUpperCase()));
		    				  
		    		  if (AllFontLabels.get(i).getText().toUpperCase().indexOf(SearchBox.getText().toUpperCase()) >= 0 )
		    		  {
		    			  if (FoundOne == false)
		    			  {
			    			  Content.scrollRectToVisible(AllFontLabels.get(AllFontLabels.size() -1).getBounds());
			    			  Content.scrollRectToVisible(AllFontLabels.get(i).getBounds());
			    			  FoundOne = true;
		    			  }

		    			  AllFontLabels.get(i).setOpaque(true);
		    			  AllFontLabels.get(i).setBackground(Color.yellow);

		    		  }
		    		  else
		    		  {
		    			  AllFontLabels.get(i).setOpaque(false);
		    			  AllFontLabels.get(i).setBackground(Color.white);
		    		  }
		    			  
		    		  
		    	  }
		      }
		});
	}
	
	public void CollectContent()
	{
		
		// Directory path here
		String ComputerFontPath = "/Volumes/Macintosh HD/Library/Fonts"; 
		File ComputerFontfolder = new File(ComputerFontPath);
		File[] ComputerlistOfFiles = ComputerFontfolder.listFiles(); 
		
		String MyFontPath = System.getProperty("user.home") + "/Library/Fonts"; 
		File MyFontfolder = new File(MyFontPath);
		File[] MylistOfFiles = MyFontfolder.listFiles(); 
				
		for (int i = 0; i < ComputerlistOfFiles.length; i++) 
		{
			if(ComputerlistOfFiles[i].isHidden() == false )
			{
				AllFontNames.add(ComputerlistOfFiles[i].getName());
				AllFontPaths.add(ComputerlistOfFiles[i].getPath());				
			}
		}
		
		for (int i = 0; i < MylistOfFiles.length; i++) 
		{
			if(MylistOfFiles[i].isHidden() == false )
			{
				AllFontNames.add(MylistOfFiles[i].getName());
				AllFontPaths.add(MylistOfFiles[i].getPath());				
			}
		}
		
		Collections.sort(AllFontNames.subList(1, AllFontNames.size()));
		Collections.sort(AllFontPaths.subList(1, AllFontPaths.size()));
		
	}

	public void AddContent()
	{
		int y = 1;
		String FontPath = null;

		for (String F : AllFontNames)
		{
			File ThisFontPath = null;
			for (String P : AllFontPaths)
			{
				if(F.equals(P.replace("/Volumes/Macintosh HD/Library/Fonts/", "")) || F.equals(P.replace(System.getProperty("user.home") + "/Library/Fonts/", "")))
				{
					ThisFontPath = new File(P);
					FontPath = P;
					System.out.println(F + " :: " + P);
					break;
				}
			}
			
			System.out.println("Path For Button!: " + FontPath);
			
			try 
			{

				
				Font font = Font.createFont(Font.TRUETYPE_FONT,ThisFontPath);
				
				int FontSize = 5;
				int FontPixelHeight = 0;
				boolean LoopAgain = true;
				
				do 
				{
					BufferedImage bi = new BufferedImage(5, 5, BufferedImage.TYPE_INT_RGB);
					FontMetrics fm = bi.getGraphics().getFontMetrics(new Font(font.getName(), Font.PLAIN, FontSize));
					FontPixelHeight = fm.getAscent();

					if (FontPixelHeight >= 20 )
					{
						LoopAgain = false;
						
					}
					else 
					{
						FontSize = FontSize + 1;
					}
					System.out.println(FontSize);
					
					
				} while (LoopAgain);			
				

				JLabel FontLabel = new JLabel(F);
				FontLabel.setFont(new Font(font.getName(), Font.PLAIN, FontSize));
				JLabel SampleLabel = new JLabel("The quick brown fox jumps over the lazy dog");
				SampleLabel.setFont(new Font(font.getName(), Font.PLAIN, FontSize));
				JLabel SpacerLabel = new JLabel(" ");
				SpacerLabel.setFont(new Font(font.getName(), Font.PLAIN, FontSize));
				
				AllFontLabels.add(FontLabel);
					
				GridBagConstraints GBC = new GridBagConstraints();
				GBC.fill = GridBagConstraints.HORIZONTAL;
				GBC.weightx = 0.5;
	        	GBC.weighty = 0.5;
	        	GBC.gridx = 0;
	        	GBC.gridy = y;
	        	GBC.gridwidth = 10;
	        	GBC.insets = new Insets(5,10,0,10);
	        	
	        	Content.add(FontLabel,GBC);
	        	
	        	y = y + 1;
	        	GBC.gridx = 0;
	        	GBC.gridy = y;
	        	GBC.insets = new Insets(0,10,5,10);
	        	
	        	Content.add(SampleLabel,GBC);
	        	
	        	GBC.fill = GridBagConstraints.NONE;
				GBC.weightx = 0;
	        	GBC.weighty = 0.5;
	        	y = y + 1;
	        	GBC.gridy = y;
	        	GBC.anchor = GridBagConstraints.NORTHWEST;
	        	GBC.insets = new Insets(0,0,0,0);
	        	
	        	final String FinalFontPath = FontPath;
	        	
	        	JButton OIF = new JButton("Open In Finder");
	        	Content.add(OIF,GBC);
	        	OIF.addActionListener(
	        			new ActionListener( ) 
	        			{
							@Override
							public void actionPerformed(ActionEvent e) 
							{
								ButtonActions.OpenInFinder(FinalFontPath);
								System.out.println(FinalFontPath);
							}
	        			}
	        			);
	        	
	        	y = y + 1;
	        	GBC.gridy = y;
	        	GBC.insets = new Insets(0,0,0,0);
	        	
	    		JLabel Separator = new JLabel("---------------------------------------------");
	    		Separator.setForeground(Color.lightGray);
	    		Content.add(Separator , GBC);
	        	
//	        	JSeparator JS = new JSeparator();
//	        	Content.add(JS,GBC);
//	        	Content.add(new JSeparator(),GBC);
//	        	Content.add(new JSeparator(SwingConstants.HORIZONTAL),GBC);
	        	
	        	y = y + 1;
	        	GBC.gridy = y;
	        	
	        					
			} 
			catch (FontFormatException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			catch (IOException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			ThisWindow.repaint();

		}

	}

	private InputStream getResourceAsStream(java.lang.String p) {
		// TODO Auto-generated method stub
		return null;
	}


}
