package cybersoft.java20.dev3lopers.gear3sproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BillDTO {

	private double price;
	private String currency;
	private String method;
	private String intent;
	private String description;

}
