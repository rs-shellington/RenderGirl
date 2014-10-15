package com.greyhoundsoftware.raytrace;

import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class RenderGirlView extends JFrame {
	
	private BufferedImage img;
	private JLabel imglabel;
	
	public RenderGirlView(int width,int height) {
		
		super("RenderGirl");
		
		init(width,height);
		
	}
	
	private void init(int width, int height) {
		
		//JFrame f = new JFrame("RenderGirl");
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {System.exit(0);}
        });
        
        
        
        Dimension d = new Dimension(width,height);
        setMinimumSize(d);
        
        img = new BufferedImage(width,height,BufferedImage.TYPE_3BYTE_BGR);
        for(int i = 0;i < width;i++) {
        	for(int j = 0; j < height;j++){
        		img.setRGB(i, j, 255<<8);
        	}
        }
        
        ImageIcon imageIcon = new ImageIcon(img);
        imglabel = new JLabel();
        imglabel.setIcon(imageIcon);
        add(imglabel);
        
        
        pack();
        setVisible(true);
	}

	public static void main(String[] args) {
		
		RenderGirlView rgv = new RenderGirlView(1024,768);
		
		
		
	}

	public BufferedImage getImg() {
		return img;
	}

	public void setImg(BufferedImage img) {
		
		this.remove(imglabel);
		this.img = img;
		
		imglabel = new JLabel();
		ImageIcon icon = new ImageIcon(img);
		imglabel.setIcon(icon);
		
		this.add(imglabel);
		
		pack();
		this.update(this.getGraphics());
	}

	
}
