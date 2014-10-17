package com.greyhoundsoftware.raytrace;

import org.apache.commons.math3.geometry.euclidean.threed.Rotation;
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

			// This was my reference...
			// http://scratchapixel.com/lessons/3d-basic-lessons/lesson-9-ray-triangle-intersection/ray-triangle-intersection-geometric-solution/

			Vector3D v0v1 = p2.subtract(p1);
			Vector3D v0v2 = p3.subtract(p1);
			Vector3D N = v0v1.crossProduct(v0v2);
			double nDotRay = N.dotProduct(ray.direction);
			if(nDotRay == 0) {  //ray parallel to triangle
				return false;
			}


			// inside-out test edge0

			Vector3D v0p = P.subtract(p1);
			double v = N.dotProduct(v0v1.crossProduct(v0p));
			if (v < 0.0) {
				return false;
			}



			// inside-out test edge1

			Vector3D v1p = P.subtract(p2);
			Vector3D v1v2 = p3.subtract(p2);
			double w = N.dotProduct(v1v2.crossProduct(v1p));
			if(w < 0) {
				return false;
			}

			// inside-out test edge2

			Vector3D v2p = P.subtract(p3);
			Vector3D v2v0 = p1.subtract(p3);
			double u = N.dotProduct(v2v0.crossProduct(v2p));
			if (u < 0) {
				return false;
			}


			return true;

		}

		return false;

	}
	
	@Override
	public Triangle rotate(Rotation rotate) {
		
		Vector3D p1New = rotate.applyTo(p1);
		Vector3D p2New = rotate.applyTo(p2);
		Vector3D p3New = rotate.applyTo(p3);
		
		Triangle tri = new Triangle(p1New,p2New,p3New);
		tri.setMaterial(material);
		tri.setName(name);
		
		return tri;
		
	}
	
	@Override
	public Triangle transpose(Vector3D t) {
	
		Vector3D p1New = p1.add(t);
		Vector3D p2New = p2.add(t);
		Vector3D p3New = p3.add(t);
		
		Triangle tri = new Triangle(p1New,p2New,p3New);
		tri.setMaterial(material);
		tri.setName(name);
		
		return tri;
	
	}

}
