/*
 * CategoryToStringConverter.java
 * 
 * Copyright (C) 2016 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Category;

@Component
@Transactional
public class CategoryToStringConverter implements Converter<Category, String> {

	@Override
	public String convert(Category Category) {
		String result;

		if (Category == null)
			result = null;
		else
			result = String.valueOf(Category.getId());

		return result;
	}

}
