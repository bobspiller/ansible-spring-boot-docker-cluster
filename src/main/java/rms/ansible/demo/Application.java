package rms.ansible.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.net.Inet4Address;
import java.net.UnknownHostException;


@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}

class Greeting {
    private final String message;

    Greeting(String message) {
        this.message = message;
    }

    @SuppressWarnings("unused")
    public String getMessage() {
        return message;
    }
}

@Service
class LocalHostService {
    String getLocalHostInfo() {
        try {
            return Inet4Address.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            throw new LocalHostException(e.getMessage(), e);
        }
    }
}

@Service
class GreeterService {
    private final LocalHostService localHostService;

    GreeterService(LocalHostService localHostService) {
        this.localHostService = localHostService;
    }

    Greeting greet(String subject) {
        return new Greeting("Hello " + subject +
                " from " + localHostService.getLocalHostInfo() + "!");
    }
}

@RestController
@RequestMapping("/api")
class GreeterController {
    private final GreeterService service;

    GreeterController(GreeterService service) {
        this.service = service;
    }

    @GetMapping("/greeting/{subject}")
    @ResponseBody Greeting greet(@PathVariable String subject) {
        return service.greet(subject);
    }

    @GetMapping("/greeting")
    @ResponseBody Greeting greet() {
        return service.greet("World");
    }

}
