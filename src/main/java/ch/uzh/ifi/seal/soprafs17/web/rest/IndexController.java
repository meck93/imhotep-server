package ch.uzh.ifi.seal.soprafs17.web.rest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class IndexController {

	private static final String hello = "Let's start SoPra 2017! Group 09: For the Win!";

	@RequestMapping(value="/")
	@ResponseBody
	public String index() {
		return hello;
	}
}