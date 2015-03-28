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

import org.azkfw.plugin.AbstractPlugin;
import org.azkfw.plugin.PluginServiceException;

/**
 * このクラスは、ダイナミックSQL機能をサポートするためのプラグインクラスです。
 * 
 * @since 1.0.0
 * @version 1.0.0 2013/02/05
 * @author Kawakicchi
 */
public final class DynamicSQLPlugin extends AbstractPlugin {

	/**
	 * コンストラクタ
	 */
	public DynamicSQLPlugin() {
	}

	@Override
	protected void doInitialize() throws PluginServiceException {
		DynamicSQLManager.getInstance().initialize();
	}

	@Override
	protected void doDestroy() throws PluginServiceException {
		DynamicSQLManager.getInstance().destroy();
	}

	@Override
	protected void doLoad() throws PluginServiceException, IOException {
		DynamicSQLManager.getInstance().load(getConfiguration().getResourceAsStream(), getContext());
	}

}
