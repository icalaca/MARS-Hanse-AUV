import java.util.Observer;
import java.util.Observable;
import javafx.application.Application;
import javafx.scene.control.Label;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.application.Platform;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import std_msgs.Float32;
import hanse_msgs.Ampere;
import geometry_msgs.Vector3Stamped;
import geometry_msgs.PoseStamped;
import geometry_msgs.PointStamped;
import sensor_msgs.Imu;
import sensor_msgs.Temperature;
import sensor_msgs.FluidPressure;

public class GUI extends Application implements Observer{
	private Connector con;
	private Label accelerometerLbl = new Label("Accelerometer:");
	private Label gyroscopeLbl = new Label("Gyroscope:");
	private Label pingerLbl = new Label("Pinger:");
	private Label amperemeterLbl = new Label("Ampere meter:");
	private Label pollutionLbl = new Label("Pollution:");
	private Label temperatureLbl = new Label("Temperature:");
	private Label tempVarianceLbl = new Label("Variance:");
	private Label depthLbl = new Label("Depth:");
	private Label depthVarianceLbl = new Label("Variance:");
	private Label flowXLbl = new Label("X:");
	private Label flowYLbl = new Label("Y:");
	private Label flowZLbl = new Label("Z:");
	private Label angVelXLbl = new Label("X:");
	private Label angVelYLbl = new Label("Y:");
	private Label angVelZLbl = new Label("Z:");
	private Label linearAccXLbl = new Label("X:");
	private Label linearAccYLbl = new Label("Y:");
	private Label linearAccZLbl = new Label("Z:");
	private Label orientationXLbl = new Label("X:");
	private Label orientationYLbl = new Label("Y:");
	private Label orientationZLbl = new Label("Z:");
	private Label orientationWLbl = new Label("W:");
	private Label pPositionXLbl = new Label("X:");
	private Label pPositionYLbl = new Label("Y:");
	private Label pPositionZLbl = new Label("Z:");
	private Label pOrientationXLbl = new Label("X:");
	private Label pOrientationYLbl = new Label("Y:");
	private Label pOrientationZLbl = new Label("Z:");
	private Label pOrientationWLbl = new Label("W:");
	private Label positionXLbl = new Label("X:");
	private Label positionYLbl = new Label("Y:");
	private Label positionZLbl = new Label("Z:");
	
