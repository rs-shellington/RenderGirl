package com.greyhoundsoftware.raytrace;

public class Pixel {
	
	protected double x,y;
	
	public Pixel(double x, double y) {
		
		this.x = x;
		this.y = y;
		
	}
	
	public Pixel normalize(double width, double height) {
		
		double x = (this.x + .5) / width;
		double y = (this.y + .5) / height;
		
		return new Pixel(x,y);
		
	}
	
	public Pixel remap(double width, double height) {
		
		Pixel norm = this.normalize(width,height);
		
		double x = 2 * norm.x - 1; 
		double y = 1 - 2 * norm.y;
		
		return new Pixel(x,y);
		
	}
	
	public Pixel toCameraSpace(double width, double height, double fov) {
		
		Pixel remap = this.remap(width,height);
		
		double aspectRatio = width/height;
		
		double x = remap.x * aspectRatio * Math.tan(fov/2 * Math.PI/180.0);
		double y = remap.y * Math.tan(fov/2 * Math.PI/180.0);
		
		return new Pixel(x,y);
		
	}
	
	public static void main(String args[]) {
		
		for(int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				Pixel p = new Pixel(i,j);
				Pixel c = p.toCameraSpace(10, 10, 60);
				
				System.out.println(c.x + "," + c.y);
			}
		}
	}

}
