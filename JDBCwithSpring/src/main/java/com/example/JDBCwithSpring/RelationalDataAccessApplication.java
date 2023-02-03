package com.example.JDBCwithSpring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
public class RelationalDataAccessApplication implements CommandLineRunner {
	private static final Logger log = LoggerFactory.getLogger(RelationalDataAccessApplication.class);
	public static void main(String args[]) {
		SpringApplication.run(RelationalDataAccessApplication.class, args);
	}

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	public void run(String... strings) throws Exception {

		log.info("Creating tables");

		jdbcTemplate.execute("DROP TABLE customers IF EXISTS");
		jdbcTemplate.execute("CREATE TABLE customers(" +
				"id SERIAL, first_name VARCHAR(255), last_name VARCHAR(255), favorite_music VARCHAR(255))");

		// Split up the array of whole names into an array of first/last names
		List<Object[]> customerData = Arrays.asList(
				new Object[]{"John", "Woo", "I Will Always Love You"},
				new Object[]{"Jeff", "Dean", "Perfect"},
				new Object[]{"Josh", "Bloch", "Sorry"},
				new Object[]{"Josh", "Long", "Photograph"}
		);

		// Use a Java 8 stream to print out each tuple of the list
		customerData.forEach(data -> log.info(String.format("Inserting customer record for  %s %s %s", data[0], data[1], data[2])));

		jdbcTemplate.batchUpdate("INSERT INTO customers(first_name, last_name, favorite_music) VALUES (?,?,?)", customerData);

		log.info("Querying for customer records where favorite_music = 'Pop':");
		jdbcTemplate.query(
				"SELECT id, first_name, last_name, favorite_music FROM customers WHERE favorite_music = ?", new Object[]{"Pop"},
				(rs, rowNum) -> new Customer(rs.getLong("id"), rs.getString("first_name"), rs.getString("last_name"), rs.getString("favorite_music"))
		).forEach(customer -> log.info(customer.toString()));
	}
}