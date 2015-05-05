package br.com.surittec.surivalidation.util;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Path;
import javax.validation.Path.Node;
import javax.validation.Validation;
import javax.validation.Validator;

import org.hibernate.validator.internal.engine.MethodParameterNodeImpl;
import org.hibernate.validator.method.MethodConstraintViolation;
import org.hibernate.validator.method.MethodValidator;

import br.com.surittec.surivalidation.exception.ValidationException;

/**
 * Classe utilitaria para validacao com Bean Validation.
 * 
 * @author Lucas Lins
 *
 */
public abstract class ValidationUtil implements Serializable {

	private static final long serialVersionUID = 1L;

	/*
	 * Bean Validation
	 */

	public static <T> void validate(T param) throws ValidationException {
		validate(param, param.getClass().getSimpleName(), new Class<?>[] {}, null);
	}

	public static <T> void validate(T param, String paramName) throws ValidationException {
		validate(param, paramName, new Class<?>[] {}, null);
	}

	public static <T> void validate(T param, Class<?>[] groups) throws ValidationException {
		validate(param, param.getClass().getSimpleName(), groups, null);
	}

	public static <T> void validate(T param, String paramName, Class<?>[] groups) throws ValidationException {
		validate(param, paramName, groups, null);
	}

	public static <T> void validate(T param, String paramName, String validationErrorCode) throws ValidationException {
		validate(param, paramName, new Class<?>[] {}, validationErrorCode);
	}

	public static <T> void validate(T param, String paramName, Class<?>[] groups, String validationErrorCode) throws ValidationException {
		if (param == null)
			return;

		Set<ConstraintViolation<T>> constraintViolations = getValidator().validate(param, groups);
		if (!constraintViolations.isEmpty()) {

			ValidationException ve = new ValidationException();

			for (ConstraintViolation<T> cv : constraintViolations) {
				ve.addMessageWithCodeAndComponent(
						validationErrorCode,
						getPropertyPath(paramName, cv.getPropertyPath()),
						cv.getMessage());
			}

			throw ve;
		}
	}

	/*
	 * Method validation
	 */

	public static <T> void validateMethod(T object, Method method, String[] parameterNames, Object[] parameterValues) throws ValidationException {
		validateMethod(object, method, parameterNames, parameterValues, new Class<?>[] {}, null);
	}

	public static <T> void validateMethod(T object, Method method, String[] parameterNames, Object[] parameterValues, String validationErrorCode)
			throws ValidationException {
		validateMethod(object, method, parameterNames, parameterValues, new Class<?>[] {}, validationErrorCode);
	}

	public static <T> void validateMethod(T object, Method method, String[] parameterNames, Object[] parameterValues, Class<?>[] groups)
			throws ValidationException {
		validateMethod(object, method, parameterNames, parameterValues, groups, null);
	}

	public static <T> void validateMethod(T object, Method method, String[] parameterNames, Object[] parameterValues, Class<?>[] groups,
			String validationErrorCode) throws ValidationException {
		MethodValidator mv = getValidator().unwrap(MethodValidator.class);

		Set<MethodConstraintViolation<T>> constraintViolations = mv.validateAllParameters(object, method, parameterValues, groups);

		if (!constraintViolations.isEmpty()) {
			ValidationException ve = new ValidationException();

			for (MethodConstraintViolation<T> cv : constraintViolations) {
				ve.addMessageWithCodeAndComponent(
						validationErrorCode,
						getPropertyPath(parameterNames[cv.getParameterIndex()], cv.getPropertyPath()),
						cv.getMessage());
			}

			throw ve;
		}
	}

	/*
	 * Protected Methods
	 */

	protected static Validator getValidator() {
		return Validation.buildDefaultValidatorFactory().getValidator();
	}

	protected static String getPropertyPath(String paramName, Path path) {
		StringBuilder pp = new StringBuilder(paramName != null ? paramName : "");
		Iterator<Node> it = path.iterator();
		while (it.hasNext()) {
			Node node = it.next();
			if (!(node instanceof MethodParameterNodeImpl)) {
				pp.append(".");
				pp.append(node.getName());
			}
		}
		return pp.toString();
	}

}
