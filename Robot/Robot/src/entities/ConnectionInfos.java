package entities;

public class ConnectionInfos {

	private String ip;
	public static int INPUT_PORT=4000;
	public static int POSE_PORT=60000;
	public ConnectionInfos(String ip) {
		this.ip = ip;
	}

	public String getIp() {
		return ip;

	}

}
