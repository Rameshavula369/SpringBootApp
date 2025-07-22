package com.effort.settings;

import java.io.Serializable;

import org.springframework.stereotype.Component;
@Component
public class BrandingConstants implements Serializable{

	private String resendActivationCodeBodySmsForKalpataru = "Please download HumanTree from https://bit.ly/2UK6qbN And Login using Phone: {EMP_PHONE} and Activation Code: {ACTIVATION_CODE} on mobile app.";

	public String getResendActivationCodeBodySmsForKalpataru() {
		return resendActivationCodeBodySmsForKalpataru;
	}
}
