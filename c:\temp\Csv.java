import com.shawtonabbey.pgem.plugin.csv.models.*;

public class Csv {
	@Column(idx=0, name="name_first") public String name_first;
	@Column(idx=1, name="name_last") public String name_last;
	@Column(idx=2, name="nametype_name") public String nametype_name;
	@Column(idx=3, name="address") public String address;
	@Column(idx=4, name="subtype_name") public String subtype_name;
	@Column(idx=5, name="type_name") public String type_name;
}
