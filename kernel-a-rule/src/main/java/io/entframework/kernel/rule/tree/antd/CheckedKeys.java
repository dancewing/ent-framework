package io.entframework.kernel.rule.tree.antd;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Antd vue 树选中key
 */
@Data
public class CheckedKeys<T> {
    private List<T> checked = new ArrayList<>();
    private List<T> halfChecked = new ArrayList<>();
}
