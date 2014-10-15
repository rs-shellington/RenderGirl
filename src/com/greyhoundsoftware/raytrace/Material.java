package com.greyhoundsoftware.raytrace;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

// ka = the ambient diffuse coefficient of reflection with: 0.0 <= ka <= 1.0
// Ia = uniform intensity of ambient light (r,g,b) 0.0 <= r <= 1.0
// kd = the diffuse coefficient of reflection with: 0.0 <= kd <= 1.0
// Ip = "intensity" of point light source (r,g,b) 0.0 <= r <= 1.0
// d = distance
// N = Normal(vector) at the intersect pt.
// L = vector to the light source
// Frequently ka is set to be the same as kd
//I = ka * Ia + [(kd * Ip) / (d)] * (N dot L)
// I = ka * Ia * C + [(kd * Ip * C) / d] * N dot L
// C = color of the object

public class Material {
	
	public enum COLOR  {RED,GREEN,BLUE};
	
	// 0 = dull things 1 = shiny (reflective) things
	Vector3D Ka; // ka = the ambient diffuse coefficient of reflection with: 0.0 <= ka <= 1.0
	Vector3D Kd; // kd = the diffuse coefficient of reflection with: 0.0 <= kd <= 1.0
	Vector3D Ks; // ks = specular coefficient of reflection
	
	// a red "medium" shiny thing would be color{1,0,0}, Ka{.5,0.0}, Kd{.5,0.0}
	
	public Material(Vector3D Ka, Vector3D Kd, Vector3D Ks) {
		
		this.Ka = new Vector3D(Ka.toArray());
		this.Kd = new Vector3D(Kd.toArray());
		this.Ks = new Vector3D(Ks.toArray());
		
	}

	public Vector3D getKa() {
		return Ka;
	}

	public void setKa(Vector3D ka) {
		Ka = ka;
	}

	public Vector3D getKd() {
		return Kd;
	}

	public void setKd(Vector3D kd) {
		Kd = kd;
	}
	
	public void setKs(Vector3D ks) {
		Ks = ks;
	}
	
	public Vector3D getKs() {
		return Ks;
	}
	

}
