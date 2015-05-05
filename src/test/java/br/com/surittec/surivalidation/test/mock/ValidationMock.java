package br.com.surittec.surivalidation.test.mock;

import java.util.Date;

import br.com.surittec.surivalidation.constraints.CEP;
import br.com.surittec.surivalidation.constraints.DateRange;
import br.com.surittec.surivalidation.constraints.MaxDate;
import br.com.surittec.surivalidation.constraints.MinDate;

public class ValidationMock {

	@CEP
	private String cep;

	@DateRange(minDate = "01/01/2015", maxDate = "31/12/2015")
	private Date dateRange;

	@MaxDate(maxDate = "31/12/2015")
	private Date maxDate;

	@MinDate(minDate = "01/01/2015")
	private Date minDate;

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public Date getDateRange() {
		return dateRange;
	}

	public void setDateRange(Date dateRange) {
		this.dateRange = dateRange;
	}

	public Date getMaxDate() {
		return maxDate;
	}

	public void setMaxDate(Date maxDate) {
		this.maxDate = maxDate;
	}

	public Date getMinDate() {
		return minDate;
	}

	public void setMinDate(Date minDate) {
		this.minDate = minDate;
	}

}
