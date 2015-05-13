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
package org.azkfw.dsql.entity;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

import org.azkfw.dsql.DsqlTestCase;
import org.junit.Test;

/**
 * @since 1.0.0
 * @version 1.0.0 2015/03/24
 * @author Kawakicchi
 */
public class DSQLEntityTest extends DsqlTestCase {

	@Test
	public void test01() throws IOException {
		DSQLEntity entity = DSQLEntity.getInstance("test01", this.getClass().getResourceAsStream("/test01.txt"), Charset.forName("UTF-8"));

		assertFalse("Emptyチェック", entity.isEmpty());
		assertEquals("名前", "test01", entity.getName());

		assertEquals("SQL", readTestTextFile("/test01-expect.txt"), entity.getPlainSQL());
		
		List<DSQLLineEntity> lines = entity.getLineList();
		assertNotNull("インスタンス", lines);
		assertEquals("行数", 5, lines.size());

		DSQLLineEntity line = null;

		line = lines.get(0);
		assertTrue("コメント", line.isComment());
		assertFalse("グループ", line.isGroup());
		assertFalse("パラメータ", line.isParameter());
		line = lines.get(1);
		assertFalse("コメント", line.isComment());
		assertFalse("グループ", line.isGroup());
		assertFalse("パラメータ", line.isParameter());
		line = lines.get(2);
		assertFalse("コメント", line.isComment());
		assertFalse("グループ", line.isGroup());
		assertFalse("パラメータ", line.isParameter());
		line = lines.get(3);
		assertFalse("コメント", line.isComment());
		assertFalse("グループ", line.isGroup());
		assertFalse("パラメータ", line.isParameter());
		line = lines.get(4);
		assertEquals("コメント", false, line.isComment());
		assertEquals("グループ", false, line.isGroup());
		assertEquals("パラメータ", false, line.isParameter());
	}

	@Test
	public void test02() throws IOException {
		DSQLEntity entity = DSQLEntity.getInstance("test02",this.getClass().getResourceAsStream("/test02.txt"), Charset.forName("UTF-8"));

		assertFalse("Emptyチェック", entity.isEmpty());
		assertEquals("名前", "test02", entity.getName());

		assertEquals("SQL", readTestTextFile("/test02-expect.txt"), entity.getPlainSQL());

		List<DSQLLineEntity> lines = entity.getLineList();
		assertNotNull("インスタンス", lines);
		assertEquals("行数", 8, lines.size());

		DSQLLineEntity line = null;

		line = lines.get(0);
		assertTrue("コメント", line.isComment());
		assertFalse("グループ", line.isGroup());
		assertFalse("パラメータ", line.isParameter());
		line = lines.get(1);
		assertFalse("コメント", line.isComment());
		assertFalse("グループ", line.isGroup());
		assertFalse("パラメータ", line.isParameter());
		line = lines.get(2);
		assertFalse("コメント", line.isComment());
		assertFalse("グループ", line.isGroup());
		assertFalse("パラメータ", line.isParameter());
		line = lines.get(3);
		assertFalse("コメント", line.isComment());
		assertFalse("グループ", line.isGroup());
		assertFalse("パラメータ", line.isParameter());
		line = lines.get(4);
		assertFalse("コメント", line.isComment());
		assertFalse("グループ", line.isGroup());
		assertFalse("パラメータ", line.isParameter());
		line = lines.get(5);
		assertFalse("コメント", line.isComment());
		assertFalse("グループ", line.isGroup());
		assertFalse("パラメータ", line.isParameter());
		line = lines.get(6);
		assertFalse("コメント", line.isComment());
		assertFalse("グループ", line.isGroup());
		assertFalse("パラメータ", line.isParameter());
		line = lines.get(7);
		assertFalse("コメント", line.isComment());
		assertFalse("グループ", line.isGroup());
		assertTrue("パラメータ", line.isParameter());
		assertEquals("パラメータキー", "id", line.getParameter());
	}

