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

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * このクラスは、DynamicSQLパラメータ情報を保持するクラスです。
 * 
 * @since 1.0.0
 * @version 1.0.0 2013/02/14
 * @author Kawakicchi
 */
public final class Parameter {

	/**
	 * parameter map
	 */
	private Map<String, Object> params;

	/**
	 * コンストラクタ
	 */
	public Parameter() {
		params = new HashMap<String, Object>();
	}

	/**
	 * コンストラクタ
	 * 
	 * @param parameter パラメータ
	 */
	public Parameter(final Map<String, Object> parameter) {
		params = new HashMap<String, Object>(parameter);
	}

	/**
	 * コンストラクタ
	 * 
	 * @param parameter パラメータ
	 */
	public Parameter(final Parameter parameter) {
		params = new HashMap<String, Object>(parameter.params);
	}

	/**
	 * パラメータが存在するか判断する。
	 * 
	 * @param key キー
	 * @return パラメータが存在する場合、<code>true</code>を返す。
	 */
	public boolean isKey(final String key) {
		return params.containsKey(key);
	}

	/**
	 * パラメータを設定する。
	 * 
	 * @param key キー
	 * @param value 値
	 */
	public void put(final String key, final Object value) {
		params.put(key, value);
	}

	/**
	 * パラメータを取得する。
	 * 
	 * @param key キー
	 * @return 値
	 */
	public Object get(final String key) {
		return params.get(key);
	}

	/**
	 * パラメータを削除する
	 * 
	 * @param key キー
	 */
	public void remove(final String key) {
		params.remove(key);
	}

	/**
	 * パラメータをクリアする。
	 */
	public void clear() {
		params.clear();
	}

	/**
	 * パラメータのキーセットを取得する。
	 * 
	 * @return キーセット
	 */
	public Set<String> keySet() {
		return params.keySet();
	}
}
