package io.pivotal.pal.tracker;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TimeEntryController {
	private TimeEntryRepository timeEntryRepository;

	public TimeEntryController(TimeEntryRepository timeEntryRepository) {
		this.timeEntryRepository = timeEntryRepository;
	}

	@PostMapping("/time-entries")
	public ResponseEntity create(
			@RequestBody
					TimeEntry timeEntryToCreate) {
		return new ResponseEntity<>(timeEntryRepository.create(timeEntryToCreate), HttpStatus.CREATED);
	}

	@GetMapping("/time-entries/{id}")
	public ResponseEntity<TimeEntry> read(
			@PathVariable
					long id) {
		TimeEntry timeEntry = timeEntryRepository.find(id);
		if (timeEntry == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(timeEntry, HttpStatus.OK);
		}
	}

	@GetMapping("/time-entries")
	public ResponseEntity<List<TimeEntry>> list() {
		return new ResponseEntity<>(timeEntryRepository.list(), HttpStatus.OK);
	}

	@PutMapping("/time-entries/{id}")
	public ResponseEntity update(@PathVariable long id, @RequestBody TimeEntry expected) {
		TimeEntry timeEntry = timeEntryRepository.update(id, expected);
		if (timeEntry == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(timeEntry, HttpStatus.OK);
		}
	}

	@DeleteMapping("/time-entries/{id}")
	public ResponseEntity delete(@PathVariable long id) {
		timeEntryRepository.delete(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
