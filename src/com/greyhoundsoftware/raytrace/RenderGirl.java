package com.greyhoundsoftware.raytrace;

import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.apache.commons.math3.geometry.euclidean.threed.Rotation;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.BlockFieldMatrix;
import org.apache.commons.math3.linear.FieldMatrix;

public class RenderGirl {
	
	private Scene scene;
	private int width,height;
	
	public RenderGirl(Scene scene, int width, int height) {
		
		this.scene = scene;
		this.width = width;
		this.height = height;
	}
	
	public BufferedImage render() {
		
		BufferedImage img = new BufferedImage(width,height,BufferedImage.TYPE_3BYTE_BGR);
		Camera camera = new Camera(new Vector3D(0.0,0.0,0.0));
		Vector3D cameraPos = camera.getPosition();
		
		for(int i = 0; i < width;i++) {
        	for(int j = 0; j < height; j++) {
        		//img.setRGB(i,j,128<<16+128<<8+128); //RGB  255<<16 == red 255<<8=green 255=blue
        		//img.setRGB(i, j, (63<<16)+(63<<8)+63);
        		img.setRGB(i,j,0);
        	}
        }
		
		for(int i = 0; i < width;i++) {
        	for(int j = 0; j < height; j++) {
        		
        		
        		Pixel xy = new Pixel(i,j);
        		Pixel modified = xy.toCameraSpace(width, height, camera.getFov());
        		
        		Vector3D dir = new Vector3D(modified.x, modified.y, -1).subtract(cameraPos).normalize();
        		Ray ray = new Ray(cameraPos, dir);
        		//Ray ray = new Ray(r.applyTo(cameraPos),r.applyTo(dir));
        		
        		SceneObject closest = null;
        		double tmin = 999999999;
        		
        		for(SceneObject o : scene.getObjects()) {
        		
        			if(o.hasIntersection(ray)) {
        				
        				if(ray.getTmin() < tmin) {
        					closest = o;
        					tmin = ray.getTmin();
        				}
        			}
        			
        		}
        		
        		if(closest != null) {
        			
        			ray.setTmin(tmin);
        			
        			int redAmb = getAmbient(scene, closest.getMaterial(), Material.COLOR.RED);
        			int greenAmb = getAmbient(scene, closest.getMaterial(), Material.COLOR.GREEN);
        			int blueAmb = getAmbient(scene, closest.getMaterial(), Material.COLOR.BLUE);
        			Vector3D c = new Vector3D(redAmb,greenAmb,blueAmb);

        			for(Light l : scene.getLights()) {
        				c = c.add(shade(l,scene,closest,ray));
        			}

        			//img.setRGB(i,j,shade(scene.getLights().get(0),scene,o,ray));
        			int red = (int)Math.min(c.getX(), 255);
        			int green = (int)Math.min(c.getY(),255);
        			int blue = (int)Math.min(c.getZ(),255);
        			img.setRGB(i, j, (red<<16) + (green<<8) + blue);
        		}
        			
        		
        		
        	}
        }
		
		
		
		
		return img;
		
	}
	
