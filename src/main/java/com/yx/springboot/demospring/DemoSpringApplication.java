package com.yx.springboot.demospring;

import com.yx.springboot.demospring.model.Person;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@Controller
public class DemoSpringApplication {

	@RequestMapping("/")
	public String index(Model model){
		Person single = new Person("aa", 11);
		List<Person> list = new ArrayList<>();
		list.add(new Person("bb",22));
		list.add(new Person("cc",33));
		list.add(new Person("dd",44));
		model.addAttribute("singlePerson", single);
		model.addAttribute("people", list);
		return "index";
	}

	public static void main(String[] args) {
		SpringApplication.run(DemoSpringApplication.class, args);
	}
}
