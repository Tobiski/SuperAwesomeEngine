package superawesome.games;

import org.lwjgl.opengl.GL11;

public class HUD {
	
	private static boolean drawFPS = true;
	
	public static void renderHUD(int fpsToDraw) {
		GL11.glColor3f(1.0f, 0.0f, 0.0f);
		GL11.glBegin(GL11.GL_LINES);
			GL11.glVertex2f(SAGE.width/2, SAGE.height/2 - 14);
			GL11.glVertex2f(SAGE.width/2, SAGE.height/2 - 4);

			GL11.glVertex2f(SAGE.width/2, SAGE.height/2 + 14);
			GL11.glVertex2f(SAGE.width/2, SAGE.height/2 + 4);
			
			GL11.glVertex2f(SAGE.width/2 - 14, SAGE.height/2);
			GL11.glVertex2f(SAGE.width/2 - 4, SAGE.height/2);
			
			GL11.glVertex2f(SAGE.width/2 + 14, SAGE.height/2);
			GL11.glVertex2f(SAGE.width/2 + 4, SAGE.height/2);
		GL11.glEnd();
		
		if(drawFPS) {
			GL11.glColor3f(00.f, 0.0f, 0.0f);
			SimpleText.drawString("FPS: " + Integer.toString(fpsToDraw), 10, 10);
		}
	}
	
	public static void drawFPS(boolean drawFPS) {
		HUD.drawFPS = drawFPS;
	}
}
