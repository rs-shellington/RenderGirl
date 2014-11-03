package com.greyhoundsoftware.raytrace;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.geometry.euclidean.threed.Rotation;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

public class Box extends SceneObject {
	
	double depth, width, height;
	Vector3D center;
	List<Rectangle> sides;
	
	Vector3D normal = Vector3D.ZERO;
	
	public Box(double depth, double width, double height, Vector3D center) {
		
		this.depth = depth;
		this.width = width;
		this.height = height;
		this.center = new Vector3D(center.toArray());
		
		sides = new ArrayList<Rectangle>();
		
		Vector3D half = new Vector3D(width/2.0,height/2.0,depth/2.0);
		
		Vector3D fll = new Vector3D(center.getX() - half.getX(), 
				                    center.getY() - half.getY(), 
				                    center.getZ() + half.getZ());
		
		Vector3D flr = new Vector3D(center.getX() + half.getX(), 
                					center.getY() - half.getY(), 
                					center.getZ() + half.getZ());
		
		Vector3D fur = new Vector3D(center.getX() + half.getX(), 
									center.getY() + half.getY(), 
									center.getZ() + half.getZ());
		
		Vector3D ful= new Vector3D(center.getX() - half.getX(), 
									center.getY() + half.getY(), 
									center.getZ() + half.getZ());
		
		
		Vector3D bll = new Vector3D(center.getX() - half.getX(), 
                				    center.getY() - half.getY(), 
                				    center.getZ() - half.getZ());
		
		Vector3D blr = new Vector3D(center.getX() + half.getX(), 
									center.getY() - half.getY(), 
									center.getZ() - half.getZ());
		
		Vector3D bur = new Vector3D(center.getX() + half.getX(), 
								    center.getY() + half.getY(), 
								    center.getZ() - half.getZ());
		
		Vector3D bul= new Vector3D(center.getX() - half.getX(), 
								   center.getY() + half.getY(), 
								   center.getZ() - half.getZ());
		//public Rectangle(Vector3D ul, Vector3D ur, 
		//	     Vector3D ll, Vector3D lr)
		Rectangle front = new Rectangle(ful, fur, fll, flr);
		front.setName("front");
		
		Rectangle back = new Rectangle(bul,bur,bll,blr);
		back.setName("back");
		
		Rectangle top = new Rectangle(bul,bur,ful,fur);
		top.setName("top");
		
		Rectangle bottom = new Rectangle(bll,blr,fll,flr);
		bottom.setName("bottom");
		
		Rectangle left = new Rectangle(bul,ful,bll,fll);
		left.setName("left");
		
		Rectangle right = new Rectangle(bur,fur,blr,flr);
		right.setName("right");
		
		sides.add(front);
		sides.add(back);
		sides.add(top);
		sides.add(bottom);
		sides.add(left);
		sides.add(right);
		
	}

	@Override
	public boolean hasIntersection(Ray ray) {
		
		
		
		double tmin = Double.MAX_VALUE;
		for(Rectangle r : sides) {
			if(r.hasIntersection(ray)) {
				if(ray.getTmin() < tmin) {
					tmin = ray.getTmin();
					//save the normal for a quick fetch later
					normal = r.getNormal(Vector3D.ZERO);
				}
			}
		}
		if(tmin < Double.MAX_VALUE) {
			ray.setTmin(tmin);
			return true;
		}
		
		return false;
		
	}

	@Override
	public Box rotate(Rotation rotate) {
		
		System.out.println(center.add(center.negate()));
		
		Box origin = transpose(center.negate());
		
		List<Rectangle> rsides = new ArrayList<Rectangle>();
		for(Rectangle r : origin.sides) {
			rsides.add(r.rotate(rotate));
		}
		
		origin.sides = rsides;
		
		
		return origin.transpose(center);
	}

	@Override
	public Box transpose(Vector3D t) {
		
		List<Rectangle> newsides = new ArrayList<Rectangle>();
		
		for(Rectangle r : sides) {
			newsides.add(r.transpose(t));
		}
		
		Vector3D transposed = center.add(t);
		Box b = new Box(depth,width,height,transposed);
		b.setMaterial(material);
		b.sides = newsides;
		
		return b;
		
	}

	@Override
	public Vector3D getNormal(Vector3D point) {
		
		//returns the normal of the face that last had an intersection
		//test return true
		
		return normal;
		
	}

	public List<Rectangle>getSides() {
		
		return this.sides;
	
	}
}
