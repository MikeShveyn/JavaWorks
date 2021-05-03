package components1;

import java.awt.Graphics;

public interface ThreadBand {
	public void StopMe();
	public void ResumeMe();
	public void Sleep();
	public void DrawMe(Graphics g);
}
