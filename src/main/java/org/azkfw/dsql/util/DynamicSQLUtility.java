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
package org.azkfw.dsql.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.azkfw.dsql.DynamicSQL;

/**
 * @author Kawakicchi
 */
public final class DynamicSQLUtility {

	/**
	 * コンストラクタ
	 */
	private DynamicSQLUtility() {

	}

	/**
	 * 
	 * @param dynamicSQL ダイナミックSQL
	 * @param connection {@link Connection}
	 * @return {@link PreparedStatement}
	 * @throws SQLException SQL操作に起因する問題が発生した場合
	 */
	public static PreparedStatement prepareStatement(final DynamicSQL dynamicSQL, final Connection connection) throws SQLException {
		PreparedStatement ps = connection.prepareStatement(dynamicSQL.getExecuteSQL());
		for (int i = 1; i <= dynamicSQL.getParameters().size(); i++) {
			ps.setObject(i, dynamicSQL.getParameters().get(i - 1));
		}
		return ps;
	}
}
