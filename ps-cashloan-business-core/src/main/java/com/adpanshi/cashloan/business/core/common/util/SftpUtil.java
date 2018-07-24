package com.adpanshi.cashloan.business.core.common.util;


import com.adpanshi.cashloan.business.core.common.context.Global;
import com.jcraft.jsch.*;
import tool.util.NumberUtil;

import java.io.*;
import java.util.Properties;


/**
 * SFTP工具类
 * @author 8452
 */
public class SftpUtil {
	private static final org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(SftpUtil.class);

	private ChannelSftp channel;

	private Session session;

	/** sftp用户名 */
	private String userName;
	/** sftp密码 */
	private String password;
	/** sftp主机ip */
	private String sftpHost;
	/** sftp主机端口 */
	private int sftpPort;
	private String path;

	/**
	 * 默认构造方法
	 */
	public SftpUtil() {
	}

	/**
	 * 连接SFTP服务器
	 *
	 * @param userName
	 * @param password
	 * @param sftpHost
	 * @param sftpPort
	 */
	public SftpUtil(String userName, String password, String sftpHost, int sftpPort) throws JSchException {
		this.userName = userName;
		this.password = password;
		this.sftpHost = sftpHost;
		this.sftpPort = sftpPort;

		LOGGER.info("SFTP连接信息: " + "userName=" + userName + ", " + "password="
				+ password + ", " + "sftpHost=" + sftpHost + ", " + "sftpPort=" + sftpPort);

		connectServer();
	}

	/**
	 * 连接SFTP服务器
	 *
	 * @param userName
	 * @param password
	 * @param sftpHost
	 */
	public SftpUtil(String userName, String password, String sftpHost) throws JSchException {
		this(userName, password, sftpHost, 22);
	}

	public SftpUtil(String sftpConfigName) throws JSchException {
		String sftp = Global.getValue(sftpConfigName);

		LOGGER.info("sftp配置:" + sftp);

		this.sftpHost = JsonUtil.get(sftp, "host");
		this.sftpPort = NumberUtil.getInt(JsonUtil.get(sftp, "port"));
		this.userName = JsonUtil.get(sftp, "user");
		this.password = JsonUtil.get(sftp, "passwd");
		this.path = JsonUtil.get(sftp, "path");
		LOGGER.info("SFTP连接信息: " + "userName=" + userName + ", " + "password="
				+ password + ", " + "sftpHost=" + sftpHost + ", " + "sftpPort=" + sftpPort);
		connectServer();
	}

	/**
	 * 从SFTP上下载文件到本地
	 *
	 * @param sftp sftp工具类
	 * @param remotePath 远程服务器文件路径
	 * @param remoteFile sftp服务器文件名
	 * @param localFile 下载到本地的文件路径和名称
	 * @param localFile true 表示关闭连接 false 表示不关闭连接
	 * @return flag 下载是否成功, true-下载成功, false-下载失败
	 * @throws Exception
	 */
	public boolean downFile(SftpUtil sftp, String remotePath,
							String remoteFile, String localFile, boolean closeFlag) throws Exception {
		boolean flag = false;
		try {
			this.channel.cd(remotePath);
			InputStream input = this.channel.get(remoteFile);

			// 判断本地目录是否存在, 若不存在就创建文件夹
			if (localFile != null && !"".equals(localFile)) {
				File checkFileTemp = new File(localFile);
				if (!checkFileTemp.getParentFile().exists()) {
					// 创建文件夹, 如：在f盘创建/TXT文件夹/testTXT/两个文件夹
					checkFileTemp.getParentFile().mkdirs();
				}
			}

			FileOutputStream out = new FileOutputStream(new File(localFile));
			byte[] bt = new byte[1024];
			int length = -1;
			while ((length = input.read(bt)) != -1) {
				out.write(bt, 0, length);
			}
			input.close();
			out.close();
			if (closeFlag) {
				sftp.disconnect();
			}
			flag = true;
		} catch (SftpException e) {
			LOGGER.error("文件下载失败！", e);
			throw new RuntimeException("文件下载失败！");
		} catch (FileNotFoundException e) {
			LOGGER.error("下载文件到本地的路径有误！", e);
			throw new RuntimeException("下载文件到本地的路径有误！");
		} catch (IOException e) {
			LOGGER.error("文件写入有误！", e);
			throw new RuntimeException("文件写入有误！");
		}

		return flag;
	}

