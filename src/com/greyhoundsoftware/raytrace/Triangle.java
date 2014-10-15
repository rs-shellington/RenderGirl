package com.greyhoundsoftware.raytrace;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

public class Triangle extends Plane {
	
	public Triangle(Vector3D p1, Vector3D p2, Vector3D p3) {
		
		super(p1,p2,p3);
		
	}
	
	@Override
	public boolean hasIntersection(Ray ray) {
		
		if(super.hasIntersection(ray)) {
			
			return true;
			
		}
		
		return false;
		
	}

}
