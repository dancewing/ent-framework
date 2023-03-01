package io.entframework.kernel.db.generator.plugin.web.runtime;

import io.entframework.kernel.db.generator.utils.WebUtils;
import org.apache.commons.lang3.StringUtils;
import org.mybatis.generator.api.GeneratedFile;
import org.mybatis.generator.api.JavaFormatter;
import org.mybatis.generator.api.WriteMode;
import org.mybatis.generator.api.dom.java.CompilationUnit;

public class GeneratedTypescriptFile extends GeneratedFile {

	private final CompilationUnit compilationUnit;

	private final String fileEncoding;

	private final JavaFormatter javaFormatter;

	private final String projectAlias;

	public GeneratedTypescriptFile(CompilationUnit compilationUnit, String targetProject, String fileEncoding,
			JavaFormatter javaFormatter, String projectAlias) {
		super(targetProject);
		this.compilationUnit = compilationUnit;
		this.fileEncoding = fileEncoding;
		this.javaFormatter = javaFormatter;
		this.projectAlias = projectAlias;
	}

	@Override
	public String getFormattedContent() {
		return this.javaFormatter.getFormattedContent(compilationUnit);
	}

	@Override
	public String getFileName() {
		if (this.compilationUnit.getType() instanceof FullyQualifiedTypescriptType) {
			FullyQualifiedTypescriptType fqtt = (FullyQualifiedTypescriptType) this.compilationUnit.getType();
			String shortName = fqtt.getFileName();
			return shortName + ".ts"; //$NON-NLS-1$
		}
		else {
			FullyQualifiedTypescriptType fqtt = WebUtils.convertToTypescriptImportType(this.projectAlias,
					this.compilationUnit.getType());
			String shortName = fqtt.getFileName();
			return shortName + ".ts"; //$NON-NLS-1$
		}
	}

	@Override
	public String getTargetPackage() {
		if (this.compilationUnit.getType() instanceof FullyQualifiedTypescriptType) {
			return StringUtils.substringBeforeLast(this.compilationUnit.getType().getPackageName(), "."); //$NON-NLS-1$
		}
		return compilationUnit.getType().getPackageName();
	}

	/**
	 * This method is required by the Eclipse Java merger. If you are not running in
	 * Eclipse, or some other system that implements the Java merge function, you may
	 * return null from this method.
	 * @return the CompilationUnit associated with this file, or null if the file is not
	 * mergeable.
	 */
	public CompilationUnit getCompilationUnit() {
		return this.compilationUnit;
	}

	/**
	 * A Java file is mergeable if the getCompilationUnit() method returns a valid
	 * compilation unit.
	 * @return true, if is mergeable
	 */
	@Override
	public boolean isMergeable() {
		return true;
	}

	@Override
	public String getFileEncoding() {
		return fileEncoding;
	}

	@Override
	public WriteMode getWriteMode() {
		return this.compilationUnit.getWriteMode();
	}

}
