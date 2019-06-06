package com.baidu.ueditor.upload;

import com.baidu.ueditor.define.AppInfo;
import com.baidu.ueditor.define.BaseState;
import com.baidu.ueditor.define.FileType;
import com.baidu.ueditor.define.State;
import com.baidu.ueditor.util.QiNiuUtil;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StorageManager {
	public static final int BUFFER_SIZE = 8192;

	public StorageManager() {
	}

	public static State saveBinaryFile(String base64, String path) {
		State state = null ;
		String fileName = getFileName(path);
		String url = QiNiuUtil.upload(base64, fileName);
		if (url == null) {
			return new BaseState(false, AppInfo.FAILED_CREATE_FILE);
		}

		state = new BaseState(true);
		state.putInfo("url", url);

		return state;
	}

	public static State saveFileByInputStream(InputStream is, String path,
                                              long maxSize) {
		State state = null;

		File tmpFile = getTmpFile();

		byte[] dataBuf = new byte[ 2048 ];
		BufferedInputStream bis = new BufferedInputStream(is, StorageManager.BUFFER_SIZE);

		try {
			BufferedOutputStream bos = new BufferedOutputStream(
					new FileOutputStream(tmpFile), StorageManager.BUFFER_SIZE);

			int count = 0;
			while ((count = bis.read(dataBuf)) != -1) {
				bos.write(dataBuf, 0, count);
			}
			bos.flush();
			bos.close();

			if (tmpFile.length() > maxSize) {
				tmpFile.delete();
				return new BaseState(false, AppInfo.MAX_SIZE);
			}

			state = saveTmpFile(tmpFile, path);

			if (!state.isSuccess()) {
				tmpFile.delete();
			}

			return state;
			
		} catch (IOException e) {
		}
		return new BaseState(false, AppInfo.IO_ERROR);
	}

	public static State saveFileByInputStream(InputStream is, String path) {
		State state = null;

		File tmpFile = getTmpFile();

		byte[] dataBuf = new byte[ 2048 ];
		BufferedInputStream bis = new BufferedInputStream(is, StorageManager.BUFFER_SIZE);

		try {
			BufferedOutputStream bos = new BufferedOutputStream(
					new FileOutputStream(tmpFile), StorageManager.BUFFER_SIZE);

			int count = 0;
			while ((count = bis.read(dataBuf)) != -1) {
				bos.write(dataBuf, 0, count);
			}
			bos.flush();
			bos.close();

			state = saveTmpFile(tmpFile, path);

			if (!state.isSuccess()) {
				tmpFile.delete();
			}

			return state;
		} catch (IOException e) {
		}
		return new BaseState(false, AppInfo.IO_ERROR);
	}
	
	private static File getTmpFile() {
		File tmpDir = FileUtils.getTempDirectory();
		String tmpFileName = (Math.random() * 10000 + "").replace(".", "");
		return new File(tmpDir, tmpFileName);
	}

	private static State saveTmpFile(File tmpFile, String path) {
		State state = null ;
		String fileName = getFileName(path);
		String url = QiNiuUtil.upload(tmpFile, fileName);
		if (url == null) {
			return new BaseState(false, AppInfo.FAILED_CREATE_FILE);
		}

		state = new BaseState(true);
		state.putInfo("size", tmpFile.length() );
		state.putInfo("title", tmpFile.getName() );
		state.putInfo("url", url);
		return state;
	}

	private static State valid(File file) {
		File parentPath = file.getParentFile();

		if ((!parentPath.exists()) && (!parentPath.mkdirs())) {
			return new BaseState(false, AppInfo.FAILED_CREATE_FILE);
		}

		if (!parentPath.canWrite()) {
			return new BaseState(false, AppInfo.PERMISSION_DENIED);
		}

		return new BaseState(true);
	}

    private static String getFileName(String fileName) {
        String suffix =  FileType.getSuffixByFilename(fileName);
        return new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date())+ (int)(Math.random()*9000 +1000) + suffix;
    }
}
