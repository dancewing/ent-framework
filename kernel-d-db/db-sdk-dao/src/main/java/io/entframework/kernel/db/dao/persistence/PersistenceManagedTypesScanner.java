package io.entframework.kernel.db.dao.persistence;

import io.entframework.kernel.db.api.exception.DaoException;
import io.entframework.kernel.db.api.exception.enums.DbInitEnum;
import io.entframework.kernel.db.mybatis.persistence.PersistenceManagedTypes;
import org.mybatis.dynamic.sql.annotation.Entity;
import org.springframework.context.index.CandidateComponentsIndex;
import org.springframework.context.index.CandidateComponentsIndexLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.core.type.filter.TypeFilter;
import org.springframework.lang.Nullable;
import org.springframework.util.ClassUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class PersistenceManagedTypesScanner {

	private static final String CLASS_RESOURCE_PATTERN = "/**/*.class";

	private static final String PACKAGE_INFO_SUFFIX = ".package-info";

	private static final Set<AnnotationTypeFilter> entityTypeFilters = new LinkedHashSet<>(4);

	static {
		entityTypeFilters.add(new AnnotationTypeFilter(Entity.class, false));
		// entityTypeFilters.add(new AnnotationTypeFilter(Embeddable.class, false));
		// entityTypeFilters.add(new AnnotationTypeFilter(MappedSuperclass.class, false));
		// entityTypeFilters.add(new AnnotationTypeFilter(Converter.class, false));
	}

	private final ResourcePatternResolver resourcePatternResolver;

	@Nullable
	private final CandidateComponentsIndex componentsIndex;

	public PersistenceManagedTypesScanner(ResourceLoader resourceLoader) {
		this.resourcePatternResolver = ResourcePatternUtils.getResourcePatternResolver(resourceLoader);
		this.componentsIndex = CandidateComponentsIndexLoader.loadIndex(resourceLoader.getClassLoader());
	}

	/**
	 * Scan the specified packages and return a {@link PersistenceManagedTypes} that
	 * represents the result of the scanning.
	 * @param packagesToScan the packages to scan
	 * @return the {@link PersistenceManagedTypes} instance
	 */
	public PersistenceManagedTypes scan(String... packagesToScan) {
		ScanResult scanResult = new ScanResult();
		for (String pkg : packagesToScan) {
			scanPackage(pkg, scanResult);
		}
		return scanResult.toManagedTypes();
	}

	private void scanPackage(String pkg, ScanResult scanResult) {
		if (this.componentsIndex != null) {
			Set<String> candidates = new HashSet<>();
			for (AnnotationTypeFilter filter : entityTypeFilters) {
				candidates.addAll(this.componentsIndex.getCandidateTypes(pkg, filter.getAnnotationType().getName()));
			}
			scanResult.managedClassNames.addAll(candidates);
			scanResult.managedPackages.addAll(this.componentsIndex.getCandidateTypes(pkg, "package-info"));
			return;
		}

		try {
			String pattern = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX
					+ ClassUtils.convertClassNameToResourcePath(pkg) + CLASS_RESOURCE_PATTERN;
			Resource[] resources = this.resourcePatternResolver.getResources(pattern);
			MetadataReaderFactory readerFactory = new CachingMetadataReaderFactory(this.resourcePatternResolver);
			for (Resource resource : resources) {
				try {
					MetadataReader reader = readerFactory.getMetadataReader(resource);
					String className = reader.getClassMetadata().getClassName();
					if (matchesFilter(reader, readerFactory)) {
						scanResult.managedClassNames.add(className);
					}
					else if (className.endsWith(PACKAGE_INFO_SUFFIX)) {
						scanResult.managedPackages
								.add(className.substring(0, className.length() - PACKAGE_INFO_SUFFIX.length()));
					}
				}
				catch (FileNotFoundException ex) {
					// Ignore non-readable resource
				}
			}
		}
		catch (IOException ex) {
			throw new DaoException(DbInitEnum.ENTITY_SCAN_ERROR);
		}
	}

	/**
	 * Check whether any of the configured entity type filters matches the current class
	 * descriptor contained in the metadata reader.
	 */
	private boolean matchesFilter(MetadataReader reader, MetadataReaderFactory readerFactory) throws IOException {
		for (TypeFilter filter : entityTypeFilters) {
			if (filter.match(reader, readerFactory)) {
				return true;
			}
		}
		return false;
	}

	private static class ScanResult {

		private final List<String> managedClassNames = new ArrayList<>();

		private final List<String> managedPackages = new ArrayList<>();

		PersistenceManagedTypes toManagedTypes() {
			return PersistenceManagedTypes.of(managedClassNames, managedPackages);
		}

	}

}
