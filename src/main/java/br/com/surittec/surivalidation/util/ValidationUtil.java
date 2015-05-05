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
		validate(param, param.getClass().getSimpleName(), new Class<?>[] {}, null, new String[] {});
	}

	public static <T> void validate(T param, String paramName) throws ValidationException {
		validate(param, paramName, new Class<?>[] {}, null, new String[] {});
	}

	public static <T> void validate(T param, Class<?>[] groups) throws ValidationException {
		validate(param, param.getClass().getSimpleName(), groups, null, new String[] {});
	}

	public static <T> void validate(T param, String[] bundleNames) throws ValidationException {
		validate(param, param.getClass().getSimpleName(), new Class<?>[] {}, null, bundleNames);
	}

	public static <T> void validate(T param, String paramName, Class<?>[] groups) throws ValidationException {
		validate(param, paramName, groups, null, new String[] {});
	}

	public static <T> void validate(T param, String paramName, String validationErrorCode) throws ValidationException {
		validate(param, paramName, new Class<?>[] {}, validationErrorCode, new String[] {});
	}

	public static <T> void validate(T param, String paramName, String[] bundleNames) throws ValidationException {
		validate(param, paramName, new Class<?>[] {}, null, bundleNames);
	}

	public static <T> void validate(T param, String paramName, Class<?>[] groups, String[] bundleNames) throws ValidationException {
		validate(param, paramName, groups, null, bundleNames);
	}

	public static <T> void validate(T param, String paramName, String validationErrorCode, String[] bundleNames) throws ValidationException {
		validate(param, paramName, new Class<?>[] {}, validationErrorCode, bundleNames);
	}

	public static <T> void validate(T param, String paramName, Class<?>[] groups, String validationErrorCode, String[] bundleNames)
			throws ValidationException {
		if (param == null)
			return;

		Set<ConstraintViolation<T>> constraintViolations = getValidator(bundleNames).validate(param, groups);
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
		validateMethod(object, method, parameterNames, parameterValues, new Class<?>[] {}, null, new String[] {});
	}

	public static <T> void validateMethod(T object, Method method, String[] parameterNames, Object[] parameterValues, String validationErrorCode)
			throws ValidationException {
		validateMethod(object, method, parameterNames, parameterValues, new Class<?>[] {}, validationErrorCode, new String[] {});
	}

	public static <T> void validateMethod(T object, Method method, String[] parameterNames, Object[] parameterValues, Class<?>[] groups)
			throws ValidationException {
		validateMethod(object, method, parameterNames, parameterValues, groups, null, new String[] {});
	}

	public static <T> void validateMethod(T object, Method method, String[] parameterNames, Object[] parameterValues, String[] bundleNames)
			throws ValidationException {
		validateMethod(object, method, parameterNames, parameterValues, new Class<?>[] {}, null, bundleNames);
	}

	public static <T> void validateMethod(T object, Method method, String[] parameterNames, Object[] parameterValues, Class<?>[] groups,
			String validationErrorCode) throws ValidationException {
		validateMethod(object, method, parameterNames, parameterValues, groups, validationErrorCode, new String[] {});
	}

	public static <T> void validateMethod(T object, Method method, String[] parameterNames, Object[] parameterValues, Class<?>[] groups,
			String[] bundleNames)
			throws ValidationException {
		validateMethod(object, method, parameterNames, parameterValues, groups, null, bundleNames);
	}

	public static <T> void validateMethod(T object, Method method, String[] parameterNames, Object[] parameterValues, String validationErrorCode,
			String[] bundlesName) throws ValidationException {
		validateMethod(object, method, parameterNames, parameterValues, new Class<?>[] {}, validationErrorCode, bundlesName);
	}

	public static <T> void validateMethod(T object, Method method, String[] parameterNames, Object[] parameterValues, Class<?>[] groups,
			String validationErrorCode, String[] bundlesName) throws ValidationException {
		MethodValidator mv = getValidator(bundlesName).unwrap(MethodValidator.class);

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

	@SuppressWarnings("deprecation")
	protected static Validator getValidator(String[] bundleName) {

		/*
		 * List<String> resourceBundles = new
		 * ArrayList<String>(Arrays.asList(bundleName));
		 * resourceBundles.add(Constants.SURITTEC_CORE_BUNDLE_BASENAME);
		 * 
		 * HibernateValidatorConfiguration configuration =
		 * Validation.byProvider(HibernateValidator.class).configure();
		 * configuration.messageInterpolator( new
		 * ResourceBundleMessageInterpolator( new
		 * AggregateResourceBundleLocator( resourceBundles,
		 * configuration.getDefaultResourceBundleLocator() ) ) );
		 * 
		 * return configuration.buildValidatorFactory().getValidator();
		 */

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
