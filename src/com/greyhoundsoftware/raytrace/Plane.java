package com.greyhoundsoftware.raytrace;

import org.apache.commons.math3.geometry.euclidean.threed.Rotation;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

public class Plane extends SceneObject {
	
	Vector3D p1,p2,p3;
	
	Vector3D r0;
	Vector3D normal;
	
	public Plane() {
		
		
	}
	
	public Plane(Vector3D p1, Vector3D p2, Vector3D p3) {
		
		
		this.p1 = new Vector3D(p1.toArray());
		this.p2 = new Vector3D(p2.toArray());
		this.p3 = new Vector3D(p3.toArray());
        //p2-p1 x p3-p1		
		
		this.normal = p2.subtract(p1).crossProduct(p3.subtract(p1)).normalize();
		this.r0 = new Vector3D(p1.toArray());
		
	}
	
	public Plane(Vector3D r0, Vector3D n) {
		
		this.normal = new Vector3D(n.toArray());
		this.r0 = new Vector3D(r0.toArray());
		
	}
	
	@Override
	public boolean hasIntersection(Ray ray) {
		
		double top = r0.subtract(ray.getPoint()).dotProduct(normal);
		double bottom = ray.getDirection().dotProduct(normal);
		
		double t;
		
		if(bottom != 0) {
			t = top/bottom;
			if(t >=0) {
				ray.setTmin(t);
				ray.setTmax(t);
				return true;
			}
		}
		
		
		
		return false;
		
	}
	
	@Override
	public Vector3D getNormal(Vector3D point) {
		
		return normal;
		
	}

	@Override
	public SceneObject rotate(Rotation rotate) {
		
		Plane p = new Plane(rotate.applyTo(r0), rotate.applyTo(normal));
		p.setMaterial(material);
		p.setName(name);
		
		return p;
	}

	@Override
	public SceneObject transpose(Vector3D t) {
		
		Plane p = new Plane(r0.add(t), normal.add(t).normalize());
		p.setMaterial(material);
		p.setName(name);
		
		return p;
	}
}
