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
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.digester3.Digester;
import org.azkfw.context.Context;
import org.azkfw.dsql.entity.DSQLEntity;
import org.azkfw.lang.LoggingObject;
import org.azkfw.util.StringUtility;
import org.xml.sax.SAXException;

/**
 * このクラスは、ダイナミックSQLの管理を行うマネージャークラスです。
 * 
 * @since 1.0.0
 * @version 1.0.0 2013/02/14
 * @author Kawakicchi
 */
public final class DynamicSQLManager extends LoggingObject {

	/**
	 * Instance
	 */
	private static final DynamicSQLManager INSTANCE = new DynamicSQLManager();

	/**
	 * ダイナミックSQL情報
	 */
	private Map<String, Map<String, DSQLEntity>> dynamicSQLs;

	/**
	 * コンストラクタ
	 * <p>
	 * インスタンス生成を禁止する。
	 * </p>
	 */
	private DynamicSQLManager() {
		super(DynamicSQLManager.class);

		dynamicSQLs = new HashMap<String, Map<String, DSQLEntity>>();
	}

	/**
	 * インスタンスを取得する。
	 * 
	 * @return インスタンス
	 */
	public static DynamicSQLManager getInstance() {
		return INSTANCE;
	}

	/**
	 * ダイナミックSQLを生成する。
	 * 
	 * @param name 名前
	 * @return ダイナミックSQL。ダイナミックSQLの生成に失敗した場合、<code>null</code>を返す。
	 */
	public static DynamicSQL generate(final String name) {
		return generate(null, name, null, null);
	}

	/**
	 * ダイナミックSQLを生成する。
	 * 
	 * @param name 名前
	 * @param group グループ
	 * @return ダイナミックSQL。ダイナミックSQLの生成に失敗した場合、<code>null</code>を返す。
	 */
	public static DynamicSQL generate(final String name, final Group group) {
		return generate(null, name, group, null);
	}

	/**
	 * ダイナミックSQLを生成する。
	 * 
	 * @param name 名前
	 * @param parameter パラメータ
	 * @return ダイナミックSQL。ダイナミックSQLの生成に失敗した場合、<code>null</code>を返す。
	 */
	public static DynamicSQL generate(final String name, final Parameter parameter) {
		return generate(null, name, null, parameter);
	}

	/**
	 * ダイナミックSQLを生成する。
	 * 
	 * @param name 名前
	 * @param group グループ
	 * @param parameter パラメータ
	 * @return ダイナミックSQL。ダイナミックSQLの生成に失敗した場合、<code>null</code>を返す。
	 */
	public static DynamicSQL generate(final String name, final Group group, final Parameter parameter) {
		return generate(null, name, group, parameter);
	}

	/**
	 * ダイナミックSQLを生成する。
	 * 
	 * @param namespace 名前空間
	 * @param name 名前
	 * @return ダイナミックSQL。ダイナミックSQLの生成に失敗した場合、<code>null</code>を返す。
	 */
	public static DynamicSQL generate(final String namespace, final String name) {
		return generate(namespace, name, null, null);
	}

	/**
	 * ダイナミックSQLを生成する。
	 * 
	 * @param namespace 名前空間
	 * @param name 名前
	 * @param group グループ
	 * @return ダイナミックSQL。ダイナミックSQLの生成に失敗した場合、<code>null</code>を返す。
	 */
	public static DynamicSQL generate(final String namespace, final String name, final Group group) {
		return generate(namespace, name, group, null);
	}

	/**
	 * ダイナミックSQLを生成する。
	 * 
	 * @param namespace 名前空間
	 * @param name 名前
	 * @param parameter パラメータ
	 * @return ダイナミックSQL。ダイナミックSQLの生成に失敗した場合、<code>null</code>を返す。
	 */
	public static DynamicSQL generate(final String namespace, final String name, final Parameter parameter) {
		return generate(namespace, name, null, parameter);
	}

	/**
	 * ダイナミックSQLを生成する。
	 * 
	 * @param namespace 名前空間
	 * @param name 名前
	 * @param group グループ
	 * @param parameter パラメータ
	 * @return ダイナミックSQL。ダイナミックSQLの生成に失敗した場合、<code>null</code>を返す。
	 */
	public static DynamicSQL generate(final String namespace, final String name, final Group group, final Parameter parameter) {
		DynamicSQL dsql = null;
		DSQLEntity entity = INSTANCE.get(namespace, name);
		if (null != entity) {
			dsql = DynamicSQLBuilder.build(namespace, name, entity, group, parameter);
		}
		return dsql;
	}

	/**
	 * 初期化処理を行なう。
	 */
	public void initialize() {
		dynamicSQLs.clear();
	}

	/**
	 * 解放処理を行なう。
	 */
	public void destroy() {
		dynamicSQLs.clear();
	}

	/**
	 * 設定をロードする。
	 * 
	 * @param config 設定ファイル
	 * @param context コンテキスト
	 * @throws IOException IO操作時に問題が発生した場合
	 */
	public void load(final String config, final Context context) throws IOException {
		doLoad(null, config, context);
	}

	/**
	 * 設定をロードする。
	 * 
	 * @param namespace 名前空間
	 * @param config 設定ファイル
	 * @param context コンテキスト
	 * @throws IOException IO操作時に問題が発生した場合
	 */
	public void load(final String namespace, final String config, final Context context) throws IOException {
		doLoad(namespace, config, context);
	}

