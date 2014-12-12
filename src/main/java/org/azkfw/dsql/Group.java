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

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * このクラスは、グループ情報を保持するクラスです。
 * 
 * @since 1.0.0
 * @version 1.0.0 2013/02/14
 * @author Kawakicchi
 */
public final class Group {

	/**
	 * group set
	 */
	private Set<String> groups;

	/**
	 * コンストラクタ
	 */
	public Group() {
		groups = new HashSet<String>();
	}

	/**
	 * コンストラクタ
	 * 
	 * @param aGroups グループ
	 */
	public Group(final String... aGroups) {
		groups = new HashSet<String>();
		for (String group : aGroups) {
			groups.add(group);
		}
	}

	/**
	 * コンストラクタ
	 * 
	 * @param aGroups グループ
	 */
	public Group(final List<String> aGroups) {
		groups = new HashSet<String>();
		for (String group : aGroups) {
			groups.add(group);
		}
	}

	/**
	 * コンストラクタ
	 * 
	 * @param aGroup グループ
	 */
	public Group(final Group aGroup) {
		for (String group : aGroup.groups) {
			groups.add(group);
		}
	}

	/**
	 * グループを追加する。
	 * 
	 * @param aGroup グループ
	 */
	public void add(final String aGroup) {
		groups.add(aGroup);
	}

	/**
	 * グループが存在するか判断する。
	 * 
	 * @param aGroup グループ
	 * @return グループが存在する場合、<code>true</code>を返す。
	 */
	public boolean is(final String aGroup) {
		return groups.contains(aGroup);
	}

	/**
	 * グループをクリアする。
	 */
	public void clear() {
		groups.clear();
	}
}
