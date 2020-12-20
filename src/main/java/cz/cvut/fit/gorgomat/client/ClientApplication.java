package cz.cvut.fit.gorgomat.client;

import cz.cvut.fit.gorgomat.client.resource.CustomerResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.hateoas.config.HypermediaRestTemplateConfigurer;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableHypermediaSupport(type = EnableHypermediaSupport.HypermediaType.HAL)
public class ClientApplication {
    @Autowired
    private CustomerResource customerResource;

    public static void main(String[] args) {
        SpringApplication.run(ClientApplication.class, args);
    }

    @Bean
    RestTemplateCustomizer customizer(HypermediaRestTemplateConfigurer c) {
        return c::registerHypermediaTypes;
    }
}
