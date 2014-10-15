package com.greyhoundsoftware.raytrace;

import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

public class RenderGirlControl extends JFrame
	implements ChangeListener, MouseListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private RenderGirlModel model;
	private RenderGirlView view;
	private MaterialEditor materialEditor;

	public RenderGirlControl(String title, RenderGirlModel model, RenderGirlView view) {
		
		super(title);
		
		this.model = model;
		this.view = view;
		
		init();
		
		
		//test
		SceneObject o = model.getScene().getObjects().get(0);
		Material m = o.getMaterial();
		this.materialEditor = new MaterialEditor(this.model, this.view);
		
		view.addMouseListener(this);
		
	}
	
	private void init() {
		
		//JFrame f2 = new JFrame("RenderGirlControl");
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {System.exit(0);}
        });

        Dimension d2 = new Dimension(100,200);
        setMinimumSize(d2);
        
        JPanel panel = new JPanel();
        
        JLabel label = new JLabel("Ambient Light");
        panel.add(label);
        
        Vector3D amb = model.getScene().getAmbientLight();
        int iAmb = (int)(amb.getX() * 10);
        JSlider ambientSlider = new JSlider(JSlider.HORIZONTAL,
                0, 10, iAmb);
        ambientSlider.setMajorTickSpacing(10);
        ambientSlider.setMinorTickSpacing(1);
        ambientSlider.setPaintTicks(true);
        ambientSlider.setPaintLabels(true);
        ambientSlider.setName("ambientSlider");
        ambientSlider.addChangeListener(this);
        
        panel.add(ambientSlider);
        this.add(panel);
        
        
        JLabel xtrans = new JLabel("X Transpose");
        panel.add(xtrans);
        
        JSlider xtransSlider = new JSlider(JSlider.HORIZONTAL,-10,10,0);
        xtransSlider.setMajorTickSpacing(10);
        xtransSlider.setMinorTickSpacing(1);
        xtransSlider.setPaintTicks(true);
        xtransSlider.setPaintLabels(true);
        xtransSlider.setName("xtransSlider");
        
        xtransSlider.addChangeListener(this);
        panel.add(xtransSlider);
        
        JLabel ytrans = new JLabel("Y Transpose");
        panel.add(ytrans);
        
        JSlider ytransSlider = new JSlider(JSlider.HORIZONTAL,-10,10,0);
        ytransSlider.setMajorTickSpacing(10);
        ytransSlider.setMinorTickSpacing(1);
        ytransSlider.setPaintTicks(true);
        ytransSlider.setPaintLabels(true);
        ytransSlider.setName("ytransSlider");
        
        ytransSlider.addChangeListener(this);
        panel.add(ytransSlider);
        
        JLabel ztrans = new JLabel("Z Transpose");
        panel.add(ztrans);
        
        JSlider ztransSlider = new JSlider(JSlider.HORIZONTAL,-10,10,0);
        ztransSlider.setMajorTickSpacing(10);
        ztransSlider.setMinorTickSpacing(1);
        ztransSlider.setPaintTicks(true);
        ztransSlider.setPaintLabels(true);
        ztransSlider.setName("ztransSlider");
        
        ztransSlider.addChangeListener(this);
        panel.add(ztransSlider);
        
        pack();
        setVisible(true);
		
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		
		JSlider source = (JSlider)e.getSource();
        if (!source.getValueIsAdjusting()) {
        	
        	Scene s;
        	
        	if(source.getName().equals("ambientSlider")) {
        		
        		int fps = (int)source.getValue();
        		double amb = fps/10.0;

        		this.model.getScene().setAmbientLight(new Vector3D(amb,amb,amb));
        		RenderGirl theGirl = new RenderGirl(this.model.getScene(),1024,768);
        		this.view.setImg(theGirl.render());

        		System.out.println(fps);
        	}
        	
        	if(source.getName().contains("transSlider")) {
        		
        		Vector3D t = Vector3D.ZERO;
        		int pos = source.getValue();
        		double ft = pos/10.;
        		
        		if(source.getName().equals("xtransSlider")) {
        	
        			t = new Vector3D(ft,0,0);
        		}
        		else if(source.getName().equals("ytransSlider")) {
        			t = new Vector3D(0,ft,0);
        		}
        		else {
        			t = new Vector3D(0,0,ft);
        		}
        	
        		s = this.model.getScene().transpose(t);
        		
        		RenderGirl theGirl = new RenderGirl(s,1024,768);
        		this.view.setImg(theGirl.render());
        		this.model.setScene(s);
        		
        		System.out.println(ft);
        	
        		source.setValue(0);
        	
        	}
        	
        }
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		System.out.println("mouseClicked");
		int x = e.getX();
		int y = e.getY();
		
		Pixel p = new Pixel(x,y);
	
		p = p.toCameraSpace(1024, 768, new Camera(Vector3D.ZERO).getFov());
		
		//System.out.println(p.x + "," + p.y);
		
		Scene s = model.getScene();
		for(SceneObject o:s.getObjects()) {
			
			Vector3D cameraPos = Vector3D.ZERO;
			Vector3D dir = new Vector3D(p.x, p.y, -1).subtract(cameraPos).normalize();
			Ray ray = new Ray(cameraPos, dir);
			
			if(o.hasIntersection(ray)) {
				System.out.println(o.getName());
				this.materialEditor.setMaterial(o.getMaterial());
			}
			
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	
}
