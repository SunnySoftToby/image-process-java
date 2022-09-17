import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;


public class HistogramPanel extends JPanel{
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.setColor(new Color(255, 0,0));
		g.drawLine(127,0 ,127, 150);
	}
	public void paintComponent(Graphics g, int thresh, int [] freq){
		super.paintComponent(g);
		g.setColor(new Color(255, 0,0));
		g.drawLine(thresh,0 ,thresh, 150);
		g.setColor(Color.gray);
		for(int i =0; i< freq.length; i++)
			g.drawLine(i,  150, i, 150-freq[i]);
		g.setColor(new Color(255, 0,0));
		g.drawLine(thresh,0 ,thresh, 150);
		
	}
}
