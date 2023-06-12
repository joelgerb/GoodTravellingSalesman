import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.*;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.MouseMotionAdapter;


public class AnimationFrame extends JFrame {

	final public static int FRAMES_PER_SECOND = 60;
	final public static int SCREEN_HEIGHT = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() - 100;
	final public static int SCREEN_WIDTH = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() - 50;

	private int screenCenterX = SCREEN_WIDTH / 2;
	private int screenCenterY = SCREEN_HEIGHT / 2;
	
	static final double WEIGHT_MIN = 0;
	static final double WEIGHT_MAX = 10;
	static final double WEIGHT_INIT = 1;
	static final double MODIFIER_FOR_SLIDER = 100.0;
	private JLabel showWeight;

	private double scale = 1.0;
	//point in universe on which the screen will center
	private double logicalCenterX = 0;		
	private double logicalCenterY = 0;

	//basic controls on interface... these are protected so that subclasses can access
	protected JPanel panel = null;
	protected JButton btnToggleRunTimer;
	protected JButton btnToggleKeyDelay;
	protected JButton btnReset;
	
	protected JButton btnRunRecursive;
	protected JButton btnRunNeighbour;
	protected JButton btnRunWeightedNeighbour;
	protected JButton btnRunRandom;
	
	//for future development
//	protected JButton btnRunAnts;
	protected JButton btnNewRandomNode;
	protected JButton btnThousandNewRandomNode;
	protected JLabel lblNodes;
	static JLabel lblBottom;
	
	
	
	private JSlider weight;
	
	
	private static boolean buttonDelay = true;
	private long lastKeyPress;

	private static boolean stop = false;

	private long current_time = 0;								//MILLISECONDS
	private long next_refresh_time = 0;							//MILLISECONDS
	private long last_refresh_time = 0;
	private long minimum_delta_time = 1000 / FRAMES_PER_SECOND;	//MILLISECONDS
	private long actual_delta_time = 0;							//MILLISECONDS
	protected long elapsed_time = 0;
	private boolean isPaused = false;
	

	protected KeyboardInput keyboard = new KeyboardInput();
	protected Universe universe = null;

	//local (and direct references to various objects in universe ... should reduce lag by avoiding dynamic lookup
	private Animation animation = null;
	private DisplayableSprite player1 = null;
	private ArrayList<DisplayableSprite> sprites =  (ArrayList<DisplayableSprite>) Main.nodes.clone();
	private ArrayList<Background> backgrounds = null;
	private Background background = null;
	boolean centreOnPlayer = false;
	int universeLevel = 0;
	
