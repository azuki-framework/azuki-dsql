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
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @since 1.0.0
 * @version 1.0.0 2015/03/28
 * @author Kawakicchi
 */
public class DynamicSQLTest extends DsqlTestCase {

	@Before
	public void before() throws IOException {
		DynamicSQLManager.getInstance().initialize();
		DynamicSQLManager.getInstance().load("ns", "/dynamicSQL01.xml", getTestContext());
	}

	@After
	public void after() {
		DynamicSQLManager.getInstance().destroy();
	}

	@Test
	public void test01() {
		DynamicSQL dsql = null;

		dsql = DynamicSQLManager.generate("ns", "test01");
		assertNotNull("インスタンス", dsql);
		assertEquals("名前空間", "ns", dsql.getNamespace());
		assertEquals("名前", "test01", dsql.getName());
		assertEquals("パラメータ数", 0, dsql.getParameters().size());
		assertEquals("実行SQL", readTestTextFile("/test01-expect-execute1.txt"), dsql.getExecuteSQL());
		assertEquals("整形SQL", readTestTextFile("/test01-expect-format1.txt", "\n"), dsql.getFormatSQL());

	}

	@Test
	public void test02() {
		DynamicSQL dsql = null;
		Parameter params = new Parameter();

		dsql = DynamicSQLManager.generate("ns", "test02");
		assertNotNull("インスタンス", dsql);
		assertEquals("名前空間", "ns", dsql.getNamespace());
		assertEquals("名前", "test02", dsql.getName());
		assertEquals("パラメータ数", 0, dsql.getParameters().size());
		assertEquals("実行SQL", readTestTextFile("/test02-expect-execute1.txt"), dsql.getExecuteSQL());
		assertEquals("整形SQL", readTestTextFile("/test02-expect-format1.txt", "\n"), dsql.getFormatSQL());

		params.clear();
		params.put("id", 1);
		dsql = DynamicSQLManager.generate("ns", "test02", params);
		assertNotNull("インスタンス", dsql);
		assertEquals("名前空間", "ns", dsql.getNamespace());
		assertEquals("名前", "test02", dsql.getName());
		assertEquals("パラメータ数", 1, dsql.getParameters().size());
		assertEquals("実行SQL", readTestTextFile("/test02-expect-execute2.txt"), dsql.getExecuteSQL());
		assertEquals("整形SQL", readTestTextFile("/test02-expect-format2.txt", "\n"), dsql.getFormatSQL());
	}

	@Test
	public void test03() {
		DynamicSQL dsql = null;
		Group group = new Group();
		Parameter params = new Parameter();

		dsql = DynamicSQLManager.generate("ns", "test03");
		assertNotNull("インスタンス", dsql);
		assertEquals("名前空間", "ns", dsql.getNamespace());
		assertEquals("名前", "test03", dsql.getName());
		assertEquals("パラメータ数", 0, dsql.getParameters().size());
		assertEquals("実行SQL", readTestTextFile("/test03-expect-execute1.txt"), dsql.getExecuteSQL());
		assertEquals("整形SQL", readTestTextFile("/test03-expect-format1.txt", "\n"), dsql.getFormatSQL());

		group.clear();
		group.add("COUNT");
		group.add("NAME");
		params.clear();
		params.put("name", "test");
		dsql = DynamicSQLManager.generate("ns", "test03", group, params);
		assertNotNull("インスタンス", dsql);
		assertEquals("名前空間", "ns", dsql.getNamespace());
		assertEquals("名前", "test03", dsql.getName());
		assertEquals("パラメータ数", 1, dsql.getParameters().size());
		assertEquals("実行SQL", readTestTextFile("/test03-expect-execute2.txt"), dsql.getExecuteSQL());
		assertEquals("整形SQL", readTestTextFile("/test03-expect-format2.txt", "\n"), dsql.getFormatSQL());

		group.clear();
		group.add("SELECT");
		group.add("NAME");
		group.add("AGE");
		params.clear();
		params.put("name", "test");
		params.put("fromAge", 10);
		params.put("toAge", 19);
		dsql = DynamicSQLManager.generate("ns", "test03", group, params);
		assertNotNull("インスタンス", dsql);
		assertEquals("名前空間", "ns", dsql.getNamespace());
		assertEquals("名前", "test03", dsql.getName());
		assertEquals("パラメータ数", 3, dsql.getParameters().size());
		assertEquals("実行SQL", readTestTextFile("/test03-expect-execute3.txt"), dsql.getExecuteSQL());
		assertEquals("整形SQL", readTestTextFile("/test03-expect-format3.txt", "\n"), dsql.getFormatSQL());
	}

	@Test
	public void test04() {
		DynamicSQL dsql = null;
		Parameter params = new Parameter();
		List<Integer> ages = new ArrayList<Integer>();

		dsql = DynamicSQLManager.generate("ns", "test04");
		assertNotNull("インスタンス", dsql);
		assertEquals("名前空間", "ns", dsql.getNamespace());
		assertEquals("名前", "test04", dsql.getName());
		assertEquals("パラメータ数", 0, dsql.getParameters().size());
		assertEquals("実行SQL", readTestTextFile("/test04-expect-execute1.txt"), dsql.getExecuteSQL());
		assertEquals("整形SQL", readTestTextFile("/test04-expect-format1.txt", "\n"), dsql.getFormatSQL());

		params.clear();
		params.put("ages", 1);
		dsql = DynamicSQLManager.generate("ns", "test04", params);
		assertNotNull("インスタンス", dsql);
		assertEquals("名前空間", "ns", dsql.getNamespace());
		assertEquals("名前", "test04", dsql.getName());
		assertEquals("パラメータ数", 1, dsql.getParameters().size());
		assertEquals("実行SQL", readTestTextFile("/test04-expect-execute2.txt"), dsql.getExecuteSQL());
		assertEquals("整形SQL", readTestTextFile("/test04-expect-format2.txt", "\n"), dsql.getFormatSQL());

		ages.add(1);
		params.clear();
		params.put("ages", ages);
		dsql = DynamicSQLManager.generate("ns", "test04", params);
		assertNotNull("インスタンス", dsql);
		assertEquals("名前空間", "ns", dsql.getNamespace());
		assertEquals("名前", "test04", dsql.getName());
		assertEquals("パラメータ数", 1, dsql.getParameters().size());
		assertEquals("実行SQL", readTestTextFile("/test04-expect-execute2.txt"), dsql.getExecuteSQL());
		assertEquals("整形SQL", readTestTextFile("/test04-expect-format2.txt", "\n"), dsql.getFormatSQL());

		ages.add(2);
		params.clear();
		params.put("ages", ages);
		dsql = DynamicSQLManager.generate("ns", "test04", params);
		assertNotNull("インスタンス", dsql);
		assertEquals("名前空間", "ns", dsql.getNamespace());
		assertEquals("名前", "test04", dsql.getName());
		assertEquals("パラメータ数", 2, dsql.getParameters().size());
		assertEquals("実行SQL", readTestTextFile("/test04-expect-execute3.txt"), dsql.getExecuteSQL());
		assertEquals("整形SQL", readTestTextFile("/test04-expect-format3.txt", "\n"), dsql.getFormatSQL());
	}
}
