package Helper;

import java.sql.*;

public class DBConnection {
	public static final String kullaniciAd = "root";
	public static final String sifre = "1234";
	public static final String veritabani = "hospital";
	public static final String host = "localhost";
	public static final int port = 3306;
	Connection c = null;

	public DBConnection() {
	}

	public Connection connDb() {
		try {
			this.c = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + veritabani
					+ "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
					kullaniciAd, sifre);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return c;
	}

}
