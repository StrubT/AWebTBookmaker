package ch.bfh.awebt.bookmaker.persistence.data;

import java.io.Serializable;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "users")
@NamedQuery(name = User.FIND_BY_LOGIN_QUERY, query = "SELECT u FROM User u WHERE LOWER(u.login) = LOWER(:login)")
public class User extends PersistentObject implements Serializable {

	public static final String FIND_BY_LOGIN_QUERY = "User.FIND_BY_LOGIN_QUERY";

	public static final String HASH_ALGORITHM = "MD5";

	private static final long serialVersionUID = -7147878463002225404L;

	@Column(name = "login", nullable = false, unique = true)
	private String login;

	@Column(name = "password", nullable = false)
	private byte[] passwordHash;

	@Column(name = "manager", nullable = false)
	private boolean isManager = false;

	@Column(name = "locale", nullable = false)
	private String language;

	public User() {
		super();
	}

	protected User(String login, String language) {
		this();

		this.login = login;
		this.language = language;
	}

	public User(String login, String language, byte[] passwordHash) {
		this(login, language);

		this.passwordHash = passwordHash;
	}

	public User(String login, String language, char[] password) throws NoSuchAlgorithmException {
		this(login, language, hashPassword(password));
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	byte[] getPasswordHash() {

		return Arrays.copyOf(passwordHash, passwordHash.length);
	}

	public void setPassword(char[] password) throws NoSuchAlgorithmException {

		passwordHash = hashPassword(password);
	}

	public boolean validatePassword(char[] password) throws NoSuchAlgorithmException {

		return MessageDigest.isEqual(passwordHash, hashPassword(password));
	}

	public boolean isManager() {
		return isManager;
	}

	public void setIsManager(boolean isManager) {
		this.isManager = isManager;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	private static byte[] hashPassword(char[] password) throws NoSuchAlgorithmException {

		try {
			byte[] passwordBytes = new byte[password.length * 2];
			ByteBuffer.wrap(passwordBytes).asCharBuffer().put(password);

			return MessageDigest.getInstance(User.HASH_ALGORITHM).digest(passwordBytes);

		} catch (NullPointerException | NoSuchAlgorithmException ex) {
			throw new NoSuchAlgorithmException("Could not hash password.", ex);
		}
	}
}