	@Test
	public void test03() throws IOException {
		DSQLEntity entity = DSQLEntity.getInstance("test03",this.getClass().getResourceAsStream("/test03.txt"), Charset.forName("UTF-8"));

		assertFalse("Emptyチェック", entity.isEmpty());
		assertEquals("名前", "test03", entity.getName());

		assertEquals("SQL", readTestTextFile("/test03-expect.txt"), entity.getPlainSQL());

		List<DSQLLineEntity> lines = entity.getLineList();
		assertNotNull("インスタンス", lines);
		assertEquals("行数", 12, lines.size());

		DSQLLineEntity line = null;

		line = lines.get(0);
		assertTrue("コメント", line.isComment());
		assertFalse("グループ", line.isGroup());
		assertFalse("パラメータ", line.isParameter());
		line = lines.get(1);
		assertFalse("コメント", line.isComment());
		assertFalse("グループ", line.isGroup());
		assertFalse("パラメータ", line.isParameter());
		line = lines.get(2);
		assertFalse("コメント", line.isComment());
		assertTrue("グループ", line.isGroup());
		assertFalse("パラメータ", line.isParameter());
		assertEquals("グループキー", "COUNT", line.getGroup());
		line = lines.get(3);
		assertFalse("コメント", line.isComment());
		assertTrue("グループ", line.isGroup());
		assertFalse("パラメータ", line.isParameter());
		assertEquals("グループキー", "SELECT", line.getGroup());
		line = lines.get(4);
		assertFalse("コメント", line.isComment());
		assertTrue("グループ", line.isGroup());
		assertFalse("パラメータ", line.isParameter());
		assertEquals("グループキー", "SELECT", line.getGroup());
		line = lines.get(5);
		assertFalse("コメント", line.isComment());
		assertFalse("グループ", line.isGroup());
		assertFalse("パラメータ", line.isParameter());
		line = lines.get(6);
		assertFalse("コメント", line.isComment());
		assertFalse("グループ", line.isGroup());
		assertFalse("パラメータ", line.isParameter());
		line = lines.get(7);
		assertFalse("コメント", line.isComment());
		assertFalse("グループ", line.isGroup());
		assertFalse("パラメータ", line.isParameter());
		line = lines.get(8);
		assertFalse("コメント", line.isComment());
		assertFalse("グループ", line.isGroup());
		assertTrue("パラメータ", line.isParameter());
		assertEquals("パラメータキー", "name", line.getParameter());
		line = lines.get(9);
		assertFalse("コメント", line.isComment());
		assertTrue("グループ", line.isGroup());
		assertFalse("パラメータ", line.isParameter());
		assertEquals("グループキー", "AGE", line.getGroup());
		line = lines.get(10);
		assertFalse("コメント", line.isComment());
		assertTrue("グループ", line.isGroup());
		assertTrue("パラメータ", line.isParameter());
		assertEquals("グループキー", "AGE", line.getGroup());
		assertEquals("パラメータキー", "fromAge", line.getParameter());
		line = lines.get(11);
		assertFalse("コメント", line.isComment());
		assertTrue("グループ", line.isGroup());
		assertTrue("パラメータ", line.isParameter());
		assertEquals("グループキー", "AGE", line.getGroup());
		assertEquals("パラメータキー", "toAge", line.getParameter());
	}

	@Test
	public void test04() throws IOException {
		DSQLEntity entity = DSQLEntity.getInstance("test04",this.getClass().getResourceAsStream("/test04.txt"), Charset.forName("UTF-8"));

		assertFalse("Emptyチェック", entity.isEmpty());
		assertEquals("名前", "test04", entity.getName());

		assertEquals("SQL", readTestTextFile("/test04-expect.txt"), entity.getPlainSQL());

		List<DSQLLineEntity> lines = entity.getLineList();
		assertNotNull("インスタンス", lines);
		assertEquals("行数", 8, lines.size());

		DSQLLineEntity line = null;

		line = lines.get(0);
		assertTrue("コメント", line.isComment());
		assertFalse("グループ", line.isGroup());
		assertFalse("パラメータ", line.isParameter());
		line = lines.get(1);
		assertFalse("コメント", line.isComment());
		assertFalse("グループ", line.isGroup());
		assertFalse("パラメータ", line.isParameter());
		line = lines.get(2);
		assertFalse("コメント", line.isComment());
		assertFalse("グループ", line.isGroup());
		assertFalse("パラメータ", line.isParameter());
		line = lines.get(3);
		assertFalse("コメント", line.isComment());
		assertFalse("グループ", line.isGroup());
		assertFalse("パラメータ", line.isParameter());
		line = lines.get(4);
		assertFalse("コメント", line.isComment());
		assertFalse("グループ", line.isGroup());
		assertFalse("パラメータ", line.isParameter());
		line = lines.get(5);
		assertFalse("コメント", line.isComment());
		assertFalse("グループ", line.isGroup());
		assertFalse("パラメータ", line.isParameter());
		line = lines.get(6);
		assertFalse("コメント", line.isComment());
		assertFalse("グループ", line.isGroup());
		assertFalse("パラメータ", line.isParameter());
		line = lines.get(7);
		assertFalse("コメント", line.isComment());
		assertFalse("グループ", line.isGroup());
		assertTrue("パラメータ", line.isParameter());
		assertEquals("パラメータキー", "ages", line.getParameter());
	}

