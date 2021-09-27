package com.assignment.clinic.entity;

import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

/**
 * 
 * @author Kavitha
 *
 */

public class AuditorAwareImpl implements AuditorAware<String> {

	@Override
	public Optional<String> getCurrentAuditor() {
		return Optional.of(System.getProperty("user.name"));
	}
}
