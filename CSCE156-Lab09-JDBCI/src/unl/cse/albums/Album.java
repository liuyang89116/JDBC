package unl.cse.albums;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Album {

	private Integer albumId;
	private String title;
	private Integer year;
	private Band band;
	private Integer albumNumber;
	private List<String> songTitles = new ArrayList<String>();

	public Album(Integer albumId, String title, Integer year, Band band,
			Integer albumNumber) {
		super();
		this.albumId = albumId;
		this.title = title;
		this.year = year;
		this.band = band;
		this.albumNumber = albumNumber;
	}

	public Album(String title, Integer year, String bandName) {
		this(null, title, year, new Band(bandName), null);
	}

	public Integer getAlbumId() {
		return albumId;
	}

	public String getTitle() {
		return title;
	}

	public Integer getYear() {
		return year;
	}

	public Integer getAlbumNumber() {
		return albumNumber;
	}

	public Band getBand() {
		return band;
	}

	public List<String> getSongTitles() {
		return songTitles;
	}

	public void addSong(String songTitle) {
		this.songTitles.add(songTitle);
	}

	/**
	 * This method returns a {@link #Album} instance loaded from the database
	 * corresponding to the given <code>albumId</code>. Throws an
	 * {@link IllegalStateException} upon an invalid <code>albumId</code>. All
	 * fields are loaded with this method.
	 * 
	 * @param albumId
	 * @return
	 */
	public static Album getDetailedAlbum(int albumId) {
		
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (InstantiationException e) {
			System.out.println("InstantiationException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			System.out.println("IllegalAccessException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		} catch (ClassNotFoundException e) {
			System.out.println("ClassNotFoundException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
		Connection conn = null;
		
		try {
			conn = DriverManager.getConnection(DatabaseInfo.url, DatabaseInfo.username, DatabaseInfo.password);
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
		String query1 = "SELECT AlbumsID, AlbumTitle, AlbumYear, ?, AlbumNumber "+
						"FROM Albums ";
		//String query2 = "SELECT "
		
		
		
		
		
		
		
		

		// TODO
		return null;
	}

	/**
	 * Returns a list of all albums in the database. However, only the title,
	 * year, and band name are loaded from the database.
	 * 
	 * @return
	 */
	public static List<Album> getAlbumSummaries() {

		List<Album> albumInfo = new ArrayList<Album>();

		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (InstantiationException e) {
			System.out.println("Instantiation: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			System.out.println("IllegalAccessException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		} catch (ClassNotFoundException e) {
			System.out.println("ClassNotFoundException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		Connection conn = null;

		try {
			conn = DriverManager.getConnection(DatabaseInfo.url,
					DatabaseInfo.username, DatabaseInfo.password);
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		ResultSet rs = null;

		String query = "SELECT A.AlbumTitle AS title, A.AlbumYear AS year, B.BandName AS bandName"
						+ "FROM Albums AS A, Bands AS B"
						+ " WHERE A.BandID = B.BandID";
		
		PreparedStatement ps = null;
		
		try{
			
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			
			
			
			while(rs.next()){
				Album AlbumFile = new Album(rs.getString("title"), rs.getInt("year"), rs.getString("bandName"));
				albumInfo.add(AlbumFile);
			}
		}catch(SQLException e){
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}finally{
			try{
				if(rs != null && !rs.isClosed()){
					rs.close();
				}
				if(ps != null && !ps.isClosed()){
					ps.close();
				}
			}catch(SQLException e){
				System.out.println("SQLException: ");
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
		
		try{
			if(rs != null && !rs.isClosed()){
				rs.close();
			}
			
			if(ps != null && !ps.isClosed()){
				ps.close();
			}
			
			if(conn != null && !conn.isClosed()){
				conn.close();
			}
		}catch(SQLException e){
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
		
		
		return albumInfo;

	}

	@Override
	public String toString() {
		return "Album [albumId=" + albumId + ", title=" + title + ", year="
				+ year + ", band=" + band + ", albumNumber=" + albumNumber
				+ ", songTitles=" + songTitles + "]";
	}

}