	public AnimationFrame(Animation animation)
	{
		super("");
		getContentPane().addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				thisContentPane_mousePressed(e);
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				thisContentPane_mouseReleased(e);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				contentPane_mouseExited(e);
			}
		});
		
		this.animation = animation;
		this.setVisible(true);		
		this.setFocusable(true);
		this.setSize(SCREEN_WIDTH + 20, SCREEN_HEIGHT + 36);

		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				this_windowClosing(e);
			}
		});

		this.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				keyboard.keyPressed(arg0);
			}
			@Override
			public void keyReleased(KeyEvent arg0) {
				keyboard.keyReleased(arg0);
			}
		});
		getContentPane().addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				contentPane_mouseMoved(e);
			}
			@Override
			public void mouseDragged(MouseEvent e) {
				contentPane_mouseMoved(e);
			}
		});
		
		Container cp = getContentPane();
		cp.setBackground(Color.decode("#2C2929"));
		cp.setLayout(null);

		panel = new DrawPanel();
		panel.setLayout(null);
		panel.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
		getContentPane().add(panel, BorderLayout.CENTER);


		
		btnToggleRunTimer = new JButton("Run Timer");
		if (Main.runTimer) {
			btnToggleRunTimer.setText("Run Timer: ON");
		} else {
			btnToggleRunTimer.setText("Run Timer: OFF");
		}
		btnToggleRunTimer.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				btnToggleRunTimer_mouseClicked(arg0);
			}
		});

		btnToggleRunTimer.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnToggleRunTimer.setBounds(15, 500, 130, 40);
		btnToggleRunTimer.setFocusable(false);
		getContentPane().add(btnToggleRunTimer);
		getContentPane().setComponentZOrder(btnToggleRunTimer, 0);
		
		
		
		
		btnToggleKeyDelay = new JButton("Key Delay");
		if (buttonDelay) {
			btnToggleKeyDelay.setText("Key Delay: ON");
		} else {
			btnToggleKeyDelay.setText("Key Delay: OFF");
		}
		btnToggleKeyDelay.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				btnToggleKeyDelay_mouseClicked(arg0);
			}
		});

		btnToggleKeyDelay.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnToggleKeyDelay.setBounds(15, 550, 130, 40);
		btnToggleKeyDelay.setFocusable(false);
		getContentPane().add(btnToggleKeyDelay);
		getContentPane().setComponentZOrder(btnToggleKeyDelay, 0);
		
		
		
		
		btnReset = new JButton("Reset");
		btnReset.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				btnReset_mouseClicked(arg0);
			}
		});

		btnReset.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnReset.setBounds(15, 600, 130, 40);
		btnReset.setFocusable(false);
		getContentPane().add(btnReset);
		getContentPane().setComponentZOrder(btnReset, 0);
		
		
		
		
		
		
		btnRunRecursive = new JButton("Recursive");
		btnRunRecursive.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				btnRunRecursive_mouseClicked(arg0);
			}
		});

		btnRunRecursive.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnRunRecursive.setBounds(SCREEN_WIDTH / 2 - 530, 20, 95, 40);
		btnRunRecursive.setFocusable(false);
		getContentPane().add(btnRunRecursive);
		getContentPane().setComponentZOrder(btnRunRecursive, 0);
		
		
		
		
		btnRunNeighbour = new JButton("Nearest Neighbour");
		btnRunNeighbour.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				btnRunNeighbour_mouseClicked(arg0);
			}
		});

		btnRunNeighbour.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnRunNeighbour.setBounds(SCREEN_WIDTH / 2 - 425, 20, 150, 40);
		btnRunNeighbour.setFocusable(false);
		getContentPane().add(btnRunNeighbour);
		getContentPane().setComponentZOrder(btnRunNeighbour, 0);
		
		
		
		btnRunWeightedNeighbour = new JButton("Weighted Nearest Neighbour");
		btnRunWeightedNeighbour.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				btnRunWeightedNeighbour_mouseClicked(arg0);
			}
		});

		btnRunWeightedNeighbour.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnRunWeightedNeighbour.setBounds(SCREEN_WIDTH / 2 - 265, 20, 210, 40);
		btnRunWeightedNeighbour.setFocusable(false);
		getContentPane().add(btnRunWeightedNeighbour);
		getContentPane().setComponentZOrder(btnRunWeightedNeighbour, 0);
		
		
		
		
		btnRunRandom = new JButton("Random");
		btnRunRandom.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				btnRunRandom_mouseClicked(arg0);
			}
		});

		btnRunRandom.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnRunRandom.setBounds(SCREEN_WIDTH / 2 - 45, 20, 95, 40);
		btnRunRandom.setFocusable(false);
		getContentPane().add(btnRunRandom);
		getContentPane().setComponentZOrder(btnRunRandom, 0);
		
		
		//for future development
		
