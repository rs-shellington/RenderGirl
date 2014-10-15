package com.greyhoundsoftware.raytrace;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

public class Triangle extends Plane {
	
	public Triangle(Vector3D p1, Vector3D p2, Vector3D p3) {
		
		super(p1,p2,p3);
		
	}
	
	@Override
	public boolean hasIntersection(Ray ray) {
		
		if(super.hasIntersection(ray)) {
			
			//intersection point 
			double t = ray.getTmax();
			Vector3D P = ray.point.add(ray.direction.scalarMultiply(t));
			
			/*
			//
		    // Step 2: inside-outside test
		    //
		 
		    vector C; // vector perpendicular to triangle's plane
		 
		    // edge 0
		    vector edge0 = v1 - v0;
		    vector VP0 = P - v0;
		    C = cross(edge0, VP0);
		    if (dot(N, C) < 0)
		        return false; // P is on the right side
		 
		    // edge 1
		    vector edge1 = v2 - v1;
		    vector VP1 = P - v1;
		    C = cross(edge0, VP1);
		    if (dot(N, C) < 0)
		        return false; // P is on the right side
		 
		    // edge 2
		    vector edge2 = v0 - v2;
		    vector VP2 = P - v2;
		    C = cross(CA, CP);
		    if (dot(N, C) < 0)
		        return false; // P is on the right side;
		 
		    return true; // this ray hits the triangle
			*/
			
			return true;
			
		}
		
		return false;
		
	}

}
