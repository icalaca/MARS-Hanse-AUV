import java.util.Observable;
import java.util.Observer;
import org.ros.message.MessageListener;
import org.ros.namespace.GraphName;
import org.ros.node.AbstractNodeMain;
import org.ros.node.ConnectedNode;
import org.ros.node.topic.Subscriber;
import std_msgs.Float32;
import hanse_msgs.Ampere;
import hanse_msgs.ScanningSonar;
import geometry_msgs.Vector3Stamped;
import geometry_msgs.PoseStamped;
import geometry_msgs.PointStamped;
import sensor_msgs.Imu;
import sensor_msgs.Temperature;
import sensor_msgs.FluidPressure;
import sensor_msgs.Image;


public class HanseListener extends AbstractNodeMain {
	/*private Float32 accelerometer;
	private Float32 gyroscope;
	private Float32 pinger;
	private Imu imu;
	private Ampere amperemeter;
	private Vector3Stamped flow;
	private Vector3Stamped pollution;
	private PoseStamped posemeter;
	private PointStamped positionmeter;
	private Temperature temperature;
	private FluidPressure depth;
	private ScanningSonar sonar;
	private Image frontCamera;
	private Image bottomCamera;

	public Float32 getAccelerometer(){
		return accelerometer;
	}
	
	public Float32 getGyroscope(){
		return gyroscope;
	}
	
	public Float32 getPinger(){
		return pinger;
	}
	
	public Imu getImu(){
		return imu;
	}
	
	public Ampere getAmperemeter(){
		return amperemeter;
	}
	
	public Vector3Stamped getFlow(){
		return flow;
	}
	
	public Vector3Stamped getPollution(){
		return pollution;
	}
	
	public PoseStamped getPosemeter(){
		return posemeter;
	}
	
	public PointStamped getPositionmeter(){
		return positionmeter;
	}
	
	public Temperature getTemperature(){
		return temperature;
	}
	
	public FluidPressure getDepth(){
		return depth;
	}
	
	public ScanningSonar getSonar(){
		return sonar;
	}
	
	public Image getFrontCamera(){
		return frontCamera;
	}
	
	public Image getBottomCamera(){
		return bottomCamera;
	}*/
	
	class notifyClass extends Observable{
		public void notifyChange(Object obj) {
			setChanged();
			notifyObservers(obj);
		}
	}
	
	
	private notifyClass ntfy = new notifyClass();
	
	public void addNotifyObs(Observer o){
		ntfy.addObserver(o);
	}
	
	@Override
	public GraphName getDefaultNodeName() {
		return GraphName.of("ROSJavaIC/listener");
	}
	
