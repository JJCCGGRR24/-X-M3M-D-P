/*
 * SurvivalClassToStringConverter.java
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

import domain.SurvivalClass;

@Component
@Transactional
public class SurvivalClassToStringConverter implements Converter<SurvivalClass, String> {

	@Override
	public String convert(SurvivalClass SurvivalClass) {
		String result;

		if (SurvivalClass == null)
			result = null;
		else
			result = String.valueOf(SurvivalClass.getId());

		return result;
	}

}
