package org.theberlins.citest;


import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


import org.theberlins.citest.DataGetter;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class DataGetterTest {
	
	@Autowired 
	MockMvc mvc;

	@Test
	public void testDataGetter() throws Exception{
		DataGetter dg = new DataGetter();
		mvc.perform(MockMvcRequestBuilders.get("/").accept(MediaType.ALL_VALUE))
		.andExpect(status().isOk())
		.andExpect(content().string(equalTo(dg.getMessage())));
	}
}
