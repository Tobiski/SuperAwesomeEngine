package superawesome.games;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;


public class Camera {
	private float x;
	private float y;
	private float z;
	private float rx;
	private float ry;
	private float rz;
	
	private float fov;
	private float aspect;
	private float near;
	private float far;
	
	private float sensitivity = 0.1f;
	private float gravity = 0.5f;
	private int fps = 60;
	private float currentMoveSpeed;
	private final float walkSpeed = 0.05f;
	private final float runSpeed = 0.1f;
	private float fallSpeed = 0;
	
	private boolean hudEnabled = true;
	
	private boolean jumping = false;
	private boolean onGround = true;
	
	public Camera(float fov, float aspect, float near, float far) {
		x = -50;
		y = -20;
		z = -50;
		rx = 0;
		ry = 0;
		rz = 0;
		
		this.fov = fov;
		this.aspect = aspect;
		this.near = near;
		this.far = far;
		
		initProjection();
	}
	
	private void initProjection() {
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GLU.gluPerspective(fov, aspect, near, far);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
	}
	
	public void use2D() {
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glPushMatrix();
		GL11.glLoadIdentity();
		GL11.glOrtho(0, SAGE.width, 0, SAGE.height, -1, 1);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glPushMatrix();
		GL11.glLoadIdentity();
	}
	
	public void use3D() {
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glPopMatrix();
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glPopMatrix();
	}
	
	public void useView() {
		GL11.glRotatef(rx, 1, 0, 0);
		GL11.glRotatef(ry, 0, 1, 0);
		GL11.glRotatef(rz, 0, 0, 1);
		
		GL11.glTranslatef(x, y, z);
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
	
	public void setX(float x) {
		this.x = x;
	}
	
	public void setY(float y) {
		this.y = y;
	}
	
	public void setZ(float z) {
		this.z = z;
	}
	
	public float getRX() {
		return rx;
	}
	
	public float getRY() {
		return ry;
	}
	
	public float getRZ() {
		return rz;
	}
	
	public void setRX(float rx) {
		if(rx <= 90 && rx >= -90)
			this.rx = rx;
	}
	
	public void setRY(float ry) {
		this.ry = ry;		
		if(this.ry > 360)
			this.ry = 0;
		if(this.ry < 0)
			this.ry = 360;
	}
	
	public void setRZ(float rz) {
		this.rz = rz;
	}
	
	public void setFOV(float fov) {
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GLU.gluPerspective(fov, aspect, near, far);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
	}
	
	public float getSensitivity() {
		return sensitivity;
	}
	
	public void setSensitivity(float sensitivity) {
		this.sensitivity = sensitivity;
	}
	
	public float getWalkSpeed() {
		return walkSpeed;
	}
	
	public float getRunSpeed()  {
		return runSpeed;
	}
	
	public void setMoveSpeed(float currentMoveSpeed) {
		this.currentMoveSpeed = currentMoveSpeed;
	}
	
	public void setHUD(boolean hud) {
		hudEnabled = hud;
	}
	
	public boolean getHUD() {
		return hudEnabled;
	}
	
	public void setFPS(int fps) {
		this.fps = fps;
	}
	
	public int getFPS() {
		return fps;
	}

	public void moveForward(int delta) {
		z = (float) (z+Math.cos(ry/180*Math.PI)*currentMoveSpeed*delta);
		x = (float) (x-Math.sin(ry/180*Math.PI)*currentMoveSpeed*delta);		
	}

	public void moveBackward(int delta) {
		z = (float) (z-Math.cos(ry/180*Math.PI)*currentMoveSpeed*delta);
		x = (float) (x+Math.sin(ry/180*Math.PI)*currentMoveSpeed*delta);
	}

	public void strafeRight(int delta) {
		z = (float) (z-Math.sin(ry/180*Math.PI)*currentMoveSpeed*delta);
		x = (float) (x-Math.cos(ry/180*Math.PI)*currentMoveSpeed*delta);
	}

	public void strafeLeft(int delta) {
		z = (float) (z+Math.sin(ry/180*Math.PI)*currentMoveSpeed*delta);
		x = (float) (x+Math.cos(ry/180*Math.PI)*currentMoveSpeed*delta);
	}
	
	public void turnRight() {
		ry += 0.015f;
	}
	
	public void turnLeft() {
		ry -= 0.015f;
	}
	
	public void applyGravity(int delta) {
		if(jumping) {
			fallSpeed += gravity;
			if(y-fallSpeed < -40) {
				y = -40;
			} else {
				y -= fallSpeed;
			}
			if(y <= -40) {
				jumping = false;
			}
		} else {
			if(y < -20) {
				fallSpeed -= gravity;
				if(y-fallSpeed > -20) {
					y = -20;
					onGround = true;
				} else
					y -= fallSpeed;
			} else {
				y = -20;
				fallSpeed = 0;
			}
		}
	}

	public void jump() {
		jumping = true;
		onGround = false;
		fallSpeed = 4.0f;
	}

	public boolean getJumping() {
		return jumping;
	}

	public boolean getOnGround() {
		return onGround;
	}

	public void setGravity(float gravity) {
		this.gravity = gravity;
	}
}

	