package br.com.surittec.surivalidation.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.junit.Test;

import br.com.surittec.surivalidation.exception.ValidationException;
import br.com.surittec.surivalidation.test.mock.ValidationMock;
import br.com.surittec.surivalidation.test.support.TestSupport;
import br.com.surittec.surivalidation.util.ValidationUtil;

public class ValidationUtilTest extends TestSupport {

	@Test
	public void cepInvalidoTest() {
		ValidationMock vm = new ValidationMock();
		vm.setCep("a");

		try {
			ValidationUtil.validate(vm);
			assertFalse();
		} catch (ValidationException ve) {
			assertErrorInComponent(ve, "ValidationMock.cep");
			System.out.println(ve.getErrors().get(0).getMessage());
		}
	}

	@Test
	public void cepValidoTest() {
		ValidationMock vm = new ValidationMock();
		vm.setCep("78945612");

		try {
			ValidationUtil.validate(vm);
		} catch (ValidationException ve) {
			assertFalse();
		}
	}

	@Test
	public void dateRangeInvalidoTest() throws ParseException {
		ValidationMock vm = new ValidationMock();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		vm.setDateRange(sdf.parse("31/12/2014"));

		try {
			ValidationUtil.validate(vm);
			assertFalse();
		} catch (ValidationException ve) {
			assertErrorInComponent(ve, "ValidationMock.dateRange");
			System.out.println(ve.getErrors().get(0).getMessage());
		}

		vm.setDateRange(sdf.parse("01/01/2016"));

		try {
			ValidationUtil.validate(vm);
			assertFalse();
		} catch (ValidationException ve) {
			assertErrorInComponent(ve, "ValidationMock.dateRange");
			System.out.println(ve.getErrors().get(0).getMessage());
		}
	}

	@Test
	public void dateRangeValidoTest() throws ParseException {
		ValidationMock vm = new ValidationMock();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		vm.setDateRange(sdf.parse("01/01/2015"));

		try {
			ValidationUtil.validate(vm);
		} catch (ValidationException ve) {
			assertFalse();
		}

		vm.setDateRange(sdf.parse("31/12/2015"));

		try {
			ValidationUtil.validate(vm);
		} catch (ValidationException ve) {
			assertFalse();
		}
	}

	@Test
	public void maxDateInvalidoTest() throws ParseException {
		ValidationMock vm = new ValidationMock();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		vm.setMaxDate(sdf.parse("01/01/2016"));

		try {
			ValidationUtil.validate(vm);
			assertFalse();
		} catch (ValidationException ve) {
			assertErrorInComponent(ve, "ValidationMock.maxDate");
			System.out.println(ve.getErrors().get(0).getMessage());
		}
	}

	@Test
	public void maxDateValidoTest() throws ParseException {
		ValidationMock vm = new ValidationMock();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		vm.setMaxDate(sdf.parse("31/12/2015"));

		try {
			ValidationUtil.validate(vm);
		} catch (ValidationException ve) {
			assertFalse();
		}
	}

	@Test
	public void minDateInvalidoTest() throws ParseException {
		ValidationMock vm = new ValidationMock();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		vm.setMinDate(sdf.parse("31/12/2014"));

		try {
			ValidationUtil.validate(vm);
			assertFalse();
		} catch (ValidationException ve) {
			assertErrorInComponent(ve, "ValidationMock.minDate");
			System.out.println(ve.getErrors().get(0).getMessage());
		}
	}

	@Test
	public void minDateValidoTest() throws ParseException {
		ValidationMock vm = new ValidationMock();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		vm.setMinDate(sdf.parse("01/01/2015"));

		try {
			ValidationUtil.validate(vm);
		} catch (ValidationException ve) {
			assertFalse();
		}
	}

}
