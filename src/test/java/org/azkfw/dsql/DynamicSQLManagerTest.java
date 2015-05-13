/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.azkfw.dsql;

import java.io.IOException;
import java.util.List;

import org.azkfw.dsql.entity.DSQLEntity;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @since 1.0.0
 * @version 1.0.0 2015/03/27
 * @author Kawakicchi
 */
public class DynamicSQLManagerTest extends DsqlTestCase {

	@Before
	public void before() {
		DynamicSQLManager.getInstance().initialize();
	}

	@After
	public void after() {
		DynamicSQLManager.getInstance().destroy();
	}

	@Test
	public void testLoad() throws IOException {
		DynamicSQLManager.getInstance().load("/dynamicSQL01.xml", getTestContext());

	}

	@Test
	public void testNoNamespace() throws IOException {
		DynamicSQLManager.getInstance().load("/dynamicSQL01.xml", getTestContext());

		assertNotNull(DynamicSQLManager.generate("test01"));
		assertNotNull(DynamicSQLManager.generate("test02"));
		assertNotNull(DynamicSQLManager.generate("test03"));
		assertNotNull(DynamicSQLManager.generate("test04"));
		assertNull(DynamicSQLManager.generate("test99"));

		assertNull(DynamicSQLManager.generate("ns", "test01"));
		assertNull(DynamicSQLManager.generate("ns", "test02"));
		assertNull(DynamicSQLManager.generate("ns", "test03"));
		assertNull(DynamicSQLManager.generate("ns", "test04"));
		assertNull(DynamicSQLManager.generate("ns", "test99"));

		List<DSQLEntity> dsqls = DynamicSQLManager.getInstance().getDSQLEntityList();
		assertNotNull("インスタンス", dsqls);
		assertEquals("件数", 4, dsqls.size());
		DSQLEntity dsql = null;

		dsql = dsqls.get(0);
		assertEquals("名前", "test01", dsql.getName());
		dsql = dsqls.get(1);
		assertEquals("名前", "test02", dsql.getName());
		dsql = dsqls.get(2);
		assertEquals("名前", "test03", dsql.getName());
		dsql = dsqls.get(3);
		assertEquals("名前", "test04", dsql.getName());
	}

	@Test
	public void testNamespace() throws IOException {
		DynamicSQLManager.getInstance().load("ns", "/dynamicSQL01.xml", getTestContext());

		assertNull(DynamicSQLManager.generate("test01"));
		assertNull(DynamicSQLManager.generate("test02"));
		assertNull(DynamicSQLManager.generate("test03"));
		assertNull(DynamicSQLManager.generate("test04"));
		assertNull(DynamicSQLManager.generate("test99"));

		assertNotNull(DynamicSQLManager.generate("ns", "test01"));
		assertNotNull(DynamicSQLManager.generate("ns", "test02"));
		assertNotNull(DynamicSQLManager.generate("ns", "test03"));
		assertNotNull(DynamicSQLManager.generate("ns", "test04"));
		assertNull(DynamicSQLManager.generate("ns", "test99"));

		List<DSQLEntity> dsqls = DynamicSQLManager.getInstance().getDSQLEntityList("ns");
		assertNotNull("インスタンス", dsqls);
		assertEquals("件数", 4, dsqls.size());
		DSQLEntity dsql = null;

		dsql = dsqls.get(0);
		assertEquals("名前", "test01", dsql.getName());
		dsql = dsqls.get(1);
		assertEquals("名前", "test02", dsql.getName());
		dsql = dsqls.get(2);
		assertEquals("名前", "test03", dsql.getName());
		dsql = dsqls.get(3);
		assertEquals("名前", "test04", dsql.getName());
	}

}
