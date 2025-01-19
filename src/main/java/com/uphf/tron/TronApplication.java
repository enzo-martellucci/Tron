package com.uphf.tron;

import com.uphf.tron.controller.SceneController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.util.Objects;

@SpringBootApplication
public class TronApplication extends Application
{
	@Autowired
	private Environment environment;

	private ConfigurableApplicationContext applicationContext;

	@Override
	public void init()
	{
		this.applicationContext = new SpringApplicationBuilder(TronApplication.class).run();
	}

	@Override
	public void start(Stage stage)
	{
		SceneController sceneController = this.applicationContext.getBean(SceneController.class);
		sceneController.init(this.applicationContext, stage);
	}

	@Override
	public void stop()
	{
		this.applicationContext.close();
		Platform.exit();
	}

	@Bean
	public DataSource dataSource() {
		final DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(Objects.requireNonNull(this.environment.getProperty("spring.datasource.driver-class-name")));
		dataSource.setUrl(this.environment.getProperty("url"));
		dataSource.setUsername(this.environment.getProperty("user"));
		dataSource.setPassword(this.environment.getProperty("password"));
		return dataSource;
	}

	public static void main(String[] args)
	{
		Application.launch(TronApplication.class, args);
	}
}
