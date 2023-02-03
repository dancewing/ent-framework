package io.entframework.kernel.rule.util;

import cn.hutool.core.util.ObjectUtil;
import io.entframework.kernel.rule.tree.antd.CheckedKeys;
import io.entframework.kernel.rule.tree.antd.TreeDataItem;

import java.util.List;

public final class CheckedKeysUtils {
    public static <T> CheckedKeys<T> convert(List<TreeDataItem<T>> dataItems) {
        if (ObjectUtil.isEmpty(dataItems)) {
            return new CheckedKeys<>();
        }
        CheckedKeys<T> checkedKeys = new CheckedKeys<T>();

        for (TreeDataItem<T> dataItem: dataItems) {
            for (TreeDataItem<T> item: dataItems) {
                if (dataItem.getId().equals(item.getParentId())) {
                    checkedKeys.getHalfChecked().add(dataItem.getId());
                    break;
                }
            }
        }
        for (TreeDataItem<T> dataItem: dataItems) {
            if (!checkedKeys.getHalfChecked().contains(dataItem.getId())) {
                checkedKeys.getChecked().add(dataItem.getId());
            }
        }
        return checkedKeys;
    }
}
