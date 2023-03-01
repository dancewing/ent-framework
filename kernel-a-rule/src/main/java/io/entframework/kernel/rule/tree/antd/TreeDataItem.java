package io.entframework.kernel.rule.tree.antd;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * TreeData
 *
 * @author jeff_qian
 */
@Data
@AllArgsConstructor
public class TreeDataItem<T> {

	private T id;

	private T parentId;

	private String path;

}
