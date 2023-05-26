import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Node implements DisplayableSprite {
	private String name;
	private boolean visited;
	
	private static Image image;	
	private double centerX = 0;
	private double centerY = 0;
	private double width = 50;
	private double height = 50;
	private boolean dispose = false;	

	private final double VELOCITY = 200;
	
	private static int nextNodeName = 0;
	
	public Node(double xposition, double yposition) {
		this(Integer.toString(nextNodeName), xposition, yposition);
		nextNodeName++;
	}
	
	public Node (String name, double xPosition, double yPosition) {
		this.name = name;
		this.centerX = xPosition;
		this.centerY = yPosition;
		this.visited = false;
		
		if (image == null) {
			try {
				image = ImageIO.read(new File("res/simple-sprite.png"));
			}
			catch (IOException e) {
				System.out.println(e.toString());
			}		
		}		
	}
	
	public Node getClosestNode(ArrayList<Node> nodes) {
		double closestDistance = Double.POSITIVE_INFINITY;
		Node closestNode = null;
		
		for(Node node: nodes) {
			double distance = Utilities.distance(this.centerX, this.centerY, node.getXPosition(), node.getYPosition());
			if(distance < closestDistance && distance != 0) {
				closestNode = node;
				closestDistance = distance;
			}
		}
		
		return closestNode;
	}
	
	public String getName() {
		return this.name;
	}
	
	public double getXPosition() {
		return this.centerX;
	}
	
	public double getYPosition() {
		return this.centerY;
	}
	
	public boolean getVisited() {
		return this.visited;
	}
	
	public void setVisited(boolean value) {
		this.visited = value;
	}

	public Image getImage() {
		return image;
	}
	
	//DISPLAYABLE
	
	public boolean getVisible() {
		return true;
	}
	
	public double getMinX() {
		return centerX - (width / 2);
	}

	public double getMaxX() {
		return centerX + (width / 2);
	}

	public double getMinY() {
		return centerY - (height / 2);
	}

	public double getMaxY() {
		return centerY + (height / 2);
	}

	public double getHeight() {
		return height;
	}

	public double getWidth() {
		return width;
	}

	public double getCenterX() {
		return centerX;
	};

	public double getCenterY() {
		return centerY;
	};
	
	
	public boolean getDispose() {
		return dispose;
	}

	public void update(Universe universe, KeyboardInput keyboard, long actual_delta_time) {
		
		double velocityX = 0;
		double velocityY = 0;
		
		//LEFT	
		if (keyboard.keyDown(37)) {
			velocityX = -VELOCITY;
		}
		//UP
		if (keyboard.keyDown(38)) {
			velocityY = -VELOCITY;			
		}
		// RIGHT
		if (keyboard.keyDown(39)) {
			velocityX += VELOCITY;
		}
		// DOWN
		if (keyboard.keyDown(40)) {
			velocityY += VELOCITY;			
		}

		double deltaX = actual_delta_time * 0.001 * velocityX;
        this.centerX += deltaX;
		
		double deltaY = actual_delta_time * 0.001 * velocityY;
    	this.centerY += deltaY;

	}


	@Override
	public void setDispose(boolean dispose) {
		this.dispose = true;
	}
} 
