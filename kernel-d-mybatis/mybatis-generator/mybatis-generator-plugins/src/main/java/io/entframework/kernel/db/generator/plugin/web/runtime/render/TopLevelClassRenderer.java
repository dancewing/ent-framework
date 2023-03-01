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

import org.mybatis.generator.api.dom.java.TopLevelClass;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TopLevelClassRenderer {

	public String render(TopLevelClass topLevelClass) {
		List<String> lines = new ArrayList<>();

		lines.addAll(topLevelClass.getFileCommentLines());
		lines.addAll(RenderingUtilities.renderImports(topLevelClass));
		lines.addAll(RenderingUtilities.renderInnerClassNoIndent(topLevelClass, topLevelClass));
		lines = RenderingUtilities.removeLastEmptyLine(lines);
		lines.add("");
		return lines.stream().collect(Collectors.joining(System.getProperty("line.separator"))); //$NON-NLS-1$
	}

}
