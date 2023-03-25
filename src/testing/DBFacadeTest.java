package testing;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import dbadapter.Configuration;
import dbadapter.DBFacade;
import dbadapter.HolidayOffer;

/**
 * Testing our DBFacade.
 * 
 * @author swe.uni-due.de
 *
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(DBFacade.class)
public class DBFacadeTest {

	private Connection stubCon;
	private String sqlSelect;
	private String sqlSelectB;
	private PreparedStatement ps;
	private PreparedStatement psSelectB;
	private ResultSet rs;
	private ResultSet brs;

	/**
	 * Preparing classes with static methods
	 */
	@Before
	public void setUp() {
		PowerMockito.mockStatic(DriverManager.class);

		// Declare necessary SQL queries
		sqlSelect = "SELECT * FROM HolidayOffer WHERE starttime <= ? AND endtime >= ? AND capacity >= ?";
		sqlSelectB = "SELECT * FROM Booking WHERE hid = ?";

		// Mock return values
		ps = mock(PreparedStatement.class);
		psSelectB = mock(PreparedStatement.class);
		rs = mock(ResultSet.class);
		brs = mock(ResultSet.class);

		try {
			// Setting up return values for connection and statements
			stubCon = mock(Connection.class);
			PowerMockito.when(DriverManager.getConnection(
					"jdbc:" + Configuration.getType() + "://" + Configuration.getServer() + ":"
							+ Configuration.getPort() + "/" + Configuration.getDatabase(),
					Configuration.getUser(), Configuration.getPassword())).thenReturn(stubCon);

			when(stubCon.prepareStatement(sqlSelect)).thenReturn(ps);
			when(stubCon.prepareStatement(sqlSelectB)).thenReturn(psSelectB);
			when(ps.executeQuery()).thenReturn(rs);
			when(psSelectB.executeQuery()).thenReturn(brs);

			// Setting up return values for methods
			when(rs.next()).thenReturn(true).thenReturn(false);
			when(rs.getInt(1)).thenReturn(0);
			when(rs.getTimestamp(2)).thenReturn(Timestamp.valueOf("2015-11-04 00:00:00"));
			when(rs.getTimestamp(3)).thenReturn(Timestamp.valueOf("2015-12-30 00:00:00"));
			when(rs.getString(4)).thenReturn("Bahnstr. 6");
			when(rs.getString(5)).thenReturn("Duisburg");
			when(rs.getInt(6)).thenReturn(7);
			when(rs.getDouble(7)).thenReturn(4.5);

			// Setting up return values for corresponding bo
			when(brs.next()).thenReturn(true).thenReturn(false);
			when(brs.getInt(1)).thenReturn(0);
			when(brs.getTimestamp(2)).thenReturn(Timestamp.valueOf("2015-12-01 00:00:00"));
			when(brs.getTimestamp(3)).thenReturn(Timestamp.valueOf("2015-12-01 00:00:00"));
			when(brs.getTimestamp(4)).thenReturn(Timestamp.valueOf("2015-12-04 00:00:00"));
			when(brs.getBoolean(5)).thenReturn(true);
			when(brs.getString(6)).thenReturn("Peter Schulze");
			when(brs.getString(7)).thenReturn("peter@uni-due.de");
			when(brs.getDouble(8)).thenReturn(9.0);
			when(brs.getInt(9)).thenReturn(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	/**
	 * Testing getAvailableHolidayOffers with non-empty results.
	 */
	@Test
	public void testGetAvailableHolidayOffers() {

		// Select a time where no booking exists
		Timestamp arrivalTime = Timestamp.valueOf("2015-12-05 00:00:00");
		Timestamp departureTime = Timestamp.valueOf("2015-12-20 00:00:00");

		ArrayList<HolidayOffer> hos = DBFacade.getInstance().getAvailableHolidayOffers(arrivalTime, departureTime, 1);

		// Verify how often a method has been called
		try {
			verify(stubCon, times(1)).prepareStatement(sqlSelect);
			verify(stubCon, times(1)).prepareStatement(sqlSelectB);
			verify(ps, times(1)).executeQuery();
			verify(psSelectB, times(1)).executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// Verify return values
		assertTrue(hos.size() == 1);
		assertTrue(hos.get(0).getId() == 0);
		assertTrue(hos.get(0).getBookings().size() == 1);
		assertTrue(hos.get(0).getFee() == 4.5);
		// ...

	}

	/**
	 * Testing getAvailableHolidayOffer with empty result.
	 */
	@Test
	public void testGetAvailableHolidayOffersEmpty() {

		// Select a time where already a booking exists
		Timestamp arrivalTime = Timestamp.valueOf("2015-12-02 00:00:00");
		Timestamp departureTime = Timestamp.valueOf("2015-12-03 00:00:00");

		ArrayList<HolidayOffer> hos = DBFacade.getInstance().getAvailableHolidayOffers(arrivalTime, departureTime, 1);

		// Verify how often a method has been called
		try {
			verify(stubCon, times(1)).prepareStatement(sqlSelect);
			verify(stubCon, times(1)).prepareStatement(sqlSelectB);
			verify(ps, times(1)).executeQuery();
			verify(psSelectB, times(1)).executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// Verify return values

		assertTrue(hos.size() == 0);

	}
}
