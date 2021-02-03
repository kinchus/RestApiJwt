/* 
 * 
 */
package qworks.dataserver.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author <a href="mailto:garciadjx@gmail.com">J.M. Garcia</a>
 * 
 */
public class AplicacionBase {

	protected static BufferedReader br = new BufferedReader(
			new InputStreamReader(System.in));

	/**
	 * Read an Integer from standard input
	 * @param prompt
	 * @return
	 */
	public static Integer leerInteger(String prompt) {
		Integer ret;
		try {
			String str;
			System.out.print(prompt);
			str = br.readLine();
			ret = Integer.parseInt(str);
		} catch (IOException e) {
			ret = null;
		} catch (NumberFormatException e) {
			ret = null;
		}
		return ret;
	}

	/**
	 *  Read an String from standard input
	 * @param prompt
	 * @return
	 */
	public static String readString(String prompt) {
		String ret = null;
		try {
			System.out.print(prompt);
			ret = br.readLine();
		} catch (IOException e) {
			ret = null;
		}
		return ret;
	}

	/**
	 * Read a double from standard input
	 * @param prompt
	 * @return
	 */
	public static Double readDouble(String prompt) {
		Double ret = null;
		try {
			String str;
			System.out.print(prompt);
			str = br.readLine();
			ret = Double.parseDouble(str);
		} catch (Exception e) {
			ret = null;
		}
		return ret;
	}

}
