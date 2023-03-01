package io.entframework.kernel.db.mds.ext.dto;

import io.entframework.kernel.rule.annotation.JsonHandler;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonHandler
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(builderClassName = "Builder")
public class TeachProperty {

	private String test;

}
