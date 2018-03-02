/*
 * StringToStoryConverter.java
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

import repositories.StoryRepository;
import domain.Story;

@Component
@Transactional
public class StringToStoryConverter implements Converter<String, Story> {

	@Autowired
	StoryRepository storyRepository;


	@Override
	public Story convert(String text) {
		Story result;
		int id;

		try {
			id = Integer.valueOf(text);
			result = storyRepository.findOne(id);
		} catch (Exception oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
