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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

import org.azkfw.persistence.entity.Entity;
import org.azkfw.util.StringUtility;

/**
 * このクラスは、ダイナミックSQL情報を保持するエンティティクラスです。
 * 
 * @since 1.0.0
 * @version 1.0.0 2013/02/14
 * @author Kawakicchi
 */
public final class DSQLEntity implements Entity, Iterable<DSQLLineEntity> {

	/**
	 * Dyanamic pattern
	 */
	private static Pattern PATTERN = Pattern.compile("^\\$\\{.*\\}.*$");

	/**
	 * 名前
	 */
	private String name;

	/**
	 * 行情報
	 */
	private List<DSQLLineEntity> lines;

	/**
	 * コンストラクタ
	 */
	private DSQLEntity() {
		name = null;
		lines = new ArrayList<DSQLLineEntity>();
	}

	/**
	 * 名前を設定する。
	 * 
	 * @param aName 名前
	 */
	public void setName(final String aName) {
		name = aName;
	}

	/**
	 * 名前を取得する。
	 * 
	 * @return 名前
	 */
	public String getName() {
		return name;
	}

	public void add(final DSQLLineEntity aLine) {
		lines.add(aLine);
	}

	@Override
	public boolean isEmpty() {
		if (StringUtility.isNotEmpty(name)) {
			return false;
		}
		if (0 < lines.size()) {
			return false;
		}
		return true;
	}

	@Override
	public Iterator<DSQLLineEntity> iterator() {
		return lines.iterator();
	}

	public static DSQLEntity getInstance(final File file) throws IOException {
		return getInstance(new InputStreamReader(new FileInputStream(file)));
	}

	public static DSQLEntity getInstance(final File file, final Charset charset) throws IOException {
		return getInstance(new InputStreamReader(new FileInputStream(file), charset));
	}

	public static DSQLEntity getInstance(final InputStreamReader aReader) throws IOException {
		BufferedReader reader = new BufferedReader(aReader);
		DSQLEntity dsql = new DSQLEntity();
		String line = null;
		while (null != (line = reader.readLine())) {
			String buf = line.trim();
			DSQLLineEntity dsqll = new DSQLLineEntity();
			if (buf.startsWith("#")) {
				dsqll.setComment(true);
				dsqll.setString(line);
			} else {
				if (PATTERN.matcher(buf).matches()) {
					int index = buf.indexOf("}");
					String sql = buf.substring(index + 1).trim();
					String cnt = buf.substring(2, index).trim();
					index = cnt.indexOf(":");
					if (0 == index) {
						String param = cnt.substring(1).trim();
						dsqll.setComment(false);
						dsqll.setParameter(param);
						dsqll.setSql(sql);
						dsqll.setString(line);
					} else if (cnt.length() - 1 == index) {
						String group = cnt.substring(0, cnt.length() - 1);
						dsqll.setComment(false);
						dsqll.setGroup(group);
						dsqll.setSql(sql);
						dsqll.setString(line);
					} else if (-1 == index) {
						dsqll.setComment(false);
						dsqll.setParameter(cnt);
						dsqll.setSql(sql);
						dsqll.setString(line);
					} else {
						String[] splt = cnt.split(":");
						String group = splt[0];
						String param = splt[1];
						dsqll.setComment(false);
						dsqll.setGroup(group);
						dsqll.setParameter(param);
						dsqll.setSql(sql);
						dsqll.setString(line);
					}
				} else {
					dsqll.setComment(false);
					dsqll.setSql(buf);
					dsqll.setString(line);
				}
			}
			dsql.add(dsqll);
		}
		reader.close();
		return dsql;
	}
}
