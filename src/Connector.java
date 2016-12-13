import org.ros.node.NodeMainExecutor;
import org.ros.node.NodeConfiguration;
import org.ros.node.DefaultNodeMainExecutor;
import java.net.URI;

public class Connector{
	private HanseListener subscriberNode = new HanseListener();
	
	public Connector(){
		NodeMainExecutor nodeMainExecutor = DefaultNodeMainExecutor.newDefault();
		String ip = "127.0.0.1";
		String port = "11311";
		URI masteruri = URI.create("http://"+ip+":"+port);  
		NodeConfiguration nodeConfiguration = NodeConfiguration.newPublic(ip, masteruri);
		nodeMainExecutor.execute(subscriberNode, nodeConfiguration);  
		//TODO:talker(publisher node)
	}
	
	public HanseListener getListenerInstance(){
		return subscriberNode;
	}
}
