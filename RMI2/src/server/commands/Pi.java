/*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package server.commands;

import java.io.Serializable;
import java.math.BigDecimal;
import java.rmi.RemoteException;

import callback.Callback;

/**
 * 
 * Berechnung von Pi
 * 
 * @author Robert Borsos
 * @version 23-02-2016
 *
 */
public class Pi implements Command<BigDecimal>, Serializable {

	private static final long serialVersionUID = 227L;

	private Callback callback;
	private BigDecimal result;

	/** constants used in pi computation */
	private static final BigDecimal FOUR = BigDecimal.valueOf(4);

	/** rounding mode to use during pi computation */
	private static final int roundingMode = BigDecimal.ROUND_HALF_EVEN;

	/** digits of precision after the decimal point */
	private final int digits;

	/**
	 * Construct a task to calculate pi to the specified precision.
	 * 
	 * @param digits anzahl an stellen
	 * @param cb callback
	 */
	public Pi(int digits, Callback cb) {
		this.digits = digits;
		this.callback = cb;
	}

	/**
	 * Ergebnis senden
	 */
	public void send() {
		try {
			callback.receive(result);
		} catch (RemoteException e) {
			e.getMessage();
		}
	}

	/**
	 * Calculate pi.
	 */
	public void execute() {
		computePi();
		send();
	}

	/**
	 * Compute the value of pi to the specified number of digits after the
	 * decimal point. The value is computed using Machin's formula:
	 *
	 * pi/4 = 4*arctan(1/5) - arctan(1/239)
	 *
	 * and a power series expansion of arctan(x) to sufficient precision.
	 */
	public void computePi() {
		int scale = digits + 5;
		BigDecimal arctan1_5 = arctan(5, scale);
		BigDecimal arctan1_239 = arctan(239, scale);
		BigDecimal pi = arctan1_5.multiply(FOUR).subtract(arctan1_239).multiply(FOUR);
		result = pi.setScale(digits, BigDecimal.ROUND_HALF_UP);
	}

	/**
	 * Compute the value, in radians, of the arctangent of the inverse of the
	 * supplied integer to the specified number of digits after the decimal
	 * point. The value is computed using the power series expansion for the arc
	 * tangent:
	 *
	 * arctan(x) = x - (x^3)/3 + (x^5)/5 - (x^7)/7 + (x^9)/9 ...
	 */
	public static BigDecimal arctan(int inverseX, int scale) {
		BigDecimal result, numer, term;
		BigDecimal invX = BigDecimal.valueOf(inverseX);
		BigDecimal invX2 = BigDecimal.valueOf(inverseX * inverseX);

		numer = BigDecimal.ONE.divide(invX, scale, roundingMode);

		result = numer;
		int i = 1;
		do {
			numer = numer.divide(invX2, scale, roundingMode);
			int denom = 2 * i + 1;
			term = numer.divide(BigDecimal.valueOf(denom), scale, roundingMode);
			if ((i % 2) != 0) {
				result = result.subtract(term);
			} else {
				result = result.add(term);
			}
			i++;
		} while (term.compareTo(BigDecimal.ZERO) != 0);
		return result;
	}
}