	/**
	 * 下载文件
	 *
	 * @param sftp
	 * @param remotePath 远程文件路径
	 * @param remoteFileName 远程文件名称
	 * @param localFilePath 本地文件路径
	 * @param localFileName 本地文件名称
	 * @param closeFlag 是否关闭SFTP连接true-关闭连接, false-不关闭连接
	 * @return 下载是否成功, true-下载成功, false-下载失败
	 */
	public boolean downFile(SftpUtil sftp, String remotePath, String remoteFileName,
							String localFilePath, String localFileName, boolean closeFlag) {
		boolean flag = false;
		try {
			this.channel.cd(remotePath);
			InputStream input = this.channel.get(remoteFileName);
			String localRemoteFile = localFilePath + remoteFileName;
			File checkFileTemp = null;
			// 判断本地目录是否存在, 若不存在就创建文件夹
			if (localRemoteFile != null && !"".equals(localRemoteFile)) {
				checkFileTemp = new File(localRemoteFile);
				if (!checkFileTemp.getParentFile().exists()) {
					// 创建文件夹, 如：在f盘创建/TXT文件夹/testTXT/两个文件夹
					checkFileTemp.getParentFile().mkdirs();
				}
			}

			FileOutputStream out = new FileOutputStream(new File(localRemoteFile));
			byte[] bt = new byte[1024];
			int length = -1;
			while ((length = input.read(bt)) != -1) {
				out.write(bt, 0, length);
			}
			input.close();
			out.close();
			if (closeFlag) {
				sftp.disconnect();
			}
			flag = true;

			// 下载后的文件重命名
			File upSupFile = new File(localFilePath + localFileName);
			checkFileTemp.renameTo(upSupFile);
		} catch (SftpException e) {
			LOGGER.error("文件下载失败！", e);
			throw new RuntimeException("文件下载失败！");
		} catch (FileNotFoundException e) {
			LOGGER.error("下载文件到本地的路径有误！", e);
			throw new RuntimeException("下载文件到本地的路径有误！");
		} catch (IOException e) {
			LOGGER.error("文件写入有误！", e);
			throw new RuntimeException("文件写入有误！");
		}

		return flag;
	}

	/**
	 * 上传文件到SFTP服务器
	 *
	 * @param remotePath sftp服务器路径
	 * @param fileName sftp服务器文件名
	 * @param input 本地文件流
	 * @param closeFlag 是否要关闭本次SFTP连接: true-关闭, false-不关闭
	 * @param filePathFlag 是否要创建远程的指定目录: true-创建, false-不创建
	 */
	public boolean upFile(String remotePath, String fileName, InputStream input,
						  boolean closeFlag, boolean filePathFlag) {
		boolean flag = false;
		try {
			// 判断是否要在远程目录上创建对应的目录
			if (filePathFlag) {
				String[] dirs = remotePath.split("\\/");
				String[] dirsTem = remotePath.split("\\\\");
				if(dirs != null && dirsTem!=null){
					if(dirs.length<dirsTem.length){
						dirs = dirsTem;
					}
				}
				this.channel.cd("/");
				String now = this.channel.pwd();
				for (int i = 0; i < dirs.length; i++) {
					if (dirs[i] != null && !"".equals(dirs[i])) {
						boolean dirExists = this.openDirs(dirs[i]);
						if (!dirExists) {
							this.channel.mkdir(dirs[i]);
							this.channel.cd(dirs[i]);
						}
					}
				}
				this.channel.cd(now);
			}

			this.channel.cd(remotePath);
			this.channel.put(input, fileName);
			LOGGER.info("文件上传成功，上传路径："+remotePath+"/"+fileName);
			flag = true;
		} catch (SftpException e) {
			LOGGER.error("文件上传失败！", e);
			throw new RuntimeException("文件上传失败！");
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (Exception localException1) {
					LOGGER.error("输入流关闭失败", localException1);
				}
			}
			// 判断是否要关闭本次SFTP连接
			if (closeFlag) {
				disconnect();
			}
		}

