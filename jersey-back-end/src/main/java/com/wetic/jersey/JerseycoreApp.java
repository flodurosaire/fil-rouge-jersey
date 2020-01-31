package com.wetic.jersey;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.env.Environment;

import com.wetic.jersey.config.ApplicationProperties;
import com.wetic.jersey.config.DefaultProfileUtil;
import com.wetic.jersey.domain.Authority;
import com.wetic.jersey.domain.User;
import com.wetic.jersey.repository.AuthorityRepository;
import com.wetic.jersey.repository.UserRepository;
import com.wetic.jersey.service.UserService;
import com.wetic.jersey.service.dto.UserDTO;
import com.wetic.jersey.web.rest.AccountResource;
import com.wetic.jersey.web.rest.vm.ManagedUserVM;

import io.github.jhipster.config.JHipsterConstants;

@SpringBootApplication
@EnableConfigurationProperties({ LiquibaseProperties.class, ApplicationProperties.class })
public class JerseycoreApp implements InitializingBean, CommandLineRunner {

	private static final Logger log = LoggerFactory.getLogger(JerseycoreApp.class);

	private final Environment env;

	@Autowired
    private UserService userService;
    @Autowired
    private AuthorityRepository authorityRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AccountResource accountResource;

	public JerseycoreApp(Environment env) {
		this.env = env;
	}

	/**
	 * Initializes jerseycore.
	 * <p>
	 * Spring profiles can be configured with a program argument
	 * --spring.profiles.active=your-active-profile
	 * <p>
	 * You can find more information on how profiles work with JHipster on <a href=
	 * "https://www.jhipster.tech/profiles/">https://www.jhipster.tech/profiles/</a>.
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		Collection<String> activeProfiles = Arrays.asList(env.getActiveProfiles());
		if (activeProfiles.contains(JHipsterConstants.SPRING_PROFILE_DEVELOPMENT)
				&& activeProfiles.contains(JHipsterConstants.SPRING_PROFILE_PRODUCTION)) {
			log.error("You have misconfigured your application! It should not run "
					+ "with both the 'dev' and 'prod' profiles at the same time.");
		}
		if (activeProfiles.contains(JHipsterConstants.SPRING_PROFILE_DEVELOPMENT)
				&& activeProfiles.contains(JHipsterConstants.SPRING_PROFILE_CLOUD)) {
			log.error("You have misconfigured your application! It should not "
					+ "run with both the 'dev' and 'cloud' profiles at the same time.");
		}
	}

	/**
	 * Main method, used to run the application.
	 *
	 * @param args the command line arguments.
	 */
	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(JerseycoreApp.class);
		DefaultProfileUtil.addDefaultProfile(app);
		Environment env = app.run(args).getEnvironment();
		logApplicationStartup(env);
	}

	private static void logApplicationStartup(Environment env) {
		String protocol = "http";
		if (env.getProperty("server.ssl.key-store") != null) {
			protocol = "https";
		}
		String serverPort = env.getProperty("server.port");
		String contextPath = env.getProperty("server.servlet.context-path");
		if (StringUtils.isBlank(contextPath)) {
			contextPath = "/";
		}
		String hostAddress = "localhost";
		try {
			hostAddress = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			log.warn("The host name could not be determined, using `localhost` as fallback");
		}
		log.info(
				"\n----------------------------------------------------------\n\t"
						+ "Application '{}' is running! Access URLs:\n\t" + "Local: \t\t{}://localhost:{}{}\n\t"
						+ "External: \t{}://{}:{}{}\n\t"
						+ "Profile(s): \t{}\n----------------------------------------------------------",
				env.getProperty("spring.application.name"), protocol, serverPort, contextPath, protocol, hostAddress,
				serverPort, contextPath, env.getActiveProfiles());
	}

	@Override
	public void run(String... args) throws Exception {
		Set<String> authorities = new HashSet<String>();
        Authority authorityAdmin = new Authority();
        authorityAdmin.setName("ROLE_ADMIN");
        Authority authorityUser = new Authority();
        authorityUser.setName("ROLE_USER");
        authorityRepository.save(authorityAdmin);
        authorityRepository.save(authorityUser);
        authorities.add(authorityAdmin.getName());
        authorities.add(authorityUser.getName());

        /************************ */
        // CREER ADMIN: Ajouter un admin
        /********************************* */

        if (authorityRepository.getOne(authorityAdmin.getName()) != null) {
            authorityRepository.save(authorityAdmin);
        }
        if (authorityRepository.getOne(authorityUser.getName()) != null) {
            authorityRepository.save(authorityUser);
        }
        if (!userService.getUserWithAuthoritiesByLogin("admin").isPresent()) {

            UserDTO userdto = new UserDTO();
            userdto.setLogin("admin");
            userdto.setPassword("admin");
            userdto.setFirstName("admin");
            userdto.setEmail("admin@gmail.com");
            userdto.setAuthorities(authorities);
            userService.createUser(userdto);
        }

        /************************ */
        // CREER USER: Un tester de l'inscription d'un utilisateur, une methode a
        // appleer du front
        /************************ */
        ManagedUserVM managedUserVM = new ManagedUserVM();
        managedUserVM.setLogin("user");

        managedUserVM.setPassword("user");
        managedUserVM.setActivated(true);

        managedUserVM.setEmail("test@gmail.com");
        // on ne prends que le role
        // authorityRepository.findById(AuthoritiesConstants.USER) (see methode register
        // user)
        managedUserVM.setAuthorities(authorities);

        Optional<User> userAc = userService.getUserWithAuthoritiesByLogin("user");
        if (!userAc.isPresent()) {

            accountResource.registerAccount(managedUserVM);
        }


	}
}
