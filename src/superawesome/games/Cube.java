package superawesome.games;

import org.lwjgl.opengl.GL11;

public class Cube {
	private float x;
	private float y;
	private float z;
	private float rx;
	private float ry;
	private float rz;
	private float r;
	private float g;
	private float b;
	private float size;
	
	public Cube(float x, float y, float z, float rx, float ry, float rz, float size) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.rx = rx;
		this.ry = ry;
		this.rz = rz;
		this.size = size;
		
		this.r = (float) Math.random();
		this.g = (float) Math.random();
		this.b = (float) Math.random();
	}
	
	public void render() {
		
		GL11.glTranslatef(x, y, z);
		GL11.glRotatef(rx, 1, 0, 0);
		GL11.glRotatef(ry, 0, 1, 0);
		GL11.glRotatef(rz, 0, 0, 1);
		GL11.glTranslatef(-x, -y, -z);
		
		GL11.glBegin(GL11.GL_QUADS);
			GL11.glColor3f(r, g, b);
			GL11.glVertex3f(x, y, z);
			GL11.glVertex3f(x+size, y, z);
			GL11.glVertex3f(x+size, y+size, z);
			GL11.glVertex3f(x, y+size, z);
	
			GL11.glVertex3f(x, y, z);
			GL11.glVertex3f(x, y, z+size);
			GL11.glVertex3f(x, y+size, z+size);
			GL11.glVertex3f(x, y+size, z);
			
			GL11.glVertex3f(x+size, y, z);
			GL11.glVertex3f(x+size, y, z+size);
			GL11.glVertex3f(x+size, y+size, z+size);
			GL11.glVertex3f(x+size, y+size, z);
			
			GL11.glVertex3f(x, y, z+size);
			GL11.glVertex3f(x+size, y, z+size);
			GL11.glVertex3f(x+size, y+size, z+size);
			GL11.glVertex3f(x, y+size, z+size);
			
			// TOP
			GL11.glVertex3f(x, y+size, z);
			GL11.glVertex3f(x, y+size, z+size);
			GL11.glVertex3f(x+size, y+size, z+size);
			GL11.glVertex3f(x+size, y+size, z);
		GL11.glEnd();
	}

	public float getX() {
		return x;
	}
	public float getY() {
		return y;
	}
	public float getZ() {
		return z;
	}
	public float getSize() {
		return size;
	}
}