		return flag;
	}
	/**
	 * 上传文件到SFTP服务器
	 *
	 * @param remotePath sftp服务器路径
	 * @param fileName sftp服务器文件名
	 * @param localFile 本地文件路径和名称字符串
	 * @param closeFlag 是否要关闭本次SFTP连接: true-关闭, false-不关闭
	 * @param filePathFlag 是否要创建远程的指定目录: true-创建, false-不创建
	 * @throws IOException
	 */
	public boolean upFile(String remotePath, String fileName, String localFile,
						  boolean closeFlag, boolean filePathFlag) throws Exception {
		boolean flag = false;
		InputStream input = null;
		try {
			input = new FileInputStream(localFile);

			flag = upFile(remotePath, fileName, input, closeFlag, filePathFlag);
		} catch (FileNotFoundException e) {
			LOGGER.error("FileNotFoundException 上传文件找不到！", e);
			throw new RuntimeException("上传文件路径有误！");
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (Exception localException1) {
					LOGGER.error("输入流关闭失败", localException1);
				}
			}
			// 判断是否要关闭本次SFTP连接
			if (closeFlag) {
				disconnect();
			}
		}

		return flag;
	}

	/**
	 * 连接SFTP服务器
	 *
	 */
	private void connectServer() throws JSchException {
		if (this.channel != null) {
			disconnect();
		}

		JSch jsch = new JSch();

		LOGGER.info("SFTP连接正在创建Session... ...");
		this.session = jsch.getSession(this.userName, this.sftpHost, this.sftpPort);
		if(this.password!=null){
			this.session.setPassword(password);
		}
		LOGGER.info("SFTP连接Session创建成功");

		Properties configTemp = new Properties();
		configTemp.put("StrictHostKeyChecking", "no");
		// 为Session对象设置properties
		session.setConfig(configTemp);
		session.connect();
		LOGGER.info("SFTP连接正在打开SFTP通道... ...");
		Channel _channel = this.session.openChannel("sftp");
		LOGGER.info("SFTP连接通道打开成功");

		LOGGER.info("SFTP连接中... ...");
		_channel.connect();
		LOGGER.info("SFTP连接成功");

		this.channel = ((ChannelSftp) _channel);
	}

	/**
	 * 关闭连接
	 */
	private void disconnect() {
		if (this.channel != null) {
			this.channel.exit();
		}
		if (this.session != null) {
			this.session.disconnect();
		}
		this.session = null;
		this.channel = null;
	}

	/**
	 * 读取文件流
	 * @param remotePath
	 * @param remoteFile
	 * @return
	 * @throws Exception
	 */
	public InputStream downFile(String remotePath, String remoteFile) throws Exception {
		try {
			this.channel.cd(remotePath);
			return this.channel.get(remoteFile);
		} catch (SftpException e) {
			LOGGER.error("文件下载失败！", e);
			throw new Exception("文件下载失败", e);
		}
	}

	/**
	 * 读取文件流
	 * @param remotePathName
	 * @return
	 * @throws Exception
	 */
	public InputStream downFile(String remotePathName) throws Exception {
		int lastIndex = remotePathName.lastIndexOf("/");
		String remotePath = remotePathName.substring(0,lastIndex);
		String remoteFile = remotePathName.substring(lastIndex+1);
		return downFile(remotePath,remoteFile);
	}
	/**
	 * 打开指定目录
	 * @param directory directory
	 * @return 是否打开目录
	 */
	public boolean openDirs(String directory) {
		try {
			this.channel.cd(directory);
			return true;
		} catch (SftpException e) {
			return false;
		}
	}

	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
}