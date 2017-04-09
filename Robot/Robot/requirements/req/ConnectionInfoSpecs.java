package req;

import org.junit.Assert;
import org.junit.Test;

import entities.ConnectionInfos;

	
public class ConnectionInfoSpecs {

	@Test
	public void setIp() {
		ConnectionInfos cs= new ConnectionInfos("192.168.1.1");
		String expectedIp="192.168.1.1";
		String ip=cs.getIp();
		
		Assert.assertEquals("Correct Ip Set", expectedIp, ip);	
	}

	@Test
	public void setAnotherIp() {
		ConnectionInfos cs= new ConnectionInfos("192.168.1.2");
		String expectedIp="192.168.1.2";
		String ip=cs.getIp();
		
		Assert.assertEquals("Correct Ip Set", expectedIp, ip);	
	}
	
	@Test
	public void getInputPort() {
		ConnectionInfos cs= new ConnectionInfos("192.168.1.2");
		int expectedPort=4000;
		int port=cs.INPUT_PORT;
		
		Assert.assertEquals("Correct input port obtained", expectedPort, port);	
	} 
	@Test
	public void getPosePort() {
		ConnectionInfos cs= new ConnectionInfos("192.168.1.2");
		int expectedPort=60000;
		int port=cs.POSE_PORT;
		
		Assert.assertEquals("Correct pose port obtained", expectedPort, port);	
	} 

}
