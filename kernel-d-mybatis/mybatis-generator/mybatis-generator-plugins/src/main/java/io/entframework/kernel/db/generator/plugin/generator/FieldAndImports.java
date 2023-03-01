package io.entframework.kernel.db.generator.plugin.generator;

import lombok.Data;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
public class FieldAndImports {

	private final List<Field> fields;

	private final Set<FullyQualifiedJavaType> imports;

	private final Set<String> staticImports;

	private FieldAndImports(FieldAndImports.Builder builder) {
		fields = builder.fields;
		imports = builder.imports;
		staticImports = builder.staticImports;
	}

	public List<Field> getFields() {
		return fields;
	}

	public Set<FullyQualifiedJavaType> getImports() {
		return imports;
	}

	public Set<String> getStaticImports() {
		return staticImports;
	}

	public static FieldAndImports.Builder withField(Field method) {
		return new FieldAndImports.Builder().withField(method);
	}

	public static class Builder {

		private final List<Field> fields = new ArrayList<>();

		private final Set<FullyQualifiedJavaType> imports = new HashSet<>();

		private final Set<String> staticImports = new HashSet<>();

		public FieldAndImports.Builder withField(Field field) {
			this.fields.add(field);
			return this;
		}

		public FieldAndImports.Builder withImport(FullyQualifiedJavaType importedType) {
			this.imports.add(importedType);
			return this;
		}

		public FieldAndImports.Builder withImport(String importedType) {
			this.imports.add(new FullyQualifiedJavaType(importedType));
			return this;
		}

		public FieldAndImports.Builder withImports(Set<FullyQualifiedJavaType> imports) {
			this.imports.addAll(imports);
			return this;
		}

		public FieldAndImports.Builder withStaticImport(String staticImport) {
			this.staticImports.add(staticImport);
			return this;
		}

		public FieldAndImports.Builder withStaticImports(Set<String> staticImports) {
			this.staticImports.addAll(staticImports);
			return this;
		}

		public FieldAndImports build() {
			return new FieldAndImports(this);
		}

	}

}
