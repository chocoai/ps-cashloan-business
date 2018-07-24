package com.adpanshi.cashloan.business.core.common.util.parse;

/**
 * Class类型解析器

 *
 */
public interface ClassTypeParser {
	<T> T parse(String content, Class<T> valueType);
}
