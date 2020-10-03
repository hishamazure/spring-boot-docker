 package com.hm.spring.boot.docker.demo;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@SpringBootApplication
@RestController
public class SpringBootDockerApplication {

	@GetMapping("/message")
	public String getMessage() {
		return "Welcome to Hisham Marie REST ..!!! Now ! at night again1";
	}
	

	@GetMapping("/insert")
	public String insert(HttpServletRequest request) {
		String inserted = (new SQLDatabaseConnection()).insertReord();
		
		Map<String, String> env = System.getenv();
        // Java 8
        //env.forEach((k, v) -> System.out.println(k + ":" + v));

        // Classic way to loop a map
        for (Map.Entry<String, String> entry : env.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
		
		
;		return "Requirest from IP : "+request.getRemoteAddr()+". Requesting from database North EU of record inserted: " + inserted;
	}
	
	
	@RequestMapping(value="user", method = RequestMethod.GET)
	public @ResponseBody String getItem(@RequestParam("data") String itemid){
		//access the URL :http://51.138.69.66:8080/user?data=1
		//http://52.157.222.96:8080/user?data=1
	    
	    return "The value entered is " + itemid;
	}
	
	public static void main(String[] args) {
		SpringApplication.run(SpringBootDockerApplication.class, args);
	}

}
