package superawesome.games;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

public class SAGE {

	public Camera cam;
	public Console con;
	public int mouseDX;
	public int mouseDY;
	public static final int width = 1280;
	public static final int height = 768;
	public boolean shiftDown;
	public boolean running;
	long lastFrame;
	long lastFPS;
	int fps;
	int fpsToDraw;
	
	public Cube cube;
		
	public static void main(String[] asgs) {
		SAGE engine = new SAGE();
		
		engine.init();
		engine.gameLoop();
	}
	
	public SAGE() {
		try {
			Display.setDisplayMode(new DisplayMode(width, height));
			Display.setTitle("Super Awesome Game Engine v0.01");
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
	}
	
	private void init() {
		cube = new Cube((float) (Math.random()*100), 0, (float) (Math.random()*100), 0, (float) (Math.random()*360), 0, 10);
		cam = new Camera(70, (float)Display.getWidth()/(float)Display.getHeight(), 0.3f, 1000);
		Mouse.setGrabbed(true);
		shiftDown = false;
		running = true;
		
		getDelta();
		lastFPS = getTime();
	}
	
	public long getTime() {
		return (Sys.getTime() * 1000) / Sys.getTimerResolution();
	}
	
	public void updateFPS() {
		if (getTime() - lastFPS > 1000) {
			fpsToDraw = fps;
			fps = 0;
			lastFPS += 1000;
		}
		fps++;
	}
	
	public int getDelta() {
		long time = getTime();
		int delta = (int) (time - lastFrame);
		lastFrame = time;
		
		return delta;
	}
	
	public void endGame() {
		running = false;
	}

	public void gameLoop() {
		while(running) {
			int delta = getDelta();
			handleInput(delta);
			update(delta);
			render();
			Display.sync(cam.getFPS());
		}
		
		Display.destroy();
	}

	private void update(int delta) {
		cam.applyGravity(delta);
	}

	private void handleInput(int delta) {
		// Get mouse delta movement since last call
		mouseDX = Mouse.getDX();
		mouseDY = Mouse.getDY();
		
		if(Display.isCloseRequested()) {
			running = false;
		}
		
		while(Keyboard.next()) {
			if(con != null) {
				if(Keyboard.getEventKey() == Keyboard.KEY_BACK && Keyboard.getEventKeyState())  {
					// Remove last letter of the command
					con.popLastLetter();
				} else if(Keyboard.getEventKey() == Keyboard.KEY_BACKSLASH) {
					// DO NOTHING 
				} else if(Keyboard.getEventKey() == Keyboard.KEY_RETURN && Keyboard.getEventKeyState())  {
					// Execute given command
					con.executeCommand();
				} else {
					// Append command with pressed character
					if(Keyboard.getEventKeyState()) {
						con.appendCommand(Keyboard.getEventCharacter());
					}
				}
			}
			
			if(Keyboard.getEventKey() == Keyboard.KEY_BACKSLASH && Keyboard.getEventKeyState()) {
				if(con == null) {
					con = new Console(cam, this);
				} else {
					con = null;
				}
			}
		}
		
		// Apply movement if console is not active
		if(con == null) {
			// Check for ctrl to crouch and shift to walk
			if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
				cam.setMoveSpeed(cam.getWalkSpeed());
			}
			else {
				cam.setMoveSpeed(cam.getRunSpeed());
			}
			
			// Move forward
			if(Keyboard.isKeyDown(Keyboard.KEY_UP) || Keyboard.isKeyDown(Keyboard.KEY_W)) {
				cam.moveForward(delta);
			}
			// Move backwards
			if(Keyboard.isKeyDown(Keyboard.KEY_DOWN) || Keyboard.isKeyDown(Keyboard.KEY_S)) {
				cam.moveBackward(delta);
			}					
			// Strafe right
			if(Keyboard.isKeyDown(Keyboard.KEY_D)) {
				cam.strafeRight(delta);
			}		
			// Strafe left
			if(Keyboard.isKeyDown(Keyboard.KEY_A)) {
				cam.strafeLeft(delta);
			}			
			// Turn right
			if(Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
				cam.turnRight();
			}			
			// Turn left
			if(Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
				cam.turnLeft();
			}
			// Jump
			if(Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
				if(cam.getOnGround()) {
					cam.jump();
				}
			}
			
			// Apply mouse movement to camera
			cam.setRY(cam.getRY()+mouseDX*cam.getSensitivity());
			cam.setRX(cam.getRX()-mouseDY*cam.getSensitivity());
			
			// Poll mouse events
			while(Mouse.next()) {
				if(Mouse.isButtonDown(0)) {
				}
			}
		} // End if console is not active
		
		updateFPS();
	}
	
	private void render() {
		GL11.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
		GL11.glLoadIdentity();
		cam.useView();
		
		// Draw floor
		for(int i = 0; i < 100; i++) {
			for(int j = 0; j < 100; j++) {
				if(j%2 == 0 && i%2 != 0 || j%2 != 0 && i%2 == 0)
					GL11.glColor3f(0.7f, 0.7f, 0.7f);
				else 
					GL11.glColor3f(0.9f, 0.9f, 0.9f);
				
				GL11.glBegin(GL11.GL_QUADS);
					GL11.glVertex3f(i*10, 0,j*10);
					GL11.glVertex3f(i*10, 0,j*10+10);
					GL11.glVertex3f(i*10+10, 0, j*10+10);
					GL11.glVertex3f(i*10+10, 0, j*10);
				GL11.glEnd();
			}
		}
		
		cube.render();

		cam.use2D();
		if(con != null) {
			con.render();
		}
		if(cam.getHUD()) {
			HUD.renderHUD(fpsToDraw);
		}

		cam.use3D();
		
		Display.update();
	}
}