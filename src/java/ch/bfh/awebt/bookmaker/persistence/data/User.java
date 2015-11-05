package ch.bfh.awebt.bookmaker.persistence.data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import ch.bfh.awebt.bookmaker.converters.LocalDateConverter;

/**
 * Represents an application user.
 *
 * @author strut1 &amp; touwm1
 */
@Entity
@Table(name = "users")
@NamedQuery(name = User.FIND_BY_LOGIN_QUERY, query = "SELECT u FROM User u WHERE LOWER(u.login) = LOWER(:login)")
public class User extends PersistentObject<Integer> implements Serializable {

	private static final long serialVersionUID = -7147878463002225404L;

	/**
	 * Name of the {@link NamedQuery} to find a user by their login.
	 */
	public static final String FIND_BY_LOGIN_QUERY = "User.FIND_BY_LOGIN_QUERY";

	/**
	 * Name of the algorithm to use to hash the passwords.
	 */
	public static final String HASH_ALGORITHM = "MD5";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, unique = true)
	private Integer id;

	@Column(name = "login", nullable = false, length = 25, unique = true)
	private String login;

	@Column(name = "password", nullable = false)
	private byte[] passwordHash;

	@Column(name = "manager", nullable = false)
	private boolean isManager = false;

	@Column(name = "locale", nullable = false, length = 10)
	private String language;

	@Column(name = "balance", nullable = false, precision = 10, scale = 3)
	private BigDecimal balance;

	@Column(name = "cardnumber", length = 16)
	private String creditCardNumber;

	@Column(name = "cardexpiration")
	@Convert(converter = LocalDateConverter.class)
	private LocalDate creditCardExpirationDate;

	@Column(name = "cardcode", length = 3)
	private String creditCardValidationCode;

	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<UserBet> bets;

	protected User() {
	}

	private User(String login, String language) {
		this();

		this.login = login;
		this.language = language;

		bets = new ArrayList<>();
	}

	/**
	 * Construct a user with a specified login, name and password.
	 *
	 * @param login        unique login of the user
	 * @param language     language the user wants to view the application in
	 * @param passwordHash hashed password of the user
	 */
	public User(String login, String language, byte[] passwordHash) {
		this(login, language);

		this.passwordHash = passwordHash;
	}

	/**
	 * Construct a user with a specified login, name and password.
	 *
	 * @param login    unique login of the user
	 * @param language language the user wants to view the application in
	 * @param password clear-text password of the user
	 *
	 * @throws NoSuchAlgorithmException if the password could not be hashed
	 */
	public User(String login, String language, char[] password) throws NoSuchAlgorithmException {
		this(login, language, hashPassword(password));
	}

	@Override
	public Integer getId() {
		return id;
	}

	/**
	 * Sets the unique identifier of the user.
	 *
	 * @param id unique identifier of the user
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Gets the unique login of the user.
	 *
	 * @return unique login of the user
	 */
	public String getLogin() {
		return login;
	}

	/**
	 * Sets the unique login of the user.
	 *
	 * @param login unique login of the user
	 */
	public void setLogin(String login) {
		this.login = login;
	}

	/**
	 * Gets the hashed password of the user.
	 *
	 * @return hashed password of the user
	 */
	byte[] getPasswordHash() {

		return Arrays.copyOf(passwordHash, passwordHash.length);
	}

	/**
	 * Sets the clear-text password of the user.
	 *
	 * @param password clear-text password of the user
	 *
	 * @throws NoSuchAlgorithmException if the password could not be hashed
	 */
	public void setPassword(char[] password) throws NoSuchAlgorithmException {

		passwordHash = hashPassword(password);
	}

	/**
	 * Checks if the given password matches the user's one.
	 *
	 * @param password clear-text password to match against the user's one
	 *
	 * @return whether or not the given password matches the user's one
	 *
	 * @throws NoSuchAlgorithmException if the given password could not be hashed
	 */
	public boolean validatePassword(char[] password) throws NoSuchAlgorithmException {

		return MessageDigest.isEqual(passwordHash, hashPassword(password));
	}

	/**
	 * Checks if the given password matches the user's one.
	 *
	 * @param passwordHash hashed password to match against the user's one
	 *
	 * @return whether or not the given password matches the user's one
	 */
	public boolean validatePassword(byte[] passwordHash) {

		return MessageDigest.isEqual(this.passwordHash, passwordHash);
	}

	/**
	 * Gets whether or not the user is a manager.
	 *
	 * @return whether or not the user is a manager
	 */
	public boolean isManager() {
		return isManager;
	}

	/**
	 * Sets whether or not the user is a manager.
	 *
	 * @param isManager whether or not the user is a manager
	 */
	public void setIsManager(boolean isManager) {
		this.isManager = isManager;
	}

	/**
	 * Gets the language the user wants to view the application in.
	 *
	 * @return language the user wants to view the application in
	 */
	public String getLanguage() {
		return language;
	}

	/**
	 * Sets the language the user wants to view the application in.
	 *
	 * @param language language the user wants to view the application in
	 */
	public void setLanguage(String language) {
		this.language = language;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public String getCreditCardNumber() {
		return creditCardNumber;
	}

	public void setCreditCardNumber(String creditCardNumber) {
		this.creditCardNumber = creditCardNumber;
	}

	public LocalDate getCreditCardExpirationDate() {
		return creditCardExpirationDate;
	}

	public void setCreditCardExpirationDate(LocalDate creditCardExpirationDate) {
		this.creditCardExpirationDate = creditCardExpirationDate;
	}

	public String getCreditCardValidationCode() {
		return creditCardValidationCode;
	}

	public void setCreditCardValidationCode(String creditCardValidationCode) {
		this.creditCardValidationCode = creditCardValidationCode;
	}

	public List<UserBet> getBets() {
		return Collections.unmodifiableList(bets);
	}

	public boolean addBet(UserBet bet) {
		return bets.add(bet);
	}

	public boolean removeBet(UserBet bet) {
		return bets.remove(bet);
	}

	/**
	 * Generate a hash for a given password.
	 *
	 * @param password password to generate hash for
	 *
	 * @return hash for the specified password
	 *
	 * @throws NoSuchAlgorithmException if the password could not be hashed
	 */
	public static byte[] hashPassword(char[] password) throws NoSuchAlgorithmException {

		try {
			byte[] passwordBytes = new byte[password.length * 2];
			ByteBuffer.wrap(passwordBytes).asCharBuffer().put(password);

			return MessageDigest.getInstance(User.HASH_ALGORITHM).digest(passwordBytes);

		} catch (NullPointerException | NoSuchAlgorithmException ex) {
			throw new NoSuchAlgorithmException("Could not hash password.", ex);
		}
	}
}
