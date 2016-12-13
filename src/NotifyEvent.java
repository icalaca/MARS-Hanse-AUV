public class NotifyEvent {
	public static enum Sensors {Accelerometer,Gyroscope,Pinger,Imu,Amperemeter,Flow,Pollution,Posemeter,
		Positionmeter,Temperature,Depth,Sonar,FrontCamera,BottomCamera}
	
	private Sensors sensor;
	private Object obj;
	
	public NotifyEvent(Object anObj, Sensors aSensor){
		sensor = aSensor;
		obj = anObj;
	}
	
	public Sensors getSensor(){
		return sensor;
	}
	
	public Object getObject(){
		return obj;
	}
}
