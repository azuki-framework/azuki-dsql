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
 * このクラスは、ダイナミックSQL情報を生成するビルダークラスです。
 * 
 * @since 1.0.0
 * @version 1.0.0 2015/01/08
 * @author Kawakicchi
 */
public final class DynamicSQLBuilder {

	/**
	 * コンストラクタ
	 * <p>
	 * インスタンス生成を禁止
	 * </p>
	 */
	private DynamicSQLBuilder() {

	}

	/**
	 * ダイナミックSQLを生成する。
	 * 
	 * @param name 名前
	 * @param entity エンティティ情報
	 * @return ダイナミックSQL
	 */
	public static DynamicSQL build(final String name, final DSQLEntity entity) {
		return build(null, name, entity, null, null);
	}

	/**
	 * ダイナミックSQLを生成する。
	 * 
	 * @param name 名前
	 * @param entity エンティティ情報
	 * @param group グループ情報
	 * @return ダイナミックSQL
	 */
	public static DynamicSQL build(final String name, final DSQLEntity entity, final Group group) {
		return build(null, name, entity, group, null);
	}

	/**
	 * ダイナミックSQLを生成する。
	 * 
	 * @param name 名前
	 * @param entity エンティティ情報
	 * @param parameter パラメータ情報
	 * @return ダイナミックSQL
	 */
	public static DynamicSQL build(final String name, final DSQLEntity entity, final Parameter parameter) {
		return build(null, name, entity, null, parameter);
	}

	/**
	 * ダイナミックSQLを生成する。
	 * 
	 * @param name 名前
	 * @param entity エンティティ情報
	 * @param group グループ情報
	 * @param parameter パラメータ情報
	 * @return ダイナミックSQL
	 */
	public static DynamicSQL build(final String name, final DSQLEntity entity, final Group group, final Parameter parameter) {
		return build(null, name, entity, group, parameter);
	}

	/**
	 * ダイナミックSQLを生成する。
	 * 
	 * @param namespace 名前空間
	 * @param name 名前
	 * @param entity エンティティ情報
	 * @return ダイナミックSQL
	 */
	public static DynamicSQL build(final String namespace, final String name, final DSQLEntity entity) {
		return build(namespace, name, entity, null, null);
	}

	/**
	 * ダイナミックSQLを生成する。
	 * 
	 * @param namespace 名前空間
	 * @param name 名前
	 * @param entity エンティティ情報
	 * @param group グループ情報
	 * @return ダイナミックSQL
	 */
	public static DynamicSQL build(final String namespace, final String name, final DSQLEntity entity, final Group group) {
		return build(namespace, name, entity, group, null);
	}

	/**
	 * ダイナミックSQLを生成する。
	 * 
	 * @param namespace 名前空間
	 * @param name 名前
	 * @param entity エンティティ情報
	 * @param parameter パラメータ情報
	 * @return ダイナミックSQL
	 */
	public static DynamicSQL build(final String namespace, final String name, final DSQLEntity entity, final Parameter parameter) {
		return build(namespace, name, entity, null, parameter);
	}

	/**
	 * ダイナミックSQLを生成する。
	 * 
	 * @param namespace 名前空間
	 * @param name 名前
	 * @param entity エンティティ情報
	 * @param group グループ情報
	 * @param parameter パラメータ情報
	 * @return ダイナミックSQL
	 */
	public static DynamicSQL build(final String namespace, final String name, final DSQLEntity entity, final Group group, final Parameter parameter) {
		DynamicSQL dsql = null;
		if (null != entity) {

			StringBuilder sqlExecute = new StringBuilder();
			StringBuilder sqlFormat = new StringBuilder();
			List<Object> params = new ArrayList<Object>();

			for (DSQLLineEntity line : entity) {
				String executeSql = line.getSQL();
				String formatSql = line.getFormatSQL();

				if (line.isComment()) {
					continue;
				}
				if (line.isGroup()) {
					if (null == group) {
						continue;
					}
					if (!group.is(line.getGroup())) {
						continue;
					}
				}
				if (line.isParameter()) {
					if (null == parameter) {
						continue;
					}
					if (!parameter.isKey(line.getParameter())) {
						continue;
					}
					Object obj = parameter.get(line.getParameter());
					if (obj instanceof List<?>) {
						List<?> list = (List<?>) obj;
						StringBuilder sb = new StringBuilder();
						for (int i = 0; i < list.size(); i++) {
							if (i != 0) {
								sb.append(", ");
							}
							sb.append("?");
							params.add(list.get(i));
						}

						executeSql = executeSql.replaceAll("\\?", sb.toString());
						formatSql = formatSql.replaceAll("\\?", sb.toString());
					} else {
						params.add(obj);
					}
				}

				if (0 < sqlExecute.length()) {
					sqlExecute.append(" ");
					sqlFormat.append("\n");
				}
				sqlExecute.append(executeSql);
				sqlFormat.append(formatSql);
			}

			dsql = new BasicDynamicSQL(namespace, name, sqlExecute.toString(), sqlFormat.toString(), params);
		}
		return dsql;
	}

	/**
	 * このクラスは、ダイナミックSQL情報を保持するクラスです。
	 * 
	 * @since 1.0.0
	 * @version 1.0.0 2013/02/14
	 * @author Kawakicchi
	 */
	private static final class BasicDynamicSQL implements DynamicSQL {

		/** 名前空間 */
		private String namespace;

		/** 名前 */
		private String name;

		/** 実行SQL */
		private String sqlExecute;

		/** 整形SQL */
		private String sqlFormat;

		/** パラメータ */
		private List<Object> parameters;

		/**
		 * コンストラクタ
		 * 
		 * @param namespace 名前空間
		 * @param name 名前
		 * @param execute 実行SQL
		 * @param format 整形SQL
		 * @param parameters パラメータ
		 */
		private BasicDynamicSQL(final String namespace, final String name, final String execute, final String format, final List<Object> parameters) {
			this.namespace = namespace;
			this.name = name;
			this.sqlExecute = execute;
			this.sqlFormat = format;
			this.parameters = new ArrayList<Object>(parameters);
		}

		@Override
		public String getNamespace() {
			return namespace;
		}

		@Override
		public String getName() {
			return name;
		}

		@Override
		public String getExecuteSQL() {
			return sqlExecute;
		}

		@Override
		public String getFormatSQL() {
			return sqlFormat;
		}

		@Override
		public List<Object> getParameters() {
			return parameters;
		}
	}

}
