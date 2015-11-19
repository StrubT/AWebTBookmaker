package ch.bfh.awebt.bookmaker.persistence.data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
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
import javax.persistence.Version;
import ch.bfh.awebt.bookmaker.converters.LocalDateConverter;
import ch.bfh.awebt.bookmaker.converters.LocaleConverter;
import ch.bfh.awebt.bookmaker.converters.ZoneIdConverter;

/**
 * Represents an application user.
 *
 * @author strut1 &amp; touwm1
 */
@Entity
@Table(name = "users")
@NamedQuery(name = User.FIND_BY_LOGIN_QUERY, query = "select u from User u where lower(u.login) = lower(:login)")
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

	public static final User ANONYMOUS = new User();

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, unique = true)
	private Integer id;

	@Version
	@Column(name = "version", nullable = false)
	private Timestamp versionTimeStamp;

	@Column(name = "login", nullable = false, length = 25, unique = true)
	private String login;

	@Column(name = "password", nullable = false)
	private byte[] passwordHash;

	@Column(name = "manager", nullable = false)
	private boolean isManager = false;

	@Column(name = "locale", nullable = false, length = 10)
	@Convert(converter = LocaleConverter.class)
	private Locale locale;

	@Column(name = "timeZone", nullable = false, length = 10)
	@Convert(converter = ZoneIdConverter.class)
	private ZoneId timeZone;

	@Column(name = "balance", nullable = false, precision = 10, scale = 3)
	private BigDecimal balance = BigDecimal.ZERO;

	@Column(name = "cardnumber", length = 16)
	private String creditCardNumber;

	@Column(name = "cardexpiration")
	@Convert(converter = LocalDateConverter.class)
	private LocalDate creditCardExpirationDate;

	@Column(name = "cardcode", length = 3)
	private String creditCardValidationCode;

	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<UserBet> bets;

	/**
	 * Constructs an empty user.
	 */
	protected User() {

		bets = new ArrayList<>();
	}

	private User(String login, Locale locale, ZoneId timeZone) {
		this();

		this.login = login;
		this.locale = locale;
		this.timeZone = timeZone;
	}

	/**
	 * Constructs a user with a specified login, name and password.
	 *
	 * @param login        unique login of the user
	 * @param locale       locale the user wants to view the application in
	 * @param timeZone     time zone the user wants to view times in
	 * @param passwordHash hashed password of the user
	 */
	public User(String login, Locale locale, ZoneId timeZone, byte[] passwordHash) {
		this(login, locale, timeZone);

		this.passwordHash = passwordHash;
	}

	/**
	 * Constructs a user with a specified login, name and password.
	 *
	 * @param login    unique login of the user
	 * @param locale   locale the user wants to view the application in
	 * @param timeZone time zone the user wants to view times in
	 * @param password clear-text password of the user
	 *
	 * @throws NoSuchAlgorithmException if the password could not be hashed
	 */
	public User(String login, Locale locale, ZoneId timeZone, char[] password) throws NoSuchAlgorithmException {
		this(login, locale, timeZone, hashPassword(password));
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
	protected void setId(int id) {
		this.id = id;
	}

	/**
	 * Gets the {@link Version} timestamp of the persistent object.
	 *
	 * @return timestamp of the object
	 */
	public Timestamp getVersionTimeStamp() {
		return new Timestamp(versionTimeStamp.getTime());
	}

	/**
	 * Sets the {@link Version} timestamp of the persistent object.
	 *
	 * @param timeStamp timestamp of the object
	 */
	protected void setVersionTimeStamp(Timestamp timeStamp) {
		this.versionTimeStamp = new Timestamp(timeStamp.getTime());
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
	 * Gets the locale the user wants to view the application in.
	 *
	 * @return locale the user wants to view the application in
	 */
	public Locale getLocale() {
		return locale;
	}

	/**
	 * Sets the locale the user wants to view the application in.
	 *
	 * @param locale locale the user wants to view the application in
	 */
	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	/**
	 * Gets the time zone the user wants to view times in.
	 *
	 * @return time zone the user wants to view times in
	 */
	public ZoneId getTimeZone() {
		return timeZone;
	}

	/**
	 * Sets the time zone the user wants to view times in.
	 *
	 * @param timeZone time zone the user wants to view times in
	 */
	public void setTimeZone(ZoneId timeZone) {
		this.timeZone = timeZone;
	}

	/**
	 * Gets the balance of the user's account with the bookmaker.
	 *
	 * @return balance of the user's account
	 */
	public BigDecimal getBalance() {
		return balance;
	}

	/**
	 * Sets the balance of the user's account with the bookmaker.
	 *
	 * @param balance balance of the user's account
	 */
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	/**
	 * Deposits an amount in the user's account with the bookmaker.
	 *
	 * @param amount amount to deposit
	 *
	 * @return new balance of the user's account
	 */
	public BigDecimal deposit(BigDecimal amount) {

		return this.balance = this.balance.add(amount);
	}

	/**
	 * Withdraws an amount from the user's account with the bookmaker.
	 *
	 * @param amount amount to withdraw
	 *
	 * @return new balance of the user's account
	 */
	public BigDecimal withdraw(BigDecimal amount) {

		return this.balance = this.balance.subtract(amount);
	}

	/**
	 * Gets the number of the user's credit card registered with the bookmaker.
	 *
	 * @return number of the user's credit card
	 */
	public String getCreditCardNumber() {
		return creditCardNumber;
	}

	/**
	 * Sets the number of the user's credit card registered with the bookmaker.
	 *
	 * @param creditCardNumber number of the user's credit card
	 */
	public void setCreditCardNumber(String creditCardNumber) {
		this.creditCardNumber = creditCardNumber;
	}

	/**
	 * Gets the expiration date of the user's credit card registered with the bookmaker.
	 *
	 * @return expiration date of the user's credit card
	 */
	public LocalDate getCreditCardExpirationDate() {
		return creditCardExpirationDate;
	}

	/**
	 * Sets the expiration date of the user's credit card registered with the bookmaker.
	 *
	 * @param creditCardExpirationDate expiration date of the user's credit card
	 */
	public void setCreditCardExpirationDate(LocalDate creditCardExpirationDate) {
		this.creditCardExpirationDate = creditCardExpirationDate;
	}

	/**
	 * Gets the validation code of the user's credit card registered with the bookmaker.
	 *
	 * @return validation code of the user's credit card
	 */
	public String getCreditCardValidationCode() {
		return creditCardValidationCode;
	}

	/**
	 * Sets the validation code of the user's credit card registered with the bookmaker.
	 *
	 * @param creditCardValidationCode validation code of the user's credit card
	 */
	public void setCreditCardValidationCode(String creditCardValidationCode) {
		this.creditCardValidationCode = creditCardValidationCode;
	}

	/**
	 * Gets the bets placed by the user with the bookmaker.
	 *
	 * @return unmodifiable {@link List} of the user's bets
	 */
	public List<UserBet> getBets() {
		return Collections.unmodifiableList(bets);
	}

	/**
	 * Places a new bet for the user with the bookmaker.
	 *
	 * @param bet bet to place for the user
	 *
	 * @return whether or not the bet was added to the list
	 */
	boolean addBet(UserBet bet) {
		return bets.add(bet);
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
