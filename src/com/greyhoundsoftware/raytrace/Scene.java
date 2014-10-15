package com.greyhoundsoftware.raytrace;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.geometry.euclidean.threed.Rotation;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

public class Scene {
	
	protected List<SceneObject> objects;
	protected List<Light> lights;
	Vector3D ambientLight;
	
	public Scene() {
		
		objects = new ArrayList<SceneObject>();
		lights = new ArrayList<Light>();
		ambientLight = new Vector3D(.5,.5,.5);
		
		
	}
	
	public List<SceneObject> getObjects() {
		return objects;
	}
	
	public void addObject(SceneObject obj) {
		
		objects.add(obj);
		
	}
	
	public List<Light> getLights() {
		
		return lights;
	}
	
	public void addLight(Light light) {
		
		lights.add(light);
		
	}

	public Vector3D getAmbientLight() {
		return ambientLight;
	}

	public void setAmbientLight(Vector3D ambientLight) {
		this.ambientLight = new Vector3D(ambientLight.toArray());
	}
	
	public Scene rotate(double angleInDegrees, Vector3D axis) {
		
		double radians = angleInDegrees * Math.PI/180.;
		
		Rotation rotation = new Rotation(axis,radians);
		
		Scene newScene = new Scene();
		
		for(SceneObject o : this.objects) {
			
			newScene.addObject(o.rotate(rotation));
			
		}
		
		for(Light l : lights) {
			
			newScene.addLight(l);
			
		}
		
		newScene.setAmbientLight(ambientLight);
		
		return newScene;
		
	}
	
	public Scene transpose(Vector3D t) {
		
		Scene newScene = new Scene();
		
		for(SceneObject o : this.objects) {
			newScene.addObject(o.transpose(t));
		}
		
		for(Light l : lights) {
			
			newScene.addLight(l);
			
		}
		
		newScene.setAmbientLight(ambientLight);
		
		return newScene;
	}

}
