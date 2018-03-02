/*
 * TagToStringConverter.java
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

import domain.Nulp;

@Component
@Transactional
public class NulpToStringConverter implements Converter<Nulp, String> {

	@Override
	public String convert(final Nulp nulp) {
		String result;

		if (nulp == null)
			result = null;
		else
			result = String.valueOf(nulp.getId());

		return result;
	}

}
