package org.theberlins.citest;


import org.junit.jupiter.api.*;

import org.theberlins.citest.DataGetter;

@DisplayName("Data Getter Tester")
class DataGetterTest {

	@Test
	void testDataGetter(){
		DataGetter dg = new DataGetter();
		System.out.println(dg.getMessage());
	}
}
