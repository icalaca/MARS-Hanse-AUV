import java.util.Observer;
import java.util.Observable;
import javafx.application.Application;
import javafx.scene.control.Label;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.transform.*;
import javafx.scene.image.WritableImage;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelWriter;
import javafx.application.Platform;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import std_msgs.Float32;
import hanse_msgs.Ampere;
import hanse_msgs.ScanningSonar;
import geometry_msgs.Vector3Stamped;
import geometry_msgs.PoseStamped;
import geometry_msgs.PointStamped;
import sensor_msgs.Imu;
import sensor_msgs.Temperature;
import sensor_msgs.FluidPressure;
import org.jboss.netty.buffer.ChannelBuffer;


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
	private static int cameraWidth = 640; //MARS default camera width 
	private static int cameraHeight = 480; //MARS default camera height 
	private WritableImage fCameraImage = new WritableImage(cameraWidth,cameraHeight);
	private WritableImage bCameraImage = new WritableImage(cameraWidth,cameraHeight);
	private Canvas canvas = new Canvas(395, 395);
	private GraphicsContext canvasGC = canvas.getGraphicsContext2D();
	
	@Override
	public void start(Stage primaryStage){
		primaryStage.setTitle("MARS Hanse Controller");
		con = new Connector();
		HanseListener listInst = con.getListenerInstance();
		listInst.addNotifyObs(this);
		
		GridPane grid = new GridPane();
		grid.setHgap(50);
		
		VBox sensorsBox = new VBox();
		sensorsBox.setSpacing(4);
		sensorsBox.getChildren().add(accelerometerLbl);
		sensorsBox.getChildren().add(gyroscopeLbl);
		sensorsBox.getChildren().add(pingerLbl);
		sensorsBox.getChildren().add(amperemeterLbl);
		sensorsBox.getChildren().add(pollutionLbl);
		sensorsBox.getChildren().add(temperatureLbl);
		sensorsBox.getChildren().add(tempVarianceLbl);
		sensorsBox.getChildren().add(depthLbl);
		sensorsBox.getChildren().add(depthVarianceLbl);
		
		VBox flowBox = new VBox();
		Label flowLbl = new Label("Flow");
		flowBox.getChildren().add(flowLbl);
		flowBox.getChildren().add(flowXLbl);
		flowBox.getChildren().add(flowYLbl);
		flowBox.getChildren().add(flowZLbl);
		sensorsBox.getChildren().add(flowBox);
		
		VBox sensorsBox2 = new VBox();
		sensorsBox2.setSpacing(4);
		VBox imuBox = new VBox();
		Label imuLbl = new Label("Imu");
		Label velLbl = new Label("Angular velocity");
		Label accLbl = new Label("Linear acceleration");
		Label orientationLbl = new Label("Orientation");
		imuBox.getChildren().add(imuLbl);
		imuBox.getChildren().add(velLbl);
		imuBox.getChildren().add(angVelXLbl);
		imuBox.getChildren().add(angVelYLbl);
		imuBox.getChildren().add(angVelZLbl);
		imuBox.getChildren().add(accLbl);
		imuBox.getChildren().add(linearAccXLbl);
		imuBox.getChildren().add(linearAccYLbl);
		imuBox.getChildren().add(linearAccZLbl);
		imuBox.getChildren().add(orientationLbl);
		imuBox.getChildren().add(orientationXLbl);
		imuBox.getChildren().add(orientationYLbl);
		imuBox.getChildren().add(orientationZLbl);
		imuBox.getChildren().add(orientationWLbl);
		sensorsBox2.getChildren().add(imuBox);

		VBox poseBox = new VBox();
		Label poseLbl = new Label("Pose meter");
		Label pPositionLbl = new Label("Position");
		Label pOrientationLbl = new Label("Orientation");
		poseBox.getChildren().add(poseLbl);
		poseBox.getChildren().add(pPositionLbl);
		poseBox.getChildren().add(pPositionXLbl);
		poseBox.getChildren().add(pPositionYLbl);
		poseBox.getChildren().add(pPositionZLbl);
		poseBox.getChildren().add(pOrientationLbl);
		poseBox.getChildren().add(pOrientationXLbl);
		poseBox.getChildren().add(pOrientationYLbl);
		poseBox.getChildren().add(pOrientationZLbl);
		poseBox.getChildren().add(pOrientationWLbl);
		sensorsBox.getChildren().add(poseBox);
		
		VBox positionBox = new VBox();
		Label positionmLbl = new Label("Position meter");
		positionBox.getChildren().add(positionmLbl);
		positionBox.getChildren().add(positionXLbl);
		positionBox.getChildren().add(positionYLbl);
		positionBox.getChildren().add(positionZLbl);
		sensorsBox2.getChildren().add(positionBox);
		
		Label sonarLbl = new Label("Sonar:");
		VBox sonarBox = new VBox();
		sonarBox.getChildren().add(sonarLbl);
		sonarBox.getChildren().add(canvas);
		
		grid.add(sensorsBox, 0, 0);
		grid.add(sensorsBox2, 1, 0);
		grid.add(sonarBox, 3, 0);
		
		ImageView fImgView = new ImageView(fCameraImage);
		ImageView bImgView = new ImageView(bCameraImage);
		
		Stage fCameraStage = new Stage();
		VBox fImgBox = new VBox();
		fImgBox.getChildren().add(fImgView);
		fCameraStage.setTitle("Front Camera");
		fCameraStage.setScene(new Scene(fImgBox, cameraWidth, cameraHeight));
		
		canvasGC.setFill(Color.BLACK);
		canvasGC.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
		
		Stage bCameraStage = new Stage();
		VBox bImgBox = new VBox();
		bImgBox.getChildren().add(bImgView);
		bCameraStage.setTitle("Bottom Camera");
		bCameraStage.setScene(new Scene(bImgBox, cameraWidth, cameraHeight));
		
		Scene scene = new Scene(grid,970,410);
		primaryStage.setScene(scene);
		primaryStage.show();
		fCameraStage.show();
		bCameraStage.show();
	}
	
	private void writeCamImage(ChannelBuffer data, int step, PixelWriter pxWriter, int camWidth, int camHeight){
		for(int x = 0;x < camWidth;x++){
			for(int y = 0;y < camHeight;y++){
				byte fr = data.getByte((int) (y*step+3*x));
				byte fg = data.getByte((int) (y*step+3*x+1));
				byte fb = data.getByte((int) (y*step+3*x+2));
				//BGR8 - MARS default format
				pxWriter.setColor((camWidth-x-1), y, Color.rgb(fb&0xFF, fg&0xFF, fr&0xFF));
			}
		}
	}
	
	private void drawSonar(ChannelBuffer data, double radAngle){
		double cX = canvas.getWidth()/2;
		double cY = canvas.getHeight()/2;
		float resolution = 0.1f;
		int uByte = 0;
		Affine affine = new Affine();
		affine.appendRotation(Math.toDegrees(radAngle),cX,cY);
		canvasGC.setTransform(affine);
		for(int i = 0;i < data.capacity();i++){
			uByte = data.getByte(i) & 0xFF;
			if(uByte > 0)
				canvasGC.setStroke(Color.rgb(0, uByte, 0));
			else canvasGC.setStroke(Color.BLACK);
			float pixelsWidth = resolution*(float)i ;
			if (pixelsWidth >= 1) {
				canvasGC.setLineWidth(Math.round(pixelsWidth));
			}else{
				canvasGC.setLineWidth(1);
			}
			canvasGC.strokeLine(cX, cY-i, cX, cY-i-1);
		}
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
			double fX = ((Vector3Stamped)obj).getVector().getX();
			double fY = ((Vector3Stamped)obj).getVector().getY();
			double fZ = ((Vector3Stamped)obj).getVector().getZ();
			Platform.runLater(()-> flowXLbl.setText("X:"+Double.toString(fX)));
			Platform.runLater(()-> flowYLbl.setText("Y:"+Double.toString(fY)));
			Platform.runLater(()-> flowZLbl.setText("Z:"+Double.toString(fZ)));
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
			ScanningSonar sonar = ((ScanningSonar)obj);
			ChannelBuffer sData = sonar.getEchoData();
			drawSonar(sData, sonar.getHeadPosition());
			break;
		case FrontCamera:
			sensor_msgs.Image frontImg = (sensor_msgs.Image)obj;
			PixelWriter fpxWriter = fCameraImage.getPixelWriter();
			ChannelBuffer fdata = frontImg.getData();
			writeCamImage(fdata, frontImg.getStep(), fpxWriter, cameraWidth, cameraHeight);
			break;
		case BottomCamera:
			sensor_msgs.Image bottomImg = (sensor_msgs.Image)obj;
			PixelWriter bpxWriter = bCameraImage.getPixelWriter();
			ChannelBuffer bdata = bottomImg.getData();
			writeCamImage(bdata, bottomImg.getStep(), bpxWriter, cameraWidth, cameraHeight);
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
