package aurora.supply_wok.platform.shared.infrastructure.persistence.jpa.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

@Component
public class DatabaseInitializer implements BeanFactoryPostProcessor, EnvironmentAware {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseInitializer.class);

    private Environment environment;

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        String url = environment.getProperty("spring.datasource.url");
        if (url == null || !url.contains("postgresql")) {
            return;
        }

        String username = environment.getProperty("spring.datasource.username");
        String password = environment.getProperty("spring.datasource.password");

        String databaseName = url.substring(url.lastIndexOf("/") + 1);
        String baseUrl = url.substring(0, url.lastIndexOf("/") + 1) + "postgres";

        try (Connection connection = DriverManager.getConnection(baseUrl, username, password);
             Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery(
                    "SELECT 1 FROM pg_database WHERE datname = '" + databaseName + "'");

            if (!resultSet.next()) {
                statement.executeUpdate("CREATE DATABASE " + databaseName);
                logger.info("Database '{}' created successfully.", databaseName);
            }

        } catch (Exception e) {
            logger.warn("Could not auto-create database '{}': {}", databaseName, e.getMessage());
        }
    }
}
