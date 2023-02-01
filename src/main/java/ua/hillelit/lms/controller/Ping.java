package ua.hillelit.lms.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * {@link Ping} is a class for making restful web service.
 * Main path "/ping" and port "8080".
 *
 * @author Dmytro Trotsenko on 2/1/23
 */

@RestController
@RequestMapping("ping")
@RequiredArgsConstructor
public class Ping {

    /**
     * GET request for getting ping status.
     *
     * @return ping status
     */
    @GetMapping
    public String ping() {
        return "OK";
    }

}