//		btnRunAnts = new JButton("Ant Colony");
//		btnRunAnts.addMouseListener(new MouseAdapter() {
//			@Override
//			public void mouseClicked(MouseEvent arg0) {
//				btnRunAnts_mouseClicked(arg0);
//			}
//		});
//
//		btnRunAnts.setFont(new Font("Tahoma", Font.BOLD, 12));
//		btnRunAnts.setBounds(SCREEN_WIDTH / 2 + 60, 20, 105, 40);
//		btnRunAnts.setFocusable(false);
//		getContentPane().add(btnRunAnts);
//		getContentPane().setComponentZOrder(btnRunAnts, 0);
		
		
		
		
		btnNewRandomNode = new JButton("New Random Node");
		btnNewRandomNode.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				btnNewRandomNode_mouseClicked(arg0);
			}
		});

		btnNewRandomNode.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnNewRandomNode.setBounds(SCREEN_WIDTH / 2 + 150, 20, 150, 40);
		btnNewRandomNode.setFocusable(false);
		getContentPane().add(btnNewRandomNode);
		getContentPane().setComponentZOrder(btnNewRandomNode, 0);
		
		
		
		
		
		btnThousandNewRandomNode = new JButton("1000 New Random Nodes");
		btnThousandNewRandomNode.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				btnThousandNewRandomNode_mouseClicked(arg0);
			}
		});

		btnThousandNewRandomNode.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnThousandNewRandomNode.setBounds(SCREEN_WIDTH / 2 + 350, 20, 190, 40);
		btnThousandNewRandomNode.setFocusable(false);
		getContentPane().add(btnThousandNewRandomNode);
		getContentPane().setComponentZOrder(btnThousandNewRandomNode, 0);
		
		
		
		
		
		
		class SliderListener implements ChangeListener {
		    

			@Override
			public void stateChanged(ChangeEvent e) {
			        JSlider source = (JSlider)e.getSource();
			        if (!source.getValueIsAdjusting()) {
			            Utilities.weightValue = source.getValue() / MODIFIER_FOR_SLIDER;
			            showWeight.setText("Weight value is = " + Utilities.weightValue);
			        }    
			   
				
			}
		}
		
		showWeight = new JLabel();
		showWeight.setText("Weight value is = " + Utilities.weightValue);
		showWeight.setForeground(Color.WHITE);
		showWeight.setBounds(16, 100, SCREEN_WIDTH - 16, 36);

		
		// create a slider
		weight = new JSlider(JSlider.VERTICAL, (int) (WEIGHT_MIN * MODIFIER_FOR_SLIDER), (int) (WEIGHT_MAX * MODIFIER_FOR_SLIDER), (int) (WEIGHT_INIT * MODIFIER_FOR_SLIDER));
		weight.setBounds(20, 140, 20 , 350);	
		weight.addChangeListener(new SliderListener());
		getContentPane().add(weight);
		getContentPane().setComponentZOrder(weight, 0);
		
		
		getContentPane().add(showWeight);
		getContentPane().setComponentZOrder(showWeight, 0);

		
		
		
		
		lblNodes = new JLabel("Nodes: ");
		lblNodes.setForeground(Color.WHITE);
		lblNodes.setFont(new Font("Consolas", Font.BOLD, 20));
		lblNodes.setBounds(16, 22, SCREEN_WIDTH - 16, 30);
		getContentPane().add(lblNodes);
		getContentPane().setComponentZOrder(lblNodes, 0);

		
		
		lblBottom = new JLabel("Time");
		lblBottom.setForeground(Color.WHITE);
		lblBottom.setFont(new Font("Consolas", Font.BOLD, 30));
		lblBottom.setBounds(16, SCREEN_HEIGHT - 30 - 16, SCREEN_WIDTH - 16, 36);
		lblBottom.setHorizontalAlignment(SwingConstants.CENTER);
		getContentPane().add(lblBottom);
		getContentPane().setComponentZOrder(lblBottom, 0);

	}

	public void start()
	{
		Thread thread = new Thread()
		{
			public void run()
			{
				animationLoop();
				System.out.println("run() complete");
			}
		};

		thread.start();
		System.out.println("main() complete");

	}	
	private void animationLoop() {

		universe = animation.getNextUniverse();
		universeLevel++;

		while (stop == false && universe != null) {

			sprites = universe.getSprites();
			player1 = universe.getPlayer1();
			backgrounds = universe.getBackgrounds();
			centreOnPlayer = universe.centerOnPlayer();
			this.scale = universe.getScale();

			// main game loop
			while (stop == false && universe.isComplete() == false) {

				//adapted from http://www.java-gaming.org/index.php?topic=24220.0
				last_refresh_time = System.currentTimeMillis();
				next_refresh_time = current_time + minimum_delta_time;

				//sleep until the next refresh time
				while (current_time < next_refresh_time)
				{
					//allow other threads (i.e. the Swing thread) to do its work
					Thread.yield();

					try {
						Thread.sleep(1);
					}
					catch(Exception e) {    					
					} 

					//track current time
					current_time = System.currentTimeMillis();
				}

				//read input
				keyboard.poll();
				handleKeyboardInput();

				//UPDATE STATE
				updateTime();				
				universe.update(keyboard, actual_delta_time);
				
				//align animation frame with logical universe
				if (player1 != null && centreOnPlayer) {
					this.logicalCenterX = player1.getCenterX();
					this.logicalCenterY = player1.getCenterY();     
				}
				else {
					this.logicalCenterX = universe.getXCenter();
					this.logicalCenterY = universe.getYCenter();
				}

				//REFRESH
				updateControls();
				this.repaint();
			}

			universe = animation.getNextUniverse();
			keyboard.poll();

		}

		System.out.println("animation complete");
		AudioPlayer.setStopAll(true);
		dispose();	

	}

	protected void updateControls() {
		
		this.lblNodes.setText(String.format("Nodes: %d", Main.nodes.size()));



	}
	


	private void updateTime() {

		current_time = System.currentTimeMillis();
		actual_delta_time = (isPaused ? 0 : current_time - last_refresh_time);
		last_refresh_time = current_time;
		elapsed_time += actual_delta_time;

	}
	
	
	protected void btnToggleRunTimer_mouseClicked(MouseEvent arg0) {
		Main.runTimer = !Main.runTimer;
		
		if (Main.runTimer) {
			btnToggleRunTimer.setText("Run Timer: ON");
		} else {
			btnToggleRunTimer.setText("Run Timer: OFF");
		}
		
	}
	
	protected void btnToggleKeyDelay_mouseClicked(MouseEvent arg0) {
		buttonDelay = !buttonDelay;
		
		if (buttonDelay) {
			btnToggleKeyDelay.setText("Key Delay: ON");
		} else {
			btnToggleKeyDelay.setText("Key Delay: OFF");
		}
	}

	
	protected void btnReset_mouseClicked(MouseEvent arg0) {
		for(DisplayableSprite node : Main.nodes) {
			node.setDispose(true);
		}
		Utilities.clearBestPaths();
	}
	
	
	
	protected void btnRunRecursive_mouseClicked(MouseEvent arg0) {
		if (Main.nodes.size() > 0) {
			Main.solvers[Main.RECURSIVE_INDEX].solve();
		}
	}
	
	protected void btnRunNeighbour_mouseClicked(MouseEvent arg0) {
		if (Main.nodes.size() > 0) {
			Main.solvers[Main.NEIGHBOUR_INDEX].solve();
		}
	}
	
	protected void btnRunWeightedNeighbour_mouseClicked(MouseEvent arg0) {
		if (Main.nodes.size() > 0) {
			Main.solvers[Main.WEIGHTED_NEIGHBOUR_INDEX].solve();
		}
	}
	
	protected void btnRunRandom_mouseClicked(MouseEvent arg0) {
		if (Main.nodes.size() > 0) {
			Main.solvers[Main.RANDOM_INDEX].solve();
		}
	}
	
	//for future development
	
