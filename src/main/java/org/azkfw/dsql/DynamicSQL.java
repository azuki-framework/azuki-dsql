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

import java.util.ArrayList;
import java.util.List;

import org.azkfw.dsql.entity.DSQLEntity;
import org.azkfw.dsql.entity.DSQLLineEntity;

/**
 * このクラスは、ダイナミックSQL情報を保持するクラスです。
 * 
 * @since 1.0.0
 * @version 1.0.0 2013/02/14
 * @author Kawakicchi
 */
public final class DynamicSQL {

	/**
	 * DynamicSQL名
	 */
	private String name;

	/**
	 * SQL
	 */
	private String sql;

	/**
	 * パラメータ
	 */
	private List<Object> parameters;

	/**
	 * コンストラクタ
	 * 
	 * @param aName 名前
	 * @param aSql SQL文
	 * @param aParameters パラメータ
	 */
	private DynamicSQL(final String aName, final String aSql, final List<Object> aParameters) {
		name = aName;
		sql = aSql;
		parameters = new ArrayList<Object>(aParameters);
	}

	/**
	 * 名前を取得する。
	 * 
	 * @return 名前
	 */
	public String getName() {
		return name;
	}

	/**
	 * SQL文を取得する。
	 * 
	 * @return SQL文
	 */
	public String getSQL() {
		return sql;
	}

	/**
	 * パラメータを取得する。
	 * 
	 * @return パラメータ
	 */
	public List<Object> getParameters() {
		return parameters;
	}

	/**
	 * ダイナミックSQLを生成する。
	 * 
	 * @param aName 名前
	 * @param aGroup グループ
	 * @param aParameter パラメータ
	 * @return ダイナミックSQL。ダイナミックSQLの生成に失敗した場合、<code>null</code>を返す。
	 */
	public static DynamicSQL generate(final String aName, final Group aGroup, final Parameter aParameter) {
		DynamicSQL dsql = null;
		DSQLEntity entity = DynamicSQLManager.get(aName);
		if (null != entity) {

			StringBuilder sql = new StringBuilder();
			List<Object> params = new ArrayList<Object>();
			for (DSQLLineEntity line : entity) {
				if (line.isComment()) {
					continue;
				}
				if (line.isGroup()) {
					if (null == aGroup) {
						continue;
					}
					if (!aGroup.is(line.getGroup())) {
						continue;
					}
				}
				if (line.isParameter()) {
					if (null == aParameter) {
						continue;
					}
					if (!aParameter.isKey(line.getParameter())) {
						continue;
					}
					params.add(aParameter.get(line.getParameter()));
				}
				sql.append(line.getSQL());
				sql.append(" ");
			}

			dsql = new DynamicSQL(aName, sql.toString(), params);
		}
		return dsql;
	}
}
