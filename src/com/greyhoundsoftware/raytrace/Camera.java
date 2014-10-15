package com.greyhoundsoftware.raytrace;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

public class Camera {
	
	protected Vector3D position;
	protected double fov = 60.0; //Field Of View (degrees)
	
	public Camera(Vector3D position) {
		
		this.position = new Vector3D(position.toArray());
		
	}

	public Vector3D getPosition() {
		return position;
	}

	public double getFov() {
		return fov;
	}

	public void setFov(double fov) {
		this.fov = fov;
	}

}