	@Override
	public void onStart(ConnectedNode connectedNode){
		//Subscribe on accelerometer topic
		Subscriber<Float32> subAccelerometer = connectedNode.newSubscriber("/hanse/accelerometer", Float32._TYPE);
		subAccelerometer.addMessageListener(new MessageListener<Float32>() {     
			@Override
			public void onNewMessage(Float32 message) {
				//accelerometer = message;
				NotifyEvent notifyEvent = new NotifyEvent(message, NotifyEvent.Sensors.Accelerometer);
				ntfy.notifyChange(notifyEvent);
			}
		});
		
		//Subscribe on amperemeter topic
		Subscriber<Ampere> subAmperemeter = connectedNode.newSubscriber("/hanse/amperemeter", Ampere._TYPE);
		subAmperemeter.addMessageListener(new MessageListener<Ampere>(){     
			@Override
			public void onNewMessage(Ampere message){
				//amperemeter = message;
				NotifyEvent notifyEvent = new NotifyEvent(message, NotifyEvent.Sensors.Amperemeter);
				ntfy.notifyChange(notifyEvent);
			}
		});
		
		//Subscribe on flow sensor topic
		Subscriber<Vector3Stamped> subFlow = connectedNode.newSubscriber("/hanse/flow", Vector3Stamped._TYPE);
		subFlow.addMessageListener(new MessageListener<Vector3Stamped>() {     
			@Override
			public void onNewMessage(Vector3Stamped message) {
				//flow = message;
				NotifyEvent notifyEvent = new NotifyEvent(message, NotifyEvent.Sensors.Flow);
				ntfy.notifyChange(notifyEvent);
			}
		});
		
		//Subscribe on gyroscope topic
		Subscriber<Float32> subGyroscope = connectedNode.newSubscriber("/hanse/gyroscope", Float32._TYPE);
		subGyroscope.addMessageListener(new MessageListener<Float32>() {     
			@Override
			public void onNewMessage(Float32 message) {
				//gyroscope = message;
				NotifyEvent notifyEvent = new NotifyEvent(message, NotifyEvent.Sensors.Gyroscope);
				ntfy.notifyChange(notifyEvent);
			}
		});
		
		//Subscribe on pinger topic
		Subscriber<Float32> subPinger = connectedNode.newSubscriber("/hanse/pinger", Float32._TYPE);
		subPinger.addMessageListener(new MessageListener<Float32>() {     
			@Override
			public void onNewMessage(Float32 message) {
				//pinger = message;
				NotifyEvent notifyEvent = new NotifyEvent(message, NotifyEvent.Sensors.Pinger);
				ntfy.notifyChange(notifyEvent);
			}
		});
		
		//Subscribe on imu topic
		Subscriber<Imu> subImu = connectedNode.newSubscriber("/hanse/imu", Imu._TYPE);
		subImu.addMessageListener(new MessageListener<Imu>() {     
			@Override
			public void onNewMessage(Imu message) {
				//imu = message;
				NotifyEvent notifyEvent = new NotifyEvent(message, NotifyEvent.Sensors.Imu);
				ntfy.notifyChange(notifyEvent);
			}
		});
		
		//Subscribe on pollution sensor topic
		Subscriber<Vector3Stamped> subPollution = connectedNode.newSubscriber("/hanse/pollution", Vector3Stamped._TYPE);
		subPollution.addMessageListener(new MessageListener<Vector3Stamped>() {     
			@Override
			public void onNewMessage(Vector3Stamped message) {
				//pollution = message;
				NotifyEvent notifyEvent = new NotifyEvent(message, NotifyEvent.Sensors.Pollution);
				ntfy.notifyChange(notifyEvent);
			}
		});
		
		//Subscribe on posemeter topic
		Subscriber<PoseStamped> subPosemeter = connectedNode.newSubscriber("/hanse/posemeter", PoseStamped._TYPE);
		subPosemeter.addMessageListener(new MessageListener<PoseStamped>() {     
			@Override
			public void onNewMessage(PoseStamped message) {
				//posemeter = message;
				NotifyEvent notifyEvent = new NotifyEvent(message, NotifyEvent.Sensors.Posemeter);
				ntfy.notifyChange(notifyEvent);
			}
		});
		
		//Subscribe on positionmeter topic
		Subscriber<PointStamped> subPositionmeter = connectedNode.newSubscriber("/hanse/positionmeter", PointStamped._TYPE);
		subPositionmeter.addMessageListener(new MessageListener<PointStamped>() {     
			@Override
			public void onNewMessage(PointStamped message) {
				//positionmeter = message;
				NotifyEvent notifyEvent = new NotifyEvent(message, NotifyEvent.Sensors.Positionmeter);
				ntfy.notifyChange(notifyEvent);
			}
		});
		
		//Subscribe on temperature sensor topic
		Subscriber<Temperature> subTemperature = connectedNode.newSubscriber("/hanse/pressure/temp", Temperature._TYPE);
		subTemperature.addMessageListener(new MessageListener<Temperature>() {     
			@Override
			public void onNewMessage(Temperature message) {
				//temperature = message;
				NotifyEvent notifyEvent = new NotifyEvent(message, NotifyEvent.Sensors.Temperature);
				ntfy.notifyChange(notifyEvent);
			}
		});
		
		//Subscribe on depth sensor topic
		Subscriber<FluidPressure> subDepth = connectedNode.newSubscriber("/hanse/pressure/depth", FluidPressure._TYPE);
		subDepth.addMessageListener(new MessageListener<FluidPressure>() {     
			@Override
			public void onNewMessage(FluidPressure message) {
				//depth = message;
				NotifyEvent notifyEvent = new NotifyEvent(message, NotifyEvent.Sensors.Depth);
				ntfy.notifyChange(notifyEvent);
			}
		});
		
		//Subscribe on sonar topic
		Subscriber<ScanningSonar> subSonar = connectedNode.newSubscriber("/hanse/sonar/scan", ScanningSonar._TYPE);
		subSonar.addMessageListener(new MessageListener<ScanningSonar>() {     
			@Override
			public void onNewMessage(ScanningSonar message) {
				//sonar = message;
				NotifyEvent notifyEvent = new NotifyEvent(message, NotifyEvent.Sensors.Sonar);
				ntfy.notifyChange(notifyEvent);
			}
		});
		
		//Subscribe on front camera topic
		Subscriber<Image> subFrontCam = connectedNode.newSubscriber("/hanse/camera/front", Image._TYPE);
		subFrontCam.addMessageListener(new MessageListener<Image>() {     
			@Override
			public void onNewMessage(Image message) {
				//frontCamera = message;
				NotifyEvent notifyEvent = new NotifyEvent(message, NotifyEvent.Sensors.FrontCamera);
				ntfy.notifyChange(notifyEvent);
			}
		});
		
		//Subscribe on bottom camera topic
		Subscriber<Image> subBottomCam = connectedNode.newSubscriber("/hanse/camera/bottom", Image._TYPE);
		subBottomCam.addMessageListener(new MessageListener<Image>() {     
			@Override
			public void onNewMessage(Image message) {
				//bottomCamera = message;
				NotifyEvent notifyEvent = new NotifyEvent(message, NotifyEvent.Sensors.BottomCamera);
				ntfy.notifyChange(notifyEvent);
			}
		});
	}
}