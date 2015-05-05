package br.com.surittec.surivalidation.exception;

import java.util.ArrayList;
import java.util.List;

import br.com.surittec.util.message.Message;

public class ValidationException extends Exception {

	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// ATTRIBUTES
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

	private static final long serialVersionUID = 1L;

	private List<Message> errors = new ArrayList<Message>();

	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// CONSTRUCTORS
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

	/**
	 * Instancia uma nova ValidationException sem nenhum código de mensagem
	 * lançado.
	 */
	public ValidationException() {
		super();
	}

	/**
	 * Instancia uma nova ValidationException com uma mensagem de valor
	 * <code>message</code> e com possíveis parâmetros em <code>params</code>.
	 */
	public ValidationException(String message, Object... params) {
		addMessage(message, params);
	}

	/**
	 * Instancia uma nova ValidationException contendo a <code>Message</code>
	 * passada por parâmetro.
	 */
	public ValidationException(Message message) {
		addMessage(message);
	}

	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// PUBLIC METHODS
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

	/**
	 * Inclui uma nova mensagem na lista de erros da exceção.
	 * 
	 * @param message
	 */
	public void addMessage(Message message) {
		errors.add(message);
	}

	/**
	 * Inclui uma nova mensagem de valor <code>message</code> e com possíveis
	 * parâmetros em <code>params</code> na lista de erros da exceção.
	 * 
	 * @param message
	 */
	public void addMessage(String message, Object... messageParams) {
		addMessage(new Message(null, null, message, messageParams));
	}

	/**
	 * Inclui uma nova mensagem de código <code>code</code>, valor
	 * <code>message</code> e com possíveis parâmetros em <code>params</code> na
	 * lista de erros da exceção.
	 * 
	 * @param message
	 */
	public void addMessageWithCode(String code, String message, Object... messageParams) {
		addMessage(new Message(code, null, message, messageParams));
	}

	/**
	 * Inclui uma nova mensagem para o campo <code>component</code>, valor
	 * <code>message</code> e com possíveis parâmetros em <code>params</code> na
	 * lista de erros da exceção.
	 * 
	 * @param message
	 */
	public void addMessageWithComponent(String component, String message, Object... messageParams) {
		addMessage(new Message(null, component, message, messageParams));
	}

	/**
	 * Inclui uma nova mensagem de código <code>code</code>, para o campo
	 * <code>component</code>, valor <code>message</code> e com possíveis
	 * parâmetros em <code>params</code> na lista de erros da exceção.
	 * 
	 * @param message
	 */
	public void addMessageWithCodeAndComponent(String code, String component, String message, Object... messageParams) {
		addMessage(new Message(code, component, message, messageParams));
	}

	/**
	 * Retorna a lista de erros da exceção.
	 * 
	 * @param message
	 */
	public List<Message> getErrors() {
		return errors;
	}

}
