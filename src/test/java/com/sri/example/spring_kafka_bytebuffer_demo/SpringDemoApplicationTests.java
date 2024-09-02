package com.sri.example.spring_kafka_bytebuffer_demo;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.kafka.test.context.EmbeddedKafka;

import java.util.List;

import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;
import static org.awaitility.Durations.TEN_SECONDS;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@EmbeddedKafka
@SpringBootTest(webEnvironment = RANDOM_PORT)
class SpringDemoApplicationTests {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate testRestTemplate;

	@Autowired
	private List<MyKafkaModel> myKafkaModels;

	@Test
	@SneakyThrows
	void send() {

		testRestTemplate.put(
			format("http://localhost:%s/send", port),
			null
		);

		await()
			.atMost(TEN_SECONDS)
			.ignoreExceptions()
			.until(() -> {
				assertThat(myKafkaModels).hasSize(1);
				return true;
			});
	}
}
