package com.greyhoundsoftware.raytrace;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

public class Rectangle extends SceneObject {
	
	Triangle t1, t2;
	
	
	public Rectangle(Vector3D ul, Vector3D ur, 
				     Vector3D ll, Vector3D lr) {
		
		t1 = new Triangle(ul, ll, ur);
		t2 = new Triangle(ur, ll, lr);
		
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

}