//	protected void btnRunAnts_mouseClicked(MouseEvent arg0) {
//	if (Main.nodes.size() > 0) {
//		Main.solvers[Main.ANTS_INDEX].solve();
//	}
//	}
	
	protected void btnNewRandomNode_mouseClicked(MouseEvent arg0) {
		Node sprite = new Node(Math.floor(Math.random() *((SCREEN_WIDTH - 10) - 150 + 1) + 150) - SCREEN_WIDTH / 2, Math.floor(Math.random() *((SCREEN_HEIGHT - 10) - 70 + 1) + 70) - SCREEN_HEIGHT / 2);
	    Main.nodes.add(sprite);
	    sprites.add(sprite);
	}
	
	protected void btnThousandNewRandomNode_mouseClicked(MouseEvent arg0) {
		for (int i = 0; i < 1000; i++) {
			btnNewRandomNode_mouseClicked(null);
		}
	}
	

	private void handleKeyboardInput() {
		

//		https://stackoverflow.com/questions/15313469/java-keyboard-keycodes-list
		
		if (!(buttonDelay) || lastKeyPress + 200 < current_time) {
			//T
			if (keyboard.keyDown(84)) {
				if (lastKeyPress + 200 < current_time) {
					setLastKeyPress();
					btnToggleRunTimer_mouseClicked(null);
				}
				
			}
			
			//K
			if (keyboard.keyDown(75)) {
				if (lastKeyPress + 200 < current_time) {
					setLastKeyPress();
					btnToggleKeyDelay_mouseClicked(null);
				}
				
			}
			
			
			//BackSpace
			if (keyboard.keyDown(8)) {
				setLastKeyPress();
				btnReset_mouseClicked(null);
			}
			
			
			//R
			if (keyboard.keyDown(82)) {
				setLastKeyPress();
				btnRunRecursive_mouseClicked(null);
			}
			
			//N
			if (keyboard.keyDown(78)) {
				setLastKeyPress();
				btnRunNeighbour_mouseClicked(null);
			}
			
			//W
			if (keyboard.keyDown(87)) {
				setLastKeyPress();
				btnRunWeightedNeighbour_mouseClicked(null);
			}
			
			//Z
			if (keyboard.keyDown(90)) {
				setLastKeyPress();
				btnRunRandom_mouseClicked(null);
			}
			
			//for future development
			//Q
//			if (keyboard.keyDown(81)) {
//				setLastKeyPress();
//				btnRunAnts_mouseClicked(null);
//			}
			
			//P
			if (keyboard.keyDown(80)) {
				setLastKeyPress();
				btnNewRandomNode_mouseClicked(null);
			}
		}
		
		

	}
	
	private void setLastKeyPress() {
		lastKeyPress = current_time;
	}

	class DrawPanel extends JPanel {

		public void paintComponent(Graphics g)
		{	
			
			if (universe == null) {
				return;
			}

			if (backgrounds != null) {
				for (Background background: backgrounds) {
					paintBackground(g, background);
				}
			}

			if (sprites != null) {
				try {
					for (DisplayableSprite activeSprite : sprites) {
						DisplayableSprite sprite = activeSprite;
						if (sprite.getVisible()) {
							if (sprite.getImage() != null) {
								g.drawImage(sprite.getImage(), translateToScreenX(sprite.getMinX()), translateToScreenY(sprite.getMinY()), scaleLogicalX(sprite.getWidth()), scaleLogicalY(sprite.getHeight()), null);						
							}
							else {
								g.setColor(Color.BLUE);
								g.fillRect(translateToScreenX(sprite.getMinX()), translateToScreenY(sprite.getMinY()), scaleLogicalX(sprite.getWidth()), scaleLogicalY(sprite.getHeight()));

							}
						}
					}
				} catch (ConcurrentModificationException e) {
				}				
			}
			
			
			for (Solver solver : Main.solvers) {
				if (solver.getBestPath() != null) {


					Node last = null;
					g.setColor(Color.WHITE);
					for (Node node : solver.getBestPath()) {
						if (last != null) {
							g.drawLine((int) last.getCenterX() + SCREEN_WIDTH / 2, (int) last.getCenterY() + SCREEN_HEIGHT / 2, (int) node.getCenterX() + SCREEN_WIDTH / 2, (int) node.getCenterY() + SCREEN_HEIGHT / 2);

						}
						
						last = node;
					}
				}
				}

		}
		
		private void paintBackground(Graphics g, Background background) {
			
			
			
			if ((g == null) || (background == null)) {
				return;
			}
			
			//what tile covers the top-left corner?
			double logicalLeft = (logicalCenterX  - (screenCenterX / scale) - background.getShiftX());
			double logicalTop =  (logicalCenterY - (screenCenterY / scale) - background.getShiftY()) ;
						
			int row = background.getRow((int)(logicalTop - background.getShiftY() ));
			int col = background.getCol((int)(logicalLeft - background.getShiftX()  ));
			Tile tile = background.getTile(col, row);
			
			boolean rowDrawn = false;
			boolean screenDrawn = false;
			while (screenDrawn == false) {
				while (rowDrawn == false) {
					tile = background.getTile(col, row);
					if (tile.getWidth() <= 0 || tile.getHeight() <= 0) {
						//no increase in width; will cause an infinite loop, so consider this screen to be done
						g.setColor(Color.GRAY);
						g.fillRect(0,0, SCREEN_WIDTH, SCREEN_HEIGHT);					
						rowDrawn = true;
						screenDrawn = true;						
					}
					else {
						Tile nextTile = background.getTile(col+1, row+1);
						int width = translateToScreenX(nextTile.getMinX()) - translateToScreenX(tile.getMinX());
						int height = translateToScreenY(nextTile.getMinY()) - translateToScreenY(tile.getMinY());
						g.drawImage(tile.getImage(), translateToScreenX(tile.getMinX() + background.getShiftX()), translateToScreenY(tile.getMinY() + background.getShiftY()), width, height, null);
					}					
					//does the RHE of this tile extend past the RHE of the visible area?
					if (translateToScreenX(tile.getMinX() + background.getShiftX() + tile.getWidth()) > SCREEN_WIDTH || tile.isOutOfBounds()) {
						rowDrawn = true;
					}
					else {
						col++;
					}
				}
				//does the bottom edge of this tile extend past the bottom edge of the visible area?
				if (translateToScreenY(tile.getMinY() + background.getShiftY() + tile.getHeight()) > SCREEN_HEIGHT || tile.isOutOfBounds()) {
					screenDrawn = true;
				}
				else {
					col = background.getCol(logicalLeft);
					row++;
					rowDrawn = false;
				}
			}
		}				
	}

	private int translateToScreenX(double logicalX) {
		return screenCenterX + scaleLogicalX(logicalX - logicalCenterX);
	}		
	private int scaleLogicalX(double logicalX) {
		return (int) Math.round(scale * logicalX);
	}
	private int translateToScreenY(double logicalY) {
		return screenCenterY + scaleLogicalY(logicalY - logicalCenterY);
	}		
	private int scaleLogicalY(double logicalY) {
		return (int) Math.round(scale * logicalY);
	}

	private double translateToLogicalX(int screenX) {
		int offset = screenX - screenCenterX;
		return offset / scale;
	}
	private double translateToLogicalY(int screenY) {
		int offset = screenY - screenCenterY;
		return offset / scale;			
	}
	
	protected void contentPane_mouseMoved(MouseEvent e) {
//		MouseInput.screenX = e.getX();
//		MouseInput.screenY = e.getY();
		Point point = this.getContentPane().getMousePosition();
		if (point != null) {
			MouseInput.screenX = point.x;		
			MouseInput.screenY = point.y;
			MouseInput.logicalX = translateToLogicalX(MouseInput.screenX);
			MouseInput.logicalY = translateToLogicalY(MouseInput.screenY);
		}
		else {
			MouseInput.screenX = -1;		
			MouseInput.screenY = -1;
			MouseInput.logicalX = Double.NaN;
			MouseInput.logicalY = Double.NaN;
		}
	}
	
	protected void thisContentPane_mousePressed(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) {
			MouseInput.leftButtonDown = true;
			
			boolean duplicate = false;
			
			for (Node node : Main.nodes) {
				if (MouseInput.logicalX == node.getCenterX() && MouseInput.logicalY == node.getCenterY()) {
					duplicate = true;
				}
			}
			if (!duplicate) {
				Node sprite = new Node(MouseInput.logicalX, MouseInput.logicalY);
			    Main.nodes.add(sprite);
			    sprites.add(sprite);
			}
		} else if (e.getButton() == MouseEvent.BUTTON3) {
			MouseInput.rightButtonDown = true;
			
			for (DisplayableSprite sprite : sprites) {
				if (sprite.getMinX() < MouseInput.logicalX && MouseInput.logicalX < sprite.getMaxX()) {
					if (sprite.getMinY() < MouseInput.logicalY && MouseInput.logicalY < sprite.getMaxY()) {
						sprite.setDispose(true);
						Utilities.clearBestPaths();
					}
					
				}
			}
		} else {
			System.out.println(e.getButton());
			//DO NOTHING
		}
	}
	protected void thisContentPane_mouseReleased(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) {
			MouseInput.leftButtonDown = false;
		} else if (e.getButton() == MouseEvent.BUTTON3) {
			MouseInput.rightButtonDown = false;
		} else {
			//DO NOTHING
		}
	}

	protected void this_windowClosing(WindowEvent e) {
		System.out.println("windowClosing()");
		stop = true;
		dispose();	
	}
	protected void contentPane_mouseExited(MouseEvent e) {
		contentPane_mouseMoved(e);
	}
	
	
}
