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

}
