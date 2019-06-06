package com.baidu.ueditor.upload;

import com.baidu.ueditor.define.AppInfo;
import com.baidu.ueditor.define.BaseState;
import com.baidu.ueditor.define.FileType;
import com.baidu.ueditor.define.State;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class BinaryUploader {

	public State save(HttpServletRequest request, Map<String, Object> conf) {
		if (!ServletFileUpload.isMultipartContent(request)) {
			return new BaseState(false, AppInfo.NOT_MULTIPART_CONTENT);
		}

		MultipartFile file = ((MultipartHttpServletRequest)request).getFile("upfile");

		String suffix = FileType.getSuffixByFilename(file.getOriginalFilename());

		long maxSize = ((Long) conf.get("maxSize")).longValue();
		if (!validType(suffix, (String[]) conf.get("allowFiles"))) {
			return new BaseState(false, AppInfo.NOT_ALLOW_FILE_TYPE);
		}

		try {
			State storageState = StorageManager.saveFileByInputStream(file.getInputStream(), file.getOriginalFilename(), maxSize);

			if (storageState.isSuccess()) {
				storageState.putInfo("type", suffix);
				storageState.putInfo("original", file.getOriginalFilename());
			}

			return storageState;
		} catch (IOException e) {
			return new BaseState(false, AppInfo.IO_ERROR);
		}
	}

	private boolean validType(String type, String[] allowTypes) {
		List<String> list = Arrays.asList(allowTypes);
		return list.contains(type);
	}
}
