package com.greyhoundsoftware.raytrace;

import org.apache.commons.math3.geometry.euclidean.threed.Rotation;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

public class SceneObject {
	
	
	protected Material material;
	
	protected String name = "SceneObject";
	
	public SceneObject() {
		
	}
	
	public boolean hasIntersection(Ray ray) {
		
		return false;
		
	}
	
	
	public Vector3D getColor() {
		return Vector3D.ZERO;
	}
	
	public Vector3D getKd() {
		
		return Vector3D.ZERO;
	
	}
	
	public Vector3D getNormal(Vector3D point) {
		
		return Vector3D.ZERO;
		
	}

	public Material getMaterial() {
		return material;
	}

	public void setMaterial(Material material) {
		this.material = material;
	}
	
	public SceneObject rotate(Rotation rotate) {
		
		return new SceneObject();
		
	}
	
	public SceneObject transpose(Vector3D t) {
		
		return new SceneObject();
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
