package source;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

public class index extends JPanel {
	private static JButton button1;
	private static JLabel heading;
	Font  f = new Font(Font.SERIF,  Font.BOLD, 30);


	public index() {
		setPreferredSize (new Dimension (1300, 700));
		setLayout (null);

		heading= new JLabel("Greedy-Based Job Scheduling Algorithm");
		add(heading);
		heading.setBounds(150, 20, 1000, 35);
		heading.setFont(f);
		heading.setHorizontalAlignment(JLabel.CENTER);

		button1=new JButton("Allocate");
		add(button1);
		button1.setBounds(480,500,400,80);
		button1.setFont(f);

		 BufferedImage myPicture = null;
	        BufferedImage myPicture1 = null;
	        try {
//	            myPicture = ImageIO.read(new FileInputStream("cloudlet.png"));
//	            myPicture1 = ImageIO.read(new FileInputStream("vm.png"));
				myPicture = ImageIO.read(index.class.getResource("cloudlet.png"));
				myPicture1 = ImageIO.read(index.class.getResource("vm.png"));
	        } catch (IOException e1) {
	            // TODO Auto-generated catch block
	            e1.printStackTrace();
	        }
	        JLabel picLabel = new JLabel(new ImageIcon(myPicture));
	        picLabel.setBounds(20, 120, 600, 350);
	        add(picLabel);

	        JLabel picLabel1 = new JLabel(new ImageIcon(myPicture1));
	        picLabel1.setBounds(680, 120, 600, 350);
	        add(picLabel1);

		button1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//System.out.println("Done");
				try {
					GreedyAlgo t = new GreedyAlgo();
					t.main(new String[0]);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});

	}

	public static void main (String[] args) {
		JFrame frame = new JFrame ("GUI");
		frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add (new index());
		frame.pack();
		frame.setVisible (true);
	}
}