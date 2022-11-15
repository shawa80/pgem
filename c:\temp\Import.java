import java.sql.*;
import com.shawtonabbey.pgem.plugin.csv.models.*;

public class Import implements TableImporter {
	
	public PreparedStatement init(Connection db) throws SQLException {
		
		PreparedStatement stm = db.prepareStatement(
			"insert into lknametype " +
			"(nametype_id, nametype_name) " +
			"values (?, ?);"
		);
		
		return stm;
	}
	
	public void map(PreparedStatement stm, Object obj) throws SQLException {
		Csv csv = (Csv)obj;

		stm.setObject(1, csv.nametype_id);
		stm.setObject(2, csv.nametype_name);
	}
}
