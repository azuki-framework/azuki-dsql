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
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * このクラスは、ダイナミックSQL情報を保持するエンティティクラスです。
 * 
 * @since 1.0.0
 * @version 1.0.0 2013/02/14
 * @author Kawakicchi
 */
public final class DSQLEntity implements Iterable<DSQLLineEntity> {

	/**
	 * Bind line pattern
	 */
	private static Pattern PTN_MATCH_BIND_LINE = Pattern.compile("^[\\s\\t]*(\\$\\{.+\\}).*$");

	/**
	 * Comment line pattern
	 */
	private static Pattern PTN_MATCH_COMMENT_LINE = Pattern.compile("^[\\s\\t]*#.*");

	/**
	 * 行情報
	 */
	private List<DSQLLineEntity> lines;

	/**
	 * コンストラクタ
	 */
	private DSQLEntity() {
		lines = new ArrayList<DSQLLineEntity>();
	}

	/**
	 * 行情報リストを取得する。
	 * 
	 * @return 行情報リスト
	 */
	public List<DSQLLineEntity> getLineList() {
		return lines;
	}

	/**
	 * SQLを取得する。
	 * <p>
	 * <ul>
	 * <li>コメントあり</li>
	 * <li>バインドキーあり</li>
	 * <li>改行コードあり</li>
	 * </ul>
	 * </p>
	 * 
	 * @return SQL
	 */
	public String getPlainSQL() {
		StringBuilder s = new StringBuilder();
		for (DSQLLineEntity line : this) {
			if (0 != s.length()) {
				s.append(System.lineSeparator());
			}
			s.append(line.getLine());
		}
		return s.toString();
	}

	public boolean isEmpty() {
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

	public static DSQLEntity getInstance(final InputStream stream, final Charset charset) throws IOException {
		return getInstance(new InputStreamReader(stream, charset));
	}

	public static DSQLEntity getInstance(final InputStreamReader aReader) throws IOException {
		DSQLEntity dsql = new DSQLEntity();

		if (null != aReader) {
			BufferedReader reader = null;
			reader = new BufferedReader(aReader);

			String line = null;
			while (null != (line = reader.readLine())) {
				DSQLLineEntity dsqll = new DSQLLineEntity();
				dsqll.setLine(line);

				if (PTN_MATCH_COMMENT_LINE.matcher(line).matches()) {
					dsqll.setComment(true);

				} else {
					dsqll.setComment(false);

					Matcher m = PTN_MATCH_BIND_LINE.matcher(line);
					if (!m.find()) {
						// SQL行
						dsqll.setSQL(trim(line));
						dsqll.setFormatSQL(trimSuffix(line));
					} else {
						// バインドSQL行
						int idxBindStart = m.start(1);
						int idxBindEnd = m.end(1);

						String trimSql = trim(line.substring(idxBindEnd));
						dsqll.setSQL(trimSql);

						String formatSql = line.substring(0, idxBindStart) + space(idxBindEnd - idxBindStart) + line.substring(idxBindEnd);
						dsqll.setFormatSQL(trimSuffix(formatSql));

						String bind = trim(line.substring(idxBindStart + 2, idxBindEnd - 1));
						int idxGrpSpr = bind.indexOf(":");

						if (0 == idxGrpSpr) {
							// ${:parameter}
							String param = trim(bind.substring(1));
							dsqll.setParameter(param);
						} else if (bind.length() - 1 == idxGrpSpr) {
							// ${group:}
							String group = trim(bind.substring(0, bind.length() - 1));
							dsqll.setGroup(group);
						} else if (-1 == idxGrpSpr) {
							// ${parameter}
							dsqll.setParameter(bind);
						} else {
							// ${group:parameter}
							String[] splt = bind.split(":");
							String group = trim(splt[0]);
							String param = trim(splt[1]);
							dsqll.setGroup(group);
							dsqll.setParameter(param);
						}
					}
				}
				dsql.lines.add(dsqll);
			}
			reader.close();

		}
		return dsql;
	}

	private static String trimPrefix(final String s) {
		String buf = s;
		while (0 < buf.length() && (buf.startsWith(" ") || buf.startsWith("\t"))) {
			buf = buf.substring(1);
		}
		return buf;
	}

	private static String trimSuffix(final String s) {
		String buf = s;
		while (0 < buf.length() && (buf.endsWith(" ") || buf.endsWith("\t"))) {
			buf = buf.substring(0, buf.length() - 1);
		}
		return buf;
	}

	private static String trim(final String s) {
		String buf = trimPrefix(s);
		return trimSuffix(buf);
	}

	private static String space(final int size) {
		StringBuilder s = new StringBuilder();
		for (int i = 0; i < size; i++) {
			s.append(" ");
		}
		return s.toString();
	}
}
