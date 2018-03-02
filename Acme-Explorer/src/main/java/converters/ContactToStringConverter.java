/*
 * ContactToStringConverter.java
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

import domain.Contact;

@Component
@Transactional
public class ContactToStringConverter implements Converter<Contact, String> {

	@Override
	public String convert(Contact Contact) {
		String result;

		if (Contact == null)
			result = null;
		else
			result = String.valueOf(Contact.getId());

		return result;
	}

}
