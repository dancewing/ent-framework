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

import io.entframework.kernel.db.generator.Constants;
import io.entframework.kernel.db.generator.utils.EnumInfo;
import org.apache.commons.lang3.StringUtils;
import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.api.dom.java.InnerEnum;
import org.mybatis.generator.api.dom.java.JavaDomUtils;
import org.mybatis.generator.internal.util.CustomCollectors;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class InnerEnumRenderer {

    public List<String> render(InnerEnum innerEnum, CompilationUnit compilationUnit) {
        List<String> lines = new ArrayList<>();

        lines.addAll(innerEnum.getJavaDocLines());

        lines.add(renderFirstLine(innerEnum, compilationUnit));
        //lines.addAll(renderEnumConstants(innerEnum));

        lines.addAll(RenderingUtilities.renderFields(innerEnum.getFields(), compilationUnit));
        //lines.addAll(RenderingUtilities.renderInitializationBlocks(innerEnum.getInitializationBlocks()));
        //lines.addAll(RenderingUtilities.renderClassOrEnumMethods(innerEnum.getMethods(), compilationUnit));
        lines.addAll(RenderingUtilities.renderInnerClasses(innerEnum.getInnerClasses(), compilationUnit));
        lines.addAll(RenderingUtilities.renderInnerInterfaces(innerEnum.getInnerInterfaces(), compilationUnit));
        lines.addAll(RenderingUtilities.renderInnerEnums(innerEnum.getInnerEnums(), compilationUnit));

        lines = RenderingUtilities.removeLastEmptyLine(lines);

        lines.add("}"); //$NON-NLS-1$
        lines.addAll(renderEnumVariables(innerEnum));
        lines.addAll(renderEnumArray(innerEnum));
        return lines;
    }

    private String renderFirstLine(InnerEnum innerEnum, CompilationUnit compilationUnit) {
        StringBuilder sb = new StringBuilder();

        sb.append("export interface "); //$NON-NLS-1$
        sb.append(innerEnum.getType().getShortName());
        sb.append(renderSuperInterfaces(innerEnum, compilationUnit));
        sb.append(" {"); //$NON-NLS-1$

        return sb.toString();
    }

    private List<String> renderEnumConstants(InnerEnum innerEnum) {
        List<String> answer = new ArrayList<>();

        Iterator<String> iter = innerEnum.getEnumConstants().iterator();
        while (iter.hasNext()) {
            String enumConstant = iter.next();

            if (iter.hasNext()) {
                answer.add(RenderingUtilities.JAVA_INDENT + enumConstant + ","); //$NON-NLS-1$
            } else {
                answer.add(RenderingUtilities.JAVA_INDENT + enumConstant + ","); //$NON-NLS-1$
            }
        }

        answer.add(""); //$NON-NLS-1$
        return answer;
    }

    private List<String> renderEnumArray(InnerEnum innerEnum) {
        List<String> answer = new ArrayList<>();

        EnumInfo enumInfo = (EnumInfo) innerEnum.getAttribute(Constants.TABLE_ENUM_FIELD_ATTR_SOURCE);

        if (enumInfo != null && enumInfo.getItems().size() > 0) {
            List<String> names = enumInfo.getItems().stream().map(EnumInfo.EnumItemInfo::getConstant).collect(Collectors.toList());
            answer.add("export const " + innerEnum.getType().getShortName() + "_All = [" + StringUtils.join(names, ", ") + "];");
        }
        return answer;
    }

    private List<String> renderEnumVariables(InnerEnum innerEnum) {
        List<String> answer = new ArrayList<>();

        EnumInfo enumInfo = (EnumInfo) innerEnum.getAttribute(Constants.TABLE_ENUM_FIELD_ATTR_SOURCE);

        if (enumInfo != null && enumInfo.getItems().size() > 0) {
            for (EnumInfo.EnumItemInfo item : enumInfo.getItems()) {
                Object value = item.getOriginalValue();
                String valueExp = String.valueOf(value);
                if (value instanceof String) {
                    valueExp = StringUtils.wrap(valueExp, "'");
                }
                answer.add("export const " + item.getConstant() + ": " + innerEnum.getType().getShortName() + " = { value: " + valueExp + ", label: '" + item.getLabel() + "' };");
                answer.add("");
            }
        }
        return answer;
    }

    // should return an empty string if no super interfaces
    private String renderSuperInterfaces(InnerEnum innerEnum, CompilationUnit compilationUnit) {
        return innerEnum.getSuperInterfaceTypes().stream()
                .map(tp -> JavaDomUtils.calculateTypeName(compilationUnit, tp))
                .collect(CustomCollectors.joining(", ", " implements ", "")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
    }
}
