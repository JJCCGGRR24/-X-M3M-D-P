/*
 * MiscellaneousRecordToStringConverter.java
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

import domain.MiscellaneousRecord;

@Component
@Transactional
public class MiscellaneousRecordToStringConverter implements Converter<MiscellaneousRecord, String> {

	@Override
	public String convert(MiscellaneousRecord MiscellaneousRecord) {
		String result;

		if (MiscellaneousRecord == null)
			result = null;
		else
			result = String.valueOf(MiscellaneousRecord.getId());

		return result;
	}

}
