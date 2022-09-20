import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class FilterFrame extends JFrame {
	JPanel cotrolPanelMain = new JPanel();
	JPanel cotrolPanelShow = new JPanel();;
	JPanel cotrolPanelLP = new JPanel();
	JPanel cotrolPanelHP = new JPanel();
	ImagePanel imagePanel;
	ImagePanel imagePanel2;
	JButton btnShow;
	JButton btnLP = new JButton("Low-Pass(Blur)");
	JButton btnHP = new JButton("High-Pass(Sharp)");
	int[][][] data;
	int[][][] newData;
	int height;
	int width;
	BufferedImage img = null;

	FilterFrame() {
		setBounds(0, 0, 1500, 1500);
		getContentPane().setLayout(null);
		setTitle("Image Filters Homework");
		try {
			img = ImageIO.read(new File("file/Munich.png"));
		} catch (IOException e) {
			System.out.println("IO exception");
		}
		height = img.getHeight();
		width = img.getWidth();
		data = new int[height][width][3];
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				int rgb = img.getRGB(x, y);
				data[y][x][0] = Util.getR(rgb);
				data[y][x][1] = Util.getG(rgb);
				data[y][x][2] = Util.getB(rgb);
			}
		}
		btnShow = new JButton("Show");
		cotrolPanelMain = new JPanel();
		cotrolPanelMain.setBounds(0, 0, 1200, 200);
		getContentPane().add(cotrolPanelMain);
		cotrolPanelShow.add(btnShow);
		cotrolPanelShow.add(btnLP);
		cotrolPanelShow.add(btnHP);
		cotrolPanelMain.add(cotrolPanelShow);
		imagePanel = new ImagePanel();
		imagePanel.setBounds(20, 220, 700, 700);
		getContentPane().add(imagePanel);
		imagePanel2 = new ImagePanel();
		imagePanel2.setBounds(720, 220, 700, 700);
		getContentPane().add(imagePanel2);

		btnShow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Util.drawImg(imagePanel, img);
			}
		});

		btnLP.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
					newData = new int[height][width][3];
					for(int rgb = 0; rgb < 3; rgb++)
						for(int i =0; i < height; i++)
							for (int j =0; j < width; j++){
								if(i -1 <0 || j -1 < 0 || i + 1 >= height || j +1 >= width){
									newData[i][j][rgb] = data[i][j][rgb];
									continue;
								}
								int sum = data[i][j][rgb] + data[i][j-1][rgb] + data[i][j+1][rgb]
										+ data[i-1][j][rgb] + data[i-1][j-1][rgb] + data[i-1][j+1][rgb]
										+ data[i+1][j][rgb] + data[i+1][j-1][rgb] + data[i+1][j+1][rgb];
								sum /=9;
								newData[i][j][rgb] = Util.checkPixelBounds(sum);
							}
					Util.drawImg(imagePanel2,newData);
				  //put your code here
			}
		});
		
		btnHP.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				newData = new int[height][width][3];
				for(int rgb = 0; rgb < 3; rgb++)
					for(int i =0; i < height; i++)
						for (int j =0; j < width; j++){
							if(i -1 <0 || j -1 < 0 || i + 1 >= height || j +1 >= width){
								newData[i][j][rgb] = data[i][j][rgb];
								continue;
							}
							int sum = ( data[i][j-1][rgb] + data[i][j+1][rgb]
									+ data[i-1][j][rgb] + data[i-1][j-1][rgb] + data[i-1][j+1][rgb]
									+ data[i+1][j][rgb] + data[i+1][j-1][rgb] + data[i+1][j+1][rgb]);
							sum /= -9;
							sum += data[i][j][rgb] * 17/9;
							newData[i][j][rgb] = Util.checkPixelBounds(sum);
						}
				Util.drawImg(imagePanel2,newData);
				  //put your code here
			}
		});
	}// end of the constructor
		
	public static void main(String[] args) {
		FilterFrame frame = new FilterFrame();
		frame.setSize(1500, 1500);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
//end of the class
