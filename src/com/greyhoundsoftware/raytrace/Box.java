package com.greyhoundsoftware.raytrace;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.geometry.euclidean.threed.Rotation;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

public class Box extends SceneObject {
	
	double depth, width, height;
	Vector3D center;
	List<Rectangle> sides;
	
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
		Rectangle back = new Rectangle(bul,bur,bll,blr);
		
		Rectangle top = new Rectangle(bul,bur,ful,fur);
		Rectangle bottom = new Rectangle(bll,blr,fll,flr);
		
		Rectangle left = new Rectangle(bul,ful,bll,fll);
		Rectangle right = new Rectangle(bur,fur,blr,flr);
		
		sides.add(front);
		sides.add(back);
		sides.add(top);
		sides.add(bottom);
		sides.add(left);
		sides.add(right);
		
	}

	@Override
	public boolean hasIntersection(Ray ray) {
		
		/*Rectangle front = sides.get(0);
		return sides.get(0).hasIntersection(ray);
		*/
		
		for(Rectangle r : sides) {
			if(r.hasIntersection(ray)) {
				return true;
			}
		}
		
		return false;
		
	}

	@Override
	public Box rotate(Rotation rotate) {
		
		
		
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
		
		
		Vector3D rotated = center.add(t);
		Box b = new Box(depth,width,height,rotated);
		b.setMaterial(material);
		
		return b;
		
	}

	@Override
	public Vector3D getNormal(Vector3D point) {
		// TODO Auto-generated method stub
		
		return sides.get(0).getNormal(point);
	}

	public List<Rectangle>getSides() {
		
		return this.sides;
	
	}
}
