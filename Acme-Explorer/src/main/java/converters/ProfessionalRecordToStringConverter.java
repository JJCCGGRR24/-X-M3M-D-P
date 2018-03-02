/*
 * ProfessionalRecordToStringConverter.java
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

import domain.ProfessionalRecord;

@Component
@Transactional
public class ProfessionalRecordToStringConverter implements Converter<ProfessionalRecord, String> {

	@Override
	public String convert(ProfessionalRecord ProfessionalRecord) {
		String result;

		if (ProfessionalRecord == null)
			result = null;
		else
			result = String.valueOf(ProfessionalRecord.getId());

		return result;
	}

}
