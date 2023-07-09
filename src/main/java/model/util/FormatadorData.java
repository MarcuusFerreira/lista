package model.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FormatadorData {

	public static LocalDate formatarDataMySQL(String sql) {
		return LocalDate.parse(sql, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
	}

	public static LocalDateTime formatarLocalDateTimeMySQL(String sql) {
		return LocalDateTime.parse(sql, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
	}
	
	public static String formatarDataParaTela(LocalDate data) {
		return data.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
	}
	
	public static String formataLocalDateTimeParaTela(LocalDateTime data) {
		return data.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
	}
}
