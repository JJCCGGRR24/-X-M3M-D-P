/*
 * StringToFolderConverter.java
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

import repositories.FolderRepository;
import domain.Folder;

@Component
@Transactional
public class StringToFolderConverter implements Converter<String, Folder> {

	@Autowired
	FolderRepository folderRepository;


	@Override
	public Folder convert(String text) {
		Folder result;
		int id;

		try {
			id = Integer.valueOf(text);
			result = folderRepository.findOne(id);
		} catch (Exception oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