	/**
	 * 設定をロードする。
	 * 
	 * @param stream ストリーム
	 * @param context コンテキスト
	 * @throws IOException IO操作時に問題が発生した場合
	 */
	public void load(final InputStream stream, final Context context) throws IOException {
		doLoad(null, stream, context);
	}

	/**
	 * 設定をロードする。
	 * 
	 * @param namespace 名前空間
	 * @param stream ストリーム
	 * @param context コンテキスト
	 * @throws IOException IO操作時に問題が発生した場合
	 */
	public void load(final String namespace, final InputStream stream, final Context context) throws IOException {
		doLoad(namespace, stream, context);
	}

	/**
	 * 設定をロードする。
	 * 
	 * @param namespace 名前空間
	 * @param config 設定ファイル
	 * @param context コンテキスト情報
	 * @throws IOException IO操作時に問題が発生した場合
	 */
	private void doLoad(final String namespace, final String config, final Context context) throws IOException {
		InputStream stream = context.getResourceAsStream(config);
		if (null == stream) {
			String msg = String.format("Not found dynamicSQL file.[%s]", config);
			error(msg);
			throw new IOException(msg);
		}
		doLoad(namespace, stream, context);
	}

	/**
	 * 設定をロードする。
	 * 
	 * @param namespace 名前空間
	 * @param stream 設定ファイル
	 * @param context コンテキスト情報
	 * @throws IOException IO操作時に問題が発生した場合
	 */
	private synchronized void doLoad(final String namespace, final InputStream stream, final Context context) throws IOException {
		List<DynamicSQLXMLEntity> dsqls = null;
		try {
			Digester digester = new Digester();
			digester.addObjectCreate("azuki/dynamicSQLs", ArrayList.class);
			digester.addObjectCreate("azuki/dynamicSQLs/dynamicSQL", DynamicSQLXMLEntity.class);
			digester.addSetProperties("azuki/dynamicSQLs/dynamicSQL");
			digester.addSetNext("azuki/dynamicSQLs/dynamicSQL", "add");
			dsqls = digester.parse(stream);
		} catch (SAXException ex) {
			error(ex);
			throw new IOException(ex);
		} catch (IOException ex) {
			error(ex);
			throw ex;
		}

		Map<String, DSQLEntity> dsqlsMap = null;
		if (dynamicSQLs.containsKey(namespace)) {
			dsqlsMap = dynamicSQLs.get(namespace);
		} else {
			dsqlsMap = new HashMap<String, DSQLEntity>();
			dynamicSQLs.put(namespace, dsqlsMap);
		}

		for (DynamicSQLXMLEntity entity : dsqls) {
			info(String.format("DynamicSQL loading.[ns:%s, name:%s]", s(namespace), entity.getName()));

			if (dsqlsMap.containsKey(entity.getName())) {
				error(String.format("Duplicate dynamicSQL name.[ns:%s, name:%s]", s(namespace), entity.getName()));
				continue;
			}

			InputStream is = context.getResourceAsStream(entity.getFile());
			if (null == is) {
				String msg = String.format("Not found dynamicSQL file.[ns:%s, name:%s, path:%s]", s(namespace), entity.getName(), entity.getFile());
				error(msg);
				throw new IOException(msg);
			}

			DSQLEntity dsql = DSQLEntity.getInstance(new InputStreamReader(is));
			dsqlsMap.put(entity.getName(), dsql);

			StringBuilder msg = new StringBuilder();
			msg.append(String.format("DynamicSQL : %s", entity.getName()));
			msg.append(System.lineSeparator());
			msg.append(dsql.getPlainSQL());

			debug(msg.toString());
		}
	}

	/**
	 * ダイナミックSQLを生成する。
	 * 
	 * @param namespace 名前空間
	 * @param name 名前
	 * @return ダイナミックSQLエンティティ
	 */
	private DSQLEntity get(final String namespace, final String name) {
		DSQLEntity entity = null;
		if (dynamicSQLs.containsKey(namespace)) {
			Map<String, DSQLEntity> dsqls = dynamicSQLs.get(namespace);
			if (dsqls.containsKey(name)) {
				entity = dsqls.get(name);
			}
		}
		return entity;
	}

	private static String s(final String string) {
		if (null == string) {
			return "";
		}
		return string;
	}

	/**
	 * このクラスは、ダイナミックSQL情報を保持するエンティティクラスです。
	 * 
	 * @since 1.0.0
	 * @version 1.0.0 2013/02/15
	 * @author Kawakicchi
	 */
	public static class DynamicSQLXMLEntity {

		/**
		 * ダイナミックSQL名
		 */
		private String name;

		/**
		 * ダイナミックSQLファイル
		 */
		private String file;

		/**
		 * コンストラクタ
		 */
		public DynamicSQLXMLEntity() {

		}

		/**
		 * ダイナミックSQL名を設定する。
		 * 
		 * @param name
		 */
		public void setName(final String name) {
			this.name = name;
		}

		/**
		 * ダイナミックSQL名を取得する。
		 * 
		 * @return
		 */
		public String getName() {
			return name;
		}

		/**
		 * ダイナミックSQLファイルを設定する。
		 * 
		 * @param aFile ファイル
		 */
		public void setFile(final String file) {
			this.file = file;
		}

		/**
		 * ダイナミックSQLファイルを取得する。
		 * 
		 * @return ファイル
		 */
		public String getFile() {
			return file;
		}

		public boolean isEmpty() {
			if (StringUtility.isNotEmpty(name)) {
				return false;
			}
			if (StringUtility.isNotEmpty(file)) {
				return false;
			}
			return true;
		}
	}

}
