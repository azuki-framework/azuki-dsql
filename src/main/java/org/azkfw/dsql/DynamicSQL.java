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

import java.util.List;

/**
 * このインターフェースは、ダイナミックSQLを定義するインターフェースです。
 * 
 * @since 1.0.0
 * @version 1.0.0 2013/02/14
 * @author Kawakicchi
 */
public interface DynamicSQL {

	/**
	 * 名前空間を取得する。
	 * 
	 * @return 名前空間
	 */
	public String getNamespace();

	/**
	 * 名前を取得する。
	 * 
	 * @return 名前
	 */
	public String getName();

	/**
	 * 実行用SQLを取得する。
	 * 
	 * @return SQL
	 */
	public String getExecuteSQL();

	/**
	 * 整形済みSQLを取得する。
	 * 
	 * @return SQL
	 */
	public String getFormatSQL();

	/**
	 * パラメータを取得する。
	 * 
	 * @return パラメータ
	 */
	public List<Object> getParameters();

}
