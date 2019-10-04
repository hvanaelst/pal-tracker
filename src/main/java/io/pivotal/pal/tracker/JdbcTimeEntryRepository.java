package io.pivotal.pal.tracker;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

public class JdbcTimeEntryRepository implements TimeEntryRepository {
	private JdbcTemplate jdbcTemplate;

	public JdbcTimeEntryRepository(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public TimeEntry create(TimeEntry timeEntry) {
		KeyHolder generatedKeyHolder = new GeneratedKeyHolder();

		jdbcTemplate.update(connection -> {
			PreparedStatement statement = connection
					.prepareStatement("INSERT INTO time_entries (project_id, user_id, date, hours) " + "VALUES (?, ?, ?, ?)",
							RETURN_GENERATED_KEYS);

			statement.setLong(1, timeEntry.getProjectId());
			statement.setLong(2, timeEntry.getUserId());
			statement.setDate(3, Date.valueOf(timeEntry.getDate()));
			statement.setInt(4, timeEntry.getHours());

			return statement;
		}, generatedKeyHolder);

		return find(generatedKeyHolder.getKey().longValue());
	}

	@Override
	public TimeEntry find(long id) {
		try {
			return jdbcTemplate.queryForObject("select * from time_entries where id = ?", new Object[] { id },
					(rs, rowNum) -> new TimeEntry(rs.getLong("id"), rs.getLong("project_id"), rs.getLong("user_id"),
							rs.getDate("date").toLocalDate(), rs.getInt("hours")));
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public List<TimeEntry> list() {
		return jdbcTemplate.query("select * from time_entries",
				(rs, rowNum) -> new TimeEntry(rs.getLong("id"), rs.getLong("project_id"), rs.getLong("user_id"),
						rs.getDate("date").toLocalDate(), rs.getInt("hours")));
	}

	@Override
	public TimeEntry update(long id, TimeEntry timeEntry) {
		jdbcTemplate.update(connection -> {
			PreparedStatement statement = connection
					.prepareStatement("UPDATE time_entries set project_id = ?, user_id = ?, date = ?, hours = ? where id = ?");

			statement.setLong(1, timeEntry.getProjectId());
			statement.setLong(2, timeEntry.getUserId());
			statement.setDate(3, Date.valueOf(timeEntry.getDate()));
			statement.setInt(4, timeEntry.getHours());
			statement.setLong(5, id);

			return statement;
		});

		return find(id);
	}

	@Override
	public void delete(long id) {
		jdbcTemplate.update(connection -> {
			PreparedStatement statement = connection
					.prepareStatement("delete from time_entries where id = ?");

			statement.setLong(1, id);

			return statement;
		});
	}
}
