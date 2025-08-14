package controller;


import entity.ReverseIp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import repository.ReverseIpRepository;

import jakarta.servlet.http.HttpServletRequest;

import java.time.LocalDateTime;

@RestController
public class IpController {

    private static final Logger logger = LoggerFactory.getLogger(IpController.class);

    @Autowired
    private ReverseIpRepository repository;

    @GetMapping("/")
    @Retryable(maxAttempts = 3, backoff = @Backoff(delay = 1000))
    public String reverseIp(HttpServletRequest request) {
        String clientIp = request.getHeader("X-Forwarded-For") != null ?
                request.getHeader("X-Forwarded-For").split(",")[0].trim() : request.getRemoteAddr();
        if (clientIp ==  null || clientIp.isEmpty()) {
            logger.error("No Ip found, try again");
            return  "Error: No IP found";
        }

        String reverseIp = String.join(".", new StringBuilder(clientIp)
                .reverse().toString().split("\\."));

        ReverseIp entity = new ReverseIp();
        entity.setIp(reverseIp);
        entity.setCreated_at(LocalDateTime.now());
        repository.save(entity);

        logger.info("{}", new Object() {
            public String client_Ip = clientIp;
            public String reversed_ip = reverseIp;
        });

        return reverseIp;
    }
}
