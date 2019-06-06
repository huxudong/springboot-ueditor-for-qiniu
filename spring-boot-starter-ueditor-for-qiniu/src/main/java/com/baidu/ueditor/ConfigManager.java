package com.baidu.ueditor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baidu.ueditor.define.ActionMap;

import java.util.HashMap;
import java.util.Map;

/**
 * 配置管理器
 * @author hancong03@baidu.com
 *
 */
public final class ConfigManager {

	private String configFile = null;
	private JSONObject jsonConfig = null;
	// 涂鸦上传filename定义
	private final static String SCRAWL_FILE_NAME = "scrawl";
	// 远程图片抓取filename定义
	private final static String REMOTE_FILE_NAME = "remote";

	/*
	 * 通过一个给定的路径构建一个配置管理器， 该管理器要求地址路径所在目录下必须存在config.properties文件
	 */
	public ConfigManager (String config) {
		this.configFile = config;
		this.initEnv();
	}

	/**
	 * 配置管理器构造工厂
	 *
	 * @param configFile 配置文件
	 * @return 配置管理器实例或者null
	 */
	public static ConfigManager getInstance(String configFile) {
		try {
			return new ConfigManager(configFile);
		} catch (Exception e) {
			return null;
		}
	}


	// 验证配置文件加载是否正确
	public boolean valid () {
		return this.jsonConfig != null;
	}

	public JSONObject getAllConfig () {
		return this.jsonConfig;
	}

	public Map<String, Object> getConfig ( int type ) {

		Map<String, Object> conf = new HashMap<String, Object>();
		String savePath = null;

		switch ( type ) {

			case ActionMap.UPLOAD_FILE:
				conf.put( "isBase64", "false" );
				conf.put( "maxSize", this.jsonConfig.getLong( "fileMaxSize" ) );
				conf.put( "allowFiles", this.getArray( "fileAllowFiles" ) );
				conf.put( "fieldName", this.jsonConfig.getString( "fileFieldName" ) );
				savePath = this.jsonConfig.getString( "filePathFormat" );
				break;

			case ActionMap.UPLOAD_IMAGE:
				conf.put( "isBase64", "false" );
				conf.put( "maxSize", this.jsonConfig.getLong( "imageMaxSize" ) );
				conf.put( "allowFiles", this.getArray( "imageAllowFiles" ) );
				conf.put( "fieldName", this.jsonConfig.getString( "imageFieldName" ) );
				savePath = this.jsonConfig.getString( "imagePathFormat" );
				break;

			case ActionMap.UPLOAD_VIDEO:
				conf.put( "maxSize", this.jsonConfig.getLong( "videoMaxSize" ) );
				conf.put( "allowFiles", this.getArray( "videoAllowFiles" ) );
				conf.put( "fieldName", this.jsonConfig.getString( "videoFieldName" ) );
				savePath = this.jsonConfig.getString( "videoPathFormat" );
				break;

			case ActionMap.UPLOAD_SCRAWL:
				conf.put( "filename", ConfigManager.SCRAWL_FILE_NAME );
				conf.put( "maxSize", this.jsonConfig.getLong( "scrawlMaxSize" ) );
				conf.put( "fieldName", this.jsonConfig.getString( "scrawlFieldName" ) );
				conf.put( "isBase64", "true" );
				savePath = this.jsonConfig.getString( "scrawlPathFormat" );
				break;

			case ActionMap.CATCH_IMAGE:
				conf.put( "filename", ConfigManager.REMOTE_FILE_NAME );
				conf.put( "filter", this.getArray( "catcherLocalDomain" ) );
				conf.put( "maxSize", this.jsonConfig.getLong( "catcherMaxSize" ) );
				conf.put( "allowFiles", this.getArray( "catcherAllowFiles" ) );
				conf.put( "fieldName", this.jsonConfig.getString( "catcherFieldName" ) + "[]" );
				savePath = this.jsonConfig.getString( "catcherPathFormat" );
				break;

			case ActionMap.LIST_IMAGE:
				conf.put( "allowFiles", this.getArray( "imageManagerAllowFiles" ) );
				conf.put( "dir", this.jsonConfig.getString( "imageManagerListPath" ) );
				conf.put( "count", this.jsonConfig.getInteger( "imageManagerListSize" ) );
				break;

			case ActionMap.LIST_FILE:
				conf.put( "allowFiles", this.getArray( "fileManagerAllowFiles" ) );
				conf.put( "dir", this.jsonConfig.getString( "fileManagerListPath" ) );
				conf.put( "count", this.jsonConfig.getInteger( "fileManagerListSize" ) );
				break;

		}

		conf.put( "savePath", savePath );
//		conf.put( "rootPath", this.rootPath );

		return conf;

	}

	private void initEnv () {
		try{
			JSONObject jsonConfig = JSON.parseObject(this.configFile);
			this.jsonConfig = jsonConfig;
		} catch ( Exception e ) {
			this.jsonConfig = null;
		}

	}


	private String[] getArray ( String key ) {

		JSONArray jsonArray = this.jsonConfig.getJSONArray( key );
		String[] result = new String[ jsonArray.size() ];

		for ( int i = 0, len = jsonArray.size(); i < len; i++ ) {
			result[i] = jsonArray.getString( i );
		}

		return result;

	}
	
}
