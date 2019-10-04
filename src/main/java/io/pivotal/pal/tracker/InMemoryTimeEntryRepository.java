package io.pivotal.pal.tracker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryTimeEntryRepository implements TimeEntryRepository {
	private HashMap<Long, TimeEntry> timeEntryMap = new HashMap<>();
	private long lastId = 0;

	public InMemoryTimeEntryRepository() {}

	public TimeEntry create(TimeEntry timeEntry) {
		long id = timeEntry.getId() == 0 ? ++lastId : timeEntry.getId();
		timeEntry.setId(id);
		timeEntryMap.put(id, timeEntry);
		return timeEntry;
	}

	public TimeEntry find(long id) {
		return timeEntryMap.get(id);
	}

	public List<TimeEntry> list() {
		return new ArrayList(timeEntryMap.values());
	}

	public TimeEntry update(long id, TimeEntry timeEntry) {
		if (!timeEntryMap.containsKey(id)) {
			return null;
		}
		timeEntry.setId(id);
		timeEntryMap.put(id, timeEntry);
		return timeEntry;
	}

	public void delete(long id) {
		timeEntryMap.remove(id);
	}
}
