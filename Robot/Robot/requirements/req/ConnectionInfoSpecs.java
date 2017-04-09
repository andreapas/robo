package req;

import org.junit.Assert;
import org.junit.Test;

import shells.ConnectionInfos;

	
public class ConnectionInfoSpecs {

	@Test
	public void setIp() {
		ConnectionInfos cs= new ConnectionInfos("192.168.1.1");
		String expectedIp="192.168.1.1";
		String ip=cs.getIp();
		
		Assert.assertEquals("Correct Ip Set", expectedIp, ip);	
	}

}
