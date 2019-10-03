package io.pivotal.pal.tracker;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EnvController {

	private String port;
	private String memoryLimit;
	private String index;
	private String addr;

	public EnvController(
			@Value("${port:NOT SET}") String port,
			@Value("${memory.limit:NOT SET}") String memoryLimit,
			@Value("${cf.instance.index:NOT SET}") String index,
			@Value("${cf.instance.addr:NOT SET}") String addr) {
		this.port = port;
		this.memoryLimit = memoryLimit;
		this.index = index;
		this.addr = addr;
	}

	@GetMapping("/env")
	public Map<String, String> getEnv() {
		HashMap<String, String> map = new HashMap<>();
		map.put("PORT", port);
		map.put("MEMORY_LIMIT", memoryLimit);
		map.put("CF_INSTANCE_INDEX", index);
		map.put("CF_INSTANCE_ADDR", addr);
		return map;
	}
}