	protected Vector3D shade(Light l, Scene scene, SceneObject o, Ray ray) {
		
		//Light l = scene.getLights().get(0);
		
		Vector3D dir = ray.getDirection();
		Vector3D intersectPt = ray.getPoint().add(dir.scalarMultiply(ray.getTmin()+1e-10));
		//intersectPt = intersectPt.add(new Vector3D(.001,.001,.001));
		Vector3D lightDir = l.getPosition().subtract(intersectPt).normalize();
		
		Ray toLight = new Ray(intersectPt, lightDir);
		
		for(SceneObject blocker : scene.getObjects()) {
			
			if(!blocker.equals(o)) {
				
				if(blocker.hasIntersection(toLight)) {
					// in shadow
					
					// ka = the ambient diffuse coefficient of reflection with: 0.0 <= ka <= 1.0
					// Ia = uniform intensity of ambient light
					// kd = the diffuse coefficient of reflection with: 0.0 <= kd <= 1.0
					// Ip = "intensity" of point light source
					// d = distance
					// N = Normal(vector) at the intersect pt.
					// L = vector to the light source
					
					//I = ka * Ia + [(kd * Ip) / (d)] * (N dot L)
					
					// just the ambient I = ka*Ia
					/*int red = getAmbient(scene, o.getMaterial(), Material.COLOR.RED);
					int green = getAmbient(scene, o.getMaterial(), Material.COLOR.GREEN);
					int blue = getAmbient(scene, o.getMaterial(), Material.COLOR.BLUE);
					*/
        			//return((red << 16) + (green << 8) + blue);
        			//return new Vector3D(red,green,blue);
        			return Vector3D.ZERO;
					
				}
				
			}
			
		}
		
		
		/*
		//I = ka * Ia + [(kd * Ip) / (d)] * (N dot L)
		
		*/
		
		int red = getShadedColor(toLight, intersectPt, l, o, Material.COLOR.RED);
		if(red > 0) { 
				red += getSpecular(toLight,intersectPt,l,o,Material.COLOR.RED);
		}
		
		red = Math.min(255,red);
		
		int green = getShadedColor(toLight, intersectPt, l, o, Material.COLOR.GREEN);
		if(green > 0) {
				green += getSpecular(toLight,intersectPt,l,o,Material.COLOR.GREEN);
		}
		
		green = Math.min(255, green);
		
		int blue = getShadedColor(toLight, intersectPt, l, o, Material.COLOR.BLUE);
		if(blue > 0) {
				blue += getSpecular(toLight,intersectPt,l,o,Material.COLOR.BLUE);
		}
		blue = Math.min(255, blue);
		
		//return (red<<16) + (green << 8) + blue;
		return new Vector3D(red,green,blue);
		//return o.getColor();
		
	}
	
	protected int getSpecular(Ray toLight, Vector3D intersectPt, Light l, 
			SceneObject o, Material.COLOR color) {
		
		//R = 2(L dot N)*N-L
		//V = direction toward viewer
		Vector3D L = toLight.getDirection().normalize();
		Vector3D N = o.getNormal(intersectPt);
		double NdotL = N.dotProduct(L);
		N = N.scalarMultiply(2*NdotL);
		Vector3D R = N.subtract(L);
		
		R = R.normalize();
		Vector3D V = new Vector3D(intersectPt.toArray()); //assume camera at 0,0,0
		V = V.negate().normalize();
		
		double RdotV = R.dotProduct(V);
		if(RdotV < 0) {
			return 0;
		}
		
		
		RdotV = Math.pow(RdotV, 350.0);
		//RdotV = Math.max(RdotV, 0) * o.getMaterial().getColor().toArray()[color.ordinal()];
		RdotV = Math.max(RdotV, 0) * o.getMaterial().getKs().toArray()[color.ordinal()];
		RdotV *= 255;
		
		int ret = (int)RdotV;
		
		ret = Math.min(ret, 255);
		
		return ret;
		//return 0;
	}
	
	protected int getShadedColor(Ray toLight, Vector3D intersectPt, Light l, SceneObject o, Material.COLOR color) {
		
		//I = ka * Ia + [(kd * Ip) / (d)] * (N dot L)
		double NdotL = toLight.getDirection().dotProduct(o.getNormal(intersectPt));
		NdotL = Math.max(NdotL, 0);
		
		//(Ip * Kd ) / d
		double C = l.getIntensity().toArray()[color.ordinal()] * o.getMaterial().getKd().toArray()[color.ordinal()];
		//C /= l.getPosition().distance(intersectPt);
		
		
		C *= NdotL;
		//C *=  o.getMaterial().getColor().toArray()[color.ordinal()];
		
		//The ambient light is already added in at the start.
		/*C += (o.getMaterial().getKa().toArray()[color.ordinal()] * 
				scene.getAmbientLight().toArray()[color.ordinal()] * 
				o.getMaterial().getColor().toArray()[color.ordinal()]);
		*/
		C *= 255;
		
		int iC = (int) C;
		return Math.min(iC, 255);
		
	}
	
