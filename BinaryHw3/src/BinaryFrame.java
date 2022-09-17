import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class BinaryFrame extends JFrame {
	final int DEFAULT_THRESH = 128;
	JPanel cotrolPanel;
	HistogramPanel histogramPanel;
	ImagePanel imagePanel;
	ImagePanel imagePanelBin;
	JButton btnShow;
	String filename = "file/Munich.png";
	//String filename = "file/F16.png";
	//String filename = "file/plate.png";
	
	int thresh = DEFAULT_THRESH;
	JSlider sliderBin;
	JLabel lbFilename;
	JTextField tfFilename;
	JTextField tfBinValue;
	JLabel lbThresh;
	JLabel lbHistogram;
	int[][][] data;
	int[][] gray;
	int[] histogram;
	int height;
	int width;
	BufferedImage img;
	String keyValue = "";

	BinaryFrame() {
		lbFilename = new JLabel("File name (png):");
		setBounds(0, 0, 1500, 1500);
		getContentPane().setLayout(null);
		setTitle("Binary Imaging (Homework 3)");
		btnShow = new JButton("Show");
		btnShow.setBounds(10, 8, 80, 25);
		lbFilename.setBounds(110, 8, 100, 25);
		tfFilename = new JTextField();
		tfFilename.setText(filename);
		tfFilename.setBounds(205, 8, 200, 25);
		tfBinValue = new JTextField();
		tfBinValue.setText(thresh + "");
		tfBinValue.setBounds(280, 200, 40, 25);
		lbThresh = new JLabel("Threshold");
		lbThresh.setBounds(330, 200, 90, 25);
		lbHistogram = new JLabel("Histogram");
		lbHistogram.setBounds(280, 100, 90, 25);
		int xOffset1 = 7;
		sliderBin = new JSlider(0, 255, DEFAULT_THRESH);
		sliderBin.setBounds(0, 200, 270, 25);
		histogramPanel = new HistogramPanel();
		histogramPanel.setBounds(0 + xOffset1, 40, 256, 150);
		histogramPanel.setBackground(new Color(255, 255, 0));

		cotrolPanel = new JPanel();
		cotrolPanel.setBounds(0, 0, 1500, 500);
		cotrolPanel.setLayout(null);
		cotrolPanel.add(btnShow);
		cotrolPanel.add(lbFilename);
		cotrolPanel.add(tfFilename);
		cotrolPanel.add(sliderBin);
		cotrolPanel.add(histogramPanel);
		cotrolPanel.add(tfBinValue);
		cotrolPanel.add(lbThresh);
		cotrolPanel.add(lbHistogram);
		
		imagePanel = new ImagePanel();
		imagePanel.setBounds(10, 240, 700, 950);
		imagePanelBin = new ImagePanel();
		imagePanelBin.setBounds(750, 240, 700, 950);

		add(imagePanel);
		add(cotrolPanel);
		add(imagePanelBin);

		btnShow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				filename = tfFilename.getText();
				loadImage();
				Graphics g = imagePanel.getGraphics();
				imagePanel.paintComponent(g);
				Graphics g1= imagePanelBin.getGraphics();
				imagePanel.paintComponent(g1);
				g.drawImage(img, 0, 0, null);
				gray();
				histogram = histogram();
				normalize(histogram);
				Graphics g2 = histogramPanel.getGraphics();
				histogramPanel.paintComponent(g2, DEFAULT_THRESH, histogram);
				drawBinary();
			}
		});

		sliderBin.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				//Put your code here
				thresh = sliderBin.getValue();
				tfBinValue.setText( thresh + "");
				histogramPanel.paintComponent(histogramPanel.getGraphics(), thresh, histogram);
				drawBinary();
			}
		});

		tfBinValue.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				//Put your code here
				if(e.getKeyCode() == KeyEvent.VK_ENTER){
					try{
						thresh = Integer.parseInt(tfBinValue.getText());
						if(thresh >= 255){
							thresh = 255;
							tfBinValue.setText(thresh +"");
						}
						sliderBin.setValue(thresh);
						histogramPanel.paintComponent(histogramPanel.getGraphics(), thresh, histogram);
						drawBinary();
					}catch (Exception err){
						tfBinValue.setText(thresh+"");
						return;
					}
				}
				
			}
		});
	}

	int[][][] binary() {
		int[][][] newData = new int[height][width][3];
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				if (gray[i][j] >= thresh) {
					newData[i][j][0] = 255;
					newData[i][j][1] = 255;
					newData[i][j][2] = 255;
				} else {
					newData[i][j][0] = 0;
					newData[i][j][1] = 0;
					newData[i][j][2] = 0;
				}
			}

		}
		return newData;
	}

	void gray() {
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				gray[i][j] = Util.checkPixelBounds(Util.covertToGray(
						data[i][j][0], data[i][j][1], data[i][j][2]));
			}
		}
	}

	int[] histogram() {
		histogram = new int[256];
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				histogram[gray[i][j]]++;
			}
		}
		return histogram;
	}

	void normalize(int[] arr) {
		int max = arr[0];
		for (int i = 1; i < arr.length; i++) {
			if (arr[i] > max)
				max = arr[i];

		}
		double factor = 0.9;
		double scale = 150.0 * factor / max;
		for (int i = 1; i < arr.length; i++)
			arr[i] = (int) (arr[i] * scale);
	}

	void drawBinary() {
		int[][][] newData = binary();
		BufferedImage newImg = new BufferedImage(width, height,
				BufferedImage.TYPE_4BYTE_ABGR);
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				int color = Util.makeColor(newData[i][j][0], newData[i][j][1],
						newData[i][j][2]);
				newImg.setRGB(j, i, color);
			}
		}
		Graphics g1 = imagePanelBin.getGraphics();
		g1.drawImage(newImg, 0, 0, width, height, BinaryFrame.this);
	}

	void loadImage() {
		initThresh();
		try {
			img = ImageIO.read(new File(filename));
		} catch (IOException e) {
			System.out.println("IO exception");
		}
		height = img.getHeight();
		width = img.getWidth();
		data = new int[height][width][3];
		gray = new int[height][width];
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				int rgb = img.getRGB(x, y);
				data[y][x][0] = Util.getR(rgb);
				data[y][x][1] = Util.getG(rgb);
				data[y][x][2] = Util.getB(rgb);
			}
		}
	}

	void initThresh() {
		tfBinValue.setText(DEFAULT_THRESH + " ");
		sliderBin.setValue(DEFAULT_THRESH);
	}

	public static void main(String [] args){
		BinaryFrame frame = new BinaryFrame();
		
		frame.setSize(1500, 1500);
		frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
