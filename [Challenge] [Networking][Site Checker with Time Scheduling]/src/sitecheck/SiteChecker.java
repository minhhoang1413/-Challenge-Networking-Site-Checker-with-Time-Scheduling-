package sitecheck;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class SiteChecker {
	private JFrame frame;
	private JTextField tfWebsite;
	private JTextField tfMin;
	private JTextField tfSec;
	int min;
	int sec;
	String site;
	Timer timer;
	MyTimerTask task;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SiteChecker window = new SiteChecker();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public SiteChecker() {
		initialize();
	}

	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.NORTH);

		JLabel lblWebsite = new JLabel("Website");
		panel.add(lblWebsite);

		tfWebsite = new JTextField();
		panel.add(tfWebsite);
		tfWebsite.setColumns(10);

		JPanel panel_1 = new JPanel();
		frame.getContentPane().add(panel_1, BorderLayout.CENTER);

		tfMin = new JTextField();
		panel_1.add(tfMin);
		tfMin.setColumns(10);

		JLabel label = new JLabel(":");
		panel_1.add(label);

		tfSec = new JTextField();
		panel_1.add(tfSec);
		tfSec.setColumns(10);

		JPanel panel_2 = new JPanel();
		frame.getContentPane().add(panel_2, BorderLayout.SOUTH);

		JButton btnStart = new JButton("Start");
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (!tfWebsite.getText().trim().isEmpty() && !tfMin.getText().trim().isEmpty()
						&& !tfSec.getText().trim().isEmpty()) {
					min = Integer.parseInt(tfMin.getText().trim());
					sec = Integer.parseInt(tfSec.getText().trim());
					site = tfWebsite.getText().trim();

					tfMin.setEditable(false);
					tfSec.setEditable(false);
					tfWebsite.setEditable(false);

					task = new MyTimerTask();
					timer = new Timer();
					timer.schedule(task, 1000, 1000);

				}
			}
		});
		panel_2.add(btnStart);

	}

	private class MyTimerTask extends TimerTask {

		@Override
		public void run() {
			if (min == 0 && sec == 0) {

				//timer = null;
				URL url;
				HttpURLConnection con;
				JDialog dialog = new JDialog();
				try {
					url = new URL(site);
					con = (HttpURLConnection) url.openConnection();
					JOptionPane.showMessageDialog(dialog, con.getResponseCode());
				} catch (MalformedURLException e) {

					e.printStackTrace();
				} catch (IOException e) {

					e.printStackTrace();
				}

				this.cancel();
				tfMin.setEditable(true);
				tfSec.setEditable(true);
				tfWebsite.setEditable(true);
			}

			else if (sec == 0) {
				sec = 59;
				min--;
			} else
				sec--;
			tfMin.setText(min + "");
			tfSec.setText(sec + "");

		}

	}
}
