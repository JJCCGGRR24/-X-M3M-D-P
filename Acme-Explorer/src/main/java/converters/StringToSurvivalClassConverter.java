/*
 * StringToSurvivalClassConverter.java
 * 
 * Copyright (C) 2016 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.SurvivalClassRepository;
import domain.SurvivalClass;

@Component
@Transactional
public class StringToSurvivalClassConverter implements Converter<String, SurvivalClass> {

	@Autowired
	SurvivalClassRepository survivalClassRepository;


	@Override
	public SurvivalClass convert(String text) {
		SurvivalClass result;
		int id;

		try {
			id = Integer.valueOf(text);
			result = survivalClassRepository.findOne(id);
		} catch (Exception oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
