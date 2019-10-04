package io.pivotal.pal.tracker;

import java.time.LocalDate;

public class TimeEntry {
	private long id;
	private long projectId;
	private long userId;
	private LocalDate date;
	private int hours;

	public TimeEntry(long projectId, long userId, LocalDate date, int hours) {
		this.projectId = projectId;
		this.userId = userId;
		this.date = date;
		this.hours = hours;
	}

	public TimeEntry(long id, long projectId, long userId, LocalDate date, int hours) {
		this(projectId, userId, date, hours);
		this.id = id;
	}

	public TimeEntry() {
	}

	public long getId() {
		return id;
	}

	void setId(long id) {
		this.id = id;
	}

	public long getProjectId() { return projectId; }
	public long getUserId() { return userId; }
	public LocalDate getDate() { return date; }
	public long getHours() { return hours; }

	@Override
	public boolean equals(Object anotherTimeEntry) {
		return this.id == ((TimeEntry) anotherTimeEntry).getId();
	}
}
