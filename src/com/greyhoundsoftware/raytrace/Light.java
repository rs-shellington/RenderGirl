package com.greyhoundsoftware.raytrace;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

public class Light {
	
	protected Vector3D position;
	protected Vector3D intensity;
	
	public Light() {
		
		position = new Vector3D(0,0,0);
		intensity = new Vector3D(1,1,1);
		
	}
	
	public Light(double x,double y,double z, double cx, double cy, double cz) {
		
		position = new Vector3D(x,y,z);
		intensity = new Vector3D(cx,cy,cz);
		
	}
	
	public Light(Vector3D p, Vector3D i) {
		
		position = new Vector3D(p.toArray());
		intensity = new Vector3D(i.toArray());
		
	}
	
	public Vector3D getPosition() {
		
		return position;
		
	}

	public Vector3D getIntensity() {
		return intensity;
	}

	public void setIntensity(Vector3D intensity) {
		this.intensity = intensity;
	}

}
