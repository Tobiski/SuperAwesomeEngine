package superawesome.games;

import org.lwjgl.opengl.GL11;

public class Console {
	private Camera cam;
	private String command;
	private SAGE engine;
	
	public Console(Camera cam, SAGE engine) {
		this.cam = cam;
		this.engine = engine;
		this.command = "";
	}
	
	public void appendCommand(char letter) {
		command += letter;
	}
	
	public void popLastLetter() {
		if(command.length() > 0) {
			command = command.substring(0, command.length() - 1);
		}
	}
	
	public void executeCommand() {
		if(command.contains("sensitivity")) {
			int pos = command.lastIndexOf('y') + 2;
			if(pos+2 < command.length()) {
				command = command.substring(pos);
				float sensitivity = Float.parseFloat(command);
				cam.setSensitivity(sensitivity);
			}
		} else if(command.contains("fov")) {
			int pos = command.lastIndexOf('v') + 2;
			if(pos+1 < command.length()) {
				command = command.substring(pos);
				int fov = Integer.parseInt(command);
				cam.setFOV(fov);
				System.out.println("Setting fov to: " + fov);
			}
		} else if(command.contains("gravity")) {
			int pos = command.lastIndexOf('y') + 2;
			if(pos+2 < command.length()) {
				command = command.substring(pos);
				float gravity = Float.parseFloat(command);
				cam.setGravity(gravity);
			}
		} else if(command.contains("hud")) {
			int pos = command.lastIndexOf('d') + 2;
			if(pos < command.length()) {
				command = command.substring(pos);
				int hud = Integer.parseInt(command);
				if(hud == 1) {
					cam.setHUD(true);
				} else if(hud == 0) {
					cam.setHUD(false);
				}
			}
		}  else if(command.contains("fps") && !command.contains("draw")) {
			int pos = command.lastIndexOf('s') + 2;
			if(pos < command.length()) {
				command = command.substring(pos);
				int fps = Integer.parseInt(command);
				cam.setFPS(fps);
			}
		} else if(command.contains("drawfps")) {
			int pos = command.lastIndexOf('s') + 2;
			if(pos < command.length()) {
				command = command.substring(pos);
				int drawFPS = Integer.parseInt(command);
				if(drawFPS == 1) {
					HUD.drawFPS(true);
				} else if(drawFPS == 0) {
					HUD.drawFPS(false);
				}
			}
		} else if(command.equals("quit")) {
			engine.endGame();
		}
		command = "";
	}
	
	public void render() {
		GL11.glBegin(GL11.GL_QUADS);
			GL11.glColor3f(0.5f, 0.5f, 0.5f);
			GL11.glVertex2f(10, SAGE.height - 10);
			GL11.glVertex2f(10, SAGE.height - 30);
			GL11.glVertex2f(SAGE.width - 10, SAGE.height - 30);
			GL11.glVertex2f(SAGE.width - 10, SAGE.height - 10);
			GL11.glColor3f(0.0f, 0.0f, 0.0f);
			GL11.glVertex2f(12, SAGE.height - 12);
			GL11.glVertex2f(12, SAGE.height - 28);
			GL11.glVertex2f(SAGE.width - 12, SAGE.height - 28);
			GL11.glVertex2f(SAGE.width - 12, SAGE.height - 12);
		GL11.glEnd();

		GL11.glColor3f(1.0f, 1.0f, 1.0f);
		SimpleText.drawString(command, 15, SAGE.height - 25);
	}
}
