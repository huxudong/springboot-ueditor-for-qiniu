## springboot+百度编辑器向七牛云存储上传图片资源<br>
1. #### 文件导入<br>
   * 新建springboot项目
   * 不需要下载本项目，jar包已经上传到maven仓库
   * pom文件引入 
      ``` 
           <dependency>
                       <groupId>com.hxd</groupId>
                       <artifactId>spring-boot-starter-ueditor-for-qiniu</artifactId>
                       <version>1.0</version>
                   </dependency>
      ```
2. #### 项目配置<br>
   * application.properties
      ```application.yml
         spring.servlet.multipart.enabled=true
         spring.servlet.multipart.max-file-size=500MB
         spring.servlet.multipart.max-request-size=20MB
         
         #服务器统一请求接口路径和ueditor.config.js中的serverUrl要一致
         ue.server-url=/ueditor/upload
         ue.config={"imageActionName":"uploadimage","imageFieldName":"upfile","imageMaxSize":2048000,"imageAllowFiles":[".png",".jpg",".jpeg",".gif",".bmp"],"imageCompressEnable":true,"imageCompressBorder":1600,"imageInsertAlign":"none","imageUrlPrefix":"","imagePathFormat":"/ueditor/jsp/upload/image/{yyyy}{mm}{dd}/{time}{rand:6}","scrawlActionName":"uploadscrawl","scrawlFieldName":"upfile","scrawlPathFormat":"/ueditor/jsp/upload/image/{yyyy}{mm}{dd}/{time}{rand:6}","scrawlMaxSize":2048000,"scrawlUrlPrefix":"","scrawlInsertAlign":"none","snapscreenActionName":"uploadimage","snapscreenPathFormat":"/ueditor/jsp/upload/image/{yyyy}{mm}{dd}/{time}{rand:6}","snapscreenUrlPrefix":"","snapscreenInsertAlign":"none","catcherLocalDomain":["127.0.0.1","localhost","img.baidu.com"],"catcherActionName":"catchimage","catcherFieldName":"source","catcherPathFormat":"/ueditor/jsp/upload/image/{yyyy}{mm}{dd}/{time}{rand:6}","catcherUrlPrefix":"","catcherMaxSize":2048000,"catcherAllowFiles":[".png",".jpg",".jpeg",".gif",".bmp"],"videoActionName":"uploadvideo","videoFieldName":"upfile","videoPathFormat":"/ueditor/jsp/upload/video/{yyyy}{mm}{dd}/{time}{rand:6}","videoUrlPrefix":"","videoMaxSize":102400000,"videoAllowFiles":[".flv",".swf",".mkv",".avi",".rm",".rmvb",".mpeg",".mpg",".ogg",".ogv",".mov",".wmv",".mp4",".webm",".mp3",".wav",".mid"],"fileActionName":"uploadfile","fileFieldName":"upfile","filePathFormat":"/ueditor/jsp/upload/file/{yyyy}{mm}{dd}/{time}{rand:6}","fileUrlPrefix":"","fileMaxSize":51200000,"fileAllowFiles":[".png",".jpg",".jpeg",".gif",".bmp",".flv",".swf",".mkv",".avi",".rm",".rmvb",".mpeg",".mpg",".ogg",".ogv",".mov",".wmv",".mp4",".webm",".mp3",".wav",".mid",".rar",".zip",".tar",".gz",".7z",".bz2",".cab",".iso",".doc",".docx",".xls",".xlsx",".ppt",".pptx",".pdf",".txt",".md",".xml"],"imageManagerActionName":"listimage","imageManagerListPath":"/ueditor/jsp/upload/image/","imageManagerListSize":20,"imageManagerUrlPrefix":"","imageManagerInsertAlign":"none","imageManagerAllowFiles":[".png",".jpg",".jpeg",".gif",".bmp"],"fileManagerActionName":"listfile","fileManagerListPath":"/ueditor/jsp/upload/file/","fileManagerUrlPrefix":"","fileManagerListSize":20,"fileManagerAllowFiles":[".png",".jpg",".jpeg",".gif",".bmp",".flv",".swf",".mkv",".avi",".rm",".rmvb",".mpeg",".mpg",".ogg",".ogv",".mov",".wmv",".mp4",".webm",".mp3",".wav",".mid",".rar",".zip",".tar",".gz",".7z",".bz2",".cab",".iso",".doc",".docx",".xls",".xlsx",".ppt",".pptx",".pdf",".txt",".md",".xml"]}
         ue.access-key=替换自己的
         ue.secret-key=替换自己的
         #七牛机房  华东：zone0 华北：zone1 华南：zone2 北美：zoneNa0
         # ueditor.zone也可不配置，会自动识别区域
         ue.zone=zone0
         #zrk-test 华东 (请改为自己七牛bucket及域名，否则图片无法查看和上传)
         ue.bucket=kangxun
         ue.upload-dir-prefix=ueditor/example/img/
         ue.cdn=替换自己的
      ```
      其中关于Zone对象和机房的关系如下：<br>
      
      | 机房           | Zone对象      | 
      | ------------- |:-------------:| 
      | 华东          | zone0         | 
      | 华北          | zone1         | 
      | 华南          | zone2         | 
      | 北美          | zoneNa0       | 
      | 东南亚        | zoneAs0       | 
   