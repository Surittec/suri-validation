/*
 * SURITTEC
 * Copyright 2014, TTUS TECNOLOGIA DA INFORMACAO LTDA, 
 * and individual contributors as indicated by the @authors tag
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package br.com.surittec.surivalidation.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import br.com.surittec.surivalidation.constraints.Normalized;

public class NormalizedValidator implements ConstraintValidator<Normalized, String> {

	private static final String PATTERN_FORMAT = "^[0-9a-zA-Z][0-9a-zA-Z%s]{0,%d}[0-9a-zA-Z]$";
	
	private String pattern;
	
	@Override
	public void initialize(Normalized constraintAnnotation) {
		if(constraintAnnotation.length() < 2) throw new IllegalArgumentException("Lenght must be greater than 2");
		pattern = String.format(PATTERN_FORMAT, constraintAnnotation.specialCharsRegexParam(), constraintAnnotation.length() - 2);
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if(value == null) return true;
		return value.matches(pattern);
	}

}
