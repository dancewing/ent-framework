package io.entframework.kernel.db.mybatis.persistence;

import java.util.List;

class SimplePersistenceManagedTypes implements PersistenceManagedTypes {
    private final List<String> managedClassNames;

    private final List<String> managedPackages;

    SimplePersistenceManagedTypes(List<String> managedClassNames, List<String> managedPackages
    ) {
        this.managedClassNames = managedClassNames;
        this.managedPackages = managedPackages;
    }

    @Override
    public List<String> getManagedClassNames() {
        return this.managedClassNames;
    }

    @Override
    public List<String> getManagedPackages() {
        return this.managedPackages;
    }
}
