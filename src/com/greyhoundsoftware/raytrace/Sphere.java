package com.greyhoundsoftware.raytrace;

import org.apache.commons.math3.geometry.euclidean.threed.Rotation;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

public class Sphere extends SceneObject {
	
	double radius;
	Vector3D center;
	
	public Sphere() {
		
		radius = 100;
		
		
		//Kdred = .5;
		//Kdblue=0;
		//Kdgreen=0;
		
		center = new Vector3D(0.0, 0.0, 0.0);
		
	}
	
	public Sphere(double radius, Vector3D center) {
		
		this.radius = radius;
		this.center = new Vector3D(center.toArray());
		
		
		
	}
	
	@Override
	public Vector3D getKd() {
		//return new Vector3D(Kdred,Kdblue,Kdgreen);
		return null;
	}
	
	
	
	@Override
	public boolean hasIntersection(Ray ray) {
		// geometric solution
	    //Vec3<T> L = center - ray.orig;
		//Vector between ray's origin and sphere's center
		Vector3D L = center.subtract(ray.point);
		
	    //T tca = L.dot(ray.dir);
		//projection of L onto ray
		double tca = L.dotProduct(ray.getDirection());
	    if (tca < 0) return false;
	    
	    //T d2 = L.dot(L) - tca * tca;
	    double d2 = L.dotProduct(L) - tca*tca;
	    double r2 = radius*radius;
	    if (d2 > r2) return false;
	    //T thc = sqrt(radius2 - d2);
	    double thc = Math.sqrt(r2 - d2);
	    
	    double t0 = tca - thc;
	    double t1 = tca + thc;
	    
	    
	    
	    if (t0 > ray.getTmax()) { 
	    	return false;
	    }
	    else {
	    	ray.setTmin(t0);
	    	ray.setTmax(t1);
	    }
	    
	    
		return true;
		
	}

	
	
	@Override
	public Vector3D getNormal(Vector3D point) {
		
		Vector3D normal = point.subtract(center).normalize();
		return normal;
		
	}
	
	@Override
	public Sphere rotate(Rotation rotate) {
		
		Vector3D c = rotate.applyTo(center);
		Sphere r =  new Sphere(radius,c);
		r.setMaterial(material);
		
		return r;
		
	}
	
	@Override
	public Sphere transpose(Vector3D t) {
		
		Vector3D c = center.add(t);
		Sphere r = new Sphere(radius,c);
		r.setMaterial(material);
		
		return r;
		
		
	}
}
