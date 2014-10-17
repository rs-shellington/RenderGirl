package com.greyhoundsoftware.raytrace.test;

import static org.junit.Assert.*;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.junit.Test;

import com.greyhoundsoftware.raytrace.Ray;
import com.greyhoundsoftware.raytrace.Rectangle;
import com.greyhoundsoftware.raytrace.Triangle;

public class RectangleIntersectionTest {

	@Test
	public void testFindsClosest() {
		
		
		
		// Try a few basic intersection tests
		Vector3D p1,p2,p3,p4;
		
		p1 = new Vector3D(-1,1,0);
		p2 = new Vector3D(1,1,0);
		p3 = new Vector3D(-1,0,0);
		p4 = new Vector3D(1,0,0);

		Rectangle r =  new Rectangle(p1,p2,p3,p4);
		
		Vector3D r1,r2,r3,r4;
		r1 = new Vector3D(-1,1,-3);
		r2 = new Vector3D(1,1,-3);
		r3 = new Vector3D(-1,0,-3);
		r4 = new Vector3D(1,0,-3);
		
		Rectangle rect2 = new Rectangle(r1,r2,r3,r4);
		
		Ray ray = new Ray(new Vector3D(0,0,5), new Vector3D(0,0,-1));
		
		assertEquals("Ray Hits",true, r.hasIntersection(ray));
		
		double tmin = ray.getTmin();
		
		assertEquals("Ray Hits", true, rect2.hasIntersection(ray));
		assertEquals("R closer than rect2", true, tmin < ray.getTmin());
		
		System.out.println(tmin);
		System.out.println(ray.getTmin());
		
		
	}

	

}
