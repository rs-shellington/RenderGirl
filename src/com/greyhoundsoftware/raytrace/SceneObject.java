package com.greyhoundsoftware.raytrace;

import org.apache.commons.math3.geometry.euclidean.threed.Rotation;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

abstract public class SceneObject {
	
	
	protected Material material;
	
	protected String name = "SceneObject";
	
	public SceneObject() {
		
	}
	
	abstract public boolean hasIntersection(Ray ray);
	
	public Vector3D getColor() {
		return Vector3D.ZERO;
	}
	
	public Vector3D getKd() {
		
		return Vector3D.ZERO;
	
	}
	
	abstract public Vector3D getNormal(Vector3D point);

	public Material getMaterial() {
		return material;
	}

	public void setMaterial(Material material) {
		this.material = material;
	}
	
	abstract public SceneObject rotate(Rotation rotate);
	
	abstract public SceneObject transpose(Vector3D t);

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
