package com.greyhoundsoftware.raytrace.test;

import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.apache.commons.math3.geometry.euclidean.threed.Rotation;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.junit.Test;

import com.greyhoundsoftware.raytrace.Box;
import com.greyhoundsoftware.raytrace.Light;
import com.greyhoundsoftware.raytrace.Material;
import com.greyhoundsoftware.raytrace.Plane;
import com.greyhoundsoftware.raytrace.Rectangle;
import com.greyhoundsoftware.raytrace.RenderGirl;
import com.greyhoundsoftware.raytrace.Scene;
import com.greyhoundsoftware.raytrace.SceneObject;
import com.greyhoundsoftware.raytrace.Sphere;
import com.greyhoundsoftware.raytrace.Triangle;

public class BoxTester {

	
	public static void main(String[] args) {
		JFrame f = new JFrame("RenderGirl");
        f.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {System.exit(0);}
        });
        
        int width;
        int height;
        if(args.length < 2) {
        	System.out.println("RenderGirl width height");
        	width = 1024;
        	height = 768;
        }
        else {
        	width = Integer.parseInt(args[0]);
        	height = Integer.parseInt(args[1]);
        }
        
        Dimension d = new Dimension(width,height);
        f.setMinimumSize(d);
        
        Scene scene = new Scene();
        scene.setAmbientLight(new Vector3D(.4,.4,.4));
        Vector3D Kd = new Vector3D(.8,.8,.8);
        Vector3D Ka = new Vector3D(.8,.8,.8);
        
		SceneObject plane = new Plane(new Vector3D(0,-5,0), new Vector3D(0,1, 0));
        Material m4 = new Material(Kd, Ka,new Vector3D(.8,.8,.8));
        plane.setMaterial(m4); 
        //scene.addObject(plane);
        
        //Light light = new Light(5,0,-9,1,1,1);
        //Light light = new Light(5,5,-2,1,1,1);
        Light light = new Light(0,8,0,1,1,1);
        scene.addLight(light);
        
        Light l2 = new Light(0,0,0,.8,.8,.8);
        scene.addLight(l2);
        
        SceneObject s = new Sphere(.75, new Vector3D(0,2,-7));
        Material m5 = new Material(new Vector3D(0,0,1), new Vector3D(0,0,1),new Vector3D(0,0,1));
        s.setMaterial(m5);
        //scene.addObject(s);
        
        SceneObject s2= new Sphere(.25, new Vector3D(0,0,-7));
        s2.setMaterial(m5);
        //scene.addObject(s2);
        
        SceneObject box = new Box(.5, .5, .5, new Vector3D(1.5,0,-5));
        box.setMaterial(m5);
        box = box.rotate(new Rotation(new Vector3D(0,1,0), Math.PI/8.));
        //scene.addObject(box);
        
        Box btest = new Box(.5, .5, .5, new Vector3D(1.5,0,-5));
        btest = btest.rotate(new Rotation(new Vector3D(0,1,0),Math.PI/8.));
        for(Rectangle r : btest.getSides()) {
        	r.setMaterial(m5);
        	scene.addObject(r);
        	
        }
        
        
        RenderGirl theGirl = new RenderGirl(scene, width, height);
        BufferedImage img = theGirl.render();
        
        
        ImageIcon imageIcon = new ImageIcon(img);
        JLabel jLabel = new JLabel();
        jLabel.setIcon(imageIcon);
        f.add(jLabel);
        
        
        f.pack();
        f.setVisible(true);

	}

}
