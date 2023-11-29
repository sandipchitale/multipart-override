package sandipchitale.multipartoverride;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.web.multipart.MultipartResolver;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootApplication
public class MultipartOverrideApplication {

	public static void main(String[] args) {
		args = Stream.of(Stream.of("Mama"),
							 Stream.of("Mia"),
							 Arrays.stream(args))
					 .flatMap(s -> s)
					 .toArray(String[]::new);
		new SpringApplicationBuilder(MultipartOverrideApplication.class)
				.initializers((ConfigurableApplicationContext applicationContext) -> {
					ConfigurableEnvironment environment = applicationContext.getEnvironment();
					environment.getPropertySources()
							   .addFirst(new MapPropertySource("multipart-enabled-false", Map.of("spring.servlet.multipart.enabled", "false")));
				})
				.run(args);
	}


	@Bean
	public CommandLineRunner clr (ApplicationContext applicationContext) {
		return (args) -> {
			System.out.println("MultipartResolver bean = " + applicationContext.getBeansOfType(MultipartResolver.class));
		};
	}

}
