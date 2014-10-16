package com.greyhoundsoftware.raytrace.test;

import static org.junit.Assert.*;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.junit.Test;

import com.greyhoundsoftware.raytrace.Ray;
import com.greyhoundsoftware.raytrace.Triangle;

public class TriangleTest {

	@Test
	public void testHasIntersection() {
		
		
		// Try a few basic intersection tests
		Vector3D p1,p2,p3;
		
		p1 = new Vector3D(-1,0,0);
		p2 = new Vector3D(1,0,0);
		p3 = new Vector3D(0,Math.sqrt(3),0);
		
		Triangle t = new Triangle(p1,p2,p3);
		Ray ray = new Ray(new Vector3D(0,0,5), new Vector3D(0,0,-1));
		
		assertEquals("Ray Hits",true, t.hasIntersection(ray));
		
		ray = new Ray(new Vector3D(2,0,5), new Vector3D(0,0,-1));
		assertEquals("Ray Misses", false, t.hasIntersection(ray));
		
		ray = new Ray(new Vector3D(-2,0,5), new Vector3D(0,0,-1));
		assertEquals("Ray Misses", false, t.hasIntersection(ray));
		
		ray = new Ray(new Vector3D(0,Math.sqrt(3)+.005, 5), new Vector3D(0,0,-1));
		assertEquals("Ray Misses", false, t.hasIntersection(ray));
		
		ray = new Ray(new Vector3D(0,Math.sqrt(3), 5), new Vector3D(0,0,-1));
		assertEquals("Ray Hits", true, t.hasIntersection(ray));
	}

	

}
