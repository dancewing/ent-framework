/*
 *    Copyright 2006-2020 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package io.entframework.kernel.db.generator.plugin.web.runtime.render;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class BodyLineRenderer {

	public List<String> render(List<String> bodyLines) {
		List<String> lines = new ArrayList<>();
		int indentLevel = 1;
		StringBuilder sb = new StringBuilder();

		ListIterator<String> listIter = bodyLines.listIterator();
		while (listIter.hasNext()) {
			sb.setLength(0);
			String line = listIter.next();
			if (line.startsWith("}")) { //$NON-NLS-1$
				indentLevel--;
			}

			tsIndent(sb, indentLevel);
			sb.append(line);
			lines.add(sb.toString());

			if (isCodeBlockStartExceptSwitchStatement(line) || line.endsWith(":")) { //$NON-NLS-1$
				indentLevel++;
			}

			if (line.startsWith("break")) { //$NON-NLS-1$
				// if the next line is '}', then don't outdent
				if (listIter.hasNext()) {
					String nextLine = listIter.next();
					if (nextLine.startsWith("}")) { //$NON-NLS-1$
						indentLevel++;
					}

					// set back to the previous element
					listIter.previous();
				}
				indentLevel--;
			}
		}

		return lines;
	}

	private boolean isCodeBlockStartExceptSwitchStatement(String line) {
		return line.endsWith("{") && !line.startsWith("switch"); //$NON-NLS-1$ //$NON-NLS-2$
	}

	public static void tsIndent(StringBuilder sb, int indentLevel) {
		for (int i = 0; i < indentLevel; i++) {
			sb.append("  "); //$NON-NLS-1$
		}
	}

}