	protected int getAmbient(Scene scene, Material material, Material.COLOR color) {
		
		//ambient = C * Ia * ka  = Color * Ambient intensity of light * ambient coefficient of reflection
		//double dColor = material.getColor().toArray()[color.ordinal()];
		double IaKa = scene.getAmbientLight().toArray()[color.ordinal()] * material.getKa().toArray()[color.ordinal()];
		
		//double ambient = dColor * IaKa;
		double ambient = IaKa;
		// scale for 24-bit colors 0 <= C <= 255
		ambient *= 255;
		
		return Math.min(255, (int)ambient);
		
	}

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
        
        //bright red, pretty shiny
        Vector3D Kd = new Vector3D(1,0,0);
        Vector3D Ka = new Vector3D(1,0,0);
        Vector3D Ks = new Vector3D(1,0,0);
        
        Material m = new Material(Ka, Kd, Ks);
        
        Scene scene = new Scene();
        //scene.setAmbientLight(new Vector3D(.6,.6,.6));
        scene.setAmbientLight(new Vector3D(.3,.3,.3));
        SceneObject obj = new Sphere(5,new Vector3D(0.0,-3.0,-20.0));
        obj.setMaterial(m);
        scene.addObject(obj);
        
        Material m2 = new Material(new Vector3D(1,1,0), new Vector3D(1,1,0), new Vector3D(0,1,0));
        SceneObject s2 = new Sphere(1,new Vector3D(-10.0,10.0,-20));
        s2.setMaterial(m2);
        //scene.addObject(s2);
        
        Vector3D Kd2 = new Vector3D(1,1,0);
        Vector3D Ka2 = new Vector3D(1,1,0);
        Vector3D Ks2 = new Vector3D(1,1,0);
        Material m3 = new Material(Ka2,Kd2,Ks2);
        
        //radius, color center
        SceneObject yellowSphere = new Sphere(3,new Vector3D(-5,3.45,-20));
        yellowSphere.setMaterial(m3);
        scene.addObject(yellowSphere);
        
        SceneObject rightEar = new Sphere(3,new Vector3D(5,3.45,-20));
        rightEar.setMaterial(m3);
        scene.addObject(rightEar);
        
        
        //Light light = new Light(10,5,-15);
        //Light light = new Light(10,5,-5,.6,.6,.6);
        //Light light = new Light(5,0,-9,1,1,1);
        Light light = new Light(5,0,-9,.7,.7,.7);
        scene.addLight(light);
        
        Light l2 = new Light(-5,0,-9,0,0,0);
        //scene.addLight(l2);
        
        SceneObject ltmp = new Sphere(.5, light.getPosition());
        //scene.addObject(ltmp);
        
        
        
        
        
        Scene ns = scene.rotate(-90.0, new Vector3D(0,1,0));
        //ns = ns.rotate(-25.0, new Vector3D(0,1,0));
        ns = ns.transpose(new Vector3D(0,0,-45.5));
        ns = ns.transpose(new Vector3D(5,0,0));
        
        
        SceneObject center = new Sphere(.5, new Vector3D(0,0,-9));
        center.setMaterial(m3);
        ns.addObject(center);
        
        
        SceneObject plane = new Plane(new Vector3D(0,-125,0), new Vector3D(0,1, 0));
        Material m4 = new Material(Kd, Ka,new Vector3D(0,1,0));
        plane.setMaterial(m4); 
        //scene.addObject(plane);
        
        RenderGirl theGirl = new RenderGirl(scene, width, height);
        /*//RenderGirl theGirl = new RenderGirl(ns,width,height);
        BufferedImage img = theGirl.render();
        
        
        ImageIcon imageIcon = new ImageIcon(img);
        JLabel jLabel = new JLabel();
        jLabel.setIcon(imageIcon);
        f.add(jLabel);
        
        
        f.pack();
        f.setVisible(true);
        */
        RenderGirlModel model = new RenderGirlModel(scene);		
		RenderGirlView view = new RenderGirlView(1024,768);
		RenderGirlControl control = new RenderGirlControl("RenderGirl",model,view);
		view.setImg(theGirl.render());
        
        /*JFrame f2 = new JFrame("RenderGirlControl");
        f2.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {System.exit(0);}
        });

        Dimension d2 = new Dimension(100,200);
        f2.setMinimumSize(d2);
        f2.pack();
        f2.setVisible(true);
        */
        
       //RenderGirlControl control = new RenderGirlControl("RenderGirl");
        
	}

}
