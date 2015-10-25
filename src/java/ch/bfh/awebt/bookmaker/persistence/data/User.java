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

	public User() {
		super();

	}

	protected User(String login) {
		this();

		this.login = login;
	}

	public User(String login, char[] password) throws NoSuchAlgorithmException {
		this(login);

		passwordHash = hashPassword(password);
	}

	public User(String login, byte[] passwordHash) {
		this(login);

		this.passwordHash = passwordHash;
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

	private byte[] hashPassword(char[] password) throws NoSuchAlgorithmException {

		try {
			byte[] passwordBytes = new byte[password.length * 2];
			ByteBuffer.wrap(passwordBytes).asCharBuffer().put(password);

			return MessageDigest.getInstance(User.HASH_ALGORITHM).digest(passwordBytes);

		} catch (NullPointerException | NoSuchAlgorithmException ex) {
			throw new NoSuchAlgorithmException("Could not hash password.", ex);
		}
	}
}
