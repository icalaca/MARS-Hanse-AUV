import org.ros.node.NodeMainExecutor;
import org.ros.node.NodeConfiguration;
import org.ros.exception.RemoteException;
import org.ros.internal.node.client.MasterClient;
import org.ros.namespace.GraphName;
import org.ros.node.DefaultNodeMainExecutor;
import java.net.URI;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Connector{
	private HanseListener subscriberNode = new HanseListener();
	private HanseTalker publisherNode = new HanseTalker();
	private NodeMainExecutor nodeMainExecutor = DefaultNodeMainExecutor.newDefault();
	
	public Connector(){
		String ip = "127.0.0.1";
		String port = "11311";
		URI masteruri = URI.create("http://"+ip+":"+port);  
		try{
			MasterClient masterClient = new MasterClient(masteruri);
			masterClient.lookupNode(GraphName.of("/check"), "/MARS/hanse");
		}catch(RemoteException e){
            Alert a = new Alert(AlertType.ERROR);
            a.setTitle("Error!");
            a.setHeaderText("Hanse not found.");
            a.setContentText("Hanse node not found.");
            a.showAndWait();
			System.exit(1);
		}catch(RuntimeException e){
            Alert a = new Alert(AlertType.ERROR);
            a.setTitle("Error!");
            a.setHeaderText("Connection error.");
            a.setContentText("Unable to connect to ROS.");
            a.showAndWait();
			System.exit(1);
		}
		NodeConfiguration nodeConfiguration = NodeConfiguration.newPublic(ip, masteruri);
		nodeMainExecutor.execute(subscriberNode, nodeConfiguration); 
		nodeMainExecutor.execute(publisherNode, nodeConfiguration);
	}
	
	public HanseListener getListenerInstance(){
		return subscriberNode;
	}
	
	public HanseTalker getTalkerInstance(){
		return publisherNode;
	}
	
	public void shutdown(){
		nodeMainExecutor.shutdown();
	}
}
