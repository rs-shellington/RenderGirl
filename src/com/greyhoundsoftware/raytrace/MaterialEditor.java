package com.greyhoundsoftware.raytrace;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

public class MaterialEditor extends JFrame 
	implements ChangeListener, ActionListener {
	
	
	protected RenderGirlModel model;
	protected RenderGirlView view;
	protected Material m;
	
	protected JTextField kaX, kaY, kaZ;
	protected JTextField kdX, kdY, kdZ;
	protected JTextField ksX, ksY, ksZ;
	
	public MaterialEditor(RenderGirlModel model, RenderGirlView view) {
		
		
		this.model = model;
		this.m = model.getScene().getObjects().get(0).getMaterial();
		this.view = view;
		
		init();
		
	}

	public void setMaterial(Material m) {
		
		this.m = m;
		kaX.setText(Double.toString(m.getKa().getX()));
		kaY.setText(Double.toString(m.getKa().getY()));
		kaZ.setText(Double.toString(m.getKa().getZ()));
		
		kdX.setText(Double.toString(m.getKd().getX()));
		kdY.setText(Double.toString(m.getKd().getY()));
		kdZ.setText(Double.toString(m.getKd().getZ()));
		
		ksX.setText(Double.toString(m.getKs().getX()));
		ksY.setText(Double.toString(m.getKs().getY()));
		ksZ.setText(Double.toString(m.getKs().getZ()));
		
		this.paint(this.getGraphics());
	}
	
	private void init() {
		
		addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {System.exit(0);}
        });

        Dimension d2 = new Dimension(50,50);
        setMinimumSize(d2);
        this.setMaximumSize(d2);
        
        JPanel panel = new JPanel(new FlowLayout());
        panel.setMaximumSize(d2);
        JLabel Kalabel = new JLabel("Ka");
        panel.add(Kalabel);
        
        
        
        Vector3D ka = m.getKa();
        kaX = new JTextField(Double.toString(ka.getX()),5);
        kaX.setName("kaX");
        
        kaY = new JTextField(Double.toString(ka.getY()),5);
        kaY.setName("kaY");
        
        kaZ = new JTextField(Double.toString(ka.getZ()),5);
        kaZ.setName("kaZ");
        
        kaX.addActionListener(this);
        kaY.addActionListener(this);
        kaZ.addActionListener(this);
        
        panel.add(kaX);
        panel.add(kaY);
        panel.add(kaZ);
        
        
        JLabel Kdlabel = new JLabel("Kd");
        panel.add(Kdlabel);
        
        Vector3D kd = m.getKd();
        kdX = new JTextField(Double.toString(kd.getX()),5);
        kdX.setName("kdX");
        
        kdY = new JTextField(Double.toString(kd.getY()),5);
        kdY.setName("kdY");
        
        kdZ = new JTextField(Double.toString(kd.getZ()),5);
        kdZ.setName("kdZ");
        
        panel.add(kdX);
        panel.add(kdY);
        panel.add(kdZ);
        
        kdX.addActionListener(this);
        kdY.addActionListener(this);
        kdZ.addActionListener(this);
        
        JLabel Kslabel = new JLabel("Ks");
        panel.add(Kslabel);
        
        Vector3D ks = m.getKs();
        ksX = new JTextField(Double.toString(ks.getX()),5);
        ksX.setName("ksX");
        
        ksY = new JTextField(Double.toString(ks.getY()),5);
        ksY.setName("ksY");
        
        ksZ = new JTextField(Double.toString(ks.getZ()),5);
        ksZ.setName("ksZ");
        
        panel.add(ksX);
        panel.add(ksY);
        panel.add(ksZ);
        
        ksX.addActionListener(this);
        ksY.addActionListener(this);
        ksZ.addActionListener(this);
        
        this.add(panel);
        
        
        pack();
        setVisible(true);
	}
	
	
	public static void main(String[] args) {
		
		//Ka, Kd, color
		Material m = new Material(Vector3D.ZERO, Vector3D.ZERO, Vector3D.ZERO);
		SceneObject o = new Sphere();
		o.setMaterial(m);
		Scene s = new Scene();
		s.addObject(o);
		RenderGirlModel model = new RenderGirlModel(s);
		RenderGirlView view = new RenderGirlView(1024,768);
		
		MaterialEditor me = new MaterialEditor(model,view);

	}

	@Override
	public void stateChanged(ChangeEvent e) {
		// TODO Auto-generated method stub
		System.out.println(e);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		System.out.println(e);
		JTextField t = (JTextField)e.getSource();
		String name = t.getName();
		
		System.out.println(name);
		
		double kax = Double.parseDouble(kaX.getText());
		double kay = Double.parseDouble(kaY.getText());
		double kaz = Double.parseDouble(kaZ.getText());
		
		double kdx = Double.parseDouble(kdX.getText());
		double kdy = Double.parseDouble(kdY.getText());
		double kdz = Double.parseDouble(kdZ.getText());
		
		double ksx = Double.parseDouble(ksX.getText());
		double ksy = Double.parseDouble(ksY.getText());
		double ksz = Double.parseDouble(ksZ.getText());
		
		Vector3D ka = new Vector3D(kax,kay,kaz);
		Vector3D kd = new Vector3D(kdx,kdy,kdz);
		Vector3D ks = new Vector3D(ksx,ksy,ksz);
		
		Scene s = model.getScene();
		SceneObject o = s.getObjects().get(0);
		Material m = o.getMaterial();
		m.setKa(ka);
		m.setKd(kd);
		m.setKs(ks);
		
		RenderGirl theGirl = new RenderGirl(s,1024,768);
		
		BufferedImage img = theGirl.render();
		view.setImg(img);
		model.setScene(s);
		
		
		
	}

}
