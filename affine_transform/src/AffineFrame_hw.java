import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class AffineFrame_hw extends JFrame {
	JPanel cotrolPanelMain = new JPanel();
	JPanel cotrolPanelShow = new JPanel();;
	JPanel cotrolPanelBackColor = new JPanel();;
	JPanel cotrolPanelTranslate = new JPanel();;
	JPanel cotrolPanelScale = new JPanel();
	JPanel cotrolPanelRotate = new JPanel();
	JPanel cotrolPanelShear = new JPanel();;
	ImagePanel imagePanel;
	JButton btnShow;
	JButton btnTranslate;
	JButton btnScale;
	JButton btnRotate;
	JButton btnShear;
	JTextField tfRed = new JTextField(5);
	JTextField tfGreen = new JTextField(5);
	JTextField tfBlue = new JTextField(5);
	BufferedImage img;
	BufferedImage newImg;
	JTextField tfDeltaX = new JTextField(5);
	JTextField tfDeltaY = new JTextField(5);
	JTextField tfAmpX = new JTextField(5);
	JTextField tfAmpY = new JTextField(5);
	JTextField tfTheta = new JTextField(5);
	JTextField tfShearX = new JTextField(5);
	JTextField tfShearY = new JTextField(5);
	JLabel lbRed = new JLabel("背景 (R)");
	JLabel lbGreen = new JLabel("背景 (G)");
	JLabel lbBlue = new JLabel("背景 (B)");
	JLabel lbDeltaY = new JLabel("y軸位移");
	JLabel lbDeltaX = new JLabel("x軸位移");
	JLabel lbAmpX = new JLabel("x軸倍率");
	JLabel lbAmpY = new JLabel("y軸倍率");
	JLabel lbTheta = new JLabel("旋轉角度 (1~89度)");
	JLabel lbShearX = new JLabel("X軸切移");
	JLabel lbShearY = new JLabel("Y軸切移 ");
	final int[][][] data;
	int height;
	int width;

	AffineFrame_hw() {
		setBounds(0, 0, 1500, 1500);
		getContentPane().setLayout(null);
		tfRed.setText("0");
		tfGreen.setText("0");
		tfBlue.setText("0");
		tfDeltaX.setText("0");
		tfDeltaY.setText("0");
		tfTheta.setText("0");
		tfAmpX.setText("1.0");
		tfAmpY.setText("1.0");
		tfShearX.setText("0.0");
		tfShearY.setText("0.0");
		setTitle("Affine Transform Homework");
		try {
			 img = ImageIO.read(new File("plate.png"));
			//img = ImageIO.read(new File("file/Munich.png"));
			//img = ImageIO.read(new File("file/F16.png"));
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
		btnShow = new JButton("顯示");
		btnTranslate = new JButton("平移");
		btnScale = new JButton("放大/縮小");
		btnRotate = new JButton("旋轉");
		btnShear = new JButton("Shear(切移)");
		cotrolPanelMain = new JPanel();
		cotrolPanelMain.setLayout(new GridLayout(1, 7));
		cotrolPanelShow.add(btnShow);
		cotrolPanelMain.add(cotrolPanelShow);
		cotrolPanelBackColor.add(lbRed);
		cotrolPanelBackColor.add(tfRed);
		cotrolPanelBackColor.add(lbGreen);
		cotrolPanelBackColor.add(tfGreen);
		cotrolPanelBackColor.add(lbBlue);
		cotrolPanelBackColor.add(tfBlue);
		cotrolPanelMain.add(cotrolPanelBackColor);
		cotrolPanelTranslate.add(lbDeltaX);
		cotrolPanelTranslate.add(tfDeltaX);
		cotrolPanelTranslate.add(lbDeltaY);
		cotrolPanelTranslate.add(tfDeltaY);
		cotrolPanelTranslate.add(btnTranslate);
		cotrolPanelMain.add(cotrolPanelTranslate);
		cotrolPanelScale.add(lbAmpX);
		cotrolPanelScale.add(tfAmpX);
		cotrolPanelScale.add(lbAmpY);
		cotrolPanelScale.add(tfAmpY);
		cotrolPanelScale.add(btnScale);
		cotrolPanelMain.add(cotrolPanelScale);
		cotrolPanelRotate.add(lbTheta);
		cotrolPanelRotate.add(tfTheta);
		cotrolPanelRotate.add(btnRotate);
		cotrolPanelMain.add(cotrolPanelRotate);
		cotrolPanelShear.add(lbShearX);
		cotrolPanelShear.add(tfShearX);
		cotrolPanelShear.add(lbShearY);
		cotrolPanelShear.add(tfShearY);
		cotrolPanelShear.add(btnShear);
		cotrolPanelMain.add(cotrolPanelShear);
		cotrolPanelMain.add(new JPanel());
		cotrolPanelMain.add(new JPanel());
		cotrolPanelMain.add(new JPanel());
		cotrolPanelMain.setBounds(0, 0, 1200, 150);
		getContentPane().add(cotrolPanelMain);
		imagePanel = new ImagePanel();
		imagePanel.setBounds(0, 180, 1500, 1500);
		getContentPane().add(imagePanel);
		btnShow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Graphics g = imagePanel.getGraphics();
				imagePanel.paintComponent(g);
				g.drawImage(img, 0, 0, null);
			}
		});
		btnTranslate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Graphics g = imagePanel.getGraphics();
				int deltaX = Integer.parseInt(tfDeltaX.getText());
				int deltaY = Integer.parseInt(tfDeltaY.getText());
				imagePanel.paintComponent(g);
				g.drawImage(img, deltaX, deltaY, null);
			}
		});
		btnScale.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				double ax = Double.parseDouble(tfAmpX.getText());
				double ay = Double.parseDouble(tfAmpY.getText());

				int newWidth = (int) (ax * data[0].length);
				int newHeight = (int) (ay * data.length);
				newImg = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
				double[][] matrix = { { 1 / ax, 0.0, 0.0 },
						              { 0.0, 1 / ay, 0.0 }, 
						              { 0.0, 0.0, 1 } };
				int[][][] newData = new int[newHeight][newWidth][3];
				for (int y = 0; y < newData.length; y++) {
					for (int x = 0; x < newData[0].length; x++) {
						double[] postion1 = { x, y, 1.0 };
						double[] position2 = Util.affine(matrix, postion1);
						int rgb = exeBilinear(position2[0], position2[1]);
						newImg.setRGB(x, y, rgb);
					}
				}
				Graphics g = imagePanel.getGraphics();
				imagePanel.paintComponent(g);
				g.drawImage(newImg, 0, 0, null);
			}
		});
		
		
		btnRotate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int deg = Integer.parseInt(tfTheta.getText());
				if (deg >89 || deg <1)
					return;
				double theta = 1.0 * deg / 180 * Math.PI;
				double heightCos = height * Math.cos(theta);
				double heightSin = height * Math.sin(theta);
				double widthCos = width * Math.cos(theta);
				double widthSin = width * Math.sin(theta);
				int newWidth = (int) (widthCos + heightSin);
				int newHeight = (int) (heightCos + widthSin);
				newImg = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
				double[][] matrix = { { Math.cos(theta), -1 * Math.sin(theta), 0.0 },
						              { Math.sin(theta), Math.cos(theta), 0.0 }, 
						              { 0.0, 0.0, 1 } };
				fillColor(newImg);
				for (int y = 0; y < newHeight; y++) {
					for (int x = 0; x <newWidth; x++) {
						double[] Position1 = { x - newWidth/2.0, 1.0*newHeight - y - newHeight/2.0, 1.0 };
						double[] Position2 = Util.affine(matrix, Position1);
						double newX = Position2[0] + width/2;
						double newY = height - (Position2[1] + height /2.0);

						if(newX < newWidth && newX >=0 && newY < newHeight && newY >= 0){ // check inbound
							int rgb = exeBilinear(newX, newY);
							newImg.setRGB(x,y, rgb);
						}
					}
				}


				Graphics g = imagePanel.getGraphics();
				imagePanel.paintComponent(g);
				g.drawImage(newImg, 0, 0, null);
			}
		});
		
		btnShear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				double sy = Double.parseDouble(tfShearX.getText());
				double sx = Double.parseDouble(tfShearY.getText());
				int xOffset = Math.abs((int) (sy * height));
				int yOffset = Math.abs((int) (sx * width));
				int newWidth = (int) (width + xOffset);
				int newHeight = (int) (height + yOffset);
				newImg = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
				fillColor(newImg);
				/*
				 * original transform matrix 
				 * 1 sy 0 
				 * sx 1 0
				 *  0 0 1
				 */
				double det = 1.0 / (1.0 - sx * sy);
				double[][] matrix = { { 1.0 / det, -sy / det, 0 }, { -sx / det, 1.0 / det, 0 }, { 0, 0, 1 } };

				for (int y = 0; y < newHeight; y++) {
					for (int x = 0; x < newWidth; x++) {
						double[] postion1 = { x, y, 1.0 };
						double[] position2 = Util.affine(matrix, postion1);
						int newX = (int) position2[0];
						int newY = (int) position2[1];
						if (newX < width && newX >= 0 && newY < height && newY >= 0) {
							int rgb = exeBilinear(position2[0], position2[1]);
							newImg.setRGB(x, y, rgb);
						}
					}
				}
				Graphics g = imagePanel.getGraphics();
				imagePanel.paintComponent(g);
				g.drawImage(newImg, 0, 0, null);
			}
		});
	}

	void fillColor(BufferedImage img) {
		int r = Integer.parseInt(tfRed.getText());
		int g = Integer.parseInt(tfGreen.getText());
		int b = Integer.parseInt(tfBlue.getText());
		int rgb = Util.makeColor(r, g, b);
		for (int i = 0; i < img.getHeight(); i++) {
			for (int j = 0; j < img.getWidth(); j++) {
				img.setRGB(j, i, rgb);
			}
		}
	}
	
	

	int exeBilinear(double x, double y) {
		int left = Util.checkImageBounds((int) Math.floor(x), width);
		int right = Util.checkImageBounds((int) Math.ceil(x), width);
		int top = Util.checkImageBounds((int) Math.floor(y), height);
		int bottom = Util.checkImageBounds((int) Math.ceil(y), height);
		double alpha = 0.0;
		double beta = 0.0;
		if (left < right)
			alpha = x - left;
		if (top < bottom)
			beta = y - top;
		int[] rgbColor = new int[3];
		for (int i = 0; i < 3; i++)
			rgbColor[i] = Util.bilinear(data[top][left][i], 
					                    data[top][right][i], 
					                    data[bottom][left][i],
					data[bottom][right][i], alpha, beta);
		return Util.makeColor(rgbColor[0], rgbColor[1], rgbColor[2]);
	}

	
	int getX(double x1, double y1, double x2, double y2, double y) {
		double m  = (y2-y1)/(x2-x1);
		double x = (y-y1)/m + x1;
		return  (int) x;
	}
	public static void main(String[] args) {
		AffineFrame_hw frame = new AffineFrame_hw();
		frame.setSize(1500, 1500);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
