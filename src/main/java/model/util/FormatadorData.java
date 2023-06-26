package model.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class FormatadorData {

	public static LocalDate formatarDataMySQL(String sql) {
		return LocalDate.parse(sql, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
	}
	
}
