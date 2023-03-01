package io.entframework.kernel.file.minio.policy;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

class Resources extends HashSet<String> {

	public Resources() {
		super();
	}

	public Resources(String resource) {
		super();

		super.add(resource);
	}

	public Set<String> startsWith(String resourcePrefix) {
		Set<String> rv = new HashSet<>();

		for (String resource : this) {
			if (resource.startsWith(resourcePrefix)) {
				rv.add(resource);
			}
		}

		return rv;
	}

	private boolean matched(String pattern, String resource) {
		if (pattern.isEmpty()) {
			return (Objects.equals(resource, pattern));
		}

		if (pattern.equals("*")) {
			return true;
		}

		String[] parts = pattern.split("\\*");
		if (parts.length == 1) {
			return (resource.equals(pattern));
		}

		boolean tGlob = pattern.endsWith("*");
		int end = parts.length - 1;

		if (!resource.startsWith(parts[0])) {
			return false;
		}

		for (int i = 1; i < end; i++) {
			if (!resource.contains(parts[i])) {
				return false;
			}

			int idx = resource.indexOf(parts[i]) + parts[i].length();
			resource = resource.substring(idx);
		}

		return (tGlob || resource.endsWith(parts[end]));
	}

	public Set<String> match(String resource) {
		Set<String> rv = new HashSet<>();

		for (String pattern : this) {
			if (matched(pattern, resource)) {
				rv.add(pattern);
			}
		}

		return rv;
	}

}
