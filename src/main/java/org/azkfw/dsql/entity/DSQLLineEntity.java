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

import org.azkfw.util.StringUtility;

/**
 * このクラスは、ダイナミックSQL行情報を保持するエンティティクラスです。
 * 
 * @since 1.0.0
 * @version 1.0.0 2013/02/14
 * @author Kawakicchi
 */
public final class DSQLLineEntity {

	/**
	 * 未加工の行文字列
	 */
	private String line;

	/**
	 * SQL文字列(trim済み)
	 */
	private String sql;

	/**
	 * SQL文字列
	 */
	private String formatSql;

	/**
	 * コメント
	 */
	private boolean comment;

	/**
	 * グループ名
	 */
	private String group;

	/**
	 * パラメータ名
	 */
	private String parameter;

	/**
	 * コンストラクタ
	 */
	public DSQLLineEntity() {

	}

	/**
	 * 行文字列(未加工)を設定する。
	 * 
	 * @param line 行文字列
	 */
	public void setLine(final String line) {
		this.line = line;
	}

	/**
	 * 行文字列(未加工)を取得する。
	 * 
	 * @return 行文字列
	 */
	public String getLine() {
		return line;
	}

	public void setFormatSQL(final String sql) {
		formatSql = sql;
	}

	/**
	 * SQL文字列(バインドキーなし)を取得する。
	 * 
	 * @return
	 */
	public String getFormatSQL() {
		return formatSql;
	}

	/**
	 * SQL文字列(trim済み)を設定する。
	 * 
	 * @param sql
	 */
	public void setSQL(final String sql) {
		this.sql = sql;
	}

	/**
	 * SQL部分の文字列(trim済み、バインドキーなし)を取得する。
	 * 
	 * @return 文字列
	 */
	public String getSQL() {
		return sql;
	}

	public void setComment(final boolean comment) {
		this.comment = comment;
	}

	/**
	 * コメント行か判断する。
	 * 
	 * @return 判断結果
	 */
	public boolean isComment() {
		return comment;
	}

	public void setGroup(final String group) {
		this.group = group;
	}

	public String getGroup() {
		return group;
	}

	/**
	 * グループが有効か判断する。
	 * 
	 * @return 判断結果
	 */
	public boolean isGroup() {
		return StringUtility.isNotEmpty(group);
	}

	public void setParameter(final String parameter) {
		this.parameter = parameter;
	}

	public String getParameter() {
		return parameter;
	}

	/**
	 * パラメータが有効か判断する。
	 * 
	 * @return 判断結果
	 */
	public boolean isParameter() {
		return StringUtility.isNotEmpty(parameter);
	}

	public boolean isEmpty() {
		return false;
	}
}
