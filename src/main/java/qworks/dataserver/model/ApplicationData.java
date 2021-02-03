/**
 * 
 */
package qworks.dataserver.model;


/**
 * @author <a href="mailto:garciadjx@gmail.com">J.M. Garcia</a>
 *
 */
public class ApplicationData {

	private String applicattion;
	private String devEui;
	private long time;
	private String jsonData;
	
	/**
	 * 
	 */
	public ApplicationData() {
		
	}

	/**
	 * @param devEui
	 * @param time
	 * @param jsonData
	 */
	public ApplicationData(String devEui, long time, String jsonData) {
		super();
		this.devEui = devEui;
		this.time = time;
		this.jsonData = jsonData;
	}

	/**
	 * @return the applicattion
	 */
	public String getApplicattion() {
		return applicattion;
	}

	/**
	 * @param applicattion the applicattion to set
	 */
	public void setApplicattion(String applicattion) {
		this.applicattion = applicattion;
	}

	/**
	 * @return the devEui
	 */
	public String getDevEui() {
		return devEui;
	}

	/**
	 * @param devEui the devEui to set
	 */
	public void setDevEui(String devEui) {
		this.devEui = devEui;
	}

	/**
	 * @return the time
	 */
	public Long getTime() {
		return time;
	}

	/**
	 * @param time the date to set
	 */
	public void setTime(Long time) {
		this.time = time;
	}

	/**
	 * @return the jsonData
	 */
	public String getJsonData() {
		return jsonData;
	}

	/**
	 * @param jsonData the jsonData to set
	 */
	public void setJsonData(String jsonData) {
		this.jsonData = jsonData;
	}

}
