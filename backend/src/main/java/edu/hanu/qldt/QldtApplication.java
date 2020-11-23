package edu.hanu.qldt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class QldtApplication implements CommandLineRunner {

	private final InitData initData;

	@Autowired
	public QldtApplication(InitData initData) {
		this.initData = initData;
	}

	public static void main(String[] args) {
		SpringApplication.run(QldtApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		initData.Init();
	}
}
