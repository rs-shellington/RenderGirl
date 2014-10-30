package com.greyhoundsoftware.raytrace;

import org.apache.commons.math3.geometry.euclidean.threed.Rotation;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

public class Rectangle extends SceneObject {
	
	Triangle t1, t2;
	
	
	public Rectangle(Vector3D ul, Vector3D ur, 
				     Vector3D ll, Vector3D lr) {
		
		t1 = new Triangle(ul, ll, ur);
		t2 = new Triangle(ur, ll, lr);
		
	}
	
	protected  Rectangle(Triangle t1, Triangle t2) {
		
		this.t1 = t1;
		this.t2 = t2;
		
	}
	
	@Override
	public boolean hasIntersection(Ray ray) {
		
		return t1.hasIntersection(ray) || t2.hasIntersection(ray);
		
	}
	
	@Override
	public Vector3D getNormal(Vector3D point) {
		
		//The point doesn't really matter, both triangles have the same normal
		//Just return one
		
		return t1.getNormal(point);
		
	}

	@Override
	public Rectangle rotate(Rotation rotate) {
		
		Rectangle r = new Rectangle(t1.rotate(rotate), t2.rotate(rotate));
		r.setMaterial(material);
		r.setName(name);
		
		return r;
	}

	@Override
	public Rectangle transpose(Vector3D t) {
		
		Rectangle r = new Rectangle(t1.transpose(t), t2.transpose(t));
		r.setMaterial(material);
		r.setName(name);
		
		return r;
		
	}

}
