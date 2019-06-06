package com.baidu.ueditor.upload;

import com.baidu.ueditor.PathFormat;
import com.baidu.ueditor.define.AppInfo;
import com.baidu.ueditor.define.BaseState;
import com.baidu.ueditor.define.FileType;
import com.baidu.ueditor.define.State;
import org.apache.commons.codec.binary.Base64;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public final class Base64Uploader {

	public State save(HttpServletRequest request, Map<String, Object> conf) {
		String filedName = (String) conf.get("fieldName");
		String base64 = request.getParameter(filedName);
		byte[] data = decode(base64);

		long maxSize = ((Long) conf.get("maxSize")).longValue();

		if (!validSize(data, maxSize)) {
			return new BaseState(false, AppInfo.MAX_SIZE);
		}

		String suffix = FileType.getSuffix("JPG");

		String savePath = PathFormat.parse((String) conf.get("savePath"),
				(String) conf.get("filename"));

		savePath = savePath + suffix;
		String rootPath = "";
		String physicalPath = rootPath + savePath;

		State storageState = StorageManager.saveBinaryFile(base64, physicalPath);

		if (storageState.isSuccess()) {
			storageState.putInfo("url", PathFormat.format(savePath));
			storageState.putInfo("type", suffix);
			storageState.putInfo("original", "");
		}

		return storageState;
	}

	private byte[] decode(String content) {
		return Base64.decodeBase64(content);
	}

	private boolean validSize(byte[] data, long length) {
		return data.length <= length;
	}
	
}