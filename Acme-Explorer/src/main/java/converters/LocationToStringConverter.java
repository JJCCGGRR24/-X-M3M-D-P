/*
 * AdministratorToStringConverter.java
 * 
 * Copyright (C) 2016 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package converters;

import java.net.URLEncoder;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Location;

@Component
@Transactional
public class LocationToStringConverter implements Converter<Location, String> {

	@Override
	public String convert(final Location l) {
		String result;
		StringBuilder builder;

		if (l == null)
			result = null;
		else
			try {
				builder = new StringBuilder();
				builder.append(URLEncoder.encode(l.getName(), "UTF-8"));
				builder.append("|");
				builder.append(URLEncoder.encode(Double.toString(l.getAltitude()), "UTF-8"));
				builder.append("|");
				builder.append(URLEncoder.encode(Double.toString(l.getLatitude()), "UTF-8"));
				result = builder.toString();
			} catch (final Throwable oops) {
				throw new RuntimeException(oops);
			}

		return result;
	}

}
