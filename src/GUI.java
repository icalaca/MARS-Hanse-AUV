import java.util.Observer;
import java.util.Observable;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class GUI extends Application implements Observer{
	@Override
	public void start(Stage primaryStage){
		Connector con = new Connector();
		GridPane grid = new GridPane();
		HanseListener listInst = con.getListenerInstance();
		
		primaryStage.setTitle("MARS Hanse Controller");
		listInst.addNotifyObs(this);
		grid.setAlignment(Pos.CENTER);
		//Image img = new Image("");
		//ImageView imgv = new ImageView(img);
		//TODO
		
		Scene scene = new Scene(grid);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	@Override
	public void update(Observable o,Object arg){
		NotifyEvent notifyEvent = (NotifyEvent)arg;
		switch(notifyEvent.getSensor()){
		case Amperemeter:
			//TODO
			break;
		case Gyroscope:
			//TODO
			break;
		case Pinger:
			//TODO
			break;
		case Imu:
			//TODO
			break;
		case Accelerometer:
			//TODO
			break;
		case Flow:
			//TODO
			break;
		case Pollution:
			//TODO
			break;
		case Posemeter:
			//TODO
			break;
		case Positionmeter:
			//TODO
			break;
		case Temperature:
			//TODO
			break;
		case Depth:
			//TODO
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
	
	public static void main(String args[]){
		launch(args);
	}
}
