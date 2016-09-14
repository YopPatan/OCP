package packages;

import java.sql.SQLException;

public class TestResourceCloseable implements AutoCloseable {
	public String name;
	
	public TestResourceCloseable(String name) {
		this.name = name;
		System.out.println("Autocloseable: here create resource: " + this.name);
	}
	
	public void close() throws SQLException {
		System.out.println("Autocloseable: here close resource: " + this.name);
		if (this.name.length() > 10) {
			throw new SQLException("Autocloseable throw exception:" + this.name);
		}
	}
}
