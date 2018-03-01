package com.example.demo;

import javax.xml.bind.JAXBException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.example.domain.Product;
import com.marklogic.client.DatabaseClient;
import com.marklogic.client.DatabaseClientFactory;
import com.marklogic.client.document.JSONDocumentManager;
import com.marklogic.client.document.XMLDocumentManager;
import com.marklogic.client.io.JAXBHandle;
import com.marklogic.client.query.QueryManager;

@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = {"com"})
public class MarkLogicSampleApplication {

    @Value("${marklogic.host}")
    private String host;

    @Value("${marklogic.port}")
    private int port;

    @Value("${marklogic.username}")
    private String username;

    @Value("${marklogic.password}")
    private String password;

    @Bean
    public DatabaseClient getDatabaseClient() {
        try {
            // TODO: is this really (still) required?
            // configure once before creating a client
            DatabaseClientFactory.getHandleRegistry().register(
                    JAXBHandle.newFactory(Product.class)
            );
        } catch (JAXBException e) {
            e.printStackTrace();
        }

        return DatabaseClientFactory.newClient(host, port, username, password,
                DatabaseClientFactory.Authentication.DIGEST);
    }

    @Bean
    public QueryManager getQueryManager() {
        return getDatabaseClient().newQueryManager();
    }

    @Bean
    public XMLDocumentManager getXMLDocumentManager() {
        return getDatabaseClient().newXMLDocumentManager();
    }

    @Bean
    public JSONDocumentManager getJSONDocumentManager() {
        return getDatabaseClient().newJSONDocumentManager();
    }

    @Bean
    public String getMarkLogicBaseURL() {
        return String.format("http://%s:%d", host, port);
    }

    /**
     * The entrance point to the sample application, starts Spring Boot.
     */
    public static void main(String[] args) throws Exception {
        SpringApplication.run(MarkLogicSampleApplication.class, args);
    }

}