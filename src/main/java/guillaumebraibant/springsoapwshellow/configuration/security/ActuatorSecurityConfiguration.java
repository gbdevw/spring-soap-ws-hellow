package guillaumebraibant.springsoapwshellow.configuration.security;

import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Configure la sécurité des endpoints actuator/health, actuator/info et actuator/metric.
 * Ici, la sécurité autorise les requêtes non-authentifiées aux endpoints.
 */
@Configuration
public class ActuatorSecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.requestMatcher(EndpointRequest.toAnyEndpoint()).authorizeRequests()
			.anyRequest().permitAll();
	}
}