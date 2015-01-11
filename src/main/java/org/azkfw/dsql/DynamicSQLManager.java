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
import org.azkfw.persistence.entity.Entity;
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
	private Map<String, DSQLEntity> dynamicSQLs = new HashMap<String, DSQLEntity>();

	/**
	 * コンストラクタ
	 * <p>
	 * インスタンス生成を禁止する。
	 * </p>
	 */
	private DynamicSQLManager() {
		super(DynamicSQLManager.class);
	}

	/**
	 * 初期化処理を行なう。
	 */
	public static void initialize() {

	}

	/**
	 * 解放処理を行なう。
	 */
	public static void destroy() {

	}

	/**
	 * 設定をロードする。
	 * 
	 * @param aConfig 設定ファイル
	 * @param aContext コンテキスト
	 * @throws IOException IO操作時に問題が発生した場合
	 */
	public static void load(final String aConfig, final Context aContext) throws IOException {
		INSTANCE.doLoad(aConfig, aContext);
	}

	/**
	 * 設定をロードする。
	 * 
	 * @param aStream ストリーム
	 * @param aContext コンテキスト
	 * @throws IOException IO操作時に問題が発生した場合
	 */
	public static void load(final InputStream aStream, final Context aContext) throws IOException {
		INSTANCE.doLoad(aStream, aContext);
	}

	/**
	 * ダイナミックSQLを生成する。
	 * 
	 * @param aName 名前
	 * @return ダイナミックSQL
	 */
	public static DSQLEntity get(final String aName) {
		return INSTANCE.doGet(aName);
	}

	/**
	 * 設定をロードする。
	 * 
	 * @param aConfig 設定ファイル
	 * @param aContext コンテキスト
	 * @throws IOException IO操作時に問題が発生した場合
	 */
	private void doLoad(final String aConfig, final Context aContext) throws IOException {
		InputStream stream = aContext.getResourceAsStream(aConfig);
		if (null == stream) {
			error("Not found dynamicSQL file.[" + aConfig + "]");
			throw new IOException("Not found dynamicSQL file.[" + aConfig + "]");
		}
		doLoad(stream, aContext);
	}

	/**
	 * 設定をロードする。
	 * 
	 * @param aStream 設定ファイル
	 * @param aContext コンテキスト
	 * @throws IOException IO操作時に問題が発生した場合
	 */
	private void doLoad(final InputStream aStream, final Context aContext) throws IOException {
		List<DynamicSQLEntity> dsqls = null;
		try {
			Digester digester = new Digester();
			digester.addObjectCreate("azuki/dynamicSQLs", ArrayList.class);
			digester.addObjectCreate("azuki/dynamicSQLs/dynamicSQL", DynamicSQLEntity.class);
			digester.addSetProperties("azuki/dynamicSQLs/dynamicSQL");
			digester.addSetNext("azuki/dynamicSQLs/dynamicSQL", "add");
			dsqls = digester.parse(aStream);
		} catch (SAXException ex) {
			error(ex);
			throw new IOException(ex);
		} catch (IOException ex) {
			error(ex);
			throw ex;
		}

		for (DynamicSQLEntity entity : dsqls) {
			info("DynamicSQL loading.[" + entity.getName() + "]");
			if (dynamicSQLs.containsKey(entity.getName())) {
				error("Duplicate dynamicSQL name.[" + entity.getName() + "]");
				continue;
			}
			InputStream is = aContext.getResourceAsStream(entity.getFile());
			if (null != is) {
				DSQLEntity dsql = DSQLEntity.getInstance(new InputStreamReader(is));
				dynamicSQLs.put(entity.getName(), dsql);
			} else {
				error("Not found dynamicSQL file.[" + entity.getFile() + "]");
				throw new IOException("Not found dynamicSQL file.[" + entity.getFile() + "]");
			}
		}
	}

	/**
	 * ダイナミックSQLを生成する。
	 * 
	 * @param aName 名前
	 * @param aGroup グループ
	 * @param aParameter パラメーター
	 * @return ダイナミックSQL
	 */
	private DSQLEntity doGet(final String aName) {
		return dynamicSQLs.get(aName);
	}

	/**
	 * このクラスは、ダイナミックSQL情報を保持するエンティティクラスです。
	 * 
	 * @since 1.0.0
	 * @version 1.0.0 2013/02/15
	 * @author Kawakicchi
	 */
	public static class DynamicSQLEntity implements Entity {

		private String name;

		private String file;

		public DynamicSQLEntity() {

		}

		public void setName(final String aName) {
			name = aName;
		}

		public String getName() {
			return name;
		}

		public void setFile(final String aFile) {
			file = aFile;
		}

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
