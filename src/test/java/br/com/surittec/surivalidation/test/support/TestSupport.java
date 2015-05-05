package br.com.surittec.surivalidation.test.support;

import org.junit.Assert;

import br.com.surittec.surivalidation.exception.ValidationException;
import br.com.surittec.util.message.Message;

public abstract class TestSupport {

	protected void assertFalse() {
		Assert.assertTrue(false);
	}

	protected void assertErrorInComponent(ValidationException ve, String component) {
		Assert.assertNotNull(ve.getErrors());
		Message msg = null;
		for (Message message : ve.getErrors()) {
			if (message.getComponent() != null && message.getComponent().equals(component)) {
				msg = message;
				break;
			}
		}

		if (msg == null) {
			assertFalse();
		}
		Assert.assertNotNull(msg.getMessage());
		Assert.assertFalse(msg.getMessage().startsWith("br.com.surittec.surivalidation.constraints."));
	}

}