	@Test
	public void test10() throws IOException {
		DSQLEntity entity = DSQLEntity.getInstance("test10",this.getClass().getResourceAsStream("/test10.txt"), Charset.forName("UTF-8"));

		List<DSQLLineEntity> lines = entity.getLineList();
		assertNotNull("インスタンス", lines);
		assertEquals("名前", "test10", entity.getName());
		//assertEquals("行数", 8, lines.size());

		DSQLLineEntity line = null;
		// comment
		line = lines.get(0);
		assertTrue("コメント", line.isComment());
		assertFalse("グループ", line.isGroup());
		assertFalse("パラメータ", line.isParameter());
		line = lines.get(1);
		assertTrue("コメント", line.isComment());
		assertFalse("グループ", line.isGroup());
		assertFalse("パラメータ", line.isParameter());
		line = lines.get(2);
		assertTrue("コメント", line.isComment());
		assertFalse("グループ", line.isGroup());
		assertFalse("パラメータ", line.isParameter());
		line = lines.get(3);
		assertTrue("コメント", line.isComment());
		assertFalse("グループ", line.isGroup());
		assertFalse("パラメータ", line.isParameter());
		line = lines.get(4);
		assertTrue("コメント", line.isComment());
		assertFalse("グループ", line.isGroup());
		assertFalse("パラメータ", line.isParameter());
		line = lines.get(5);
		assertTrue("コメント", line.isComment());
		assertFalse("グループ", line.isGroup());
		assertFalse("パラメータ", line.isParameter());
		// sql
		line = lines.get(6);
		assertFalse("コメント", line.isComment());
		assertFalse("グループ", line.isGroup());
		assertFalse("パラメータ", line.isParameter());
		line = lines.get(7);
		assertFalse("コメント", line.isComment());
		assertFalse("グループ", line.isGroup());
		assertFalse("パラメータ", line.isParameter());
		line = lines.get(8);
		assertFalse("コメント", line.isComment());
		assertFalse("グループ", line.isGroup());
		assertFalse("パラメータ", line.isParameter());
		line = lines.get(9);
		assertFalse("コメント", line.isComment());
		assertFalse("グループ", line.isGroup());
		assertFalse("パラメータ", line.isParameter());
		line = lines.get(10);
		assertFalse("コメント", line.isComment());
		assertFalse("グループ", line.isGroup());
		assertFalse("パラメータ", line.isParameter());
		// group
		line = lines.get(11);
		assertFalse("コメント", line.isComment());
		assertTrue("グループ", line.isGroup());
		assertFalse("パラメータ", line.isParameter());
		assertEquals("グループキー", "group", line.getGroup());
		line = lines.get(12);
		assertFalse("コメント", line.isComment());
		assertTrue("グループ", line.isGroup());
		assertFalse("パラメータ", line.isParameter());
		assertEquals("グループキー", "group", line.getGroup());
		line = lines.get(13);
		assertFalse("コメント", line.isComment());
		assertTrue("グループ", line.isGroup());
		assertFalse("パラメータ", line.isParameter());
		assertEquals("グループキー", "group", line.getGroup());
		line = lines.get(14);
		assertFalse("コメント", line.isComment());
		assertTrue("グループ", line.isGroup());
		assertFalse("パラメータ", line.isParameter());
		assertEquals("グループキー", "group", line.getGroup());
		line = lines.get(15);
		assertFalse("コメント", line.isComment());
		assertTrue("グループ", line.isGroup());
		assertFalse("パラメータ", line.isParameter());
		assertEquals("グループキー", "group", line.getGroup());
		line = lines.get(16);
		assertFalse("コメント", line.isComment());
		assertTrue("グループ", line.isGroup());
		assertFalse("パラメータ", line.isParameter());
		assertEquals("グループキー", "group", line.getGroup());
		line = lines.get(17);
		assertFalse("コメント", line.isComment());
		assertTrue("グループ", line.isGroup());
		assertFalse("パラメータ", line.isParameter());
		assertEquals("グループキー", "group", line.getGroup());
		// param
		line = lines.get(19);
		assertFalse("コメント", line.isComment());
		assertFalse("グループ", line.isGroup());
		assertTrue("パラメータ", line.isParameter());
		assertEquals("パラメータキー", "param", line.getParameter());
		line = lines.get(20);
		assertFalse("コメント", line.isComment());
		assertFalse("グループ", line.isGroup());
		assertTrue("パラメータ", line.isParameter());
		assertEquals("パラメータキー", "param", line.getParameter());
		line = lines.get(21);
		assertFalse("コメント", line.isComment());
		assertFalse("グループ", line.isGroup());
		assertTrue("パラメータ", line.isParameter());
		assertEquals("パラメータキー", "param", line.getParameter());
		line = lines.get(22);
		assertFalse("コメント", line.isComment());
		assertFalse("グループ", line.isGroup());
		assertTrue("パラメータ", line.isParameter());
		assertEquals("パラメータキー", "param", line.getParameter());
		line = lines.get(23);
		assertFalse("コメント", line.isComment());
		assertFalse("グループ", line.isGroup());
		assertTrue("パラメータ", line.isParameter());
		assertEquals("パラメータキー", "param", line.getParameter());
		line = lines.get(24);
		assertFalse("コメント", line.isComment());
		assertFalse("グループ", line.isGroup());
		assertTrue("パラメータ", line.isParameter());
		assertEquals("パラメータキー", "param", line.getParameter());
		line = lines.get(25);
		assertFalse("コメント", line.isComment());
		assertFalse("グループ", line.isGroup());
		assertTrue("パラメータ", line.isParameter());
		assertEquals("パラメータキー", "param", line.getParameter());
		// param
		line = lines.get(27);
		assertFalse("コメント", line.isComment());
		assertFalse("グループ", line.isGroup());
		assertTrue("パラメータ", line.isParameter());
		assertEquals("パラメータキー", "param", line.getParameter());
		line = lines.get(28);
		assertFalse("コメント", line.isComment());
		assertFalse("グループ", line.isGroup());
		assertTrue("パラメータ", line.isParameter());
		assertEquals("パラメータキー", "param", line.getParameter());
		line = lines.get(29);
		assertFalse("コメント", line.isComment());
		assertFalse("グループ", line.isGroup());
		assertTrue("パラメータ", line.isParameter());
		assertEquals("パラメータキー", "param", line.getParameter());
		line = lines.get(30);
		assertFalse("コメント", line.isComment());
		assertFalse("グループ", line.isGroup());
		assertTrue("パラメータ", line.isParameter());
		assertEquals("パラメータキー", "param", line.getParameter());
		line = lines.get(31);
		assertFalse("コメント", line.isComment());
		assertFalse("グループ", line.isGroup());
		assertTrue("パラメータ", line.isParameter());
		assertEquals("パラメータキー", "param", line.getParameter());
		line = lines.get(32);
		assertFalse("コメント", line.isComment());
		assertFalse("グループ", line.isGroup());
		assertTrue("パラメータ", line.isParameter());
		assertEquals("パラメータキー", "param", line.getParameter());
		line = lines.get(33);
		assertFalse("コメント", line.isComment());
		assertFalse("グループ", line.isGroup());
		assertTrue("パラメータ", line.isParameter());
		assertEquals("パラメータキー", "param", line.getParameter());
		// group and param
		line = lines.get(35);
		assertFalse("コメント", line.isComment());
		assertTrue("グループ", line.isGroup());
		assertTrue("パラメータ", line.isParameter());
		assertEquals("グループキー", "group", line.getGroup());
		assertEquals("パラメータキー", "param", line.getParameter());
		line = lines.get(36);
		assertFalse("コメント", line.isComment());
		assertTrue("グループ", line.isGroup());
		assertTrue("パラメータ", line.isParameter());
		assertEquals("グループキー", "group", line.getGroup());
		assertEquals("パラメータキー", "param", line.getParameter());
		line = lines.get(37);
		assertFalse("コメント", line.isComment());
		assertTrue("グループ", line.isGroup());
		assertTrue("パラメータ", line.isParameter());
		assertEquals("グループキー", "group", line.getGroup());
		assertEquals("パラメータキー", "param", line.getParameter());
		line = lines.get(38);
		assertFalse("コメント", line.isComment());
		assertTrue("グループ", line.isGroup());
		assertTrue("パラメータ", line.isParameter());
		assertEquals("グループキー", "group", line.getGroup());
		assertEquals("パラメータキー", "param", line.getParameter());
		line = lines.get(39);
		assertFalse("コメント", line.isComment());
		assertTrue("グループ", line.isGroup());
		assertTrue("パラメータ", line.isParameter());
		assertEquals("グループキー", "group", line.getGroup());
		assertEquals("パラメータキー", "param", line.getParameter());
		line = lines.get(40);
		assertFalse("コメント", line.isComment());
		assertTrue("グループ", line.isGroup());
		assertTrue("パラメータ", line.isParameter());
		assertEquals("グループキー", "group", line.getGroup());
		assertEquals("パラメータキー", "param", line.getParameter());
		line = lines.get(41);
		assertFalse("コメント", line.isComment());
		assertTrue("グループ", line.isGroup());
		assertTrue("パラメータ", line.isParameter());
		assertEquals("グループキー", "group", line.getGroup());
		assertEquals("パラメータキー", "param", line.getParameter());
	}
}
