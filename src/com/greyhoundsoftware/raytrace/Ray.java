package com.greyhoundsoftware.raytrace;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

public class Ray {
	
	protected Vector3D point;
	protected Vector3D direction;
	protected double tmin;
	protected double tmax;
	
	
	public Ray(Vector3D point, Vector3D direction) {
		
		this.point = new Vector3D(point.toArray());
		this.direction = new Vector3D(direction.toArray());
		
		tmin = 0.0;
		tmax = Double.MAX_VALUE;
		
	}


	public Vector3D getPoint() {
		return point;
	}


	public void setPoint(Vector3D point) {
		this.point = new Vector3D(point.toArray());
	}


	public Vector3D getDirection() {
		return direction;
	}


	public void setDirection(Vector3D direction) {
		this.direction = new Vector3D(direction.toArray());
	}
	
	public void setTmin(double tmin) {
		this.tmin = tmin;
	}
	
	public double getTmin() {
		return tmin;
	}
	
	public void setTmax(double tmax) {
		this.tmax = tmax;
	}
	
	public double getTmax() {
		return tmax;
	}

}
