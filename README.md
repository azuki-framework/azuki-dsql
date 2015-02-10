azuki-dsql
==========

Azuki Framework dynamicSQL library

## 説明

## デモ

## 依存

## 使用方法

### パラメータ

    #
    # IDがidで指定されたNAMEを取得する。
    #
                    select
                        NAME
                    from
                        SAMPLE
                    where
    ${id}               ID = ?


### パラメータ(List)
    #
    # IDがidsで指定されたNAME一覧を取得する。
    #
                   select
                        NAME
                    from
                        SAMPLE
                    where
    ${ids}              ID in (?)


### グループ

    #
    # NOが指定範囲内のNAME一覧を取得する。
    #
                    select
                        NAME
                    from
                        SAMPLE
                    where
    ${GROUP1:no}        NO = ?
    ${GROUP2:}          NO 
    ${GROUP2:start}        between  ?
    ${GROUP2:end}          and      ?


## インストール
pom.xml

	<repositories>
		<repository>
			<id>AzukiFrameworkRepository</id>
			<url>https://raw.github.com/azuki-framework/maven-repository/master/</url>
			<snapshots>
				<enabled>true</enabled>
				<updatePolicy>always</updatePolicy>
			</snapshots>
		</repository>
	</repositories>

	<dependencies>
		<dependency>
			<groupId>org.azkfw</groupId>
			<artifactId>azuki-dsql</artifactId>
			<version>1.4.4</version>
		</dependency>
	</dependencies>

## ライセンス

[Apache Licence2.0](https://github.com/azuki-framework/azuki-dsql/blob/master/LICENSE)

## Author

[Azuki Framework](https://github.com/azuki-framework)