	@Override
	public void start(Stage primaryStage){
		con = new Connector();
		HanseListener listInst = con.getListenerInstance();
		listInst.addNotifyObs(this);
		
		primaryStage.setTitle("MARS Hanse Controller");
		GridPane grid = new GridPane();
		grid.setVgap(1);
		grid.setHgap(50);
		grid.setPadding(new Insets(6, 6, 6, 6));
		grid.add(accelerometerLbl, 0, 0);
		grid.add(gyroscopeLbl, 0, 1);
		grid.add(pingerLbl, 0, 2);
		grid.add(amperemeterLbl, 0, 3);
		grid.add(pollutionLbl, 0, 4);
		grid.add(temperatureLbl, 0, 6);
		grid.add(tempVarianceLbl, 0, 7);
		grid.add(depthLbl, 0, 9);
		grid.add(depthVarianceLbl, 0, 10);
		
		Label flowLbl = new Label("Flow");
		grid.add(flowLbl, 0, 12);
		grid.add(flowXLbl, 0, 13);
		grid.add(flowYLbl, 0, 14);
		grid.add(flowZLbl, 0, 15);
		
		Label imuLbl = new Label("Imu");
		Label velLbl = new Label("Angular velocity");
		Label accLbl = new Label("Linear acceleration");
		Label orientationLbl = new Label("Orientation");
		grid.add(imuLbl, 1, 0);
		grid.add(velLbl, 1, 1);
		grid.add(angVelXLbl, 1, 2);
		grid.add(angVelYLbl, 1, 3);
		grid.add(angVelZLbl, 1, 4);
		grid.add(accLbl, 1, 5);
		grid.add(linearAccXLbl, 1, 6);
		grid.add(linearAccYLbl, 1, 7);
		grid.add(linearAccZLbl, 1, 8);
		grid.add(orientationLbl, 1, 9);
		grid.add(orientationXLbl, 1, 10);
		grid.add(orientationYLbl, 1, 11);
		grid.add(orientationZLbl, 1, 12);
		grid.add(orientationWLbl, 1, 13);
		
		Label poseLbl = new Label("Pose meter");
		Label pPositionLbl = new Label("Position");
		Label pOrientationLbl = new Label("Orientation");
		grid.add(poseLbl, 0, 17);
		grid.add(pPositionLbl, 0, 18);
		grid.add(pPositionXLbl, 0, 19);
		grid.add(pPositionYLbl, 0, 20);
		grid.add(pPositionZLbl, 0, 21);
		grid.add(pOrientationLbl, 0, 22);
		grid.add(pOrientationXLbl, 0, 23);
		grid.add(pOrientationYLbl, 0, 24);
		grid.add(pOrientationZLbl, 0, 25);
		grid.add(pOrientationWLbl, 0, 26);
		Label positionmLbl = new Label("Position meter");
		grid.add(positionmLbl, 1, 15);
		grid.add(positionXLbl, 1, 16);
		grid.add(positionYLbl, 1, 17);
		grid.add(positionZLbl, 1, 18);
		
		Scene scene = new Scene(grid,500,480);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	@Override
	public void update(Observable o,Object arg){
		NotifyEvent notifyEvent = (NotifyEvent)arg;
		Object obj = notifyEvent.getObject();
		switch(notifyEvent.getSensor()){
		case Amperemeter:
			double ampere = ((Ampere)obj).getAmpere();
			Platform.runLater(()-> amperemeterLbl.setText("Ampere meter:"+Double.toString(ampere)));
			break;
		case Gyroscope:
			float gyroscope = ((Float32)obj).getData();
			Platform.runLater(()-> gyroscopeLbl.setText("Gyroscope:"+Float.toString(gyroscope)));
			break;
		case Pinger:
			float pinger = ((Float32)obj).getData();
			Platform.runLater(()-> pingerLbl.setText("Pinger:"+Float.toString(pinger)));
			break;
		case Imu:
			//Also have variance and covariance
			double angVelX = ((Imu)obj).getAngularVelocity().getX();
			double angVelY = ((Imu)obj).getAngularVelocity().getY();
			double angVelZ = ((Imu)obj).getAngularVelocity().getZ();
			double linearAccX = ((Imu)obj).getLinearAcceleration().getX();
			double linearAccY = ((Imu)obj).getLinearAcceleration().getY();
			double linearAccZ = ((Imu)obj).getLinearAcceleration().getZ();
			double orientationX = ((Imu)obj).getOrientation().getX();
			double orientationY = ((Imu)obj).getOrientation().getY();
			double orientationZ = ((Imu)obj).getOrientation().getZ();
			double orientationW = ((Imu)obj).getOrientation().getW();
			Platform.runLater(()-> angVelXLbl.setText("X:"+Double.toString(angVelX)));
			Platform.runLater(()-> angVelYLbl.setText("Y:"+Double.toString(angVelY)));
			Platform.runLater(()-> angVelZLbl.setText("Z:"+Double.toString(angVelZ)));
			Platform.runLater(()-> linearAccXLbl.setText("X:"+Double.toString(linearAccX)));
			Platform.runLater(()-> linearAccYLbl.setText("Y:"+Double.toString(linearAccY)));
			Platform.runLater(()-> linearAccZLbl.setText("Z:"+Double.toString(linearAccZ)));
			Platform.runLater(()-> orientationXLbl.setText("X:"+Double.toString(orientationX)));
			Platform.runLater(()-> orientationYLbl.setText("Y:"+Double.toString(orientationY)));
			Platform.runLater(()-> orientationZLbl.setText("Z:"+Double.toString(orientationZ)));
			Platform.runLater(()-> orientationWLbl.setText("W:"+Double.toString(orientationW)));
			break;
		case Accelerometer:
			float accelerometer = ((Float32)obj).getData();
			Platform.runLater(()-> accelerometerLbl.setText("Accelerometer:"+Float.toString(accelerometer)));
			break;
		case Flow:
			double x = ((Vector3Stamped)obj).getVector().getX();
			double y = ((Vector3Stamped)obj).getVector().getY();
			double z = ((Vector3Stamped)obj).getVector().getZ();
			Platform.runLater(()-> flowXLbl.setText("X:"+Double.toString(x)));
			Platform.runLater(()-> flowYLbl.setText("Y:"+Double.toString(y)));
			Platform.runLater(()-> flowZLbl.setText("Z:"+Double.toString(z)));
			break;
		case Pollution:
			double pollution = ((Vector3Stamped)obj).getVector().getY();
			Platform.runLater(()-> pollutionLbl.setText("Pollution:"+Double.toString(pollution)));
			break;
		case Posemeter:
			double pPositionX = ((PoseStamped)obj).getPose().getPosition().getX();
			double pPositionY = ((PoseStamped)obj).getPose().getPosition().getY();
			double pPositionZ = ((PoseStamped)obj).getPose().getPosition().getZ();
			double pOrientationX = ((PoseStamped)obj).getPose().getOrientation().getX();
			double pOrientationY = ((PoseStamped)obj).getPose().getOrientation().getY();
			double pOrientationZ = ((PoseStamped)obj).getPose().getOrientation().getZ();
			double pOrientationW = ((PoseStamped)obj).getPose().getOrientation().getW();
			Platform.runLater(()-> pPositionXLbl.setText("X:"+Double.toString(pPositionX)));
			Platform.runLater(()-> pPositionYLbl.setText("Y:"+Double.toString(pPositionY)));
			Platform.runLater(()-> pPositionZLbl.setText("Z:"+Double.toString(pPositionZ)));
			Platform.runLater(()-> pOrientationXLbl.setText("X:"+Double.toString(pOrientationX)));
			Platform.runLater(()-> pOrientationYLbl.setText("Y:"+Double.toString(pOrientationY)));
			Platform.runLater(()-> pOrientationZLbl.setText("Z:"+Double.toString(pOrientationZ)));
			Platform.runLater(()-> pOrientationWLbl.setText("W:"+Double.toString(pOrientationW)));
			break;
		case Positionmeter:
			double positionX = ((PointStamped)obj).getPoint().getX();
			double positionY = ((PointStamped)obj).getPoint().getY();
			double positionZ = ((PointStamped)obj).getPoint().getZ();
			Platform.runLater(()-> positionXLbl.setText("X:"+Double.toString(positionX)));
			Platform.runLater(()-> positionYLbl.setText("Y:"+Double.toString(positionY)));
			Platform.runLater(()-> positionZLbl.setText("Z:"+Double.toString(positionZ)));
			break;
		case Temperature:
			double temp = ((Temperature)obj).getTemperature();
			double tvariance = ((Temperature)obj).getVariance();
			Platform.runLater(()-> temperatureLbl.setText("Temperature:"+Double.toString(temp)));
			Platform.runLater(()-> tempVarianceLbl.setText("Variance:"+Double.toString(tvariance)));
			break;
		case Depth:
			double depth = ((FluidPressure)obj).getFluidPressure();
			double dvariance = ((FluidPressure)obj).getVariance();
			Platform.runLater(()-> depthLbl.setText("Depth:"+Double.toString(depth)));
			Platform.runLater(()-> depthVarianceLbl.setText("Variance:"+Double.toString(dvariance)));
			break;
		case Sonar:
			//TODO
			break;
		case FrontCamera:
			//TODO
			break;
		case BottomCamera:
			//TODO
		}
	}
	
	@Override
	public void stop(){
		con.shutdown();
	}
	
	public static void main(String args[]){
		launch(args);
	}
}
