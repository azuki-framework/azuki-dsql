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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
	public DSQLEntity() {
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
}
