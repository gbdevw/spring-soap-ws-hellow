package guillaumebraibant.springsoapwshellow.configuration.soap;

import javax.xml.ws.Endpoint;

import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import guillaumebraibant.springsoapwshellow.soap.SayHelloInterfaceImpl;

@Configuration
public class EndpointConfiguration {

    @Bean 
    public SayHelloInterfaceImpl registerSayHelloImpl () 
    {
        return new SayHelloInterfaceImpl();
    }

    @Bean
    public Endpoint publishOrderTimeDockOrgService (final SayHelloInterfaceImpl implementor, final Bus bus)
    {
        final EndpointImpl endpoint = new EndpointImpl(bus, implementor);
        endpoint.publish("/sayhello");
        return endpoint;
    }
}