package io.entframework.kernel.db.mybatis.persistence;

import org.mybatis.dynamic.sql.util.Assert;

import java.util.List;

public interface PersistenceManagedTypes {

	/**
	 * Return the class names the persistence provider must add to its set of managed
	 * classes.
	 * @return the managed class names
	 * @see PersistenceUnitInfo#getManagedClassNames()
	 */
	List<String> getManagedClassNames();

	/**
	 * Return a list of managed Java packages, to be introspected by the persistence
	 * provider.
	 * @return the managed packages
	 */
	List<String> getManagedPackages();

	/**
	 * Create an instance using the specified managed class names.
	 * @param managedClassNames the managed class names
	 * @return a {@link PersistenceManagedTypes}
	 */
	static PersistenceManagedTypes of(String... managedClassNames) {
		Assert.notNull(managedClassNames, "'managedClassNames' must not be null");
		return new SimplePersistenceManagedTypes(List.of(managedClassNames), List.of());
	}

	/**
	 * Create an instance using the specified managed class names and packages.
	 * @param managedClassNames the managed class names
	 * @param managedPackages the managed packages
	 * @return a {@link PersistenceManagedTypes}
	 */
	static PersistenceManagedTypes of(List<String> managedClassNames, List<String> managedPackages) {
		return new SimplePersistenceManagedTypes(managedClassNames, managedPackages);
	}

}
