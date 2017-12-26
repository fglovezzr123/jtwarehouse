package com.wing.socialcontact.util;

import java.io.InputStream;
import java.util.Calendar;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSErrorCode;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.ServiceException;
import com.aliyun.oss.model.CannedAccessControlList;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import com.wing.socialcontact.config.OssConfig;

public class FileUploadUtil {
	
	public static final String PROJECT_NAME = "tojoy/tojoyClould";
	/**
	 * 设置缓存30天时间
	 * @param metadata
	 */
	private static void setCache(ObjectMetadata metadata) {
		metadata.setHeader("Cache-Control", "max-age=2592000");
	}

	/**
	 * 上傳oss
	 * @param io 文件流
	 * @param ext 文件後綴(例如.jpg .bmp .txt)
	 * @param path 文件路径(注意：不要以/或者\开头，例如:系统模块上传图片附件等，只需配置路径system,实际上传路径为tianjiu/system/年/月/日/uuid.ext)
	 * @return path(上传路径)
	 */
	public static String uploadFileInputStream(InputStream io, String ext,String path) {
		return FileUploadUtil.uploadFileInputStream(io,ext,path,"",2);
	}
	//type 1：音频   1：图片
	public static String uploadFileInputStream(InputStream io, String ext,String path,Integer type) {
		return FileUploadUtil.uploadFileInputStream(io,ext,path,"",type);
	}
	/**
	 * 上傳oss
	 * @param io 文件流
	 * @param ext 文件後綴(例如.jpg .bmp .txt)
	 * @param path 文件路径(注意：不要以/或者\开头，例如:系统模块上传图片附件等，只需配置路径system,实际上传路径为tianjiu/system/年-月/日/uuid.ext)
	 * @return path(上传路径)
	 */
	public static String uploadFileInputStream(InputStream io, String ext,String path,String proportion,Integer type) {
		if(StringUtils.isNotBlank(ext)){
			if(!ext.startsWith(".")){
				ext = "."+ext;
			}
		}
		String fileType = "image/";
		if(type==1){
			fileType="audio/";
		}
		OssConfig ossConfig = (OssConfig) SpringContextUtil.getBean("ossConfig");
		OSSClient client = new OSSClient(ossConfig.getOss_endpoint(), ossConfig.getOss_access_Key(), ossConfig.getOss_secret_Access_Key());
		// 创建Bucket
		ensureBucket(client, ossConfig.getOss_bucketName());
		String key = PROJECT_NAME+"/"+path +"/"+getCurrentDate()+fileType + UUID.randomUUID() + ext;
		if(!StringUtil.isEmpty(proportion)){
			key = PROJECT_NAME+"/"+path +"/"+getCurrentDate()+fileType  + UUID.randomUUID()+"whRatio="+proportion + ext;
		}
		ObjectMetadata objectMetadata = new ObjectMetadata();
		objectMetadata.setContentType(getcontentType(ext.replace(".","")));
		objectMetadata.setObjectAcl(CannedAccessControlList.PublicRead);
		setCache(objectMetadata);
		PutObjectResult por = client.putObject(new PutObjectRequest(ossConfig.getOss_bucketName(), key,io,objectMetadata));
		return key;
	}
	/**
	 * 获得当前年/月/日字符串
	 * @return
	 */
	public static String getCurrentDate(){
	    Calendar now = Calendar.getInstance();  
		return new StringBuffer().append(now.get(Calendar.YEAR))
				.append(now.get(Calendar.MONTH) + 1)
				.append("/")
				.append(now.get(Calendar.DAY_OF_MONTH))
				.append("/")
				.toString();
	}
	 /**
	   * Description: 判断OSS服务文件上传时文件的contentType
	   *
	   * @param FilenameExtension 文件后缀
	   * @return String
	   */
	  public static String getcontentType(String FilenameExtension) {
	    if (FilenameExtension.equalsIgnoreCase("bmp")) {
	      return "image/bmp";
	    }
	    if (FilenameExtension.equalsIgnoreCase("gif")) {
	      return "image/gif";
	    }
	    if (FilenameExtension.equalsIgnoreCase("jpeg") ||
	        FilenameExtension.equalsIgnoreCase("jpg") ||
	        FilenameExtension.equalsIgnoreCase("png")) {
	      return "image/jpeg";
	    }
	    if (FilenameExtension.equalsIgnoreCase("html")) {
	      return "text/html";
	    }
	    if (FilenameExtension.equalsIgnoreCase("txt")) {
	      return "text/plain";
	    }
	    if (FilenameExtension.equalsIgnoreCase("vsd")) {
	      return "application/vnd.visio";
	    }
	    if (FilenameExtension.equalsIgnoreCase("pptx") ||
	        FilenameExtension.equalsIgnoreCase("ppt")) {
	      return "application/vnd.ms-powerpoint";
	    }
	    if (FilenameExtension.equalsIgnoreCase("docx") ||
	        FilenameExtension.equalsIgnoreCase("doc")) {
	      return "application/msword";
	    }
	    if (FilenameExtension.equalsIgnoreCase("xml")) {
	      return "text/xml";
	    }
	    return "image/jpeg";
	  }
	  
	  /**
	     * 创建Bucket
	     *
	     * @param client  OSSClient对象
	     * @param bucketName  BUCKET名
	     * @throws OSSException
	     * @throws ClientException
	     */
	    public static void ensureBucket(OSSClient client, String bucketName)throws OSSException, ClientException{
	        try{
	            client.createBucket(bucketName);
	        }catch(ServiceException e){
	            if(!OSSErrorCode.BUCKET_ALREADY_EXISTS.equals(e.getErrorCode())){
	                throw e;
	            }
	        }
	    }
	    
	    /**
	     * 把Bucket设置成所有人可读
	     *
	     * @param client  OSSClient对象
	     * @param bucketName  Bucket名
	     * @throws OSSException
	     * @throws ClientException
	     */
	    private static void setBucketPublicReadable(OSSClient client, String bucketName)throws OSSException, ClientException{
	        //创建bucket
	        client.createBucket(bucketName);
	        //设置bucket的访问权限， public-read-write权限
	        client.setBucketAcl(bucketName, CannedAccessControlList.PublicRead);
	    }
}
