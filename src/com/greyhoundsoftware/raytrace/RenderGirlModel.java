package com.greyhoundsoftware.raytrace;

import java.awt.image.BufferedImage;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

public class RenderGirlModel {

	private Scene scene;
	
	public RenderGirlModel(Scene scene) {
		
		this.scene = scene;
		
	}
	
	
	public void updateAmbient(Vector3D amb) {
		
		scene.setAmbientLight(amb);
		
	}


	public Scene getScene() {
		return scene;
	}


	public void setScene(Scene scene) {
		this.scene = scene;
	}

	
	public static void main(String[] args) {
		
		Scene scene = new Scene();
		scene.setAmbientLight(new Vector3D(.3,.3,.3));
		
		Material m = new Material(new Vector3D(.5,0,.5), new Vector3D(.5,0,.5), 
				new Vector3D(.5,0,.5));
		
		Sphere sphere = new Sphere(1.0, new Vector3D(0,0,-5));
		sphere.setName("Purple Sphere");
		sphere.setMaterial(m);
		scene.addObject(sphere);
		
		Light l = new Light(3,5,-2,1,1,1);
		//Light l = new Light(0,0,0,1,1,1);
		//l = new Light(3,5,-2,.3,0,.4);
		scene.addLight(l);
		
		SceneObject plane = new Plane(new Vector3D(0,-5,0), new Vector3D(0,1, 0));
        Material m4 = new Material(new Vector3D(.8,.8,.8), 
        		                   new Vector3D(.8,.8,.8),
        		                   new Vector3D(.8,.8,.8));
        plane.setMaterial(m4); 
        //scene.addObject(plane);
		
        SceneObject tri = new Triangle(new Vector3D(-1,0,-7), 
                          new Vector3D(1,0,-7),
                          new Vector3D(0,Math.sqrt(3.0),-7));
        tri.setName("Purple triangle");
        tri.setMaterial(m);
        scene.addObject(tri);
        
		int width=1024;
		int height = 768;
		RenderGirl theGirl = new RenderGirl(scene,width,height);
		
		RenderGirlModel model = new RenderGirlModel(scene);		
		RenderGirlView view = new RenderGirlView(1024,768);
		RenderGirlControl control = new RenderGirlControl("RenderGirl",model,view);
		
		
		
		view.setImg(theGirl.render());
		
		
		
	}
	
	
}
