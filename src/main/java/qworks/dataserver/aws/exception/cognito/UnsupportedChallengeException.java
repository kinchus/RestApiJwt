package qworks.dataserver.aws.exception.cognito;

import qworks.dataserver.aws.exception.AwsException;

/**
* @author <a href="mailto:garciadjx@gmail.com">J.M. Garcia</a>
 *
 */
public class UnsupportedChallengeException extends AwsException {

	private static final long serialVersionUID = -7287140172154517319L;

	/**
	 * @param challengeName
	 */
	public UnsupportedChallengeException(String challengeName) {
		super("Unsupported AWS Cognito challenge: " + challengeName);
	}
}
