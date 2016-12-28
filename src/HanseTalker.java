import org.ros.concurrent.CancellableLoop;
import org.ros.namespace.GraphName;
import org.ros.node.AbstractNodeMain;
import org.ros.node.ConnectedNode;
import org.ros.node.topic.Publisher;
import hanse_msgs.sollSpeed;

public class HanseTalker extends AbstractNodeMain {
	private byte rightSpeed;
	private byte leftSpeed;
	private byte backSpeed;
	private byte frontSpeed;
	
	private boolean bMoveForward = false;
	private boolean bMoveBackward = false;
	private boolean bMoveRight = false;
	private boolean bMoveLeft = false;
	private boolean bMoveUp = false;
	private boolean bMoveDown = false;
	
	@Override
	public GraphName getDefaultNodeName() {
		return GraphName.of("ROSJavaIC/talker");
	}
	
	public void moveForward(){
		bMoveForward = true;
	}
	
	public void moveBackward(){
		bMoveBackward = true;
	}
	
	public void moveRight(){
		bMoveRight = true;
	}
	
	public void moveLeft(){
		bMoveLeft = true;
	}
	
	public void moveUp(){
		bMoveUp = true;
	}
	
	public void moveDown(){
		bMoveDown = true;
	}
	@Override
	public void onStart(final ConnectedNode connectedNode) {		
		Publisher<sollSpeed> rightPublisher = connectedNode.newPublisher("/hanse/motors/right", sollSpeed._TYPE);
		Publisher<sollSpeed> leftPublisher = connectedNode.newPublisher("/hanse/motors/left", sollSpeed._TYPE);
		Publisher<sollSpeed> frontPublisher = connectedNode.newPublisher("/hanse/motors/downFront", sollSpeed._TYPE);
		Publisher<sollSpeed> backPublisher = connectedNode.newPublisher("/hanse/motors/downBack", sollSpeed._TYPE);
		connectedNode.executeCancellableLoop(new CancellableLoop() {
			@Override
			protected void setup() {
				rightSpeed = 0;
				leftSpeed = 0;
				frontSpeed = 0;
				backSpeed = 0;
			}
			@Override
			protected void loop() throws InterruptedException {
				sollSpeed speedRight = rightPublisher.newMessage();
				sollSpeed speedLeft = leftPublisher.newMessage();
				sollSpeed speedFront = frontPublisher.newMessage();
				sollSpeed speedBack = backPublisher.newMessage();
				if(bMoveForward){
					if(rightSpeed < 120 && leftSpeed < 120){
						rightSpeed += 10;
						leftSpeed += 10;
					}
					bMoveForward = false;
				}
				if(bMoveBackward){
					if(rightSpeed > -120 && leftSpeed > -120){
						rightSpeed -= 10;
						leftSpeed -= 10;
					}
					bMoveBackward = false;
				}
				if(bMoveLeft){
					if(rightSpeed < 120)
						rightSpeed += 10;
					if(leftSpeed > -120)
						leftSpeed -= 10;
					bMoveLeft = false;
				}
				if(bMoveRight){
					if(leftSpeed > -120)
						rightSpeed -= 10;
					if(rightSpeed < 120)
						leftSpeed += 10;
					bMoveRight = false;
				}
				if(bMoveUp){
					if(frontSpeed < 120 && backSpeed < 120){
						frontSpeed += 10;
						backSpeed += 10;
					}
					bMoveUp = false;
				}
				if(bMoveDown){
					if(frontSpeed > -120 && backSpeed > -120){
						frontSpeed -=10;
						backSpeed -=10;
					}
					bMoveDown = false;
				}
				speedRight.setData(rightSpeed);
				speedLeft.setData(leftSpeed);
				speedFront.setData(frontSpeed);
				speedBack.setData(backSpeed);
				rightPublisher.publish(speedRight);
				leftPublisher.publish(speedLeft);
				frontPublisher.publish(speedFront);
				backPublisher.publish(speedBack);
				Thread.sleep(500);
			}
		});
	}
}